package com.artemifyMusicStudio.controller.searchCommand;

import android.widget.EditText;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.useCase.UserAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * A command to handle the request of search a user with a userID
 */
public class SearchUserCommand extends SearchServiceCommand {
    private final UserAccess acctServiceManager;

    /**
     * Constructor of SearchUserCommand
     * @param activityServiceCache a ActivityServiceCache object
     *
     */
    public SearchUserCommand(ActivityServiceCache activityServiceCache,
                             EditText inputTargetName){
        super(activityServiceCache, inputTargetName);
        this.acctServiceManager = this.activityServiceCache.getUserAcctServiceManager();
        this.searchType = "User";
    }

    @Override
    protected ArrayList<String> getTargetEntityIDs(String userName) {
        if (this.acctServiceManager.exists(userName)){
            // This might need to change when we want a more flexible searching in Phase 2
            return new ArrayList<>(List.of(userName));
        }
        return null;
    }

    @Override
    protected String getSearchResultDescription(int index, String searchString, String targetEntityID) {
        return "  " + index + ". " + searchString;
    }

}
