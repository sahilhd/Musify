package com.artemifyMusicStudio.controller.commandCreator;

import android.view.View;
import android.widget.EditText;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.userInputRequestCommand.AddToNewPlaylistCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.AdminLogInCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.CreateNewPlaylistCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.CreateRegularAccountCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.PopUpInputDialogCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.RegularLogInCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.UploadSongCommand;

/**
 *  A input command factory to manufacture commands that are in input command  category which require user to provide inputs
 */
public class UserInputRequestCommandCreator implements SimpleButtonCommandCreator {
    private final ActivityServiceCache activityServiceCache;
    private EditText inputUserName;
    private EditText inputPassword;

    private EditText inputSongName;
    private EditText inputMinute;
    private EditText inputSecond;
    private EditText inputLyrics;
    private boolean isPublic;

    private EditText inputPlaylistName;
    private EditText inputDescription;


    /**
     * Constructor of UserInputCommand Creator
     * @param activityServiceCache a ActivityServiceCache object
     * @param inputUserName a inputUserName
     * @param inputPassword a inputPassword
     */
    public UserInputRequestCommandCreator(ActivityServiceCache activityServiceCache,
                                          EditText inputUserName, EditText inputPassword){
        this.activityServiceCache = activityServiceCache;
        this.inputUserName = inputUserName;
        this.inputPassword = inputPassword;
    }

    /**
     * Constructor of UserInputCommand Creator
     * @param activityServiceCache a ActivityServiceCache object
     * @param inputSongName a inputSongName
     * @param inputMinute a inputMinute
     * @param inputSecond a inputSecond
     * @param inputLyrics a inputLyrics
     * @param isPublic whether isPublic
     */
    public UserInputRequestCommandCreator(ActivityServiceCache activityServiceCache, EditText inputSongName,
                                          EditText inputMinute, EditText inputSecond, EditText inputLyrics,
                                          boolean isPublic){
        this.activityServiceCache = activityServiceCache;
        this.inputSongName = inputSongName;
        this.inputMinute = inputMinute;
        this.inputSecond = inputSecond;
        this.inputLyrics = inputLyrics;
        this.isPublic = isPublic;
    }

    /**
     * Constructor of UserInputCommand Creator
     * @param activityServiceCache a ActivityServiceCache object
     * @param inputPlaylistName a inputPlaylistName
     * @param inputDescription a inputDescription
     * @param isPublic whether isPublic
     */
    public UserInputRequestCommandCreator(ActivityServiceCache activityServiceCache,
                                          EditText inputPlaylistName, EditText inputDescription,
                                          boolean isPublic){
        this.activityServiceCache = activityServiceCache;
        this.inputPlaylistName = inputPlaylistName;
        this.inputDescription = inputDescription;
        this.isPublic = isPublic;
    }

    /**
     * Constructor of UserInputCommand Creator
     * @param activityServiceCache a ActivityServiceCache object
     */
    public UserInputRequestCommandCreator(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public View.OnClickListener create(CommandItemType type) {
        switch (type){
            case REGULAR_LOG_IN_MODE:
                return new RegularLogInCommand(this.activityServiceCache, this.activityServiceCache.getLanguagePresenter(), this.activityServiceCache.getUserAcctServiceManager(),
                        this.inputUserName, this.inputPassword);
            case ADMIN_LOG_IN_MODE:
                return new AdminLogInCommand(this.activityServiceCache, this.activityServiceCache.getLanguagePresenter(), this.activityServiceCache.getUserAcctServiceManager(),
                        this.inputUserName, this.inputPassword);
            case UPLOAD_SONG:
                return new UploadSongCommand(this.activityServiceCache,
                        this.activityServiceCache.getLanguagePresenter(), this.inputSongName,
                        this.inputMinute, this.inputSecond, this.inputLyrics, this.isPublic);
            case CREATE_REGULAR_ACCOUNT:
                return new CreateRegularAccountCommand(this.activityServiceCache, this.activityServiceCache.getLanguagePresenter(), this.activityServiceCache.getUserAcctServiceManager(),
                        this.activityServiceCache.getPlaylistManager(), this.inputUserName, this.inputPassword);
            case CREATE_NEW_PLAYLIST:
                return new CreateNewPlaylistCommand(this.activityServiceCache,
                        this.activityServiceCache.getLanguagePresenter(), this.inputPlaylistName,
                        this.inputDescription, this.isPublic);
            case ADD_TO_NEW_PLAYLIST:
                return new AddToNewPlaylistCommand(this.activityServiceCache,
                        this.activityServiceCache.getLanguagePresenter(), this.inputPlaylistName,
                        this.inputDescription, this.isPublic);
            case POP_UP_SEARCH_USER_DIALOG:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Search by User",
                        "Enter the username of the user you wish to search",
                        CommandItemType.SEARCH_USER);
            case POP_UP_SEARCH_PLAYLIST_DIALOG:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Search by Playlist",
                        "Enter the name of the playlist you wish to search",
                        CommandItemType.SEARCH_PLAYLIST);
            case POP_UP_SEARCH_SONG_DIALOG:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Search by Song",
                        "Enter the name of the song you wish to search",
                        CommandItemType.SEARCH_SONG);
            case POP_UP_REMOVE_FROM_QUEUE_DIALOG:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Remove from Queue",
                        "Enter the index of the song in the queue you wish to remove",
                        CommandItemType.REMOVE_FROM_QUEUE);
            case BAN_USER:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Ban a user:",
                        "Enter the username of the user you wish to ban",
                        CommandItemType.BAN_USER);
            case UNBAN_USER:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Unban a user:",
                        "Enter the username of the user you wish to unban",
                        CommandItemType.UNBAN_USER);
            case DELETE_USER:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Delete a user:",
                        "Enter the username of the user you wish to delete",
                        CommandItemType.DELETE_USER);
            case GRANT_ADMIN_RIGHT:
                return new PopUpInputDialogCommand(this.activityServiceCache,
                        "Make existing user an admin:",
                        "Enter the username of the user you wish to make an admin",
                        CommandItemType.GRANT_ADMIN_RIGHT);
            default:
                return null;
        }
    }
}
