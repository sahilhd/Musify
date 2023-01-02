package com.gateway;

import androidx.appcompat.app.AppCompatActivity;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.useCase.PlaylistEntityContainer;
import com.useCase.SongEntityContainer;
import com.useCase.UserEntityContainer;

import java.io.IOException;

/**
 * An abstract Gateway class that handles system I/O.
 */

public abstract class IGateway implements SimpleGateway{

    protected final AppCompatActivity currentPageActivity;

    /**
     * Constructor of IGateway
     * @param currentPageActivity a PageActivity object
     */
    public IGateway(AppCompatActivity currentPageActivity){
        this.currentPageActivity = currentPageActivity;
    }

    /**
     * An abstract method to save entities to a file
     * @param fileName a String to represent the file name
     * @param entities a container that stores the entities to be saved
     * @throws IOException throw IOException
     */
    public abstract void saveToFile(String fileName, Object entities) throws IOException;

    /**
     * An abstract method that read user entities from a file
     * @return a UserContainer class object
     *
     */
    public abstract UserEntityContainer readUsersFromFile();

    /**
     * An abstract method that read playlist entities from a file
     * @return a PlaylistContainer object
     *
     */
    public abstract PlaylistEntityContainer readPlaylistsFromFile();

    /**
     * An abstract method that read song entities from a file
     * @return a SongContainer object
     */
    public abstract SongEntityContainer readSongsFromFile();

    /**
     * An abstract method that read activityServiceCache from a file
     * @return a ActivityServiceCache object
     *
     */
    public abstract ActivityServiceCache readActivityServiceCacheFromFile();
}
