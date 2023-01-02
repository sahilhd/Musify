package com.useCase;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

import com.entity.Playlist;

public class PlaylistManagerTest {

    private static PlaylistManager playlists;

    @Before
    public void before() {
        HashMap<Integer, HashMap<String, String>> playlistInfo = new HashMap<>();

        HashMap<String, String> playlist1 = new HashMap<>();
        playlist1.put("Playlist Name", "My Songs");
        playlist1.put("Description", "Your songs the world will listen!");
        playlist1.put("Creator Username", "Glass Animals");
        playlist1.put("IsPublic","true");
        playlist1.put("Number of Likes", "8000000");
        playlist1.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(101, playlist1);

        HashMap<String, String> playlist2 = new HashMap<>();
        playlist2.put("Playlist Name", "Private Songs");
        playlist2.put("Description", "Unlisted soundtracks");
        playlist2.put("Creator Username", "Glass Animals");
        playlist2.put("IsPublic", "false");
        playlist2.put("Number of Likes", "0");
        playlist2.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(102, playlist2);

        HashMap<String, String> playlist3 = new HashMap<>();
        playlist3.put("Playlist Name", "Favourites");
        playlist3.put("Description", "Songs you like will appear here!");
        playlist3.put("Creator Username", "Glass Animals");
        playlist3.put("IsPublic", "true");
        playlist3.put("Number of Likes", "4000000");
        playlist3.put("Date Time Created", "2022-02-17 21:10:39.311");
        playlistInfo.put(103, playlist3);

        HashMap<String, String> playlist4 = new HashMap<>();
        playlist4.put("Playlist Name", "My Songs");
        playlist4.put("Description", "Your songs the world will listen!");
        playlist4.put("Creator Username", "The Kid Laroi");
        playlist4.put("IsPublic","true");
        playlist4.put("Number of Likes", "7000000");
        playlist4.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(104, playlist4);

        HashMap<String, String> playlist5 = new HashMap<>();
        playlist5.put("Playlist Name", "Private Songs");
        playlist5.put("Description", "Unlisted soundtracks");
        playlist5.put("Creator Username", "The Kid Laroi");
        playlist5.put("IsPublic", "false");
        playlist5.put("Number of Likes", "0");
        playlist5.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(105, playlist5);

        HashMap<String, String> playlist6 = new HashMap<>();
        playlist6.put("Playlist Name", "Favourites");
        playlist6.put("Description", "Songs you like will appear here!");
        playlist6.put("Creator Username", "The Kid Laroi");
        playlist6.put("IsPublic", "true");
        playlist6.put("Number of Likes", "4000000");
        playlist6.put("Date Time Created", "2022-02-17 21:10:39.311");
        playlistInfo.put(106, playlist6);

        HashMap<String, String> playlist7 = new HashMap<>();
        playlist7.put("Playlist Name", "My Songs");
        playlist7.put("Description", "Your songs the world will listen!");
        playlist7.put("Creator Username", "Dua Lipa");
        playlist7.put("IsPublic","true");
        playlist7.put("Number of Likes", "10000000");
        playlist7.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(107, playlist7);

        HashMap<String, String> playlist8 = new HashMap<>();
        playlist8.put("Playlist Name", "Private Songs");
        playlist8.put("Description", "Unlisted soundtracks");
        playlist8.put("Creator Username", "Dua Lipa");
        playlist8.put("IsPublic", "false");
        playlist8.put("Number of Likes", "0");
        playlist8.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(108, playlist8);

        HashMap<String, String> playlist9 = new HashMap<>();
        playlist9.put("Playlist Name", "Favourites");
        playlist9.put("Description", "Songs you like will appear here!");
        playlist9.put("Creator Username", "Dua Lipa");
        playlist9.put("IsPublic", "true");
        playlist9.put("Number of Likes", "5000000");
        playlist9.put("Date Time Created", "2022-02-17 21:10:39.311");
        playlistInfo.put(109, playlist9);

        HashMap<String, String> playlist10 = new HashMap<>();
        playlist10.put("Playlist Name", "My Songs");
        playlist10.put("Description", "Your songs the world will listen!");
        playlist10.put("Creator Username", "adminUser");
        playlist10.put("IsPublic","true");
        playlist10.put("Number of Likes", "2");
        playlist10.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(110, playlist10);

        HashMap<String, String> playlist11 = new HashMap<>();
        playlist11.put("Playlist Name", "Private Songs");
        playlist11.put("Description", "Unlisted soundtracks");
        playlist11.put("Creator Username", "adminUser");
        playlist11.put("IsPublic", "false");
        playlist11.put("Number of Likes", "0");
        playlist11.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(111, playlist11);

        HashMap<String, String> playlist12 = new HashMap<>();
        playlist12.put("Playlist Name", "Favourites");
        playlist12.put("Description", "Songs you like will appear here!");
        playlist12.put("Creator Username", "adminUser");
        playlist12.put("IsPublic", "true");
        playlist12.put("Number of Likes", "1");
        playlist12.put("Date Time Created", "2022-02-17 21:10:39.311");
        playlistInfo.put(112, playlist12);

        HashMap<String, String> playlist13 = new HashMap<>();
        playlist13.put("Playlist Name", "My Songs");
        playlist13.put("Description", "Your songs the world will listen!");
        playlist13.put("Creator Username", "foreignUser");
        playlist13.put("IsPublic","true");
        playlist13.put("Number of Likes", "9");
        playlist13.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(113, playlist13);

        HashMap<String, String> playlist14 = new HashMap<>();
        playlist14.put("Playlist Name", "Private Songs");
        playlist14.put("Description", "Unlisted soundtracks");
        playlist14.put("Creator Username", "foreignUser");
        playlist14.put("IsPublic", "false");
        playlist14.put("Number of Likes", "0");
        playlist14.put("Date Time Created", "2022-01-17 21:10:39.311");
        playlistInfo.put(114, playlist14);

        HashMap<String, String> playlist15 = new HashMap<>();
        playlist15.put("Playlist Name", "Favourites");
        playlist15.put("Description", "Songs you like will appear here!");
        playlist15.put("Creator Username", "foreignUser");
        playlist15.put("IsPublic", "true");
        playlist15.put("Number of Likes", "5");
        playlist15.put("Date Time Created", "2022-02-17 21:10:39.311");
        playlistInfo.put(115, playlist15);


        HashMap<Integer, ArrayList<Integer>> playlistSongs = new HashMap<>();
        ArrayList<Integer> pl101 = new ArrayList<>();
        pl101.add(201);
        playlistSongs.put(101, pl101);

        ArrayList<Integer> pl103 = new ArrayList<>();
        pl103.add(202);
        pl103.add(205);
        playlistSongs.put(103, pl103);

        ArrayList<Integer> pl104 = new ArrayList<>();
        pl104.add(202);
        playlistSongs.put(104, pl104);

        ArrayList<Integer> pl106 = new ArrayList<>();
        pl106.add(205);
        playlistSongs.put(106, pl106);

        ArrayList<Integer> pl107 = new ArrayList<>();
        pl107.add(205);
        playlistSongs.put(107, pl107);

        ArrayList<Integer> pl109 = new ArrayList<>();
        pl109.add(201);
        playlistSongs.put(109, pl109);

        ArrayList<Integer> pl111 = new ArrayList<>();
        pl111.add(203);
        playlistSongs.put(111, pl111);

        ArrayList<Integer> pl112 =  new ArrayList<>();
        pl112.add(201);
        pl112.add(202);
        pl112.add(205);
        playlistSongs.put(112, pl112);

        ArrayList<Integer> pl114 = new ArrayList<>();
        pl114.add(204);
        playlistSongs.put(114, pl114);

        ArrayList<Integer> pl115 = new ArrayList<>();
        pl115.add(205);
        playlistSongs.put(115, pl115);

        playlists = new PlaylistManager(playlistInfo, playlistSongs);
    }

    @Test
    public void testGetPlaylists() {
        PlaylistEntityContainer listOfPlaylistsContainer = playlists.getPlaylists();
        ArrayList<Playlist> listofplaylists = listOfPlaylistsContainer.values();
        assertTrue(listofplaylists.contains(playlists.findPlaylist(101)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(102)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(103)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(104)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(105)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(106)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(107)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(108)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(109)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(110)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(111)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(112)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(113)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(114)));
        assertTrue(listofplaylists.contains(playlists.findPlaylist(115)));
    }

    @Test
    public void testSetPlaylists() {
        ArrayList<Playlist> newplaylists = new ArrayList<>();
        Playlist newpl = new Playlist(116, "Tester Playlist","for testing set playlist",
                "foreignUser", Timestamp.valueOf("2022-01-17 21:10:39.311"), true);
        newplaylists.add(newpl);
        playlists.setPlaylists(newplaylists);
        assertTrue(playlists.exists(116));
        assertFalse(playlists.exists(101));
    }

    @Test
    public void testExists() {
        assertTrue(playlists.exists(111));
        assertFalse(playlists.exists(201));
        assertFalse(playlists.exists(116));
        assertFalse(playlists.exists(1001));
        assertTrue(playlists.exists(101));
    }

    @Test
    public void testFindPlaylist() {
        assertEquals("My Songs", playlists.getPlaylistName(101));
        assertEquals("Your songs the world will listen!", playlists.getPlaylistDescription(101));
        assertEquals("Glass Animals", playlists.getCreatorUsername(101));
        assertEquals(Timestamp.valueOf("2022-01-17 21:10:39.311"), playlists.getDateTimeCreated(101));
        assertTrue(playlists.IsPublic(101));
        assertEquals(8000000, playlists.getNumLikes(101));

        assertNull(playlists.findPlaylist(200));
    }

    @Test
    public void testAddPlaylist() {
        Date date = new Date();
        long time = date.getTime();
        playlists.addPlaylist("milk", "dairy",
                "adminUser", new Timestamp(time), true);
        playlists.exists(116);
        assertTrue(playlists.exists(116));
        assertEquals(116, playlists.latestPlaylistID());
    }

    @Test
    public void testLikePlaylist() {
        playlists.likePlaylist(101);
        assertEquals(8000001, playlists.findPlaylist(101).getNumLikes());
    }

    @Test
    public void testAddToPlaylist() {
        playlists.addToPlaylist(103,203);
        assertTrue(playlists.findPlaylist(103).getSongs().contains(203));
    }

    @Test
    public void testAddableToPlaylist() {
        assertTrue(playlists.addableToPlaylist(103, true));
        assertTrue(playlists.addableToPlaylist(102, true));
        assertFalse(playlists.addableToPlaylist(103, false));
        assertTrue(playlists.addableToPlaylist(102, false));
    }

    @Test
    public void testGetListofPlaylistNames() {
        ArrayList<Integer> playlistIDs = new ArrayList<>();
        playlistIDs.add(101);
        playlistIDs.add(105);
        playlistIDs.add(109);
        ArrayList<String> actual = playlists.getListOfPlaylistNames(playlistIDs);

        ArrayList<String> expect = new ArrayList<>();
        expect.add("My Songs");
        expect.add("Private Songs");
        expect.add("Favourites");

        assertEquals(actual, expect);
    }

    @Test
    public void testGetListofSongsID() {
        ArrayList<Integer> expect = new ArrayList<>();
        expect.add(201);
        expect.add(202);
        expect.add(205);

        ArrayList<Integer> actual = playlists.getListOfSongsID(112);

        assertEquals(actual, expect);
    }
}
