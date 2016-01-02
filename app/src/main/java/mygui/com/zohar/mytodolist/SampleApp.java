package mygui.com.zohar.mytodolist;

import android.app.Application;

import com.parse.Parse;


/**
 * Created by danielshamama on 02/01/2016.
 */
public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
//        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));
    }
}
