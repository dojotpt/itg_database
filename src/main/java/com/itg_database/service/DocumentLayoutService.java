package com.itg_database.service;


import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import com.itg_database.exception.DocumentProcessingException;
import com.itg_database.model.Detections;
import com.itg_database.model.DocumentTranslator;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import com.itg_database.exception.ModelInitializationException;
import org.springframework.stereotype.Service;
import ai.djl.modality.cv.Image;

@Service
@Slf4j
public class DocumentLayoutService {
    private Predictor<Image, Detections> predictor;
    private Criteria<Image, Detections> criteria;

    @PostConstruct
    public void initializeModel() {
        // criteria for LayoutLM
        try {
            criteria = Criteria.builder()
                    .optEngine("PyTorch")                       // backend
                    .setTypes(Image.class, Detections.class)    //input/outputs
                    .optModelUrls("path/to/model")              //model location
                    .optTranslator(new DocumentTranslator())    //conversions
                    .build();
            predictor = criteria.loadModel().newPredictor();
        } catch (Exception e) {
            log.error("Failed to load model", e);
            throw new ModelInitializationException("Failed to load model", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (predictor != null) {
            predictor.close();
        }
    }
    public Detections analyzeLayout(Image document) throws DocumentProcessingException {
       try {
           return predictor.predict(document);
       } catch (Exception e) {
           throw new DocumentProcessingException("Error processing Document", e);
       }
       }
    }

