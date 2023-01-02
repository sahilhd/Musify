package com.artemifyMusicStudio;

import android.os.Bundle;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.UserInputRequestCommandCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * A SearchPage Activity
 */
public class SearchPage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

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
        switch (creatorType){
            case "UserInputCommandCreator":
                return new UserInputRequestCommandCreator(this.activityServiceCache);
            case "TransitionCommandCreator":
                return new PageTransitionCommandCreator(this.activityServiceCache);
            default:
                return null;
        }
    }

    @Override
    protected void populateIdMenuMap() {
        this.idMenuItemMap.put(CommandItemType.POP_UP_SEARCH_USER_DIALOG,
                R.id.search_by_user);
        this.idMenuItemMap.put(CommandItemType.POP_UP_SEARCH_SONG_DIALOG,
                R.id.search_by_song);
        this.idMenuItemMap.put(CommandItemType.POP_UP_SEARCH_PLAYLIST_DIALOG,
                R.id.search_by_playlist);
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                List.of(CommandItemType.POP_UP_SEARCH_USER_DIALOG,
                        CommandItemType.POP_UP_SEARCH_SONG_DIALOG,
                        CommandItemType.POP_UP_SEARCH_PLAYLIST_DIALOG)
        );
        ArrayList<CommandItemType> tempList2 = new ArrayList<>(
                List.of(CommandItemType.EXIT_PAGE)
        );
        menuCommandCreatorMap.put("UserInputCommandCreator", tempList1);
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList2);
    }

}