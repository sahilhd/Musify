package com.presenters;

public class EnglishPresenter extends LanguagePresenter {

    /**
     * Constructor of EnglishPresenter
     * @param language a Language type
     */
    public EnglishPresenter(LanguageType language) {
        super(language);
    }


    /**
     * Translating String from English to English
     * @param english a English String
     * @return a English String
     */
    @Override
    public String translateString(String english) {
        return english;
    }

}

