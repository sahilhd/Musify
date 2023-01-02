package com.presenters;

import java.io.Serializable;

public abstract class LanguagePresenter implements SimplePresenter, Serializable {

    protected final LanguageType language;

    /**
     * Constructor for LanguagePresenter
     * @param language the language used in the presenter
     */
    public LanguagePresenter(LanguageType language){
        this.language = language;
    }

    /**
     * An method to translate string from english to another language
     * @param english a English String
     * @return a String in other language
     */
    public abstract String translateString(String english);
    
}
