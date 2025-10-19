package com.jesusnear.versemapper.loaders;

import java.util.List;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesusnear.versemapper.dto.NoticedVersesDTO;
import com.jesusnear.versemapper.models.NoticedVerse;

public class NoticedVerseFileLoader {
    public List<NoticedVerse> loadNoticedVersesFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        NoticedVersesDTO data = mapper.readValue(new File(filePath), NoticedVersesDTO.class);
        
        return data.getVerses().stream()
            .map(v -> new NoticedVerse(
                v.book(),
                v.chapter(),
                v.verse()
            ))
            .toList();
    }
}
