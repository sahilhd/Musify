package com.presenters;

/**
 * A GPresenter class that create the corresponding Presenter class.
 */
public class PresenterCreator {

    /**
     * A method to create a concrete language presenter
     * @param languageType an Enum from the LanguageType
     * @return a language presenter object
     */
    public LanguagePresenter createLanguagePresenter(LanguageType languageType) {
        if (languageType == LanguageType.ENGLISH) {
            return new EnglishPresenter(languageType);
        }
        else if (languageType == LanguageType.GERMAN){
            return  new GermanPresenter(languageType);
        }
        return null;
    }
}
