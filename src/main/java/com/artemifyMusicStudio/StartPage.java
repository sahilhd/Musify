package com.artemifyMusicStudio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.entity.Playlist;
import com.entity.Song;
import com.entity.User;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.presenters.LanguageType;
import com.presenters.PresenterCreator;
import com.useCase.PlaylistEntityContainer;
import com.useCase.PlaylistManager;
import com.useCase.Queue;
import com.useCase.SongEntityContainer;
import com.useCase.SongManager;
import com.useCase.UserAccess;
import com.useCase.UserEntityContainer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Program Starting Page Activity
 */
public class StartPage extends AppCompatActivity {
    private IGateway ioGateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        // Initialize the IGateway
        GatewayCreator gatewayCreator = new GatewayCreator();
        this.ioGateway = gatewayCreator.createIGateway(FileType.SER, this);
        try {
            Button button = findViewById(R.id.button_start);
            button.setOnClickListener(v -> {
                ActivityServiceCache finalActivityServiceCache = getActivityServiceCache();

                Intent it = new Intent(StartPage.this, MainPage.class);
                it.putExtra("cache", finalActivityServiceCache);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
            });

            Button button1 = findViewById(R.id.generate_default_entities);
            button1.setOnClickListener(v -> {
                UserEntityContainer users = populateUserEntities();
                PlaylistEntityContainer playlists = populatePlaylistEntities();
                SongEntityContainer songs = populateSongEntities();

                // Initialize Language Presenter
                PresenterCreator presenterCreator = new PresenterCreator();
                LanguagePresenter languagePresenter = presenterCreator.createLanguagePresenter(LanguageType.ENGLISH);

                // Initialize the UserAccess, PlaylistManager, SongManager and Queue for only once
                UserAccess acctServiceManager = new UserAccess(users);
                PlaylistManager playlistManager = new PlaylistManager(playlists);
                SongManager songManager = new SongManager(acctServiceManager, playlistManager, songs);
                Queue queue = new Queue();

                // Initialize the ActivityServiceCache
                ActivityServiceCache activityServiceCache =
                        new ActivityServiceCache(languagePresenter, acctServiceManager, queue,
                        playlistManager, songManager, "");
                try {
                    ioGateway.saveToFile("Users.ser", users);
                    ioGateway.saveToFile("Playlists.ser", playlists);
                    ioGateway.saveToFile("Songs.ser", songs);
                    ioGateway.saveToFile("ActivityServiceCache.ser", activityServiceCache);
                } catch (FileNotFoundException e) {
                    Log.e("warning", "cannot find file");
                } catch (IOException e){
                    Log.e("warning", "IO exception");
                }
            });
        }catch (Exception e){
            Log.e("warning", "other things go wrong");
        }
    }

    private ActivityServiceCache getActivityServiceCache(){
        ActivityServiceCache activityServiceCache = ioGateway.readActivityServiceCacheFromFile();
        if (activityServiceCache != null){
            return activityServiceCache;
        }else{
            return buildActivityServiceCacheFromEntities();
        }
    }

    private ActivityServiceCache buildActivityServiceCacheFromEntities() {
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER, this);
        UserEntityContainer users = ioGateway.readUsersFromFile();
        PlaylistEntityContainer playlists = ioGateway.readPlaylistsFromFile();
        SongEntityContainer songs = ioGateway.readSongsFromFile();
        if(users==null || playlists == null || songs == null){
            return null;
        }else{
            // Initialize Language Presenter
            PresenterCreator presenterCreator = new PresenterCreator();
            LanguagePresenter languagePresenter = presenterCreator.createLanguagePresenter(LanguageType.ENGLISH);

            // Initialize the UserAccess, PlaylistManager, SongManager and Queue for only once
            UserAccess acctServiceManager = new UserAccess(users);
            PlaylistManager playlistManager = new PlaylistManager(playlists);
            SongManager songManager = new SongManager(acctServiceManager, playlistManager, songs);
            Queue queue = new Queue();

            // Initialize a ActivityServiceCache
            return new ActivityServiceCache(languagePresenter,acctServiceManager,
                    queue,playlistManager, songManager, "");
        }
    }

    private UserEntityContainer populateUserEntities(){
        // Fake User entities based on Nimit's .csv files
        User user1 = new User("Glass Animals",
                "pwd1",
                "english",
                new ArrayList<>(List.of(Timestamp.valueOf("2021-06-17 21:10:39.311"))),
                false,
                false,
                new ArrayList<>(List.of("Dua Lipa")),
                new ArrayList<>(Arrays.asList("The Kid Laroi", "Dua Lipa", "adminUser", "foreignUser")),
                35933859, 101, 102, 103,
                new ArrayList<>(Arrays.asList(101,103)), new ArrayList<>(List.of(102)),
                new ArrayList<>(Arrays.asList(101,103,106,107)));
        User user2 = new User("The Kid Laroi",
                "pwd2",
                "english",
                new ArrayList<>(List.of(Timestamp.valueOf("2021-06-17 21:10:39.311"))),
                false,
                false,
                new ArrayList<>(List.of("Glass Animals")),
                new ArrayList<>(Arrays.asList("Dua Lipa", "adminUser")),
                41549942,104,105,106,
                new ArrayList<>(Arrays.asList(104,106)), new ArrayList<>(List.of(105)),
                new ArrayList<>(Arrays.asList(101,109)));

        User user3 = new User("Dua Lipa",
                "pwd3",
                "english",
                new ArrayList<>(List.of(Timestamp.valueOf("2021-06-19 21:22:01.555"))),
                true,
                false,
                new ArrayList<>(List.of("Glass Animals", "The Kid Laroi")),
                new ArrayList<>(Arrays.asList("Glass Animals", "adminUser")),
                71825610,107,108,109,
                new ArrayList<>(Arrays.asList(107,109)), new ArrayList<>(List.of(108)),
                new ArrayList<>(Arrays.asList(101,104)));
        User user4 = new User("adminUser",
                "pwd4",
                "english",
                new ArrayList<>(List.of(Timestamp.valueOf("2021-06-17 21:10:09.699"))),
                false,
                true,
                new ArrayList<>(Arrays.asList("The Kid Laroi", "Dua Lipa", "foreignUser")),
                new ArrayList<>(),
                299,110,111,112,
                new ArrayList<>(Arrays.asList(110,112)), new ArrayList<>(List.of(111)),
                new ArrayList<>(Arrays.asList(101,104,109)));
        User user5 = new User("foreignUser",
                "pwd5",
                "english",
                new ArrayList<>(List.of(Timestamp.valueOf("2021-06-17 21:11:01.846"))),
                false,
                false,
                new ArrayList<>(List.of("Glass Animals")),
                new ArrayList<>(List.of("adminUser")),
                900,113,114,115,
                new ArrayList<>(Arrays.asList(113,115)), new ArrayList<>(List.of(114)),
                new ArrayList<>(Arrays.asList(103,106,107)));
        UserEntityContainer userEntityContainer = new UserEntityContainer();
        userEntityContainer.add(user1);
        userEntityContainer.add(user2);
        userEntityContainer.add(user3);
        userEntityContainer.add(user4);
        userEntityContainer.add(user5);
        return userEntityContainer;
    }

    private SongEntityContainer populateSongEntities(){
        Song song201 = new Song(201, "Heat Waves", new int[]{3, 59}, "Glass Animals",
                Timestamp.valueOf("2022-06-17 21:10:39.311"), 1000000, 2000000,
                "Heat waves been faking me out", true);
        Song song202 = new Song(202, "STAY", new int[]{2, 22}, "The Kid Laroi",
                Timestamp.valueOf("2022-06-18 21:10:39.311"), 500000,3000000,
                "I need you to stay",true);
        Song song203 = new Song(203, "Untitled", new int[]{3, 49}, "adminUser",
                Timestamp.valueOf("2022-06-19 21:10:39.311"), 0,0,
                "Easy as pie", false);
        Song song204 = new Song(204, "Untitled2", new int[]{0, 1}, "foreignUser",
                Timestamp.valueOf("2022-07-18 21:10:39.311"), 0,0,
                "A dime a dozen", false);
        Song song205 = new Song(205, "One Kiss", new int[]{3, 35}, "Dua Lipa",
                Timestamp.valueOf("2012-06-18 21:10:39.311"), 10000000,1929034,
                "One kiss is all it takes", true);
        SongEntityContainer songs = new SongEntityContainer();
        songs.add(song201);
        songs.add(song202);
        songs.add(song203);
        songs.add(song204);
        songs.add(song205);
        return songs;
    }

    private PlaylistEntityContainer populatePlaylistEntities(){
        // Fake Playlists entities based on Nimit's .csv files
        Playlist playlist101 = new Playlist(101, "My Songs", "Your songs the world will listen!",
                "Glass Animals", Timestamp.valueOf("2022-01-17 21:10:39.311"), true,
                new ArrayList<>(List.of(201)),8000000);
        Playlist playlist102 = new Playlist(102, "Private Songs", "Unlisted soundtracks",
                "Glass Animals", Timestamp.valueOf("2022-01-17 21:10:39.311"), false,
                new ArrayList<>(),0);
        Playlist playlist103 = new Playlist(103, "Favourites", "Songs you like will appear here!",
                "Glass Animals", Timestamp.valueOf("2022-02-17 21:10:39.311"), true,
                new ArrayList<>(Arrays.asList(202, 205)),4000000);
        Playlist playlist104 = new Playlist(104, "My Songs", "Your songs the world will listen!",
                "The Kid Laroi", Timestamp.valueOf("2022-01-17 21:10:39.311"), true,
                new ArrayList<>(List.of(202)),7000000);
        Playlist playlist105 = new Playlist(105, "Private Songs", "Unlisted soundtracks",
                "The Kid Laroi", Timestamp.valueOf("2022-01-17 21:10:39.311"), false,
                new ArrayList<>(),0);
        Playlist playlist106 = new Playlist(106, "Favourites", "Songs you like will appear here!",
                "The Kid Laroi", Timestamp.valueOf("2022-01-17 21:10:39.311"), true,
                new ArrayList<>(List.of(205)),10000000);
        Playlist playlist107 = new Playlist(107, "My Songs", "Your songs the world will listen!",
                "Dua Lipa", Timestamp.valueOf("2022-01-17 21:10:39.311"), true,
                new ArrayList<>(List.of(205)),8000000);
        Playlist playlist108 = new Playlist(108, "Private Songs", "Unlisted soundtracks",
                "Dua Lipa", Timestamp.valueOf("2022-01-17 21:10:39.311"), false,
                new ArrayList<>(),0);
        Playlist playlist109 = new Playlist(109, "Favourites", "Songs you like will appear here!",
                "Dua Lipa", Timestamp.valueOf("2022-02-17 21:10:39.311"), true,
                new ArrayList<>(List.of(201)),5000000);
        Playlist playlist110 = new Playlist(110, "My Songs", "Your songs the world will listen!",
                "adminUser", Timestamp.valueOf("2022-01-17 21:10:39.311"), true,
                new ArrayList<>(),2);
        Playlist playlist111 = new Playlist(111, "Private Songs", "Unlisted soundtracks",
                "adminUser", Timestamp.valueOf("2022-01-17 21:10:39.311"), false,
                new ArrayList<>(203),0);
        Playlist playlist112 = new Playlist(112, "Favourites", "Songs you like will appear here!",
                "adminUser", Timestamp.valueOf("2022-02-17 21:10:39.311"), true,
                new ArrayList<>(Arrays.asList(201, 202, 205)),1);
        Playlist playlist113 = new Playlist(113, "My Songs", "Your songs the world will listen!",
                "foreignUser", Timestamp.valueOf("2022-01-17 21:10:39.311"), true,
                new ArrayList<>(),9);
        Playlist playlist114 = new Playlist(114, "Private Songs", "Unlisted soundtracks",
                "foreignUser", Timestamp.valueOf("2022-01-17 21:10:39.311"), false,
                new ArrayList<>(204),0);
        Playlist playlist115 = new Playlist(115, "Favourites", "Songs you like will appear here!",
                "foreignUser", Timestamp.valueOf("2022-02-17 21:10:39.311"), true,
                new ArrayList<>(List.of(205)),5);
        PlaylistEntityContainer playlistEntityContainer = new PlaylistEntityContainer();
        playlistEntityContainer.add(playlist101);
        playlistEntityContainer.add(playlist102);
        playlistEntityContainer.add(playlist103);
        playlistEntityContainer.add(playlist104);
        playlistEntityContainer.add(playlist105);
        playlistEntityContainer.add(playlist106);
        playlistEntityContainer.add(playlist107);
        playlistEntityContainer.add(playlist108);
        playlistEntityContainer.add(playlist109);
        playlistEntityContainer.add(playlist110);
        playlistEntityContainer.add(playlist111);
        playlistEntityContainer.add(playlist112);
        playlistEntityContainer.add(playlist113);
        playlistEntityContainer.add(playlist114);
        playlistEntityContainer.add(playlist115);
        return playlistEntityContainer;
    }
}
