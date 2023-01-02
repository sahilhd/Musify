package com.useCase;

import com.entity.Playlist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A map playlistID -> Playlist Entity object, which will be used to store all playlist entities in PlaylistManager
 */

public class PlaylistEntityContainer implements Serializable {
    public final HashMap<Integer, Playlist> playlists = new HashMap<>();

    /**
     * Add a playlist to the container
     * @param playlist a Playlist object
     */
    public void add(Playlist playlist) {
        playlists.put(Integer.parseInt(playlist.toString()), playlist);
    }

    /**
     * Find a Playlist entity from the HashMap playlists
     * @param playlistID an Integer to represent playlistID
     * @return a Playlist Entity object with the specified playlist ID
     */
    public Playlist findEntity(Integer playlistID) {
        if (exists(playlistID)) {
            return playlists.get(playlistID);
        }
        return null;
    }

    /**
     * Delete a playlist entity from the HashMap playlists
     * @param playlistID an Integer to represent playlistID
     *  Precondition: playlistID exits in this.playlists
     */
    public void deleteEntity(Integer playlistID) {
        playlists.remove(playlistID);
    }

    /**
     * Check whether a playlist entity exists
     * @param playlistID an Integer to represent playlistID
     * @return true if the playlist with the specified ID exists, and false otherwise.
     */
    public boolean exists(Integer playlistID) {
        return playlists.containsKey(playlistID);
    }

    /**
     *
     * @return an array list containing all playlist entities in the program
     */
    public ArrayList<Playlist> values() {
        return new ArrayList<>(playlists.values());
    }

    /**
     *
     * @return an array list containing all IDs of playlist entities in the program.
     */
    public ArrayList<Integer> keys() {
        return new ArrayList<>(playlists.keySet());
    }
}
