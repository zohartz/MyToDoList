package mygui.com.zohar.mytodolist;


import android.app.FragmentTransaction;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CreateATask extends AppCompatActivity  {

    TextView dataView;
    EditText categoryET;
    EditText teamNameET;
    EditText locationET;
    EditText DueDate;
    MyDBHandler dbHandler;
    RadioButton imp;
    RadioButton normal;
    String prio;
    MultiAutoCompleteTextView  des;
    Spinner spinner;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_atask);
        //dataView = (TextView) findViewById(R.id.dateView);
        categoryET = (EditText) findViewById(R.id.categoryET);
        teamNameET = (EditText) findViewById(R.id.teamNameET);
        locationET = (EditText) findViewById(R.id.locationET);
        des=(MultiAutoCompleteTextView)findViewById(R.id.des);
        DueDate=(EditText)findViewById(R.id.dateTV);
        imp=(RadioButton)findViewById(R.id.imp);
        normal=(RadioButton)findViewById(R.id.normal);
        //dbHandler = new MyDBHandler(this, null, null, 1);
        // printDatabase();
        dbHandler = new MyDBHandler(this);

        addItemsOnSpinner2();
        //addListenerOnButton();
        //addListenerOnSpinnerItemSelection();

    }


    //Add a product to the database
    public void createTask(View view){
        Log.d("TKT", "here4444 ");
        Task task = new Task(categoryET.getText().toString(),prio,locationET.getText().toString(),des.getText().toString(),
                String.valueOf(spinner.getSelectedItem()));

        dbHandler.addProduct(task);
        //printDatabase();
        Log.d("Reading: ", "Reading all contacts..");
        List<Task> mytasks = dbHandler.getAllContacts();

        for (Task tsk : mytasks) {
            String log = "Id: "+tsk.get_id()+" ,Category: " + tsk.get_category() + " ,Priority: "
                    + tsk.get_priority()+ " ,location: " + tsk.get_location()
                    + " ,description: "+ tsk.get_description()+ " ,teamMame: " + tsk.get_memName();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        Log.d("TKT", "addtoParse ");

        ParseObject testObj=new ParseObject("Task");
        testObj.put("id",1);
        //testObj.put("title", "sometilel");///change
        testObj.put("category",categoryET.getText().toString());
        testObj.put("priority",prio);
        testObj.put("location",locationET.getText().toString());
        testObj.put("dueDate",DueDate.getText().toString());
        testObj.put("description",des.getText().toString());
        testObj.put("memname",String.valueOf(spinner.getSelectedItem()));
        testObj.put("status","waiting");


        try {
            testObj.save();
        }catch(ParseException e){
            e.printStackTrace();
        }
    }



    public void onRadioButtonClicked(View view) {
        Log.d("TKT", "here5555 ");

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.imp:
                if (checked)
                    prio="important";
                break;
            case R.id.normal:
                if (checked)
                    prio="normal";
                break;
        }
    }


    public void onRadioButtonClicked2(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy");

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tmw:
                if (checked)
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date tomorrow = calendar.getTime();
                DueDate.setText(format.format(tomorrow));

                break;
            case R.id.today:
                if (checked)
                    //  DueDate.append("tonight")
                    DueDate.setText(format.format(new Date()));

                break;
        }
    }

    //Print the database
    // public void printDatabase(){
    //    Log.d("TKT", "here6666 ");

    //  String dbString = dbHandler.databaseToString();
    // dataView.setText(dbString);
    //  categoryET.setText("");

    //  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_atask, menu);
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


    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText)findViewById(R.id.dateTV);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    Datedialog dialog=new Datedialog(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner = (Spinner) findViewById(R.id.spinner2);
        final List<String> list = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("type", "emp");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, ParseException e) {
                if (e == null) {
                    for (Iterator<ParseUser> i = userList.iterator(); i.hasNext(); ) {
                        ParseUser item = i.next();
                        // tasksList.add(item.get("id").toString())
                        list.add(item.getUsername());
                    }

                } else {

                }
            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

}
