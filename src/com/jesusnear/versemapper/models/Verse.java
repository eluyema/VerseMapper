package com.jesusnear.versemapper.models;

import java.util.ArrayList;
import java.util.List;

public class Verse {
    private int book;
    private int chapter;
    private int verse;

    private List<VerseTranslation> verseTranslation;

    public Verse(int book, int chapter, int verse) {
        this.book = book;
        this.chapter = chapter;
        this.verse = verse;
        this.verseTranslation = new ArrayList<>();
    }

    public String generateUniqVerseCode() {
        return book + "-" + chapter + "-" + verse;
    }

    public Integer getBook() {
        return book;
    }

    public int getChapter() {
        return chapter;
    }

    public int getVerse() {
        return verse;
    }

    public List<VerseTranslation> getVerseTranslation() {
        return verseTranslation;
    }
}
