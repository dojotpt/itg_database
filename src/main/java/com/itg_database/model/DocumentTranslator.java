package com.itg_database.model;

import ai.djl.ndarray.NDList;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.modality.cv.Image;

public class DocumentTranslator implements Translator<Image, Detections> {
    @Override
    public Detections processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
        return null;
    }

    @Override
    public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
        return null;
    }

    public DocumentTranslator() {
    }
}
