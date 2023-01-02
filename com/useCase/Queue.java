package com.useCase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class responsible for storing songs that have played, to be played, and is currently playing.
 */

public class Queue implements Serializable {

    private int nowPlaying = 0;
    private ArrayList<Integer> upcomingSongs;
    private ArrayList<Integer> recentlyPlayedSongs;

    /**
     * Constructor for a Queue Object
     * @param upcomings An ArrayList of all upcoming songs to be played
     * @param played An ArrayList of all songs previously played
     */
    public Queue(ArrayList<Integer> upcomings, ArrayList<Integer> played) {
        upcomingSongs = upcomings;
        recentlyPlayedSongs = played;
    }

    /**
     * Constructor for an empty Queue object.
     */
    public Queue(){
        upcomingSongs = new ArrayList<>();
        recentlyPlayedSongs = new ArrayList<>();
    }

    /**
     * getter for getNowPlaying
     * @return the ID of the song that is currently playing
     */
    public int getNowPlaying() {
        return nowPlaying;
    }

    /**
     * setter for getNowPlaying
     * @param nowPlaying The ID of the song that is currently playing
     */
    public void setNowPlaying(int nowPlaying) {
        this.nowPlaying = nowPlaying;
    }

    /**
     * getter for upcomingSongs
     * @return an ArrayList of all songs to be played.
     */
    public ArrayList<Integer> getUpcomingSongs() {
        return upcomingSongs;
    }

    /**
     * setter for upcomingSongs
     * @param upcomingSongs an ArrayList of all songs to be played
     */
    public void setUpcomingSongs(ArrayList<Integer> upcomingSongs) {
        this.upcomingSongs = upcomingSongs;
    }

    /**
     * getter for recentlyPlayedSongs
     * @return an ArrayList of all recently played songs
     */
    public ArrayList<Integer> getRecentlyPlayedSongs() {
        return recentlyPlayedSongs;
    }

    /**
     * setter for recentlyPlayedSongs
     * @param recentlyPlayedSongs an ArrayList of all recently played songs
     */
    public void setRecentlyPlayedSongs(ArrayList<Integer> recentlyPlayedSongs) {
        this.recentlyPlayedSongs = recentlyPlayedSongs;
    }

    /**
     * Add a song to the queue at the specified index
     * @param songID The ID of a song
     * @param index The position at which the song will be played.
     */
    public void addToQueue(int songID, int index) {
        upcomingSongs.add(index, songID);
    }

    /**
     * Remove a song from the queue.
     * @param songIndex The ID of a song
     */
    public void removeFromQueue(int songIndex) {
        upcomingSongs.remove(songIndex);
    }

    /**
     * Clear a queue by emptying all objects in the queue
     */
    public void clearQueue() {
        setUpcomingSongs(new ArrayList<>());
    }

    /**
     * Remove the song that is currently playing.
     */
    public void popFromQueue() {
        recentlyPlayedSongs.add(0, nowPlaying);
        nowPlaying = upcomingSongs.get(0);
        removeFromQueue(0);
    }

    /**
     * Shuffle the songs in the queue.
     */
    public void shuffleQueue() {
        Collections.shuffle(upcomingSongs);
    }

    /**
     * Add a song to the end of the queue.
     * @param songID The ID of a song
     */
    public void addToQueueEnd(int songID) {
        int length = upcomingSongs.size();
        addToQueue(songID, length);
    }
}
