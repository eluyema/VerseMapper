package com.jesusnear.versemapper.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.jesusnear.versemapper.models.NoticedVerse;
import com.jesusnear.versemapper.models.Verse;
import com.jesusnear.versemapper.models.VerseTranslation;

public class NoticedVerseRepository {
    private final Connection con;

    public NoticedVerseRepository(Connection connection) {
        this.con = connection;
    }

    public void saveNoticedVerses(List<NoticedVerse> verses) {
        String insertNoticedVerseSql = "INSERT INTO NOTICED_VERSES (BOOK_ID, CHAPTER, VERSE) VALUES (?, ?, ?)";

        try (PreparedStatement insertNoticedVerse = con.prepareStatement(insertNoticedVerseSql))
                {
                    con.setAutoCommit(false);
                    for(int i = 0; i< verses.size(); i++) {
                            NoticedVerse verse = verses.get(i);
                            insertNoticedVerse.setInt(1, verse.book());
                            insertNoticedVerse.setInt(2, verse.chapter());
                            insertNoticedVerse.setInt(3, verse.verse());
                            insertNoticedVerse.executeUpdate();
                            if(i%100 == 0) {
                                System.out.println("Inserted " + i + " noticed verses from " + verses.size());
                            }
                    }
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
