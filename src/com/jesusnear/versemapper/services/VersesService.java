package com.jesusnear.versemapper.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jesusnear.versemapper.loaders.VersesLoader;
import com.jesusnear.versemapper.models.PlainVerse;
import com.jesusnear.versemapper.models.Verse;
import com.jesusnear.versemapper.models.VerseTranslation;

public class VersesService {
    private final VersesLoader loader;

    public VersesService(VersesLoader loader) {
        this.loader = loader;
    }

    public List<Verse> getVerses(Map<String, String> mapFilePathToLanguage) throws IOException {
        List<PlainVerse> plainVerses = new ArrayList<>();

        for(Map.Entry<String, String> entry : mapFilePathToLanguage.entrySet()) {
            String language = entry.getKey();
            String filePath = entry.getValue();
            List<PlainVerse> loadedVerses = loader.loadVersesFromFile(filePath, language);
            plainVerses.addAll(loadedVerses);
        }

        Map<String, Verse> verseCodeToVerse = new java.util.HashMap<>();

        plainVerses.stream()
            .forEach(v -> {
                Verse verse = new Verse(
                    v.book(),
                    v.chapter(),
                    v.verse()
                );

                VerseTranslation verseTranslation = new VerseTranslation(
                    v.text(),
                    v.translationName(),
                    v.language()
                );

                String verseCode = verse.generateUniqVerseCode();

                if(verseCodeToVerse.containsKey(verseCode)) {
                    verseCodeToVerse.get(verseCode)
                        .getVerseTranslation()
                        .add(verseTranslation);
                } else {
                    verse.getVerseTranslation().add(verseTranslation);
                    verseCodeToVerse.put(verseCode, verse);
                }
            });

        return verseCodeToVerse.values().stream().toList();
    }
}
