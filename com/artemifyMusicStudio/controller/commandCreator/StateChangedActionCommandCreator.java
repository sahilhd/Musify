package com.artemifyMusicStudio.controller.commandCreator;

import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.stateChangedActionCommand.AddToQueueCommand;
import com.artemifyMusicStudio.controller.stateChangedActionCommand.PlayPlaylistCommand;
import com.artemifyMusicStudio.controller.stateChangedActionCommand.PlaySongCommand;

public class StateChangedActionCommandCreator implements SimpleButtonCommandCreator {

    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor of ActionCommandCreator
     * @param activityServiceCache a ActivityServiceCache object
     */
    public StateChangedActionCommandCreator(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }
    @Override
    public View.OnClickListener create(CommandItemType type) {
        switch (type){
            case PLAY_SONG:
                return new PlaySongCommand(activityServiceCache);
            case ADD_TO_QUEUE:
                return new AddToQueueCommand(this.activityServiceCache);
            case PLAY_PLAYLIST:
                return new PlayPlaylistCommand(this.activityServiceCache,
                        this.activityServiceCache.getLanguagePresenter(),
                        this.activityServiceCache.getTargetPlaylistID());
            default:
                return null;
        }
    }
}
