package com.itg_database.model;

public class DocumentSection {
    private String type; // document type: article, review, etc
    private String content; // actual text
    private BoundingBox location; // position in document

    public DocumentSection(String type, String content, BoundingBox location) {
        this.type = type;
        this.content = content;
        this.location = location;
    }
    public DocumentSection() {
    }
}
