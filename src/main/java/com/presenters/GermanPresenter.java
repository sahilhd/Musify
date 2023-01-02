package com.presenters;

public class GermanPresenter extends LanguagePresenter {

    /**
     * Constructor for LanguagePresenter
     *
     * @param language the language used in the presenter
     */
    public GermanPresenter(LanguageType language) {
        super(language);
    }

    /**
     * Translate a String to a German String
     * @param otherLanguageString string from other language
     * @return a German String
     */
    @Override
    public String translateString(String otherLanguageString) {
        return translator.translate("","de", otherLanguageString);
    }
}
