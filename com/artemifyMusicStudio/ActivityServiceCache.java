package com.artemifyMusicStudio;

import com.presenters.EnglishPresenter;
import com.presenters.LanguagePresenter;
import com.presenters.LanguageType;
import com.useCase.PlaylistEntityContainer;
import com.useCase.PlaylistManager;
import com.useCase.Queue;
import com.useCase.SongEntityContainer;
import com.useCase.SongManager;
import com.useCase.UserAccess;
import com.useCase.UserEntityContainer;

import java.io.Serializable;

/**
 * A ActivityServiceCache class with responsibility to parsing important data to conduct page activities in the program
 *
 * ====Private Attribute====:
 *
 * pageActivity: A PageActivity object
 *
 * userAcctServiceManager: A UserAccess object in Use Case layer
 *
 * queueManager: A Queue class object in Use Case layer
 *
 * playlistManager: A PlaylistManager object in Use Case layer
 *
 * songManager: A SongManager object in Use Case layer
 *
 * userID: A string to represent a user's ID
 *
 *
 */
public class ActivityServiceCache implements Serializable {

    private PageActivity currentPageActivity;
    private LanguagePresenter languagePresenter;
    private final UserAccess userAcctServiceManager;
    private final Queue queueManager;
    private final PlaylistManager playlistManager;
    private final SongManager songManager;
    private String userID;

    private String targetUserID;

    private String targetSongID;

    private String targetPlaylistID;

    /**
     * A constructor of the PageCreator class
     * @param languagePresenter a LanguagePresenter object
     * @param userAcctServiceManager A UserAccess
     * @param queueManager A Queue class
     * @param playlistManager A PlaylistManager object
     * @param songManager A SongManager object
     * @param userID A string to represent a user's ID
     */
    public ActivityServiceCache(LanguagePresenter languagePresenter, UserAccess userAcctServiceManager,
                                Queue queueManager, PlaylistManager playlistManager,
                                SongManager songManager, String userID) {
        this.languagePresenter = languagePresenter;
        this.userAcctServiceManager = userAcctServiceManager;
        this.queueManager = queueManager;
        this.playlistManager = playlistManager;
        this.songManager = songManager;
        this.userID = userID;
        this.targetUserID = "";
        this.targetSongID = "";
        this.targetPlaylistID = "";
    }

    /**
     * This constructor simply serve as removing any warnings in the IDE
     */
    public ActivityServiceCache(){
        languagePresenter = new EnglishPresenter(LanguageType.ENGLISH);
        userAcctServiceManager = new UserAccess(new UserEntityContainer());
        queueManager = new Queue();
        playlistManager = new PlaylistManager(new PlaylistEntityContainer());
        songManager = new SongManager(userAcctServiceManager, playlistManager, new SongEntityContainer());
    }

    /**
     * A setter to set the page activity
     * @param pageActivity a PageActivity object
     */
    public void setCurrentPageActivity(PageActivity pageActivity) {
        this.currentPageActivity = pageActivity;
    }

    /**
     * A setter to set userID for the PageCreator
     * @param userID a userID string
     */
    public void setUserID(String userID){this.userID = userID;}

    /**
     * A setter to set languagePresenter for the PageCreator
     * @param languagePresenter a LanguagePresenter object
     */
    public void setLanguagePresenter(LanguagePresenter languagePresenter) {
        this.languagePresenter = languagePresenter;
    }

    /**
     * A setter to set target user id when the searching of user is successful
     * @param targetUserID a String to represent targetUserID
     */
    public void setTargetUserID(String targetUserID) {
        this.targetUserID = targetUserID;
    }

    /**
     * A setter to set target song id when the searching of song is successful
     * @param targetSongID a String to represent targetSongID
     */
    public void setTargetSongID(String targetSongID) {
        this.targetSongID = targetSongID;
    }

    /**
     * A setter to set target playlist id when the searching of playlist is successful
     * @param targetPlaylistID a String to represent targetPlaylistID
     */
    public void setTargetPlaylistID(String targetPlaylistID) {
        this.targetPlaylistID = targetPlaylistID;
    }

    /**
     * A getter to get the Current Page Activity
     * @return this.currentPageActivity
     */
    public PageActivity getCurrentPageActivity() {
        return currentPageActivity;
    }

    /**
     * A getter to get the userAcctServiceManager
     * @return this.userAcctServiceManager
     */
    public UserAccess getUserAcctServiceManager() {
        return userAcctServiceManager;
    }

    /**
     * A getter to get the language presenter
     * @return this.languagePresenter
     */
    public LanguagePresenter getLanguagePresenter() {
        return languagePresenter;
    }

    /**
     * A getter to get the userID
     * @return this.userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * A getter to get the target userID
     * @return this.targetUserID
     */
    public String getTargetUserID() {
        return targetUserID;
    }

    /**
     * A getter to get the target playlist id
     * @return this.targetPlaylistID
     */
    public String getTargetPlaylistID() {
        return targetPlaylistID;
    }

    /**
     * A getter to get the target song id
     * @return this.targetSongID
     */
    public String getTargetSongID() {
        return targetSongID;
    }

    /**
     * A getter to get the playlist manager
     * @return this.playListManager
     */
    public PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    /**
     * A getter to get the song manager
     * @return this.songManager
     */
    public SongManager getSongManager() {
        return songManager;
    }

    /**
     * A getter to get the Queue manager
     * @return this.queueManager
     */
    public Queue getQueueManager() {
        return queueManager;
    }

}
