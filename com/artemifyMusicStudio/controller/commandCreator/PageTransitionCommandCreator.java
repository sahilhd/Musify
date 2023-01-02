package com.artemifyMusicStudio.controller.commandCreator;

import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.pageTransitionCommand.CreateAccountCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.EnableAdminCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.EnableRegularUserHomePageCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.ExitPageCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeAddToExistingPlaylistDisplayCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeLogOutCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeNewPlaylistPageCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokePlaylistDisplayPage;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeProfileAndSettingPageCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeQueueCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeSearchPageCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeSongDisplayPage;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeUploadSongPageCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.InvokeUserDisplayPage;
import com.artemifyMusicStudio.controller.pageTransitionCommand.JumpToCreateNewPlaylistCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.StartLogInCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.ViewFollowersCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.ViewFollowingsCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.ViewQueueCommand;

/**
 *  A transition command factory to manufacture commands that are in transition command  category
 */
public class PageTransitionCommandCreator implements SimpleButtonCommandCreator {
    private final ActivityServiceCache activityServiceCache;
    private String targetID = "";

    /**
     *  A constructor of the transitionCommandCreator
     * @param activityServiceCache a ActivityServiceCache Object
     */
    public PageTransitionCommandCreator(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * A setter to set the targetID
     * @param targetID a string of targetID
     */
    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    @Override
    public View.OnClickListener create(CommandItemType type) {
        switch (type){
            case LOG_IN:
                return new StartLogInCommand(this.activityServiceCache);
            case ACCOUNT_CREATION:
                return new CreateAccountCommand(this.activityServiceCache);
            case INVOKE_SEARCH:
                return new InvokeSearchPageCommand(this.activityServiceCache);
            case INVOKE_QUEUE:
                return new InvokeQueueCommand(this.activityServiceCache);
            case VIEW_QUEUE:
                return new ViewQueueCommand(this.activityServiceCache);
            case ENABLE_ADMIN_MODE:
                return new EnableAdminCommand(this.activityServiceCache);
            case INVOKE_SONG_UPLOAD:
                return new InvokeUploadSongPageCommand(this.activityServiceCache);
            case INVOKE_SONG_DISPLAY:
                return new InvokeSongDisplayPage(this.activityServiceCache, this.targetID);
            case INVOKE_PLAYLIST_DISPLAY:
                return new InvokePlaylistDisplayPage(this.activityServiceCache, this.targetID);
            case INVOKE_USER_DISPLAY:
                return new InvokeUserDisplayPage(this.activityServiceCache, this.targetID);
            case INVOKE_ADD_TO_EXISTING_PLAYLIST_DISPLAY:
                return new InvokeAddToExistingPlaylistDisplayCommand(this.activityServiceCache);
            case PROFILE_AND_SETTING:
                return new InvokeProfileAndSettingPageCommand(this.activityServiceCache);
            case INVOKE_CREATE_NEW_PLAYLIST:
                return new InvokeNewPlaylistPageCommand(this.activityServiceCache);
            case VIEW_FOLLOWINGS:
                return new ViewFollowingsCommand(this.activityServiceCache);
            case VIEW_FOLLOWERS:
                return new ViewFollowersCommand(this.activityServiceCache);
            case LOG_OUT:
                return new InvokeLogOutCommand(this.activityServiceCache);
            case EXIT_PAGE:
                return new ExitPageCommand(this.activityServiceCache);
            case QUIT_ADMIN_MODE:
                return new EnableRegularUserHomePageCommand(this.activityServiceCache);
            case JUMP_TO_CREATE_NEW_PLAYLIST:
                return new JumpToCreateNewPlaylistCommand(this.activityServiceCache);
            default:
                return null;
        }
    }
}
