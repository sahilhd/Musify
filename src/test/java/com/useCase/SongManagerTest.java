package com.useCase;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

import com.entity.Song;

public class SongManagerTest {

    private static SongManager songManager;
    private static PlaylistManager playlists;

    @Before
    public void before() {
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

        UserAccess users = new UserAccess(userInfo, userLoginHistory, userFollowInfo, userPlaylistInfo);

        HashMap<Integer, HashMap<String, String>> playlistInfo5 = new HashMap<>();

        HashMap<String, String> playlist1 = new HashMap<>();
        playlist1.put("Playlist Name", "My Songs");
        playlist1.put("Description", "Your songs the world will listen!");
        playlist1.put("Creator Username", "Glass Animals");
        playlist1.put("IsPublic","true");
        playlist1.put("Number of Likes", "8000000");
        playlist1.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo5.put(101, playlist1);

        HashMap<Integer, ArrayList<Integer>> playlistSongs = new HashMap<>();
        ArrayList<Integer> pl101 = new ArrayList<>();
        pl101.add(201);
        playlistSongs.put(101, pl101);

        playlists = new PlaylistManager(playlistInfo5, playlistSongs);

        HashMap<Integer, HashMap<String, String>> songInfo = new HashMap<>();

        HashMap<String, String> song = new HashMap<>();
        song.put("Name", "Heat Waves");
        song.put("Artist", "Glass Animals");
        song.put("Date Time Created", "2022-01-17 21:10:39.311");
        song.put("Lyrics", "Heat waves been faking me out");
        song.put("isPublic", String.valueOf(true));
        song.put("Number of Likes", String.valueOf(1000000));
        song.put("Number of Listens", String.valueOf(5000000));

        songInfo.put(201, song);

        HashMap<Integer, int[]> songDuration = new HashMap<>();
        songDuration.put(201, new int[]{3, 59});

        songManager = new SongManager(songInfo, songDuration, users, playlists);
    }

    @Test
    public void getSongs() {
        SongEntityContainer songsListContainer = songManager.getSongs();
        ArrayList<Song> songsList = songsListContainer.values();
        assertEquals(1, songsList.size());
        Song song1 = songManager.findSong(201);
        Song song2 = songManager.findSong(202);
        assertTrue(songsList.contains(song1));
        assertFalse(songsList.contains(song2));
    }

    @Test
    public void setSongs() {
        SongEntityContainer songListContainer = songManager.getSongs();
        ArrayList<Song> songList = songListContainer.values();
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        Song song = new Song(202, "STAY", new int[]{2, 25}, "The Kid Laroi", dateTime,
                "I need you to stay", true);
        songList.add(song);
        songManager.setSongs(songList);
        SongEntityContainer songsListContainer = songManager.getSongs();
        ArrayList<Song> songsList = songsListContainer.values();
        assertEquals(2, songsList.size());
        Song song1 = songManager.findSong(201);
        Song song2 = songManager.findSong(202);
        assertTrue(songsList.contains(song1));
        assertTrue(songsList.contains(song2));
    }

    @Test
    public void exists() {
        assertTrue(songManager.exists(201));
        assertFalse(songManager.exists(202));
    }

    @Test
    public void findSong() {
        Song song1 = songManager.findSong(201);
        assertEquals("Heat Waves", song1.getName());
        assertEquals("Glass Animals", song1.getArtistUsername());
    }

    @Test
    public void getSongName() {
        assertEquals("Heat Waves", songManager.getSongName(201));
    }

    @Test
    public void getSongArtist() {
        assertEquals("Glass Animals", songManager.getSongArtist(201));
    }

    @Test
    public void getSongDateTimeCreated() {
        assertEquals("2022-01-17 21:10:39.311", String.valueOf(songManager.getSongDateTimeCreated(201)));
    }

    @Test
    public void getSongLyrics() {
        assertEquals("Heat waves been faking me out", songManager.getSongLyrics(201));
    }

    @Test
    public void getSongNumLikes() {
        assertEquals(1000000, songManager.getSongNumLikes(201));
    }

    @Test
    public void getSongNumListens() {
        assertEquals(5000000, songManager.getSongNumListens(201));
    }

    @Test
    public void getSongDuration() {
        int[] duration = songManager.getSongDuration(201);
        assertEquals(3, duration[0]);
        assertEquals(59, duration[1]);
    }

    @Test
    public void isPublic() {
        assertTrue(songManager.isPublic(201));
    }

    @Test
    public void latestSongID() {
        assertEquals(201, songManager.latestSongID());
    }

    @Test
    public void likeSong() {
        songManager.likeSong(201);
        assertEquals(1000001, songManager.getSongNumLikes(201));
    }

    @Test
    public void getPlaylistSongNames() {
        ArrayList<Integer> songID = playlists.getListOfSongsID(101);
        ArrayList<String> songNames = songManager.getPlaylistSongNames(songID);
        assertTrue(songNames.contains("Heat Waves"));
    }

    @Test
    public void getStringSongsIDsFromSongName() {
        ArrayList<String> songID = songManager.getStringSongsIDsFromSongName("Heat Waves");
        assertTrue(songID.contains("201"));
    }

    @Test
    public void getStringSongIDsFromCreator() {
        ArrayList<String> songID = songManager.getStringSongIDsFromCreator("Glass Animals");
        assertTrue(songID.contains("201"));
    }

    @Test
    public void deleteSongsByIDs() {
        ArrayList<String> songID = new ArrayList<>();
        songID.add("201");
        songManager.deleteSongsByIDs(songID);
        assertFalse(songManager.exists(201));
    }

}