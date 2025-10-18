package com.jesusnear.versemapper.models;

public class VerseTranslation {
    private String text;
    private String translationName;
    private String language;

    public VerseTranslation(String text, String translationName, String language) {
        this.text = text;
        this.translationName = translationName;
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public String getTranslationName() {
        return translationName;
    }

    public void setTranslationName(String translationName) {
        this.translationName = translationName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
