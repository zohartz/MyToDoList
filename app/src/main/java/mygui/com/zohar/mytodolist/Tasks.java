package mygui.com.zohar.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;

public class Tasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_logout) {
            ParseUser.logOut();
            invalidateOptionsMenu();
            Toast.makeText(getApplicationContext(), "Disconnected...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Tasks.this, SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Tasks.this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
