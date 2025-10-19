package com.jesusnear.versemapper.loaders;

import java.io.IOException;
import java.util.List;

import com.jesusnear.versemapper.models.PlainVerse;

public interface VersesLoader {
    public List<PlainVerse> loadVersesFromFile(String filePath) throws IOException;
}
