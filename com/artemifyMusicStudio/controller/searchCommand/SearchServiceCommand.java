package com.artemifyMusicStudio.controller.searchCommand;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.SearchResultPage;
import com.presenters.LanguagePresenter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * An abstract SearchServiceCommand to provide unify helper methods for all its child search command
 */
public abstract class SearchServiceCommand implements View.OnClickListener {
    protected final ActivityServiceCache activityServiceCache;
    protected final LanguagePresenter languagePresenter;
    protected String searchType = "";
    protected final EditText InputTargetName;

    /**
     * Constructor of SearchServiceCommand
     * @param activityServiceCache a PageCreator object
     * @param userInputTargetName a EditText to capture the user input for search
     */
    public SearchServiceCommand(ActivityServiceCache activityServiceCache, EditText userInputTargetName){
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = activityServiceCache.getLanguagePresenter();
        this.InputTargetName = userInputTargetName;
    }

    /**
     * A template method to execute Search related command
     */
    @Override
    public void onClick(View view) {
        // get current page activity
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();

        // Take input from User to get Search ids with the searched name
        String userInputSearchString = InputTargetName.getText().toString();
        ArrayList<String> targetEntityIDs = this.getTargetEntityIDs(userInputSearchString);

        // Check whether songs with the userInputSongName exits
        if (targetEntityIDs != null){
            //this.viewSuccessfulSearchResult(userInputSearchString, searchResults);
            LinkedHashMap<String, String> searchResultMap = populateSearchResultMap(userInputSearchString,
                    targetEntityIDs);
            SearchResultContainer searchResults = new SearchResultContainer(searchResultMap);
            Intent it = new Intent(currentPageActivity, SearchResultPage.class);
            it.putExtra("cache", this.activityServiceCache);
            it.putExtra("searchType", this.searchType);
            it.putExtra("searchResults", searchResults);
            it.putExtra("userInputSearchString", userInputSearchString);
            currentPageActivity.startActivity(it);
        }
        else{
            String warningMsg = this.languagePresenter.translateString(userInputSearchString +
                    " does not exists!");
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A generic protected helper method to populate the search result based on the name that provided from the user
     * @return a HashMap stores the target entity ids as the key and the description of that entity
     *         as a correspondent
     */
    protected LinkedHashMap<String, String> populateSearchResultMap(String searchString,
                                                              ArrayList<String> uniqueIDs){
        int numberOfSongs = uniqueIDs.size();
        LinkedHashMap<String, String> searchResultMap = new LinkedHashMap<>();
        for (int i = 0 ; i < numberOfSongs; i++){
            String uniqueID = uniqueIDs.get(i);
            String currDescription = this.getSearchResultDescription(i+1,
                    searchString, uniqueID);
            searchResultMap.put(uniqueID, currDescription);
        }
        return searchResultMap;
    }

    /**
     * A protected abstract method to force child search commands to define their own way to
     * retrieve the target entity ids based on the search name string
     * @param searchString a String that user typed in to search
     * @return a list stores all searched entity ids in String
     */
    protected abstract ArrayList<String> getTargetEntityIDs (String searchString);

    /**
     * A protected abstract method to force child search commands to define their own description of the search results
     * @param index a integer index
     * @param searchString a String for search
     * @param targetEntityID a String for target entity id
     * @return a String of search result description
     */

    protected abstract String getSearchResultDescription(int index, String searchString, String targetEntityID);


}
