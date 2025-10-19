package com.jesusnear.versemapper.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.jesusnear.versemapper.models.Verse;
import com.jesusnear.versemapper.models.VerseTranslation;

public class VerseRepository {
    private final Connection con;

    public VerseRepository(Connection connection) {
        this.con = connection;
    }

    public void saveVerses(List<Verse> verses) {
        String insertVerseSql = "INSERT INTO VERSES (BOOK_ID, CHAPTER, VERSE) VALUES (?, ?, ?)";
        String insertVerseTranslationSql = """
            INSERT INTO VERSE_TRANSLATIONS (LANGUAGE, NAME, CONTENT, BOOK_ID, CHAPTER, VERSE) VALUES (?, ?, ?, ?, ?, ?) 
        """;
            try (PreparedStatement insertVerse = con.prepareStatement(insertVerseSql);
                    PreparedStatement insertVerseTranslation = con.prepareStatement(insertVerseTranslationSql))
                
                {
                    con.setAutoCommit(false);
                    for(int i = 0; i< verses.size(); i++) {
                            Verse verse = verses.get(i);
                            insertVerse.setInt(1, verse.getBook());
                            insertVerse.setInt(2, verse.getChapter());
                            insertVerse.setInt(3, verse.getVerse());
                            insertVerse.addBatch();
                            
                            List<VerseTranslation> translations = verse.getVerseTranslation();

                            for(int j = 0; j < translations.size(); j++) {
                                VerseTranslation vt = translations.get(j);
                                insertVerseTranslation.setString(1, vt.getLanguage());
                                insertVerseTranslation.setString(2, vt.getTranslationName());
                                insertVerseTranslation.setString(3, vt.getText());
                                insertVerseTranslation.setInt(4, verse.getBook());
                                insertVerseTranslation.setInt(5, verse.getChapter());
                                insertVerseTranslation.setInt(6, verse.getVerse());
                                insertVerseTranslation.addBatch();
                            }

                            if(i%1000 == 0) {
                                System.out.println("Inserted to batch " + i + " verses from " + verses.size());
                            }
                    }
                    System.out.println("Execute Verses Batch...");
                    int[] updateVerseCounts = insertVerse.executeBatch();
                
                    System.out.println("Execute Verses Translations Batch...");
                    int[] updateVerseTranslationsCounts = insertVerseTranslation.executeBatch();
                    System.out.println("Committing transaction...");
                    con.commit(); 
                    System.out.println("Transaction committed!!!");
                }catch (SQLException e) {
                    try {
                        con.rollback();
                    } catch (SQLException ex) {
                        e.addSuppressed(ex);
                    }
                    throw new RuntimeException(e);
                }
        }

}
