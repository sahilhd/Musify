package com.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Playlist implements Serializable {
    private final int id;
    private String name;
    private String description;
    private String creatorUsername;
    private Timestamp dateTimeCreated;
    private boolean isPublic;
    private ArrayList<Integer> songs;
    private int numLikes;

    /**
     * Playlist constructor used when creating a new playlist
     * @param id                the id of this Playlist
     * @param name              the name of this Playlist
     * @param description       the description of this Playlist
     * @param creatorUsername   the username of the User who created this Playlist
     * @param dateTimeCreated   the date and time this Playlist was created
     * @param isPublic          checks whether this Playlist is public
     */
    public Playlist(int id, String name, String description, String creatorUsername, Timestamp dateTimeCreated,
                    boolean isPublic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creatorUsername = creatorUsername;
        this.dateTimeCreated = dateTimeCreated;
        this.isPublic = isPublic;
        songs = new ArrayList<>();
        numLikes = 0;
    }

    /**
     * Playlist constructor used when the program restarts to instantiate all playlists that existed on the platform
     * and were saved on an external file before the program stopped running.
     * @param id                the id of this Playlist
     * @param name              the name of this Playlist
     * @param description       the description of this Playlist
     * @param creatorUsername   the name of User who created this Playlist
     * @param dateTimeCreated   the date and time this Playlist was created
     * @param isPublic          checks whether this Playlist is public
     * @param songs             the list of Songs in this Playlist
     * @param numLikes          the number of likes this playlist has received
     */
    public Playlist(int id, String name, String description, String creatorUsername, Timestamp dateTimeCreated,
                    boolean isPublic, ArrayList<Integer> songs, int numLikes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creatorUsername = creatorUsername;
        this.dateTimeCreated = dateTimeCreated;
        this.isPublic = isPublic;
        this.songs = songs;
        this.numLikes = numLikes;
    }

    // id getter
    public int getId() {
        return id;
    }

    // name getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // description getter and setter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // creatorUsername getter and setter
    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    // getDateTimeCreated getter and setter
    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Timestamp dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    // isPublic getter and setter
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    // songs getter and setter
    public ArrayList<Integer> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Integer> songs) {
        this.songs = songs;
    }

    // numLikes getter and setter
    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    /**
     * Counts the number of songs in the playlist
     * @return the number of songs in the playlist
     */
    public int numSongs() {
        return songs.size();
    }

    /**
     * Converts the id from an int to a String
     * @return the String representation of the id
     */
    @NonNull
    public String toString() {
        return String.valueOf(getId());
    }

    /**
     * Removes song from playlist
     * @param songID    the id of the song to be removed
     */
    public void removeSong(int songID){
        songs.remove(Integer.valueOf(songID));
    }
}


