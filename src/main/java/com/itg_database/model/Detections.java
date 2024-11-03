package com.itg_database.model;

import java.util.List;

public class Detections {
    private List<DocumentSection> sections;

    public Detections(List<DocumentSection> sections) {
        this.sections = sections;
    }

    public Detections() {
    }

    public List<DocumentSection> getSections() {
        return sections;
    }

    public void setSections(List<DocumentSection> sections) {
        this.sections = sections;
    }
}
