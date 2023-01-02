package com.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Timestamp;

public class Song implements Serializable {
    private final int id;
    private String name;
    private int[] duration;
    private String artistUsername;
    private Timestamp dateTimeCreated;
    private int numLikes;
    private int numListens;
    private String lyrics;
    private boolean isPublic;

    /**
     * Song constructor used when uploading a new song
     * @param id                the id of this Song
     * @param name              the name of this Song
     * @param duration          the duration of this song given as a list [minutes, seconds]
     * @param artistUsername    the username of the User who uploaded this Song
     * @param dateTimeCreated   the date and time this Song was uploaded
     * @param lyrics            the lyrics of this Song
     * @param isPublic          checks whether this Song is public
     */
    public Song(int id, String name, int[] duration, String artistUsername,
                Timestamp dateTimeCreated, String lyrics, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.artistUsername = artistUsername;
        this.dateTimeCreated = dateTimeCreated;
        this.numLikes = 0;
        this.numListens = 0;
        this.lyrics = lyrics;
        this.isPublic = isPublic;
    }

    /**
     *  Song constructor used when the program restarts to instantiate all songs that existed on the platform and were
     *  saved on an external file before the program stopped running.
     * @param id                the id of this Song
     * @param name              the name of this Song
     * @param duration          the duration of this song given as a list [minutes, seconds]
     * @param artistUsername    the username of the User who uploaded this Song
     * @param dateTimeCreated   the date and time this Song was uploaded
     * @param numLikes          the number of likes this Song has received
     * @param numListens        the number of times this Song was listened to
     * @param lyrics            the lyrics of this Song
     * @param isPublic          checks whether this Song is public
     */
    public Song(int id, String name, int[] duration, String artistUsername,
                Timestamp dateTimeCreated, int numLikes, int numListens, String lyrics,
                boolean isPublic) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.artistUsername = artistUsername;
        this.dateTimeCreated = dateTimeCreated;
        this.numLikes = numLikes;
        this.numListens = numListens;
        this.lyrics = lyrics;
        this.isPublic = isPublic;
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

    // duration getter and setter
    public int[] getDuration() {
        return duration;
    }

    public void setDuration(int[] duration) {
        this.duration = duration;
    }

    // artistUsername getter and setter
    public String getArtistUsername() {
        return artistUsername;
    }

    public void setArtistUsername(String artistUsername) {
        this.artistUsername = artistUsername;
    }

    // dateTimeCreated getter and setter
    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Timestamp dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    // numLikes getter and setter
    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    // numListens getter and setter
    public int getNumListens() {
        return numListens;
    }

    public void setNumListens(int numListens) {
        this.numListens = numListens;
    }

    // getLyrics getter and setter
    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    // isPublic getter and setter
    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    /**
     * Converts the id from an int to a String
     * @return the String representation of the id
     */
    @NonNull
    public String toString(){
        return String.valueOf(getId());
    }
}

