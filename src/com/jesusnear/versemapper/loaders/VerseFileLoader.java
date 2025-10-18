package com.jesusnear.versemapper.loaders;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesusnear.versemapper.dto.BibleDto;
import com.jesusnear.versemapper.models.PlainVerse;
import com.jesusnear.versemapper.models.Verse;
import com.jesusnear.versemapper.models.VerseTranslation;

public class VerseFileLoader implements VersesLoader {
    public List<PlainVerse> loadVersesFromFile(String filePath, String language) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        BibleDto data = mapper.readValue(new File(filePath), BibleDto.class);
        
        return data.getVerses().stream()
            .map(v -> new PlainVerse(
                v.book(),
                v.chapter(),
                v.verse(),
                language,
                (String) data.getMetadata().get("shortname"),
                v.text()
            ))
            .toList();
    }
}
