package com.jesusnear.versemapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jesusnear.versemapper.loaders.VerseFileLoader;
import com.jesusnear.versemapper.loaders.VersesLoader;
import com.jesusnear.versemapper.models.Verse;
import com.jesusnear.versemapper.repositories.VerseRepository;
import com.jesusnear.versemapper.services.VersesService;
import com.jesusnear.versemapper.utils.JDBCUtilities;

class PopulateVerses {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Map<String, String> loadBibleProperties(String propertiesFilePath) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(propertiesFilePath);
        prop.loadFromXML(fis);

        return (Map) prop;
    }

    public static void main(String[] args) throws IOException, SQLException {
        if (args[0] == null || args[1] == null) {
            System.err.println("Properties file not specified at command line");
            return;
        }
        
        Map<String, String> LANGUAGE_FILE_MAP = loadBibleProperties(args[1]);
        
        JDBCUtilities JDBCUtilitiesService = new JDBCUtilities(args[0]);
        
        Connection connection = JDBCUtilitiesService.getConnection();

        VersesLoader loader = new VerseFileLoader();
        VersesService versesService = new VersesService(loader);

        List<Verse> verses = versesService.getVerses(LANGUAGE_FILE_MAP);

        VerseRepository verseRepository = new VerseRepository(connection);

        verseRepository.saveVerses(verses);
    }
}