package com.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

public class User implements Serializable {

    private String username;
    private String password;
    private String displayLanguage;
    private ArrayList<Timestamp> loginHistory;
    private boolean isBanned;
    private boolean isAdmin;
    private ArrayList<String> following;
    private ArrayList<String> followers;
    private int hoursListened;
    private int myPublicSongsPlaylistId;
    private int myPrivateSongsPlaylistId;
    private int favoritesPlaylistId;
    private ArrayList<Integer> myPublicPlaylists;
    private ArrayList<Integer> myPrivatePlaylists;
    private ArrayList<Integer> myLikedPlaylists;

    /**
     * User constructor used when creating a new user
     * @param username  the username of this User
     * @param password  the password of this User
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        displayLanguage = "english";
        loginHistory = new ArrayList<>();
        isBanned = false;
        isAdmin = false;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        hoursListened = 0;
        myPublicSongsPlaylistId = 0;
        myPrivateSongsPlaylistId = 0;
        favoritesPlaylistId = 0;
        myPublicPlaylists = new ArrayList<>();
        myPrivatePlaylists = new ArrayList<>();
        myLikedPlaylists = new ArrayList<>();
    }

    /**
     * User constructor used when the program restarts to instantiate all users who existed on the platform and were
     * saved on an external file before the program stopped running.
     * @param username                  the username of this User
     * @param password                  the password of this User
     * @param displayLanguage           the preferred display language of this User
     * @param loginHistory              the login history of this User
     * @param isBanned                  checks whether this User is banned
     * @param isAdmin                   checks whether this User is an admin
     * @param following                 the list of Users this User is following
     * @param followers                 the list of Users this User is follower by
     * @param hoursListened             the number of hours of songs listened by this User
     * @param myPublicSongsPlaylistId   the id of this User's "My Songs" playlist - a playlist of the public songs that
     *                                  this User has uploaded
     * @param myPrivateSongsPlaylistId  the id of this User's "Private Songs" playlist - a playlist of the private songs
     *                                  that this User has uploaded
     * @param favoritesPlaylistId       the id of this User's "Favorites" playlist - a playlist of the public songs that
     *                                  this User has liked
     * @param myPublicPlaylists         the list of ids of the public playlists that this User has created
     * @param myPrivatePlaylists        the list of ids of the private playlists that this User has created
     * @param myLikedPlaylists          the list of ids of the public playlists that this User has liked
     */
    public User(String username, String password, String displayLanguage, ArrayList<Timestamp> loginHistory,
                boolean isBanned, boolean isAdmin, ArrayList<String> following, ArrayList<String> followers,
                int hoursListened, int myPublicSongsPlaylistId, int myPrivateSongsPlaylistId, int favoritesPlaylistId,
                ArrayList<Integer> myPublicPlaylists, ArrayList<Integer> myPrivatePlaylists,
                ArrayList<Integer> myLikedPlaylists){
        this.username = username;
        this.password = password;
        this.displayLanguage = displayLanguage;
        this.loginHistory = loginHistory;
        this.isBanned = isBanned;
        this.isAdmin = isAdmin;
        this.following = following;
        this.followers = followers;
        this.hoursListened = hoursListened;
        this.myPublicSongsPlaylistId = myPublicSongsPlaylistId;
        this.myPrivateSongsPlaylistId = myPrivateSongsPlaylistId;
        this.favoritesPlaylistId = favoritesPlaylistId;
        this.myPublicPlaylists = myPublicPlaylists;
        this.myPrivatePlaylists = myPrivatePlaylists;
        this.myLikedPlaylists = myLikedPlaylists;
    }

    // username getter and setter
    public String getUsername(){ return username; }

    public void setUsername(String username){
        this.username = username;
    }

    //password getter and setter
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    //displayLanguage getter and setter
    public String getDisplayLanguage() {
        return displayLanguage;
    }

    public void setDisplayLanguage(String displayLanguage) {
        this.displayLanguage = displayLanguage;
    }

    // loginHistory getter and setter
    public ArrayList<Timestamp> getLoginHistory(){
        return loginHistory;
    }

    public void setLoginHistory(ArrayList<Timestamp> loginHistory){
        this.loginHistory = loginHistory;
    }

    // isBanned getter and setter
    public boolean getIsBanned(){
        return isBanned;
    }

    public void setBanned(boolean isBanned){
        this.isBanned = isBanned;
    }

    // isAdmin getter and setter
    public boolean getIsAdmin(){
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    // followers getter and setter
    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    // following getter and setter
    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    // hoursListened getter and setter
    public int getHoursListened() {
        return hoursListened;
    }

    public void setHoursListened(int hoursListened) {
        this.hoursListened = hoursListened;
    }

    // myPublicSongsPlaylistId getter and setter
    public int getMyPublicSongsPlaylistId() {
        return myPublicSongsPlaylistId;
    }

    public void setMyPublicSongsPlaylistId(int myPublicSongsPlaylistId) {
        myPublicPlaylists.add(myPublicSongsPlaylistId);
        this.myPublicSongsPlaylistId = myPublicSongsPlaylistId;

    }

    // myPrivateSongsPlaylistId getter and setter
    public int getMyPrivateSongsPlaylistId() {
        return myPrivateSongsPlaylistId;
    }

    public void setMyPrivateSongsPlaylistId(int myPrivateSongsPlaylistId) {
        myPrivatePlaylists.add(myPrivateSongsPlaylistId);
        this.myPrivateSongsPlaylistId = myPrivateSongsPlaylistId;
    }

    // favoritesPlaylistId getter and setter
    public int getFavoritesPlaylistId() {
        return favoritesPlaylistId;
    }

    public void setFavoritesPlaylistId(int favoritesPlaylistId) {
        myPublicPlaylists.add(favoritesPlaylistId);
        this.favoritesPlaylistId = favoritesPlaylistId;
    }

    // myPublicPlaylists getter and setter
    public ArrayList<Integer> getMyPublicPlaylists() {
        return myPublicPlaylists;
    }

    public void setMyPublicPlaylists(ArrayList<Integer> myPublicPlaylists) {
        this.myPublicPlaylists = myPublicPlaylists;
    }

    // myPrivatePlaylists getter and setter
    public ArrayList<Integer> getMyPrivatePlaylists() {
        return myPrivatePlaylists;
    }

    public void setMyPrivatePlaylists(ArrayList<Integer> myPrivatePlaylists) {
        this.myPrivatePlaylists = myPrivatePlaylists;
    }

    // myLikedPlaylists getter and setter
    public ArrayList<Integer> getMyLikedPlaylists() {
        return myLikedPlaylists;
    }

    public void setMyLikedPlaylists(ArrayList<Integer> myLikedPlaylists) {
        this.myLikedPlaylists = myLikedPlaylists;
    }

    /**
     * Adds a new user login session everytime this User logs in
     * @param dateTime  the date and time that this User logged in at
     */
    public void addLogin(Timestamp dateTime){
        loginHistory.add(dateTime);
    }

    /**
     * Counts the number Users this User was following
     * @return  the number of Users this User was following
     */
    public int numFollowing() {
        return following.size();
    }

    /**
     * Counts the number of Users this User was followed by
     * @return  the number of Users this User was followed by
     */
    public int numFollowers() {
        return followers.size();
    }

    /**
     * Counts the number of public playlists this User has created
     * @return  the number of public playlists this User has created
     */
    public int numPublicPlaylists() {
        return myPublicPlaylists.size();
    }

    /**
     * Counts the number of private playlists this User has created
     * @return  the number of private playlists this User has created
     */
    public int numPrivatePlaylists() {
        return myPrivatePlaylists.size();
    }

    /**
     * Counts the number of playlists this User has liked
     * @return  the number of playlists this User has liked
     */
    public int numLikedPlaylists() {
        return myLikedPlaylists.size();
    }

    /**
     * Converts the id from an int to a String
     * @return the String representation of the id
     */
    @NonNull
    public String toString() {
        return username;
    }

    /**
     * Playlist will be removes from Liked Playlists
     * @param playlistID The ID of the playlist
     */
    public void removeFromLikedPlaylist(int playlistID) {
        myLikedPlaylists.remove(Integer.valueOf(playlistID));
    }
}
