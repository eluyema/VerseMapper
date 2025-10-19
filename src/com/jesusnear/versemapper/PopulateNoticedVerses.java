package com.jesusnear.versemapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.jesusnear.versemapper.loaders.NoticedVerseFileLoader;
import com.jesusnear.versemapper.models.NoticedVerse;
import com.jesusnear.versemapper.repositories.NoticedVerseRepository;
import com.jesusnear.versemapper.utils.JDBCUtilities;

class PopulateNoticedVerses {
    public static void main(String[] args) throws IOException, SQLException {
        if (args[0] == null || args[1] == null) {
            System.err.println("Properties file not specified at command line");
            return;
        }

        String pathTOFile = args[1];
        
        JDBCUtilities JDBCUtilitiesService = new JDBCUtilities(args[0]);
        
        Connection connection = JDBCUtilitiesService.getConnection();

        NoticedVerseFileLoader loader = new NoticedVerseFileLoader();



        List<NoticedVerse> verses = loader.loadNoticedVersesFromFile(pathTOFile);

        NoticedVerseRepository verseRepository = new NoticedVerseRepository(connection);

        verseRepository.saveNoticedVerses(verses);
    }
}