package com.artemifyMusicStudio.controller;

import android.view.View;

/**
 * A Interface to create a View.OnClickListener
 */
public interface SimpleButtonCommandCreator {

    View.OnClickListener create(CommandItemType type);
}
