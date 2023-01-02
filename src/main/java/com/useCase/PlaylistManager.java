package com.useCase;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import com.entity.Playlist;

/**
 * A class responsible for storing all playlists and making all playlist related changes.
 */
public class PlaylistManager implements Serializable {

    private PlaylistEntityContainer playlists = new PlaylistEntityContainer();

    private static final AtomicInteger IdCounter = new AtomicInteger(100);

    /**
     * Constructor for PlaylistManager object
     * @param playlistsInfo stores information on playlist in this format:
     *                      {Playlist ID: {"Playlist Name": String, "Description": String, "Creator Username": String,
     *                      "IsPublic": String, "Number of Likes": String, "Date Time Created": String}}
     * @param playlistsSongs stores an array list of integers that represent the song's IDs for each
     *                       playlist in this format:
     *                       {Playlist ID: [Song ID 1, Song ID 2, ...]}
     */
    public PlaylistManager(HashMap<Integer, HashMap<String, String>> playlistsInfo,
                           HashMap<Integer, ArrayList<Integer>> playlistsSongs) {
        for (Integer ids: playlistsInfo.keySet()) {
            // set the IdCounter as the largest number found amongst the IDs of all playlist, to avoid duplicate IDs
            if (ids > IdCounter.intValue()) {
                IdCounter.set(ids);
            }
            // get parameters values of each playlist to create playlist entities
            HashMap<String, String> currPlaylist = playlistsInfo.get(ids);
            assert currPlaylist != null;
            String name = currPlaylist.get("Playlist Name");
            String desc = currPlaylist.get("Description");
            String username = currPlaylist.get("Creator Username");
            boolean isPublic = Boolean.parseBoolean(currPlaylist.get("IsPublic"));
            int numLikes = Integer.parseInt(Objects.requireNonNull(currPlaylist.get("Number of Likes")));
            Timestamp timeCreated = Timestamp.valueOf(currPlaylist.get("Date Time Created"));
            ArrayList<Integer> songslist = playlistsSongs.get(ids);
            Playlist newPlaylist = new Playlist(ids, name, desc, username, timeCreated,
                    isPublic, songslist, numLikes);
            playlists.add(newPlaylist);
        }
    }

    /**
     * Constructor of PlaylistManager, this constructor will be used with Serializable gateway
     * @param playlists a Playlist Entity Container object
     */
    public PlaylistManager(PlaylistEntityContainer playlists){
        for (Integer ids: playlists.keys()) {
            if (ids > IdCounter.intValue()) {
                IdCounter.set(ids);
            }
        }
        this.playlists = playlists;
    }

    /**
     * A getter to get the playlists container object
     * @return this.playlists
     */
    public PlaylistEntityContainer getPlaylists() {
        return playlists;
    }

    /**
     * getter for the list of all playlists
     * @return the list of all playlists
     */
    protected ArrayList<Playlist> getPlaylistInArray() {
        return playlists.values();
    }

    /**
     * setter for the list of all playlists
     * @param playlists An ArrayList of playlists
     */
    protected void setPlaylists(ArrayList<Playlist> playlists) {
        PlaylistEntityContainer playlistList = new PlaylistEntityContainer();
        for (Playlist album: playlists) {
            playlistList.add(album);
        }
        this.playlists = playlistList;
    }

    /**
     * Check if a playlist with the specified ID exists
     * @param id The ID of the playlist
     * @return true if the playlist exists, and false otherwise.
     */
    public boolean exists(int id) {
        return playlists.exists(id);
    }

    /**
     * Find the playlist with the specified ID.
     * @param id The ID of the playlist
     * @return the Playlist object if it exists, and null otherwise.
     */
    public Playlist findPlaylist(int id) {
        return playlists.findEntity(id);
    }

    /**
     *
     * @param id The ID of a playlist
     * @return The name of a playlist
     */
    public String getPlaylistName(int id) {
        return findPlaylist(id).getName();
    }

    /**
     *
     * @param id The ID of a playlist
     * @return The description of a playlist
     */
    public String getPlaylistDescription(int id) {
        return findPlaylist(id).getDescription();
    }

    /**
     *
     * @param id The ID of a playlist
     * @return The name of the user that created the playlist
     */
    public String getCreatorUsername(int id) {
        return findPlaylist(id).getCreatorUsername();
    }

    /**
     *
     * @param id The ID of a playlist
     * @return true if a playlist is public, and false otherwise
     */
    public boolean IsPublic(int id) {
        return findPlaylist(id).isPublic();
    }

    /**
     *
     * @param id The ID of a playlist
     * @return The number of likes this playlist has
     */
    public int getNumLikes(int id) {
        return findPlaylist(id).getNumLikes();
    }

    /**
     *
     * @param id The ID of a playlist
     * @return The time stamp of when this playlist was created
     */
    public Timestamp getDateTimeCreated(int id) {
        return findPlaylist(id).getDateTimeCreated();
    }


    /**
     * Create a new Playlist object and add it to the list of all playlists
     * @param name The name of the playlist.
     * @param description The description of the playlist.
     * @param creatorUsername The username of the user that created the playlist.
     * @param dateTimeCreated The TimeStamp of when this playlist was created.
     * @param isPublic Public status of this playlist
     */
    public void addPlaylist(String name, String description, String creatorUsername,
                            Timestamp dateTimeCreated, boolean isPublic) {
        int newID = IdCounter.incrementAndGet();
        Playlist newPl = new Playlist(newID, name, description, creatorUsername, dateTimeCreated, isPublic);
        playlists.add(newPl);
    }

    /**
     *
     * @return The ID of the newest playlist created.
     */
    public int latestPlaylistID() {
        return IdCounter.intValue();
    }

    /**
     * Have the user with the specified username like the playlist with the specified playlist ID.
     * @param id The ID of a playlist
     *
     * Note: controllers have to manually add this playlist ID to the user's list of liked playlist
     */
    public void likePlaylist (int id) {
        findPlaylist(id).setNumLikes(findPlaylist(id).getNumLikes() + 1);
    }

    /**
     * Have the user with the specified username unlike the playlist with the specified playlist ID.
     * @param id The ID of a playlist
     *
     * Note: controllers have to manually delete this playlist ID to the user's list of liked playlist
     */
    public void unlikePlaylist(int id){
        findPlaylist(id).setNumLikes(findPlaylist(id).getNumLikes() - 1);
    }

    /**
     * Add  a song with specific song ID into the playlist with playlist ID.
     * @param playlistID The ID of a playlist
     * @param songID The ID of a song
     */
    public void addToPlaylist(int playlistID, int songID) {
        findPlaylist(playlistID).getSongs().add(songID);
    }

    /**
     * Remove the song with songID from user's Favourite playlist.
     * @param userFavouritesID  id of user's Favourites playlist
     * @param songID            id of song to be removed
     */
    public void removeFromFavoritePlaylist(int userFavouritesID, int songID){
        findPlaylist(userFavouritesID).removeSong(songID);
    }

    /**
     * Check if a song is allowed to be added to a playlist, whenever the case is not: Public Playlist & Private Songs.
     * @param playlistID The ID of a playlist
     * @param songIsPublic Determines if a song is public or not
     * @return true if the song can be added to a playlist, and false otherwise
     */
    public boolean addableToPlaylist(int playlistID, boolean songIsPublic) {
        boolean playlistIsPublic = findPlaylist(playlistID).isPublic();
        return !(playlistIsPublic && !songIsPublic);
    }

    /**
     * (Get the input for this method from UserAccess.getListOfPlaylistsIDs)
     *
     * @param playlistIDs an ArrayList of integers that represent the IDs of the playlists
     * @return an ArrayList of the playlist titles
     */
    public ArrayList<String> getListOfPlaylistNames(ArrayList<Integer> playlistIDs) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        for (Integer IDs: playlistIDs) {
            playlists.add(findPlaylist(IDs));
        }
        ArrayList<String> playlistName = new ArrayList<>();
        for (Playlist playlist: playlists) {
            playlistName.add(playlist.getName());
        }
        return playlistName;
    }

    /**
     *
     * (to use for SongManager.getPlaylistSongNames)
     *
     * @param playlistID The ID of a playlist
     * @return an ArrayList of integers that represent the IDs of the songs in the playlist
     */
    public ArrayList<Integer> getListOfSongsID(int playlistID) {
        return findPlaylist(playlistID).getSongs();
    }

    /**
     * Get the IDs of playlists whose title matches the input, playlistName.
     * @param playlistName The name of a playlist
     * @return A list of strings representing the IDs of playlists whose title matches playlistName,
     *         or null if there are none.
     */
    public ArrayList<String> getListOfStringPlaylistIDs(String playlistName) {
        ArrayList<String> IDs = new ArrayList<>();
        for (Playlist playlist: getPlaylistInArray()) {
            if (Objects.equals(playlist.getName(), playlistName) && playlist.isPublic()) {
                IDs.add(String.valueOf(playlist.getId()));
            }
        }
        if (IDs.isEmpty()) {
            return null;
        }
        return IDs;
    }

    /**
     * A method to delete multiple playlists based on the provided ids. This is method will be invoked an Admin deletes
     * a non-admin user.
     * @param playlistIDs a list stores all playlist ids that will be deleted
     *
     * Precondition:
     * All ids in playlist ids exists in this.playlists object
     */
    public void deletePlaylistsByIDs(ArrayList<String> playlistIDs) {
        for (String playlistID : playlistIDs){
            this.playlists.deleteEntity(Integer.parseInt(playlistID));
        }
    }
}

