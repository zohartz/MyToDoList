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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {
    private EditText usernameView;
    private  EditText passWordView;
    private EditText phoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameView = (EditText) findViewById(R.id.usernameET);
        passWordView = (EditText) findViewById(R.id.passwordET);
        phoneView = (EditText) findViewById(R.id.phoneET);

        findViewById(R.id.sendBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validationError = false;
                StringBuilder validationErrorMsg = new StringBuilder("Please ");
                if (SignUp.this.isEmpty(usernameView)) {
                    validationError = true;
                    validationErrorMsg.append("enter a username");
                }
                if (SignUp.this.isEmpty(passWordView)) {
                    validationError = true;
                    validationErrorMsg.append("enter a password");
                }
                if (SignUp.this.isEmpty(phoneView)) {
                    validationError = true;
                    validationErrorMsg.append("enter a phone#");
                }
                validationErrorMsg.append(".");

                if (validationError) {
                    Toast.makeText(SignUp.this, validationErrorMsg.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                final ProgressDialog dig = new ProgressDialog(SignUp.this);
                dig.setTitle("In progress");
                dig.setMessage("Signing up...Please wait.");
                dig.show();

                ParseUser user = new ParseUser();
                user.setEmail(usernameView.getText().toString());
                user.setPassword(passWordView.getText().toString());
                user.setUsername(usernameView.getText().toString());
                user.put("phone",phoneView.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        dig.dismiss();
                        if (e != null) {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(SignUp.this, CreateTeam.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            SignUp.this.startActivity(intent);
                        }
                    }
                });


            }
        });
    }

    public boolean isEmpty(EditText et){
        if(et.getText().toString().trim().length() > 0){
            return false;
        } else return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
