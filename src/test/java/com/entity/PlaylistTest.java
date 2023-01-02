package com.entity;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlaylistTest {
    final Timestamp dateTime = new Timestamp(System.currentTimeMillis());
    final ArrayList<Integer> playlist1Songs = new ArrayList<>();
    final Playlist playlist1 = new Playlist(101, "Favorites", "Songs you like will appear here!",
            "testUser1", dateTime, true, playlist1Songs, 10);

    @Test
    public void getId() {
        assertEquals(101, playlist1.getId());
    }

    @Test
    public void getName() {
        assertEquals("Favorites", playlist1.getName());
    }

    @Test
    public void setName() {
        playlist1.setName("Top 50 - Global");
        assertEquals("Top 50 - Global", playlist1.getName());
    }

    @Test
    public void getDescription() {
        assertEquals("Songs you like will appear here!", playlist1.getDescription());
    }

    @Test
    public void setDescription() {
        playlist1.setDescription("Your daily update of the most played tracks right now!");
        assertEquals("Your daily update of the most played tracks right now!", playlist1.getDescription());
    }

    @Test
    public void getCreatorUsername() {
        assertEquals("testUser1", playlist1.getCreatorUsername());
    }

    @Test
    public void setCreatorUsername() {
        playlist1.setCreatorUsername("testUser2");
        assertEquals("testUser2", playlist1.getCreatorUsername());
    }

    @Test
    public void getDateTimeCreated() {
        assertEquals(dateTime, playlist1.getDateTimeCreated());
    }

    @Test
    public void setDateTimeCreated() {
        Timestamp dateTime2 = new Timestamp(120);
        playlist1.setDateTimeCreated(dateTime2);
        assertEquals(dateTime2, playlist1.getDateTimeCreated());
    }

    @Test
    public void isPublic() {
        assertTrue(playlist1.isPublic());
    }

    @Test
    public void setPublic() {
        playlist1.setPublic(false);
        assertFalse(playlist1.isPublic());
    }

    @Test
    public void getSongs() {
        assertEquals(playlist1Songs, playlist1.getSongs());
    }

    @Test
    public void setSongs() {
        ArrayList<Integer> playlist1Songs2 = new ArrayList<>();
        playlist1Songs2.add(200);
        playlist1.setSongs(playlist1Songs2);
        assertEquals(playlist1Songs2, playlist1.getSongs());
        int songId = playlist1.getSongs().get(0);
        assertEquals(200, songId);
    }

    @Test
    public void getNumLikes() {
        assertEquals(10, playlist1.getNumLikes());
    }

    @Test
    public void setNumLikes() {
        playlist1.setNumLikes(0);
        assertEquals(0, playlist1.getNumLikes());
    }

    @Test
    public void numSongs() {
        ArrayList<Integer> playlist1Songs2 = new ArrayList<>();
        playlist1Songs2.add(299);
        playlist1Songs2.add(298);
        playlist1Songs2.add(297);
        playlist1.setSongs(playlist1Songs2);
        assertEquals(3, playlist1.numSongs());
    }
}