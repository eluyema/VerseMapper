package com.jesusnear.versemapper.dto;

import java.util.List;
import java.util.Map;

public class BibleDto {
    public record VerseDto(String book_name, Integer book, Integer chapter, Integer verse, String text) {
    }

    public BibleDto() {

    }

    private Map<String, Object> metadata;

    private List<VerseDto> verses;

    public List<VerseDto> getVerses() {
        return verses;
    }

    public void setVerses(List<VerseDto> verses) {
        this.verses = verses;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
