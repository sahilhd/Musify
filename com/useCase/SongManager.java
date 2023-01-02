package com.useCase;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.entity.Song;

/**
 * A class responsible for storing all songs and making all song related changes.
 */
public class SongManager implements Serializable {

    private SongEntityContainer songs = new SongEntityContainer();

    private static final AtomicInteger IdCounter = new AtomicInteger(200);

    private final UserAccess acctServiceManager;

    private final PlaylistManager playlistManager;

    /**
     * Constructor for a SongManagerConstructor
     * @param songInfo stores information on all songs in the following format:
     *                 {ID: {"Name": String, "Artist": String, "Date Time Created": String,
     *                 "Lyrics": String, "isPublic": String, "Number of Likes": String, "Number of Listens": String}}
     * @param songDuration {ID: [minutes, seconds]}
     * @param userManager a UserAccess object that stores all users.
     * @param plManager a PlaylistManager object that stores all playlists.
     */
    public SongManager(HashMap<Integer, HashMap<String, String>> songInfo, HashMap<Integer,
            int[]> songDuration, UserAccess userManager, PlaylistManager plManager) {
        acctServiceManager = userManager;
        playlistManager = plManager;
        for (Integer ids: songInfo.keySet()) {
            // set the IdCounter as the largest number found amongst the IDs of all songs, to avoid duplicate IDs
            if (ids > IdCounter.intValue()) {
                IdCounter.set(ids);
            }
            // get parameters values of each song to create song entities
            HashMap<String, String> currSongDetails = songInfo.get(ids);
            assert currSongDetails != null;
            String name = currSongDetails.get("Name");
            String artist = currSongDetails.get("Artist");
            Timestamp dateTimeCreated = Timestamp.valueOf(currSongDetails.get("Date Time Created"));
            String lyrics = currSongDetails.get("Lyrics");
            boolean isPublic = Boolean.parseBoolean(currSongDetails.get("isPublic"));
            int[] duration = songDuration.get(ids);
            int numlikes = Integer.parseInt(Objects.requireNonNull(currSongDetails.get("Number of Likes")));
            int numlistens = Integer.parseInt(Objects.requireNonNull(currSongDetails.get("Number of Listens")));
            Song currSong = new Song(ids, name, duration, artist, dateTimeCreated,
                    numlikes, numlistens, lyrics, isPublic);
            songs.add(currSong);
        }
    }

    /**
     * Constructor of SongManager, this constructor will be used with Serializable Gateway
     * @param acctServiceManager a UserAccess object
     * @param playlistManager a PlaylistManager object
     * @param songs a Song entity container object
     */
    public SongManager(UserAccess acctServiceManager, PlaylistManager playlistManager, SongEntityContainer songs){
        for (Integer ids: songs.keys()) {
            if (ids > IdCounter.intValue()) {
                IdCounter.set(ids);
            }
        }
        this.acctServiceManager = acctServiceManager;
        this.playlistManager = playlistManager;
        this.songs = songs;
    }

    /**
     * A getter to get the song container object
     * @return this.songs
     */
    public SongEntityContainer getSongs() {
        return songs;
    }

    /**
     * getter for the list of all the songs
     * @return the songs list
     */
    public ArrayList<Song> getSongList() {
        return songs.values();
    }

    /**
     * setter for the list of all the songs
     * @param songs an ArrayList of songs
     */
    public void setSongs(ArrayList<Song> songs) {
        SongEntityContainer music = new SongEntityContainer();
        for (Song tune: songs) {
            music.add(tune);
        }
        this.songs = music;
    }

    /**
     * Checks if a song with the ID exists.
     * @param id the song's ID
     * @return true if the song exists, and false otherwise.
     */
    public boolean exists(int id) {
        return songs.exists(id);
    }

    /**
     * Finds the song with the specified ID
     * @param id the ID of the song
     * @return the Song object if it exists, and null otherwise.
     */
    public Song findSong(int id) {
        return songs.findEntity(id);
    }

    /**
     *
     * @param id The ID of a song
     * @return The name of the song
     */
    public String getSongName(int id) {
        return findSong(id).getName();
    }

    /**
     *
     * @param id The ID of a song
     * @return The name of the user that uploaded the song
     */
    public String getSongArtist(int id) {
        return findSong(id).getArtistUsername();
    }

    /**
     *
     * @param id The ID of a song
     * @return The timestamp of when the song was created
     */
    public Timestamp getSongDateTimeCreated(int id) {
        return findSong(id).getDateTimeCreated();
    }

    /**
     *
     * @param id The ID of a song
     * @return The lyrics of the song
     */
    public String getSongLyrics(int id) {
        return findSong(id).getLyrics();
    }

    /**
     *
     * @param id The ID of a song
     * @return The number of likes this song has
     */
    public int getSongNumLikes(int id) {
        return findSong(id).getNumLikes();
    }

    /**
     *
     * @param id The ID of a song
     * @return The number of listens this song has
     */
    public int getSongNumListens(int id) {
        return findSong(id).getNumListens();
    }

    /**
     *
     * @param id The ID of a song
     * @return The duration of a song in the format [minutes, seconds]
     */
    public int[] getSongDuration(int id) {
        return findSong(id).getDuration();
    }

    /**
     * Determine whether a song is public.
     * @param id The ID of the song.
     * @return true if a song is public.
     */
    public boolean isPublic(int id) {
        return findSong(id).isPublic();
    }

    /**
     * Creates a new song and adds the song to the songs list.
     * @param title The name of the song
     * @param duration The duration of the song
     * @param artist The user that uploaded the song
     * @param dateTimeCreated TimeStamp of when the song was uploaded
     * @param lyrics The lyrics of the song
     * @param isPublic Public status of the song
     */
    public void addSong(String title, int[] duration, String artist,
                        Timestamp dateTimeCreated, String lyrics, boolean isPublic) {
        int newID = IdCounter.incrementAndGet();
        Song newSong = new Song(newID, title, duration, artist, dateTimeCreated, lyrics, isPublic);
        songs.add(newSong);
        if (isPublic) {
            int playlistID = acctServiceManager.findUser(artist).getMyPublicSongsPlaylistId();
            ArrayList<Integer> newSongsList = playlistManager.findPlaylist(playlistID).getSongs();
            newSongsList.add(newID);
            playlistManager.findPlaylist(playlistID).setSongs(newSongsList);
        } else {
            int playlistID = acctServiceManager.findUser(artist).getMyPrivateSongsPlaylistId();
            ArrayList<Integer> newSongsList = playlistManager.findPlaylist(playlistID).getSongs();
            newSongsList.add(newID);
            playlistManager.findPlaylist(playlistID).setSongs(newSongsList);
        }
    }

    /**
     *
     * @return the ID of the newest song added.
     */
    public int latestSongID() {
        return IdCounter.intValue();
    }

    /**
     * Have the user with the specified username like the song with the specified song ID.
     *
     * NOTE: Controllers have to add songID to the user's Favourite playlist.
     *
     * @param id The ID of the song
     */
    public void likeSong(int id) {
        findSong(id).setNumLikes(findSong(id).getNumLikes() + 1);
    }

    public void unlikeSong(int id){
        findSong(id).setNumLikes(findSong(id).getNumLikes() - 1);
    }

    /**
     * (Get input from PlaylistManager.getListOfSongsID)
     *
     * @param songsID An ArrayList of integers that represent the IDs of songs in a playlist.
     * @return an ArrayList of the playlist's song titles.
     */
    public ArrayList<String> getPlaylistSongNames(ArrayList<Integer> songsID) {
        ArrayList<Song> songs = new ArrayList<>();
        for (Integer idNum : songsID) {
            songs.add(findSong(idNum));
        }
        ArrayList<String> title = new ArrayList<>();
        for (Song song: songs) {
            title.add(song.getName());
        }
        return title;
    }

    /**
     * Get the IDs of songs whose title matches the input, songName.
     * @param songName The title of a song
     * @return A list of strings representing the IDs of songs whose title matches songName, or null if there are none.
     */
    public ArrayList<String> getStringSongsIDsFromSongName(String songName) {
        ArrayList<String> IDs = new ArrayList<>();
        for (Song song: getSongList()) {
            if (Objects.equals(song.getName(), songName) && song.isPublic()) {
                IDs.add(String.valueOf(song.getId()));
            }
        }
        if (IDs.isEmpty()) {
            return null;
        }
        return IDs;
    }

    /**
     * A method to retrieve all song IDs from the same song creator
     * @param creatorName the name of the creator
     * @return a list stores all song ids from the creator with name <creatorName>
     */
    public ArrayList<String> getStringSongIDsFromCreator(String creatorName){
        ArrayList<String> stringSongIDs = new ArrayList<>();
        ArrayList<Integer> availableSongIDs = new ArrayList<> (this.songs.keys());
        for (Integer songID: availableSongIDs){
            Song currSong = this.findSong(songID);
            if (Objects.equals(currSong.getArtistUsername(), creatorName)){
                stringSongIDs.add(songID.toString());
            }
        }
        return stringSongIDs;
    }

    /**
     * A method to delete songs with the provided song ids
     * @param songsIds a list stores ids for songs that will be deleted
     *
     * Precondition: all ids exits in this.songs
     */
    public void deleteSongsByIDs(ArrayList<String> songsIds) {
        for (String songID: songsIds){
            this.songs.deleteEntity(Integer.parseInt(songID));
        }
    }
}
