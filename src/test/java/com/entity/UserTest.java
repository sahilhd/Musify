package com.entity;

import org.junit.Test;
import java.sql.Timestamp;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class UserTest {

    final ArrayList<Timestamp> newLogin = new ArrayList<>();
    final Timestamp dateTime = new Timestamp(System.currentTimeMillis());
    final ArrayList<String> user1Following = new ArrayList<>();
    final ArrayList<String> user1Follower = new ArrayList<>();
    final ArrayList<Integer> user1PubPlaylist = new ArrayList<>();
    final ArrayList<Integer> user1PrivPlaylist = new ArrayList<>();
    final ArrayList<Integer> user1LikePlaylist = new ArrayList<>();
    final User user1 = new User("Glass Animals", "1234567890", "english", newLogin,
            false, false, user1Following, user1Follower, 0, 101,
            102, 103, user1PubPlaylist, user1PrivPlaylist, user1LikePlaylist);

    @Test
    public void getUsername() {
        assertEquals("Glass Animals", user1.getUsername());
    }

    @Test
    public void setUsername() {
        user1.setUsername("Dua Lipa");
        assertEquals("Dua Lipa", user1.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("1234567890", user1.getPassword());
    }

    @Test
    public void setPassword() {
        user1.setPassword("1k155");
        assertEquals("1k155", user1.getPassword());
    }

    @Test
    public void getDisplayLanguage() {
        assertEquals("english", user1.getDisplayLanguage());
    }

    @Test
    public void setDisplayLanguage() {
        user1.setDisplayLanguage("japanese");
        assertEquals( "japanese", user1.getDisplayLanguage());
    }

    @Test
    public void getLoginHistory() {
        assertEquals(newLogin, user1.getLoginHistory());
    }

    @Test
    public void setLoginHistory() {
        ArrayList<Timestamp> newLogin1 = new ArrayList<>();
        newLogin1.add(dateTime);
        user1.setLoginHistory(newLogin1);
        assertEquals(newLogin1, user1.getLoginHistory());
        assertEquals(dateTime, user1.getLoginHistory().get(0));
    }

    @Test
    public void getIsBanned() {
        assertFalse(user1.getIsBanned());
    }

    @Test
    public void setBanned() {
        user1.setBanned(true);
        assertTrue(user1.getIsBanned());
    }

    @Test
    public void getIsAdmin() {
        assertFalse(user1.getIsAdmin());
    }

    @Test
    public void setIsAdmin() {
        user1.setIsAdmin(true);
        assertTrue(user1.getIsAdmin());
    }

    @Test
    public void getFollowers() {
        assertEquals(user1Follower, user1.getFollowers());
    }

    @Test
    public void setFollowers() {
        ArrayList<String> user1Followers2 = new ArrayList<>();
        user1Followers2.add("Adelle");
        user1.setFollowers(user1Followers2);
        assertEquals(user1Followers2, user1.getFollowers());
        assertEquals("Adelle", user1.getFollowers().get(0));
    }

    @Test
    public void getFollowing() {
        assertEquals(user1Following, user1.getFollowing());
    }

    @Test
    public void setFollowing() {
        ArrayList<String> user1Following2 = new ArrayList<>();
        user1Following2.add("Ariana Grande");
        user1.setFollowers(user1Following2);
        assertEquals(user1Following2, user1.getFollowers());
        assertEquals("Ariana Grande", user1.getFollowers().get(0));
    }

    @Test
    public void getHoursListened() {
        assertEquals(0, user1.getHoursListened());
    }

    @Test
    public void setHoursListened() {
        user1.setHoursListened(400);
        assertEquals(400, user1.getHoursListened());
    }

    @Test
    public void getMyPublicSongsPlaylistId() {
        assertEquals(101, user1.getMyPublicSongsPlaylistId());
    }

    @Test
    public void setMyPublicSongsPlaylistId() {
        user1.setMyPublicSongsPlaylistId(111);
        assertEquals(111, user1.getMyPublicSongsPlaylistId());
    }

    @Test
    public void getMyPrivateSongsPlaylistId() {
        assertEquals(102, user1.getMyPrivateSongsPlaylistId());
    }

    @Test
    public void setMyPrivateSongsPlaylistId() {
        user1.setMyPrivateSongsPlaylistId(122);
        assertEquals(122, user1.getMyPrivateSongsPlaylistId());
    }

    @Test
    public void getFavoritesPlaylistId() {
        assertEquals(103, user1.getFavoritesPlaylistId());
    }

    @Test
    public void setFavoritesPlaylistId() {
        user1.setFavoritesPlaylistId(133);
        assertEquals(133, user1.getFavoritesPlaylistId());
    }

    @Test
    public void getMyPublicPlaylists() {
        assertEquals(user1PubPlaylist, user1.getMyPublicPlaylists());
    }

    @Test
    public void setMyPublicPlaylists() {
        ArrayList<Integer> user1PubPlaylist1 = new ArrayList<>();
        user1PubPlaylist1.add(150);
        user1.setMyPublicPlaylists(user1PubPlaylist1);
        assertEquals(user1PubPlaylist1, user1.getMyPublicPlaylists());
        int publicId = user1.getMyPublicPlaylists().get(0);
        assertEquals(150, publicId);
    }

    @Test
    public void getMyPrivatePlaylists() {
        assertEquals(user1PrivPlaylist, user1.getMyPrivatePlaylists());
    }

    @Test
    public void setMyPrivatePlaylists() {
        ArrayList<Integer> user1PrivPlaylist1 = new ArrayList<>();
        user1PrivPlaylist1.add(160);
        user1.setMyPrivatePlaylists(user1PrivPlaylist1);
        assertEquals(user1PrivPlaylist1, user1.getMyPrivatePlaylists());
        int privId = user1.getMyPrivatePlaylists().get(0);
        assertEquals(160, privId);
    }

    @Test
    public void getMyLikedPlaylists() {
        assertEquals(user1LikePlaylist, user1.getMyLikedPlaylists());
    }

    @Test
    public void setMyLikedPlaylists() {
        ArrayList<Integer> user1LikePlaylist1 = new ArrayList<>();
        user1LikePlaylist1.add(170);
        user1.setMyLikedPlaylists(user1LikePlaylist1);
        assertEquals(user1LikePlaylist1, user1.getMyLikedPlaylists());
        int likeId = user1.getMyLikedPlaylists().get(0);
        assertEquals(170, likeId);
    }

    @Test
    public void addLogin() {
        Timestamp newTime = new Timestamp(100);
        user1.addLogin(newTime);
        ArrayList<Timestamp> loginSessions = user1.getLoginHistory();
        int length = loginSessions.size();
        assertEquals(newTime, loginSessions.get(length - 1));
    }

    @Test
    public void numFollowing() {
        ArrayList<String> followingList = new ArrayList<>();
        followingList.add("Ed Sheeran");
        followingList.add("Justin Bieber");
        followingList.add("The Kid LAROI");
        followingList.add("Avicii");
        user1.setFollowing(followingList);
        assertEquals(4, user1.numFollowing());
    }

    @Test
    public void numFollowers() {
        ArrayList<String> followersList = new ArrayList<>();
        followersList.add("Travis Scott");
        followersList.add("Post Malone");
        followersList.add("The Weeknd");
        user1.setFollowers(followersList);
        assertEquals(3, user1.numFollowers());
    }

    @Test
    public void numPublicPlaylists() {
        ArrayList<Integer> publicPlaylistList = new ArrayList<>();
        publicPlaylistList.add(199);
        publicPlaylistList.add(198);
        user1.setMyPublicPlaylists(publicPlaylistList);
        assertEquals(2, user1.numPublicPlaylists());
    }

    @Test
    public void numPrivatePlaylists() {
        ArrayList<Integer> privatePlaylistList = new ArrayList<>();
        privatePlaylistList.add(197);
        privatePlaylistList.add(196);
        privatePlaylistList.add(195);
        user1.setMyPrivatePlaylists(privatePlaylistList);
        assertEquals(3, user1.numPrivatePlaylists());
    }

    @Test
    public void numLikedPlaylists() {
        ArrayList<Integer> likedPlaylistList = new ArrayList<>();
        likedPlaylistList.add(194);
        likedPlaylistList.add(193);
        likedPlaylistList.add(192);
        likedPlaylistList.add(191);
        user1.setMyLikedPlaylists(likedPlaylistList);
        assertEquals(4, user1.numLikedPlaylists());
    }
}
