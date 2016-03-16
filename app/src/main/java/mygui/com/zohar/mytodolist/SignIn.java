package mygui.com.zohar.mytodolist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignIn extends AppCompatActivity {

    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = (EditText) findViewById(R.id.usernameET);
        password = (EditText) findViewById(R.id.passwordET);

        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validationError = false;
                StringBuilder validationErrorMsg = new StringBuilder("Please ");
                if (isEmpty(username)) {
                    validationError = true;
                    validationErrorMsg.append("enter a username");
                }
                if (isEmpty(password)) {
                    validationError = true;
                    validationErrorMsg.append("enter a password");
                }
                validationErrorMsg.append(".");

                if (validationError) {
                    Toast.makeText(SignIn.this, validationErrorMsg.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                final ProgressDialog dig = new ProgressDialog(SignIn.this);
                dig.setTitle("In progress");
                dig.setMessage("Signing up...Please wait.");
                dig.show();

                ParseUser.logInInBackground(username.getText().toString(),password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dig.dismiss();
                        if(e!=null){
                            Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(SignIn.this, CreateTeam.class);//change to TAsks!!!!
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            SignIn.this.startActivity(intent);
                        }
                    }
                });
            }
        });

        findViewById(R.id.newMngr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                SignIn.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    public void SignIn(View view){

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
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isEmpty(EditText et){
        if(et.getText().toString().trim().length() > 0){
            return false;
        } else return true;
    }
}
