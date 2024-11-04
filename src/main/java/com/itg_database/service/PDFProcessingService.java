package com.itg_database.service;

import com.itg_database.exception.PDFProcessingException;
import com.itg_database.model.Detections;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j

public class PDFProcessingService {
private final DocumentLayoutService documentLayoutService;

    public PDFProcessingService(DocumentLayoutService documentLayoutService) {
        this.documentLayoutService = documentLayoutService;
        log.info("PDFProcessingService created");
    }

    public Map<Integer, Detections> processPDF(File pdfFile) throws PDFProcessingException {
        // initialize results map
        Map<Integer, Detections> pageResults = new HashMap<>();
        log.info("PDFProcessingService started: {}", pdfFile.getName());

        try {
            validatePDF(pdfFile);

            // load pdf with try-with-resources
            try (PDDocument document = Loader.loadPDF(pdfFile)) {
                //get total # of pages
                int totalPages = document.getNumberOfPages();
                log.info("PDF contains {} pages", totalPages);

                //process each page
                for (int pageNum = 0; pageNum < totalPages; pageNum++) {
                    try {
                        //convert current page to image for processing
                        Image pageImage = convertPDFPageToImage(document, pageNum);

                        // use DocumentLayoutService to analyze page
                        Detections pageDetections = documentLayoutService.analyzeLayout(pageImage);

                        // store in map with page number
                        pageResults.put(pageNum + 1, pageDetections);

                        log.info("Processed page {}/{}", pageNum + 1, totalPages);
                    } catch (Exception e) {
                        log.error("Failed to process page {}: {}", pageNum + 1, e.getMessage());
                    }
                }
                log.info("Successfully processed {} out of {} pages", pageResults.size(), totalPages);

            } // document closes due to try-with-resources
        } catch (IOException e) {
            log.error("PDFProcessingService failed: {}", pdfFile.getName(), e);
            throw new PDFProcessingException("PDFProcessingService failed: " + e.getMessage(), e);
        }
        return pageResults;
    }

    private Image convertPDFPageToImage(PDDocument document, int pageNum) throws IOException {
        // used for document conversion
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // set dpi for quality
        float dpi = 300;

        // convert page to bufferedImage
        BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(pageNum, dpi);

        // convert to DJL Format
        return ImageFactory.getInstance().fromImage(bufferedImage);
    }

    private void validatePDF(File pdfFile) throws PDFProcessingException {
        if (pdfFile == null) {
            throw new PDFProcessingException("PDF File cannot be null");
        }
        if (!pdfFile.exists()) {
            throw new PDFProcessingException("PDF file does not exist " + pdfFile.getPath());
        }
        if (!pdfFile.canRead()) {
            throw new PDFProcessingException("PDF file is not readable: " + pdfFile.getPath());
        }
        if (!pdfFile.getName().toLowerCase().endsWith(".pdf")) {
            throw new PDFProcessingException("File is not a PDF: "+ pdfFile.getName());
        }
        try {
            Loader.loadPDF(pdfFile).close();
        } catch (IOException e) {
            throw new PDFProcessingException("Invalid or corrupted PDF file: " + pdfFile.getName());
        }
    }
}