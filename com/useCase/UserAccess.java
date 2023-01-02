package com.useCase;

import com.entity.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * A class responsible for storing all users and with adding information to/extracting information from users.
 */
public class UserAccess implements Serializable {
    private UserEntityContainer users = new UserEntityContainer();

    /**
     * Constructor for a UserAccess object
     * @param userinfo the user info HashMap with the following format
     *                 {"userName":{"Password": String, "DisplayLanguage": String, "IsBanned": String,
     *                 "IsAdmin": String, "Hours Listened": String, "My Public Songs Playlist ID": String,
     *                 "My Private Songs Playlist ID": String, "Favourites Playlist ID": String}}
     * @param userLogInHistory the user log-in history HashMap with the following format
     *                 {"userName": [Timestamp1, Timestamp2, Timestamp3,...]}
     * @param userFollowinfo the user follows & followings info Hashmap with the following format:
     *                 {"username": {"Following": [Username1, Username2, ...], "Followers": [Username1, Username2]}}
     * @param userPlaylistInfo the user playlist info Hashmap with the following format:
     *                  {"username": {"My Public Playlists": [PlaylistID 1, PlaylistID 2, ...],
     *                                "My Private Playlists": [PlaylistID 1, PlaylistID 2, ...],
     *                                "My Liked Playlists": [PlaylistID 1, PlaylistID 2, ...]}}
     */
    public UserAccess(HashMap<String, HashMap<String, String>> userinfo,
                      HashMap<String, ArrayList<Timestamp>> userLogInHistory,
                      HashMap<String, HashMap<String, ArrayList<String>>> userFollowinfo,
                      HashMap<String, HashMap<String, ArrayList<Integer>>> userPlaylistInfo) {
        for (String userName: userinfo.keySet()){
            // get parameters values of each user to create User entities
            HashMap<String, String> currUserInfoDetail = userinfo.get(userName);
            assert currUserInfoDetail != null;
            String pwd = currUserInfoDetail.get("Password");
            String displayLanguage = currUserInfoDetail.get("DisplayLanguage");
            boolean isBanned = Boolean.parseBoolean(currUserInfoDetail.get("IsBanned"));
            boolean isAdmin = Boolean.parseBoolean(currUserInfoDetail.get("IsAdmin"));
            int hrslistened = Integer.parseInt(Objects.requireNonNull(currUserInfoDetail.get("Hours Listened")));
            int publicSongsPlaylist = Integer.parseInt(Objects.requireNonNull(currUserInfoDetail.get("My Public Songs Playlist ID")));
            int privSongsPlaylist = Integer.parseInt(Objects.requireNonNull(currUserInfoDetail.get("My Private Songs Playlist ID")));
            int favPlaylist = Integer.parseInt(Objects.requireNonNull(currUserInfoDetail.get("Favourites Playlist ID")));
            ArrayList<Timestamp> loginHistory = userLogInHistory.get(userName);
            ArrayList<String> followers = Objects.requireNonNull(userFollowinfo.get(userName)).get("Followers");
            ArrayList<String> following = Objects.requireNonNull(userFollowinfo.get(userName)).get("Following");
            ArrayList<Integer> publicPlaylists = Objects.requireNonNull(userPlaylistInfo.get(userName)).get("My Public Playlists");
            ArrayList<Integer> privPlaylists = Objects.requireNonNull(userPlaylistInfo.get(userName)).get("My Private Playlists");
            ArrayList<Integer> likedPlaylists = Objects.requireNonNull(userPlaylistInfo.get(userName)).get("My Liked Playlists");
            User currUser = new User(userName, pwd, displayLanguage, loginHistory, isBanned, isAdmin,
                    following, followers, hrslistened, publicSongsPlaylist, privSongsPlaylist, favPlaylist,
                    publicPlaylists, privPlaylists, likedPlaylists);
            users.add(currUser);
        }
    }

    /**
     * Constructor of UserAccess, this constructor will be used with Serializable Gateway
     * @param users a User Entity container object
     */
    public UserAccess(UserEntityContainer users){
        this.users = users;
    }

    /**
     * getter to get this.user
     */
    public UserEntityContainer getUsers() {
        return users;
    }

    /**
     * getter for the users list
     * @return the users list
     */
    protected ArrayList<User> getUserList(){
        return users.values();
    }

    /**
     * setter for users attribute
     * @param users the new users list
     */
    protected void setUsers(ArrayList<User> users){
        UserEntityContainer user = new UserEntityContainer();
        for (User person: users) {
            user.add(person);
        }
        this.users = user;
    }

    /**
     * A method to check if a user exists with the username and password pair specified as well as other
     * information relevant to logging in.
     *
     * @param username a username
     * @param password a password
     * @return a hashmap representing important information such as if this user exists,
     * if they are banned, and if they are admin.
     */
    public HashMap<String, Boolean> checkLogin(String username, String password){
        HashMap<String, Boolean> results = new HashMap<>();
        //Set each property to false by default
        results.put("Exists", false);
        results.put("IsBanned", false);
        results.put("IsAdmin", false);
        //Loop to check if a user exists with this username password pair
        for (User user: getUserList()){
            //update results if such a user exists and update their loginHistory
            if ((Objects.equals(user.getUsername(), username)) && (Objects.equals(user.getPassword(), password))){
                results.replace("Exists", true);

                //update the login history
                updateLoginHistory(user);

                //update results if this user is banned
                if (user.getIsBanned()){
                    results.replace("IsBanned", true);
                }
                //update results if this user is an admin
                if (user.getIsAdmin()){
                    results.replace("IsAdmin", true);
                }
            }
        }
        return results;
    }

    /**
     * A private helper to update the user log-in info
     * @param user: the user entity object
     */
    private void updateLoginHistory(User user){
        //Date object
        Date date= new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class
        Timestamp ts = new Timestamp(time);
        user.getLoginHistory().add(ts);
    }

    /**
     * Checks if a user with this username exists.
     *
     * @param username a username
     * @return true if this user exists, false otherwise.
     */
    public boolean exists(String username){
        return users.exists(username);
    }

    /**
     * Finds a user with the specified username.
     *
     * @param username a username
     * @return if a User with username exists, return the User object, otherwise return null
     */
    public User findUser(String username){
        return users.findEntity(username);
    }

    /**
     * Creates a new user with username and password and adds the user to the users list.
     *
     * @param username a username
     * @param password a password
     */
    public void addUser(String username, String password){
        User currUser = new User(username, password);
        updateLoginHistory(currUser);
        users.add(currUser);
    }

    /**
     * Removes the User with username from users.
     *
     * @param username a username
     *
     * Precondition: a user account with userID <username> exists in this.users
     *
     */
    public void delete(String username){
        users.deleteEntity(username);
    }

    /**
     * Bans the User with username.
     *
     * @param username a username
     * @return true if a User with username existed and the banning was successful, false otherwise.
     */
    public boolean ban(String username){
        //check if User with username exists
        if (this.exists(username)){
            User foundUser = this.findUser(username);
            //Since we now know that a User with username exists, foundUser must be type User and not null
            assert foundUser != null;
            foundUser.setBanned(true);
            return true;
        }
        return false;
    }

    /**
     * Unbans the User with username.
     *
     * @param username a username
     * @return true if a User with username existed and the unbanning was successful, false otherwise.
     */
    public boolean unban(String username){
        //check if User with username exists
        if (this.exists(username)){
            User foundUser = this.findUser(username);
            //Since we now know that a User with username exists, foundUser must be type User and not null
            assert foundUser != null;
            foundUser.setBanned(false);
            return true;
        }
        return false;
    }

    /**
     * Makes the User with username an admin
     * @param username the username of a User
     * @return true if a User with username existed and was made admin, false otherwise.
     */
    public boolean makeAdmin(String username){
        if (this.exists(username)){
            User foundUser = this.findUser(username);
            //Since we now know that a User with username exists, foundUser must be type User and not null
            assert foundUser != null;
            foundUser.setIsAdmin(true);
            return true;
        }
        return false;
    }

    /**
     * Determine whether the User with username is an admin
     * @param username the username of a User
     * @return true if a User with username is an admin, false otherwise.
     *
     * Precondition:
     * the User with <username> exists in this.users.
     *
     */
    public boolean isAdmin(String username){
        return findUser(username).getIsAdmin();
    }

    /**
     *
     * @param username the username of a User
     * @return a String representation for the previous login Timestamp of the User with <username>
     *
     * Precondition:
     * The User with <username> exists in this.users.
     */
    public ArrayList<String> getPreviousLogin(String username){
        User currUser = findUser(username);
        ArrayList<Timestamp> loginHistory = currUser.getLoginHistory();
        ArrayList<String> stringOfLoginHistory = new ArrayList<>();
        for (Timestamp history: loginHistory){
            String timestamp = history.toString();
            stringOfLoginHistory.add(timestamp);
        }
        return stringOfLoginHistory;
    }

    /**
     *
     * @param username the username of a user
     * @return a list of names representing the user's followers
     */
    public ArrayList<String> getFollowers(String username){
        User currUser = findUser(username);
        return currUser.getFollowers();
    }

    /**
     *
     * @param username the username of a user
     * @return a list of names representing the user's following
     */
    public ArrayList<String> getFollowing(String username){
        User currUser = findUser(username);
        return currUser.getFollowing();
    }

    /** A method to add a following/follower relationship between two users.
     *
     * @param beingFollowed the user that is being followed by isFollowing
     * @param isFollowing the user that is following beingFollowed
     */
    public void addFollower(String beingFollowed, String isFollowing){
        User currUser = findUser(beingFollowed);
        User otherUser = findUser(isFollowing);
        currUser.getFollowers().add(isFollowing);
        otherUser.getFollowing().add(beingFollowed);
    }

    /** A method to remove a following/follower relationship between two users.
     *
     * @param username the user from whom beingRemoved is unfollowing
     * @param beingRemoved the user that is unfollowing username
     */
    public void removeFollower(String username, String beingRemoved){
        User currUser = findUser(username);
        User otherUser = findUser(beingRemoved);
        currUser.getFollowers().remove(beingRemoved);
        otherUser.getFollowing().remove(username);
    }

    /**
     *
     * @return the user info HashMap with the following format
     *        {"userName":{"Password": String, "IsBanned": String, "IsAdmin": String}}
     */
    public HashMap<String, HashMap<String, String>> outputUserInfo(){
        HashMap<String, HashMap<String, String>> userInfo = new HashMap<>();
        for (User user: getUserList()){
            HashMap<String, String> userInfoDetail = new HashMap<>();
            String userName = user.getUsername();
            String password = user.getPassword();
            String displayLanguage = user.getDisplayLanguage();
            String isBanned = Boolean.toString(user.getIsBanned());
            String isAdmin = Boolean.toString(user.getIsAdmin());
            userInfoDetail.put("Password", password);
            userInfoDetail.put("DisplayLanguage", displayLanguage);
            userInfoDetail.put("IsBanned", isBanned);
            userInfoDetail.put("IsAdmin", isAdmin);
            userInfo.put(userName, userInfoDetail);
        }
        return userInfo;
    }

    /**
     *
     * @return the user login history with the following format
     *         {"userName": [Timestamp1, Timestamp2, Timestamp3,...]}
     */
    public HashMap<String, ArrayList<Timestamp>> userLogInHistory(){
        HashMap<String, ArrayList<Timestamp>> loginHistory = new HashMap<>();
        for (User user: getUserList()){
            loginHistory.put(user.getUsername(), user.getLoginHistory());
        }
        return loginHistory;
    }

    /**
     * Retrieve the user's preferred display language based on a provided userID
     * @param username a userID string
     * @return a String to represent the preferred display language of the user with userID
     *
     * Precondition:
     * The User with <username> exists in this.users.
     */
    public String getUserDisplayLanguage(String username) {
        User currUser = findUser(username);
        return currUser.getDisplayLanguage();
    }

    /**
     *
     * @param username the username of a user
     * @return The number of hours this user has listened
     */
    public int getHoursListened(String username) {
        return findUser(username).getHoursListened();
    }

    /**
     *
     * @param username the username of a user
     * @return the number of following this user has
     */
    public int getNumFollowing(String username) {
        return findUser(username).numFollowing();
    }

    /**
     *
     * @param username the username of a user
     * @return the number of followers this user has
     */
    public int getNumFollowers(String username) {
        return findUser(username).numFollowers();
    }

    /**
     *
     * @param username the username of a user
     * @return the number of public playlist this user has
     */
    public int getNumPublicPlaylists(String username) {
        return findUser(username).numPublicPlaylists();
    }

    /**
     *
     * @param username the username of a user
     * @return the number of private playlist this user has
     */
    public int getNumPrivatePlaylists(String username) {
        return findUser(username).numPrivatePlaylists();
    }

    /**
     *
     * @param username the username of a user
     * @return the number of playlist this user has liked
     */
    public int getNumLikedPlaylists(String username) {
        return findUser(username).numLikedPlaylists();
    }

    /**
     * Call this after a new playlist is created, so that the playlist can be added to its respective list of playlists.
     *
     * @param creatorUsername The username of the user that created the playlist
     * @param playlistID The ID of the new playlist
     * @param isPublic Determines if a playlist is public or not
     */
    public void addToUserPlaylist(String creatorUsername, int playlistID, boolean isPublic) {
        if (isPublic) {
            findUser(creatorUsername).getMyPublicPlaylists().add(playlistID);
        } else {
            findUser(creatorUsername).getMyPrivatePlaylists().add(playlistID);
        }
    }

    /**
     * Call this after a user liked a playlist, so that the playlist is added to user's list of liked playlists.
     *
     * @param username The username of the user that liked this playlist
     * @param playlistID The ID of the playlist
     */
    public void addToUserLikedPlaylist(String username, int playlistID) {
        findUser(username).getMyLikedPlaylists().add(playlistID);
    }

    /**
     * Call this after a user unliked a playlist, so that the playlist is deleted to user's list of liked playlists.
     *
     * @param username The username of the user that liked this playlist
     * @param playlistID The ID of the playlist
     */
    public void deleteFromUserLikedPlaylist(String username, int playlistID){
        findUser(username).removeFromLikedPlaylist(playlistID);
    }

    /**
     *
     * @param username the username of a user
     * @return The ID of this user's public songs playlist
     */
    public int getUserPublicSongsID(String username) {
        return findUser(username).getMyPublicSongsPlaylistId();
    }

    /**
     *
     * @param username the username of a user
     * @return The ID of this user's private songs playlist
     */
    public int getUserPrivateSongsID(String username) {
        return findUser(username).getMyPrivateSongsPlaylistId();
    }

    /**
     *
     * @param username the username of a user
     * @return The ID of this user's favourite songs playlist
     */
    public int getUserFavouritesID(String username) {
        return findUser(username).getFavoritesPlaylistId();
    }

    /**
     *
     * @param username the username of a user
     * @return a list of integers representing the IDs of this user's liked playlists
     */
    public ArrayList<Integer> getUserLikedPlaylistsIDs(String username){
        return findUser(username).getMyLikedPlaylists();
    }

    /**
     *
     * (to use for PlaylistManager.getListOfPlaylistNames)
     *
     * @param username the username of the user
     * @param playlistType The type of list of playlists: "Public", "Private", "Liked"
     * @return an ArrayList of integers that represent the IDs of the playlists
     */
    public ArrayList<Integer> getListOfPlaylistsIDs(String username, String playlistType) {
        ArrayList<Integer> playlistsID;
        if (Objects.equals(playlistType, "Public")) {
            playlistsID = findUser(username).getMyPublicPlaylists();
        } else if (Objects.equals(playlistType, "Private")) {
            playlistsID = findUser(username).getMyPrivatePlaylists();
        } else if (Objects.equals(playlistType, "Liked")) {
            playlistsID = findUser(username).getMyLikedPlaylists();
        } else {
            return null;
        }
        return playlistsID;
    }

    /**
     * A method to retrieve all playlist ids under a username. This method will be invoked when an Admin deletes
     * a non-admin user
     * @param username a string to represent a username
     * @return a list stores all playlist ids under the username
     */
    public ArrayList<String> getListOfAllPlaylistIDsFromUser(String username){
        ArrayList<String> playlistIDsInString = new ArrayList<>();
        ArrayList<Integer> userPlaylistIDs = this.getListOfPlaylistsIDs(username, "Public");
        ArrayList<Integer> userPrivatePlaylistIDs = this.getListOfPlaylistsIDs(username, "Private");
        userPlaylistIDs.addAll(userPrivatePlaylistIDs);
        for (Integer playlistID : userPlaylistIDs){
            playlistIDsInString.add(Integer.toString(playlistID));
        }
        return playlistIDsInString;
    }

    /**
     *
     * @param username The username of the user
     * @param playlistID The ID of a playlist
     * @return true if the user has access to edit a playlist (if the user created the playlist)
     */
    public boolean editPlaylistAccess(String username, int playlistID) {
        User user = findUser(username);
        return user.getMyPublicPlaylists().contains(playlistID) || user.getMyPrivatePlaylists().contains(playlistID);
    }

    /**
     *
     * @param playlistID The ID of a playlist
     * @return The username of the user that created the playlist with the specified ID
     */
    public String getCreator(int playlistID) {
        for (User user: getUserList()) {
            if (getListOfPlaylistsIDs(user.getUsername(), "Public").contains(playlistID)) {
                return user.getUsername();
            }
            if (getListOfPlaylistsIDs(user.getUsername(),"Private").contains(playlistID)) {
                return user.getUsername();
            }
        }
        return null;
    }
}
