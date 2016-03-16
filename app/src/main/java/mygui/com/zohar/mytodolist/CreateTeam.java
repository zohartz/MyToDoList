package mygui.com.zohar.mytodolist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateTeam extends AppCompatActivity {
    private Button addbtn;
    private Button createTeamBtn;
    private EditText teamName;
    private EditText newEmail;
    private EditText newPhone;
    private ListView usersList;
    public ArrayList<NewUser> newUsers;
    public List<String> emails;
    private myListAdaptor listAdaptor;
    private int requestCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestCount = 0 ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        createTeamBtn = (Button) findViewById(R.id.createTeamBTN);
        addbtn = (Button) findViewById(R.id.addMemberBTN);
        newEmail = (EditText) findViewById(R.id.newMemberMailET);
        newPhone = (EditText) findViewById(R.id.newMemberPhoneET);
        usersList = (ListView) findViewById(R.id.L1);
        newUsers = new ArrayList<NewUser>();
        emails = new ArrayList<String>();
        teamName = (EditText) findViewById(R.id.teamNameET);
        listAdaptor = new myListAdaptor(this, R.layout.list_item, (ArrayList<NewUser>) newUsers);

        usersList.setAdapter(listAdaptor);

        if(newUsers.isEmpty()) createTeamBtn.setText(getString(R.string.done));
        createTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createTeamBtn.getText().toString() == getString(R.string.done)) {
                    if (CreateTeam.this.isEmpty(teamName)) {
                        Toast.makeText(CreateTeam.this, "There is no data.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        ParseUser curUser = ParseUser.getCurrentUser();
                        curUser.put("Team", teamName.getText().toString());
                        curUser.saveInBackground();
                        startActivity(new Intent(CreateTeam.this, Tasks.class));
                    }
                } else {


                    final ProgressDialog dig = new ProgressDialog(CreateTeam.this);
                    dig.setTitle("In progress");
                    dig.setMessage("Signing up all new employees...Please wait.");
                    dig.show();

                    for (int idx = 0; idx < newUsers.size(); idx++) {
                        ParseUser user = new ParseUser();
                        user.setEmail(newUsers.get(idx).getEmail());
                        user.setPassword(newUsers.get(idx).getPhone());
                        user.setUsername(newUsers.get(idx).getEmail());
                        user.put("phone", newUsers.get(idx).getPhone());
                        user.put("type", "emp");
                        user.put("Team", teamName.getText().toString());
                        try {
                            user.signUp();
                            requestCount++;
                            newUsers.remove(idx);
                            listAdaptor.notifyDataSetInvalidated();
                        } catch (ParseException e) {
                            dig.dismiss();
                            Toast.makeText(CreateTeam.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (requestCount == newUsers.size() + 1) {
                        ParseUser curUser = ParseUser.getCurrentUser();
                        curUser.put("Team", teamName.getText().toString());
                        curUser.saveInBackground();
                        SendMails();
                    } else requestCount = 0;

                }
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validationError = false;
                StringBuilder validationErrorMsg = new StringBuilder("Please ");
                if (CreateTeam.this.isEmpty(newEmail)) {
                    validationError = true;
                    validationErrorMsg.append("enter an email");
                }
                if (CreateTeam.this.isEmpty(newPhone)) {
                    validationError = true;
                    validationErrorMsg.append("enter a phonenumber");
                }

                validationErrorMsg.append(".");

                if (validationError) {
                    Toast.makeText(CreateTeam.this, validationErrorMsg.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                add(new NewUser(newEmail.getText().toString(), newPhone.getText().toString()));
            }
        });
    }

    public boolean isEmpty(EditText et) {
        if (et.getText().toString().trim().length() > 0) {
            return false;
        } else return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class myListAdaptor extends ArrayAdapter<NewUser> {
        private int layout;
        private ArrayList<NewUser> values;

        public myListAdaptor(Context context, int resource, ArrayList<NewUser> objects) {
            super(context, resource, objects);
            layout = resource;
            values = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewholder = new ViewHolder();
            viewholder.title = (TextView) convertView.findViewById(R.id.listitem_text);
            viewholder.button = (Button) convertView.findViewById(R.id.listitem_delbtn);
            viewholder.phone = (TextView) convertView.findViewById(R.id.listItem_smTxt);
            viewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),
                            "Remove  " + getItem(position).getEmail() + " from list",
                            Toast.LENGTH_SHORT).show();
                    listAdaptor.remove(getItem(position));
                    if(newUsers.isEmpty()) createTeamBtn.setText(getString(R.string.done));

                }
            });
            viewholder.title.setText(getItem(position).getEmail());
            viewholder.phone.setText(getItem(position).getPhone());
            convertView.setTag(viewholder);
            return convertView;
        }
    }

    public class ViewHolder {
        TextView title;
        TextView phone;
        Button button;

    }

    public void add(NewUser user) {
        newUsers.add(user);
        listAdaptor.notifyDataSetChanged();
        if(!newUsers.isEmpty()) createTeamBtn.setText(getString(R.string.creat));
        newEmail.setText(null);
        newPhone.setText(null);
    }

    public void SendMails() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] to = new String[newUsers.size()];
        for (int i = 0; i < newUsers.size(); i++) {
            to[i] = newUsers.get(i).getEmail();
        }

        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Thats sent from your mennager!");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_TEXT,
                "Please, download Our Task manager application from here: url... \n " +
                        "Your information for authorization:\n" +
                        "User Name - your Email, Password - your phone number."
        );
        intent.setType("message/rfc822");
        startActivityForResult(Intent.createChooser(intent, "Choose an Email client:"), 1);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.d("TKT", "here33333 ");
                startActivity(new Intent(this, Tasks.class));
            }
        }
    }
}