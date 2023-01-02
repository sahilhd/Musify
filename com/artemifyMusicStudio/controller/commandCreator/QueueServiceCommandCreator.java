package com.artemifyMusicStudio.controller.commandCreator;

import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.queueServiceCommand.PlayPreviousSongCommand;
import com.artemifyMusicStudio.controller.queueServiceCommand.RepeatSongInfCommand;
import com.artemifyMusicStudio.controller.queueServiceCommand.RepeatSongOnceCommand;
import com.artemifyMusicStudio.controller.queueServiceCommand.ShuffleQueueCommand;
import com.artemifyMusicStudio.controller.queueServiceCommand.SkipSongCommand;
import com.presenters.LanguagePresenter;
import com.useCase.Queue;

public class QueueServiceCommandCreator implements SimpleButtonCommandCreator {

    private final LanguagePresenter languagePresenter;
    private final ActivityServiceCache activityServiceCache;
    private final Queue queueService;

    /**
     * Constructor for QueueServiceCommandCreator class
     * @param activityServiceCache  instance of ActivityServiceCache class to parse important data
     *                              to conduct page activities
     */
    public QueueServiceCommandCreator(ActivityServiceCache activityServiceCache) {
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = this.activityServiceCache.getLanguagePresenter();
        this.queueService = this.activityServiceCache.getQueueManager();
    }

    @Override
    public View.OnClickListener create(CommandItemType type) {
        switch (type) {
            case SHUFFLE_QUEUE:
                return new ShuffleQueueCommand(activityServiceCache, languagePresenter,
                        queueService);
            case SKIP_SONG:
                return new SkipSongCommand(activityServiceCache, languagePresenter, queueService);
            case PLAY_PREVIOUS_SONG:
                return new PlayPreviousSongCommand(activityServiceCache, languagePresenter,
                        queueService);
            case REPEAT_SONG_ONCE:
                return new RepeatSongOnceCommand(activityServiceCache, languagePresenter,
                        queueService);
            case REPEAT_SONG_INF:
                return new RepeatSongInfCommand(activityServiceCache, languagePresenter,
                        queueService);
            default:
                return null;
        }
    }
}
