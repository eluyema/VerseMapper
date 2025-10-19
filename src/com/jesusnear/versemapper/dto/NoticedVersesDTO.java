package com.jesusnear.versemapper.dto;

import java.util.List;

import com.jesusnear.versemapper.models.NoticedVerse;

public class NoticedVersesDTO {
    private List<NoticedVerse> verses;


    public NoticedVersesDTO() {

    }
    public List<NoticedVerse> getVerses() {
        return verses;
    }
    public void setVerses(List<NoticedVerse> verses) {
        this.verses = verses;
    }
}
