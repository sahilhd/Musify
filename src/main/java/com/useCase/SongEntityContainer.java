package com.useCase;

import com.entity.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A map SongID -> Song Entity object, which will be used to store all song entities in SongManager
 */

public class SongEntityContainer implements Serializable {
    public final HashMap<Integer, Song> songs = new HashMap<>();

    /**
     * Add a song to the container
     * @param song a Song object
     */
    public void add(Song song) {
        songs.put(Integer.parseInt(song.toString()), song);
    }

    /**
     * Find a song entity from the HashMap songs
     * @param songID an Integer to represent songID
     * @return a Song Entity object
     */
    public Song findEntity(Integer songID) {
        if (exists(songID)) {
            return songs.get(songID);
        }
        return null;
    }

    /**
     * Delete a song entity from the HashMap songs
     *
     * @param songID an Integer to represent songID
     *
     * Precondition: songID exits in this.songs
     */
    public void deleteEntity(Integer songID) {
        songs.remove(songID);
    }

    /**
     * Check whether a song entity exists
     * @param songID an Integer to represent songID
     * @return a boolean to indicate the status of checking
     */
    public boolean exists(Integer songID) {
        return songs.containsKey(songID);
    }

    /**
     * Get a list of all song entities
     * @return a list contains all song entities in the program
     */
    public ArrayList<Song> values() {
        return new ArrayList<>(songs.values());
    }

    /**
     * Get a list of all song IDs
     * @return a list containing all song IDs in the program.
     */
    public ArrayList<Integer> keys() {
        return new ArrayList<>(songs.keySet());
    }
}
