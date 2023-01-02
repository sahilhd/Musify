package com.artemifyMusicStudio;

import android.os.Bundle;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.QueueServiceCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.UserInputRequestCommandCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * A QueueDisplayPage Activity
 */
public class QueueDisplayPage extends PageActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_display_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        
        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        switch (creatorType) {
            case "QueueServiceCommandCreator":
                return new QueueServiceCommandCreator(this.activityServiceCache);
            case "UserInputCommandCreator" :
                return new UserInputRequestCommandCreator(this.activityServiceCache);
            case "TransitionCommandCreator" :
                return new PageTransitionCommandCreator(this.activityServiceCache);
            default:
                return null;
        }
    }

    @Override
    protected void populateIdMenuMap() {
        this.idMenuItemMap.put(CommandItemType.VIEW_QUEUE,
                R.id.view_queue);
        this.idMenuItemMap.put(CommandItemType.SHUFFLE_QUEUE,
                R.id.shuffle_queue);
        this.idMenuItemMap.put(CommandItemType.SKIP_SONG,
                R.id.skip_song);
        this.idMenuItemMap.put(CommandItemType.POP_UP_REMOVE_FROM_QUEUE_DIALOG,
                R.id.remove_from_queue);
        this.idMenuItemMap.put(CommandItemType.PLAY_PREVIOUS_SONG,
                R.id.play_previous_song);
        this.idMenuItemMap.put(CommandItemType.REPEAT_SONG_ONCE,
                R.id.repeat_song_once);
        this.idMenuItemMap.put(CommandItemType.REPEAT_SONG_INF,
                R.id.repeat_song_infinitely);
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);

    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                List.of(
                        CommandItemType.SHUFFLE_QUEUE,
                        CommandItemType.SKIP_SONG,
                        CommandItemType.PLAY_PREVIOUS_SONG,
                        CommandItemType.REPEAT_SONG_ONCE,
                        CommandItemType.REPEAT_SONG_INF)
        );
        ArrayList<CommandItemType> tempList2 = new ArrayList<>(
                List.of(CommandItemType.POP_UP_REMOVE_FROM_QUEUE_DIALOG)
        );
        ArrayList<CommandItemType> tempList3 = new ArrayList<>(
                List.of(CommandItemType.VIEW_QUEUE,
                        CommandItemType.EXIT_PAGE)
        );
        menuCommandCreatorMap.put("QueueServiceCommandCreator", tempList1);
        menuCommandCreatorMap.put("UserInputCommandCreator", tempList2);
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList3);
    }
}
