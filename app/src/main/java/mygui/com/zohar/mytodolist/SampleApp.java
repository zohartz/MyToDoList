package mygui.com.zohar.mytodolist;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;


/**
 * Created by danielshamama on 02/01/2016.
 */
public class SampleApp extends Application {
    private ParseUser currentUser;
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        currentUser = ParseUser.getCurrentUser();

    }
    public ParseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ParseUser newUs) {
        this.currentUser = newUs;
    }
}
