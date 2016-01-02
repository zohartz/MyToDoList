package mygui.com.zohar.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.parse.ParseUser;

public class DispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(ParseUser.getCurrentUser() != null){
//            startActivity(new Intent(this, Tasks.class));
//        } else {
//            startActivity(new Intent(this, SignIn.class));
//        }


    }

}
