package com.gateway;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.useCase.PlaylistEntityContainer;
import com.useCase.SongEntityContainer;
import com.useCase.UserEntityContainer;

import java.io.*;


/**
 * A Gateway class that handles .ser files.
 */

public class SerGateway extends IGateway{

    /**
     * Constructor for SerGateway
     * @param currentPageActivity a PageActivity object
     */
    public SerGateway(AppCompatActivity currentPageActivity) {
        super(currentPageActivity);
    }

    /**
     * A method to save entities to a .ser file
     * @param fileName a String to represent the file name
     * @param entities a container that stores the entities to be saved
     * @throws IOException throw IOException
     */
    @Override
    public void saveToFile(String fileName, Object entities) throws IOException {
        FileOutputStream file = this.currentPageActivity.openFileOutput(fileName,
                Context.MODE_PRIVATE);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(entities);
        output.close();
    }

    /**
     * A method that read user entities from a .ser file
     *
     * @return a UserContainer class object
     *
     */
    @Override
    public UserEntityContainer readUsersFromFile() {
        try{
            FileInputStream file = this.currentPageActivity.openFileInput("Users.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // serialize the Map
            UserEntityContainer users = (UserEntityContainer) input.readObject();
            input.close();
            return users;
        } catch (FileNotFoundException e) {
            Log.e("warning", "cannot find file");
            return null;
        } catch (IOException e) {
            Log.e("warning", "IOException");
            return null;
        } catch (ClassNotFoundException e) {
            Log.e("warning", "No class object with type UserEntityContainer");
            return null;
        }
    }

    /**
     * A method that read playlist entities from a .ser file
     *
     * @return a PlaylistContainer object
     */
    @Override
    public PlaylistEntityContainer readPlaylistsFromFile() {
        try{
            FileInputStream file = this.currentPageActivity.openFileInput("Playlists.ser") ;
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // serialize the Map
            PlaylistEntityContainer playlists = (PlaylistEntityContainer) input.readObject();
            input.close();
            return playlists;
        }catch (FileNotFoundException e) {
            Log.e("warning", "cannot find file");
            return null;
        } catch (IOException e) {
            Log.e("warning", "IOException");
            return null;
        } catch (ClassNotFoundException e) {
            Log.e("warning", "No class object with type PlaylistEntityContainer");
            return null;
        }

    }

    /**
     * A method that read song entities from a .ser file
     *
     * @return a SongContainer object
     *
     */
    @Override
    public SongEntityContainer readSongsFromFile() {
        try{
            FileInputStream file = this.currentPageActivity.openFileInput("Songs.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // serialize the Map
            SongEntityContainer songs = (SongEntityContainer) input.readObject();
            input.close();
            return songs;
        }catch (FileNotFoundException e) {
            Log.e("warning", "cannot find file");
            return null;
        } catch (IOException e) {
            Log.e("warning", "IOException");
            return null;
        } catch (ClassNotFoundException e) {
            Log.e("warning", "No class object with type SongEntityContainer");
            return null;
        }

    }

    @Override
    public ActivityServiceCache readActivityServiceCacheFromFile(){
        try{
            FileInputStream file = this.currentPageActivity.openFileInput("ActivityServiceCache.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // serialize the Map
            ActivityServiceCache activityServiceCache = (ActivityServiceCache) input.readObject();
            input.close();
            return activityServiceCache;
        } catch (IOException e) {
            Log.e("warning", "cannot find file");
            return null;
        } catch (ClassNotFoundException e) {
            Log.e("warning", "ActivityServiceCache class does not exist");
            return null;
        }

    }

}
