package com.useCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QueueTest {

    private static Queue queue;
    @Before
    public void before() {
        ArrayList<Integer> recentlyPlayed = new ArrayList<>(List.of(201, 202, 203));
        ArrayList<Integer> upcomingSongs = new ArrayList<>(List.of(204, 205, 206));
        queue = new Queue(upcomingSongs, recentlyPlayed);
    }

    @Test
    public void getNowPlaying() {
        assertEquals(0, queue.getNowPlaying());
    }

    @Test
    public void setNowPlaying() {
        queue.setNowPlaying(207);
        assertEquals(207, queue.getNowPlaying());
    }

    @Test
    public void getUpcomingSongs() {
        assertEquals(3, queue.getUpcomingSongs().size());
        int songID =  queue.getUpcomingSongs().get(0);
        assertEquals(204, songID);
        assertTrue(queue.getUpcomingSongs().contains(205));
        assertTrue(queue.getUpcomingSongs().contains(206));
        assertFalse(queue.getUpcomingSongs().contains(207));
    }

    @Test
    public void setUpcomingSongs() {
        ArrayList<Integer> upcomingSongsNew = new ArrayList<>(List.of(207, 208));
        queue.setUpcomingSongs(upcomingSongsNew);
        assertEquals(2, queue.getUpcomingSongs().size());
        int songID =  queue.getUpcomingSongs().get(0);
        assertEquals(207, songID);
        assertTrue(queue.getUpcomingSongs().contains(208));
        assertFalse(queue.getUpcomingSongs().contains(209));
    }

    @Test
    public void getRecentlyPlayedSongs() {
        assertEquals(3, queue.getRecentlyPlayedSongs().size());
        int songID =  queue.getRecentlyPlayedSongs().get(0);
        assertEquals(201, songID);
        assertTrue(queue.getRecentlyPlayedSongs().contains(202));
        assertTrue(queue.getRecentlyPlayedSongs().contains(203));
        assertFalse(queue.getRecentlyPlayedSongs().contains(204));
    }

    @Test
    public void setRecentlyPlayedSongs() {
        ArrayList<Integer> recentlyPlayedSongsNew = new ArrayList<>(List.of(209, 210));
        queue.setRecentlyPlayedSongs(recentlyPlayedSongsNew);
        assertEquals(2, queue.getRecentlyPlayedSongs().size());
        int songID =  queue.getRecentlyPlayedSongs().get(0);
        assertEquals(209, songID);
        assertTrue(queue.getRecentlyPlayedSongs().contains(210));
        assertFalse(queue.getRecentlyPlayedSongs().contains(211));
    }

    @Test
    public void addToQueue() {
        queue.addToQueue(207, 1);
        assertEquals(4, queue.getUpcomingSongs().size());
        assertTrue(queue.getUpcomingSongs().contains(207));
        int songID =  queue.getUpcomingSongs().get(1);
        assertEquals(207, songID);
    }

    @Test
    public void removeFromQueue() {
        queue.removeFromQueue(2);
        assertEquals(2, queue.getUpcomingSongs().size());
        assertTrue(queue.getUpcomingSongs().contains(204));
        assertTrue(queue.getUpcomingSongs().contains(205));
        assertFalse(queue.getUpcomingSongs().contains(206));
    }

    @Test
    public void clearQueue() {
        queue.clearQueue();
        assertEquals(0, queue.getUpcomingSongs().size());
        assertFalse(queue.getUpcomingSongs().contains(204));
        assertFalse(queue.getUpcomingSongs().contains(205));
        assertFalse(queue.getUpcomingSongs().contains(206));
    }

    @Test
    public void popFromQueue() {
        queue.setNowPlaying(207);
        queue.popFromQueue();
        assertNotEquals(207, queue.getNowPlaying());
        assertEquals(204, queue.getNowPlaying());
        assertEquals(2, queue.getUpcomingSongs().size());
        assertEquals(4, queue.getRecentlyPlayedSongs().size());
        assertTrue(queue.getRecentlyPlayedSongs().contains(207));
        assertFalse(queue.getUpcomingSongs().contains(204));
    }

    @Test
    public void addToQueueEnd() {
        queue.addToQueueEnd(207);
        assertEquals(4, queue.getUpcomingSongs().size());
        assertTrue(queue.getUpcomingSongs().contains(207));
        int songID = queue.getUpcomingSongs().get(3);
        assertEquals(207, songID);
    }
}