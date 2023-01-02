package com.artemifyMusicStudio.controller.searchCommand;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A class act as a container to store the search result
 */
public class SearchResultContainer implements Serializable {
    private final HashMap<String, String> searchResultMap;

    /**
     * Constructor of Search Result Container
     * @param searchResultMap a map stores the search result info
     */
    public SearchResultContainer(HashMap<String, String> searchResultMap){
        this.searchResultMap = searchResultMap;
    }

    /**
     * A getter to get the search result info
     * @return this.searchResultMap
     */
    public HashMap<String, String> getSearchResultMap() {
        return searchResultMap;
    }

}
