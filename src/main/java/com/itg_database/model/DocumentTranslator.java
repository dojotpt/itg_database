package com.itg_database.model;

import ai.djl.ndarray.NDList;
import ai.djl.translate.TranslatorContext;

import java.awt.*;

public class DocumentTranslator implements ai.djl.translate.Translator<java.awt.Image, com.itg_database.model.Detections> {
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
