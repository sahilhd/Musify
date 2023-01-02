package com.useCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.Assert.*;

import com.entity.User;

public class UserAccessTest {

    private static UserAccess users;

    @Before
    public void before(){
        HashMap<String, HashMap<String, String>> userInfo = new HashMap<>();

        HashMap<String, String> info = new HashMap<>();
        info.put("Password", "1234567890");
        info.put("DisplayLanguage", "english");
        info.put("IsBanned", String.valueOf(false));
        info.put("IsAdmin", String.valueOf(false));
        info.put("Hours Listened", String.valueOf(1000000));
        info.put("My Public Songs Playlist ID", String.valueOf(101));
        info.put("My Private Songs Playlist ID", String.valueOf(102));
        info.put("Favourites Playlist ID", String.valueOf(103));

        HashMap<String, String> info2 = new HashMap<>();
        info2.put("Password", "1234567890");
        info2.put("DisplayLanguage", "english");
        info2.put("IsBanned", String.valueOf(false));
        info2.put("IsAdmin", String.valueOf(true));
        info2.put("Hours Listened", String.valueOf(1000000));
        info2.put("My Public Songs Playlist ID", String.valueOf(104));
        info2.put("My Private Songs Playlist ID", String.valueOf(105));
        info2.put("Favourites Playlist ID", String.valueOf(106));

        userInfo.put("Glass Animals", info);
        userInfo.put("The Kid Laroi", info2);

        ArrayList<Timestamp> loginHistory = new ArrayList<>();
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        loginHistory.add(dateTime);
        HashMap<String, ArrayList<Timestamp>> userLoginHistory = new HashMap<>();

        userLoginHistory.put("Glass Animals", loginHistory);
        userLoginHistory.put("The Kid Laroi", loginHistory);

        HashMap<String, HashMap<String, ArrayList<String>>> userFollowInfo = new HashMap<>();

        ArrayList<String> followers = new ArrayList<>();
        followers.add("The Kid Laroi");
        ArrayList<String> following = new ArrayList<>();
        following.add("The Kid Laroi");

        ArrayList<String> followers2 = new ArrayList<>();
        followers2.add("Glass Animals");
        ArrayList<String> following2 = new ArrayList<>();
        following2.add("Glass Animals");

        HashMap<String, ArrayList<String>> followInfo = new HashMap<>();
        HashMap<String, ArrayList<String>> followInfo2 = new HashMap<>();
        followInfo.put("Following", following);
        followInfo.put("Followers", followers);
        followInfo2.put("Following", following2);
        followInfo2.put("Followers", followers2);

        userFollowInfo.put("Glass Animals", followInfo);
        userFollowInfo.put("The Kid Laroi", followInfo2);

        HashMap<String, HashMap<String, ArrayList<Integer>>> userPlaylistInfo = new HashMap<>();

        ArrayList<Integer> publicPlaylist = new ArrayList<>();
        publicPlaylist.add(101);
        ArrayList<Integer> privatePlaylist = new ArrayList<>();
        privatePlaylist.add(102);
        ArrayList<Integer> likedPlaylist = new ArrayList<>();
        likedPlaylist.add(104);

        ArrayList<Integer> publicPlaylist2 = new ArrayList<>();
        publicPlaylist2.add(104);
        ArrayList<Integer> privatePlaylist2 = new ArrayList<>();
        privatePlaylist2.add(105);
        ArrayList<Integer> likedPlaylist2 = new ArrayList<>();
        likedPlaylist2.add(101);

        HashMap<String, ArrayList<Integer>> playlistInfo = new HashMap<>();
        HashMap<String, ArrayList<Integer>> playlistInfo2 = new HashMap<>();
        playlistInfo.put("My Public Playlists", publicPlaylist);
        playlistInfo.put("My Private Playlists",privatePlaylist);
        playlistInfo.put("My Liked Playlists", likedPlaylist);
        playlistInfo2.put("My Public Playlists", publicPlaylist2);
        playlistInfo2.put("My Private Playlists",privatePlaylist2);
        playlistInfo2.put("My Liked Playlists", likedPlaylist2);

        userPlaylistInfo.put("Glass Animals", playlistInfo);
        userPlaylistInfo.put("The Kid Laroi", playlistInfo2);

        users = new UserAccess(userInfo, userLoginHistory, userFollowInfo, userPlaylistInfo);
    }

    @Test
    public void testGetUsers(){
        UserEntityContainer currUsersContainer = users.getUsers();
        ArrayList<User> currUsers = currUsersContainer.values();
        assertEquals(2, currUsers.size());
        User user1 = users.findUser("Glass Animals");
        User user2 = users.findUser("The Kid Laroi");
        assertTrue(currUsers.contains(user1));
        assertTrue(currUsers.contains(user2));
    }

    @Test
    public void testSetUsers(){
        ArrayList<User> givenUsers = new ArrayList<>();
        User newUser = new User("kevin", "1234567890");
        givenUsers.add(newUser);
        users.setUsers(givenUsers);
        assertTrue(users.exists("kevin"));
        assertFalse(users.exists("Glass Animals"));
        assertFalse(users.exists("The Kid Laroi"));
    }

    @Test
    public void testCheckLogin() {
        HashMap<String, Boolean> res = users.checkLogin("lindsey", "20220612");
        HashMap<String, Boolean> res2 = users.checkLogin("Glass Animals", "1234567890");
        HashMap<String, Boolean> res3 = users.checkLogin("The Kid Laroi", "1234567890");

        assertTrue(res.containsKey("IsAdmin"));

        assertNotEquals(Boolean.TRUE, res.get("IsAdmin"));
        assertTrue(res.containsKey("IsBanned"));
        assertNotEquals(Boolean.TRUE, res.get("IsBanned"));
        assertTrue(res.containsKey("Exists"));
        assertNotEquals(Boolean.TRUE, res.get("Exists"));

        assertTrue(res2.containsKey("IsAdmin"));
        assertNotEquals(Boolean.TRUE, res2.get("IsAdmin"));
        assertTrue(res2.containsKey("IsBanned"));
        assertNotEquals(Boolean.TRUE, res2.get("IsBanned"));
        assertTrue(res2.containsKey("Exists"));
        assertEquals(Boolean.TRUE, res2.get("Exists"));

        assertTrue(res3.containsKey("IsAdmin"));
        assertEquals(Boolean.TRUE, res3.get("IsAdmin"));
        assertTrue(res3.containsKey("IsBanned"));
        assertNotEquals(Boolean.TRUE, res3.get("IsBanned"));
        assertTrue(res3.containsKey("Exists"));
        assertEquals(Boolean.TRUE, res3.get("Exists"));
    }

    @Test
    public void testExists() {
        assertFalse(users.exists("lindsey"));
        assertTrue(users.exists("Glass Animals"));
    }

    @Test
    public void testFindUser() {
        User result = users.findUser("The Kid Laroi");

        assertEquals("The Kid Laroi", result.getUsername());
        assertEquals("1234567890", result.getPassword());
        assertEquals("english", result.getDisplayLanguage());
        assertFalse(result.getLoginHistory().isEmpty());
        assertFalse(result.getIsBanned());
        assertTrue(result.getIsAdmin());
        assertEquals(1000000, result.getHoursListened());
        assertEquals(104, result.getMyPublicSongsPlaylistId());
        assertEquals(105, result.getMyPrivateSongsPlaylistId());
        assertEquals(106, result.getFavoritesPlaylistId());
        assertFalse(result.getFollowers().isEmpty());
        assertFalse(result.getFollowing().isEmpty());
        assertFalse(result.getMyPublicPlaylists().isEmpty());
        assertFalse(result.getMyPrivatePlaylists().isEmpty());
        assertFalse(result.getMyLikedPlaylists().isEmpty());

        assertNull(users.findUser("kevin"));
    }

    @Test
    public void testAddUser() {
        users.addUser("kevin", "1234567890");
        User result = users.findUser("kevin");
        assertEquals("kevin", result.getUsername());
        assertEquals("1234567890", result.getPassword());
    }

    @Test
    public void testDelete() {
        users.addUser("kevin", "1234567890");
        users.delete("kevin");
        User result = users.findUser("kevin");
        assertNull(result);
    }

    @Test
    public void testBan() {
        assertTrue(users.ban("Glass Animals"));
        User result = users.findUser("Glass Animals");
        assertTrue(result.getIsBanned());
        assertFalse(users.ban("mike"));
    }

    @Test
    public void testUnban() {
        assertTrue(users.ban("Glass Animals"));
        User result = users.findUser("Glass Animals");
        assertTrue(result.getIsBanned());
        assertTrue(users.unban("Glass Animals"));
        assertFalse(result.getIsBanned());
        assertFalse(users.unban("kevin"));
    }

    @Test
    public void testMakeAdmin() {
        User result = users.findUser("Glass Animals");
        assertTrue(users.makeAdmin("Glass Animals"));
        assertTrue(result.getIsAdmin());
        assertFalse(users.makeAdmin("Mike"));
    }

    @Test
    public void testIsAdmin() {
        assertFalse(users.isAdmin("Glass Animals"));
        assertTrue(users.isAdmin("The Kid Laroi"));
    }

    @Test
    public void testGetPreviousLogin() {
        ArrayList<String> previousLogin2 = users.getPreviousLogin("The Kid Laroi");
        User result = users.findUser("The Kid Laroi");
        ArrayList<Timestamp> loginHistory = result.getLoginHistory();
        assertTrue(previousLogin2.contains(loginHistory.get(0).toString()));
    }

    @Test
    public void getFollowers() {
        ArrayList<String> followers = users.getFollowers("Glass Animals");
        assertEquals(1, followers.size());
        assertTrue(followers.contains("The Kid Laroi"));
        assertFalse(followers.contains("Glass Animals"));
    }

    @Test
    public void getFollowing() {
        ArrayList<String> following = users.getFollowing("The Kid Laroi");
        assertEquals(1, following.size());
        assertFalse(following.contains("The Kid Laroi"));
        assertTrue(following.contains("Glass Animals"));
    }

    @Test
    public void addFollower() {
        users.addUser("kevin", "1234567890");
        users.addFollower("Glass Animals", "kevin");
        ArrayList<String> followers = users.getFollowers("Glass Animals");
        assertTrue(followers.contains("kevin"));
        ArrayList<String> following = users.getFollowing("kevin");
        assertTrue(following.contains("Glass Animals"));
    }

    @Test
    public void removeFollower() {
        users.removeFollower("The Kid Laroi", "Glass Animals");
        ArrayList<String> followers = users.getFollowers("The Kid Laroi");
        assertFalse(followers.contains("Glass Animals"));
        ArrayList<String> following = users.getFollowing("Glass Animals");
        assertFalse(following.contains("The Kid Laroi"));
    }


    @Test
    public void testOutputUserInfo() {
        HashMap<String, HashMap<String, String>> outputInfo = users.outputUserInfo();
        assertEquals("1234567890", Objects.requireNonNull(outputInfo.get("Glass Animals")).get("Password"));
        assertEquals(String.valueOf(false), Objects.requireNonNull(outputInfo.get("Glass Animals")).get("IsAdmin"));
        assertEquals(String.valueOf(false), Objects.requireNonNull(outputInfo.get("Glass Animals")).get("IsBanned"));

        assertEquals("1234567890", Objects.requireNonNull(outputInfo.get("The Kid Laroi")).get("Password"));
        assertEquals(String.valueOf(true), Objects.requireNonNull(outputInfo.get("The Kid Laroi")).get("IsAdmin"));
        assertEquals(String.valueOf(false), Objects.requireNonNull(outputInfo.get("The Kid Laroi")).get("IsBanned"));
    }

    @Test
    public void testUserLoginHistory() {
        assertEquals(users.findUser("Glass Animals").getLoginHistory(),
                users.userLogInHistory().get("Glass Animals"));
        assertEquals(users.findUser("The Kid Laroi").getLoginHistory(),
                users.userLogInHistory().get("The Kid Laroi"));
    }


    @Test
    public void getUserDisplayLanguage() {
        assertEquals("english", users.getUserDisplayLanguage("Glass Animals"));
    }

    @Test
    public void getHoursListened() {
        assertEquals(1000000, users.getHoursListened("Glass Animals"));
    }

    @Test
    public void getNumFollowing() {
        assertEquals(1, users.getNumFollowing("Glass Animals"));

    }

    @Test
    public void getNumFollowers() {
        assertEquals(1, users.getNumFollowers("Glass Animals"));
    }

    @Test
    public void getNumPublicPlaylists() {
        assertEquals(1, users.getNumPublicPlaylists("Glass Animals"));
    }

    @Test
    public void getNumPrivatePlaylists() {
        assertEquals(1, users.getNumPrivatePlaylists("Glass Animals"));
    }

    @Test
    public void getNumLikedPlaylists() {
        assertEquals(1, users.getNumLikedPlaylists("Glass Animals"));
    }

    @Test
    public void addToUserPlaylist() {
        users.addToUserPlaylist("Glass Animals", 199, true);
        users.addToUserPlaylist("The Kid Laroi", 198, false);
        User result = users.findUser("Glass Animals");
        User result2 = users.findUser("The Kid Laroi");
        assertTrue(result.getMyPublicPlaylists().contains(199));
        assertTrue(result2.getMyPrivatePlaylists().contains(198));
    }

    @Test
    public void addToUserLikedPlaylist() {
        users.addToUserLikedPlaylist("Glass Animals", 104);
        User result = users.findUser("Glass Animals");
        assertTrue(result.getMyLikedPlaylists().contains(104));
    }

    @Test
    public void getUserPublicSongsID() {
        assertEquals(101, users.getUserPublicSongsID("Glass Animals"));
    }

    @Test
    public void getUserPrivateSongsID() {
        assertEquals(102, users.getUserPrivateSongsID("Glass Animals"));
    }

    @Test
    public void getUserFavouritesID() {
        assertEquals(103, users.getUserFavouritesID("Glass Animals"));
    }

    @Test
    public void getUserLikedPlaylistsIDs() {
        ArrayList<Integer> likedPlaylists = users.getUserLikedPlaylistsIDs("Glass Animals");
        assertEquals(1, likedPlaylists.size());
        assertTrue(likedPlaylists.contains(104));
    }

    @Test
    public void getListOfPlaylistsIDs() {
        ArrayList<Integer> likedPlaylists = users.getListOfPlaylistsIDs("Glass Animals", "Liked");
        ArrayList<Integer> publicPlaylists = users.getListOfPlaylistsIDs("The Kid Laroi", "Public");
        ArrayList<Integer> privatePlaylists = users.getListOfPlaylistsIDs("Glass Animals", "Private");
        assertEquals(1, likedPlaylists.size());
        assertTrue(likedPlaylists.contains(104));
        assertEquals(1, publicPlaylists.size());
        assertTrue(publicPlaylists.contains(104));
        assertEquals(1, privatePlaylists.size());
        assertTrue(privatePlaylists.contains(102));
    }

    @Test
    public void editPlaylistAccess() {
        assertTrue(users.editPlaylistAccess("Glass Animals", 101));
        assertTrue(users.editPlaylistAccess("Glass Animals", 102));
        assertFalse(users.editPlaylistAccess("Glass Animals", 104));
        assertFalse(users.editPlaylistAccess("Glass Animals", 105));
        assertTrue(users.editPlaylistAccess("The Kid Laroi", 104));
        assertTrue(users.editPlaylistAccess("The Kid Laroi", 105));
        assertFalse(users.editPlaylistAccess("The Kid Laroi", 101));
        assertFalse(users.editPlaylistAccess("The Kid Laroi", 102));
    }

    @Test
    public void getCreator() {
        assertEquals("Glass Animals", users.getCreator(101));
        assertEquals("Glass Animals", users.getCreator(102));
        assertEquals("The Kid Laroi", users.getCreator(104));
        assertEquals("The Kid Laroi", users.getCreator(105));
    }

    @After
    public void after(){
        users.delete("Glass Animals");
        users.delete("The Kid Laroi");
    }

}