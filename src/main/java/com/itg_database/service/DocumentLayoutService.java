package com.itg_database.service;

import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import com.itg_database.model.Detections;
import com.itg_database.model.DocumentTranslator;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@Slf4j
public class DocumentLayoutService {
    private Predictor<Image, Detections> predictor;
    private Criteria<Image, Detections> criteria;

@PostConstruct
    public void initializeModel() {
    // criteria for LayoutLM
    criteria = Criteria.builder()
            .optEngine("PyTorch")                       // backend
            .setTypes(Image.class, Detections.class)    //input/outputs
            .optModelUrls("path/to/model")              //model location
            .optTranslator(new DocumentTranslator())    //conversions
            .build();

}
}
