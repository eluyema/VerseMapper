package com.jesusnear.versemapper;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jesusnear.versemapper.loaders.VerseFileLoader;
import com.jesusnear.versemapper.loaders.VersesLoader;
import com.jesusnear.versemapper.models.Verse;
import com.jesusnear.versemapper.repositories.VerseRepository;
import com.jesusnear.versemapper.services.VersesService;
import com.jesusnear.versemapper.utils.JDBCUtilities;

class PopulateVerses {

    private static final Map<String, String> LANGUAGE_FILE_MAP = Map.of(
        "en", "D:\\study\\VerseMapper\\data\\bibles\\EN\\web.json",
        "es", "D:\\study\\VerseMapper\\data\\bibles\\ES\\rv_1909.json",
        "pt", "D:\\study\\VerseMapper\\data\\bibles\\PT\\blivre.json",
        "fr", "D:\\study\\VerseMapper\\data\\bibles\\FR\\segond_1910.json",
        "de", "D:\\study\\VerseMapper\\data\\bibles\\DE\\luther_1912.json",
        "ru", "D:\\study\\VerseMapper\\data\\bibles\\RU\\synodal.json"
    );


    public static void main(String[] args) throws IOException, SQLException {
        if (args[0] == null) {
            System.err.println("Properties file not specified at command line");
            return;
        }
        
        JDBCUtilities JDBCUtilitiesService = new JDBCUtilities(args[0]);
        
        Connection connection = JDBCUtilitiesService.getConnection();

        VersesLoader loader = new VerseFileLoader();
        VersesService versesService = new VersesService(loader);

        List<Verse> verses = versesService.getVerses(LANGUAGE_FILE_MAP);

        VerseRepository verseRepository = new VerseRepository(connection);

        verseRepository.saveVerses(verses);
    }
}