package com.artemifyMusicStudio.controller;

/**
 * An Enum class to store all menu item type
 */
public enum CommandItemType {

    // For RegularUserHomePage
    PROFILE_AND_SETTING,
    INVOKE_SEARCH,
    INVOKE_CREATE_NEW_PLAYLIST,
    INVOKE_SONG_UPLOAD,
    INVOKE_QUEUE,
    //

    // For POP DIALOG
    POP_UP_SEARCH_SONG_DIALOG,
    POP_UP_SEARCH_USER_DIALOG,
    POP_UP_SEARCH_PLAYLIST_DIALOG,
    POP_UP_REMOVE_FROM_QUEUE_DIALOG,
    //

    // For Search Page
    SEARCH_SONG,
    SEARCH_PLAYLIST,
    SEARCH_USER,
    //

    // For Search Result Page
    INVOKE_SONG_DISPLAY,
    INVOKE_PLAYLIST_DISPLAY,
    INVOKE_USER_DISPLAY,
    //
    EXIT_PAGE,
    REGULAR_LOG_IN_MODE,
    ADMIN_LOG_IN_MODE,
    ENABLE_ADMIN_MODE,
    QUIT_ADMIN_MODE,
    DELETE_USER,
    BAN_USER,
    UNBAN_USER,
    GRANT_ADMIN_RIGHT,
    CREATE_REGULAR_ACCOUNT,
    LOG_IN,
    LOG_OUT,
    ACCOUNT_CREATION,
    // For UploadSongPage
    UPLOAD_SONG,
    // For SongDisplayPage
    VIEW_LYRICS,
    INVOKE_ADD_TO_EXISTING_PLAYLIST_DISPLAY,
    JUMP_TO_CREATE_NEW_PLAYLIST,
    PLAY_SONG,
    ADD_TO_QUEUE,
    // For CreateNewPlaylistPage
    ADD_TO_NEW_PLAYLIST,
    // For UserDisplayPage:
    VIEW_FOLLOWERS,
    VIEW_FOLLOWINGS,

    // For PlaylistDisplayPage
    VIEW_PLAYLIST_SONGS,
    VIEW_CREATOR,
    PLAY_PLAYLIST,
    // For NewPlaylistPage
    CREATE_NEW_PLAYLIST,
    // For MyAccountPage
    VIEW_LOGIN_HISTORY,

    // For QueueDisplay Page
    VIEW_QUEUE,
    SHUFFLE_QUEUE,
    SKIP_SONG,
    REMOVE_FROM_QUEUE,
    PLAY_PREVIOUS_SONG,
    REPEAT_SONG_ONCE,
    REPEAT_SONG_INF
}
