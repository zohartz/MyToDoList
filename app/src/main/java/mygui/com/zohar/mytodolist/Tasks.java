package mygui.com.zohar.mytodolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mygui.com.zohar.mytodolist.tabs.SlidingTabLayout;

public class Tasks extends AppCompatActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTab;
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTab = (SlidingTabLayout) findViewById(R.id.tabs);
        mTab.setViewPager(mPager);

        imageButton = (ImageButton)findViewById(R.id.addTask);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tasks.this, CreateATask.class));
            }
        });
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] tabs;
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class MyFragment extends Fragment{
        private TextView textView;
        private ListView listView;
        private myListAdaptor listAdaptor;
        private ParseQuery<ParseObject> query;
        private ArrayList<String> tasksList;
        private ParseUser curUser;
        private Context context;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();

            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);

            return myFragment;
        }

        private static class myListAdaptor extends ArrayAdapter<String> {
            private int layout;
            private ArrayList<String> values;

            public myListAdaptor(Context context, int resource, ArrayList<String> objects) {
                super(context, resource, objects);
                layout = resource;
                values = objects;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewholder = new ViewHolder();
                viewholder.title = (TextView) convertView.findViewById(R.id.taskTitleTV);
                viewholder.status = (TextView) convertView.findViewById(R.id.taskStatusTV);

                viewholder.title.setText(values.get(position));
                viewholder.status.setText("Done");
                convertView.setTag(viewholder);
                return convertView;
            }

            public class ViewHolder {
                TextView title;
                TextView status;
            }
        }



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View layout = inflater.inflate(R.layout.activity_my__fragmant, container, false);
            textView = (TextView) layout.findViewById(R.id.position);
            listView = (ListView) layout.findViewById(R.id.tasksList);
            tasksList = new ArrayList<>();
            listAdaptor = new myListAdaptor( this.getContext() , R.layout.tasks_list_item, (ArrayList<String>) tasksList);
            listView.setAdapter(listAdaptor);

            context = this.getContext();

            curUser = ParseUser.getCurrentUser();

            query = ParseQuery.getQuery("Task");
            if(curUser.get("type").toString() == "emp"){
                query.whereEqualTo("userName", curUser.getUsername().toString());
            } else {
                query.whereNotEqualTo("memName","");
            }

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, com.parse.ParseException e) {
                    if (e == null) {
                        for (Iterator<ParseObject> i = objects.iterator(); i.hasNext(); ) {
                            ParseObject item = i.next();
                            tasksList.add(item.get("id").toString());
                        }
                        listAdaptor.notifyDataSetInvalidated();
                    } else {
                        Toast.makeText( context , e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            Bundle bundle =  getArguments();
            if (bundle!=null){
                textView.setText("The page selected is " + bundle.getInt("position"));
            }
            return layout;
        }
    }

    ///////////////////


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
