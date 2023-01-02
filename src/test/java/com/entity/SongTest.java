package com.entity;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class SongTest {
    final int[] duration = {3, 59};
    final Timestamp dateTime = new Timestamp(System.currentTimeMillis());
    final User user1 = new User("Glass Animals", "20220716");
    final String user1name = user1.getUsername();
    final String lyrics1 = "Heat waves been faking me out\n" + "Can't make you happier now";
    final Song song1 = new Song(201, "Heat Waves", duration, user1name, dateTime, 1000000, 3208546,
            lyrics1, true);

    @Test
    public void getId() {
        assertEquals(201, song1.getId());
    }

    @Test
    public void getName() {
        assertEquals("Heat Waves", song1.getName());
    }

    @Test
    public void setName() {
        song1.setName("STAY");
        assertEquals("STAY", song1.getName());
    }

    @Test
    public void getDuration() {
        assertEquals(3, song1.getDuration()[0]);
        assertEquals(59, song1.getDuration()[1]);
    }

    @Test
    public void setDuration() {
        int[] duration2 = {2, 22};
        song1.setDuration(duration2);
        assertEquals(2, song1.getDuration()[0]);
        assertEquals(22, song1.getDuration()[1]);
    }

    @Test
    public void getArtistUsername() {
        assertEquals("Glass Animals", song1.getArtistUsername());
    }

    @Test
    public void setArtistUsername() {
        song1.setArtistUsername("The Kid LAROI");
        assertEquals("The Kid LAROI", song1.getArtistUsername());
    }

    @Test
    public void getDateTimeCreated() {
        assertEquals(dateTime, song1.getDateTimeCreated());
    }

    @Test
    public void setDateTimeCreated() {
        Timestamp today = new Timestamp(100);
        song1.setDateTimeCreated(today);
        assertEquals(today, song1.getDateTimeCreated());
    }

    @Test
    public void getNumLikes() {
        assertEquals(1000000, song1.getNumLikes());
    }

    @Test
    public void setNumLikes() {
        song1.setNumLikes(0);
        assertEquals(0, song1.getNumLikes());
    }

    @Test
    public void getNumListens() {
        assertEquals(3208546, song1.getNumListens());
    }

    @Test
    public void setNumListens() {
        song1.setNumListens(2479893);
        assertEquals(2479893, song1.getNumListens());
    }

    @Test
    public void getLyrics() {
        assertEquals(lyrics1, song1.getLyrics());
    }

    @Test
    public void setLyrics() {
        String lyrics2 = "I need you to stay, need you to stay, hey";
        song1.setLyrics(lyrics2);
        assertEquals(lyrics2, song1.getLyrics());
    }

    @Test
    public void isPublic() {
        assertTrue(song1.isPublic());
    }

    @Test
    public void setPublic() {
        song1.setPublic(false);
        assertFalse(song1.isPublic());
    }
}