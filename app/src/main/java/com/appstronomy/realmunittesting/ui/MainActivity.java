package com.appstronomy.realmunittesting.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.appstronomy.realmunittesting.CustomApplication;
import com.appstronomy.realmunittesting.R;
import com.appstronomy.realmunittesting.db.DatabaseHelper;
import com.appstronomy.realmunittesting.db.model.Person;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDB;

    private List<Person> mPersons;

    private TextView mPersonDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPersonDataTextView = (TextView) findViewById(R.id.person_data);
        mDB = ((CustomApplication)getApplication()).getDatabaseHelper();
        if (mDB.isPersonDatabaseEmpty()) {
            mDB.putPersonData();
        }
        mPersons = getPersonList();
        displayData();

    }

    private void displayData() {
        if (mPersons != null && mPersons.size() != 0)
            mPersonDataTextView.setText(mPersons.get(0).toString());
    }

    private List<Person> getPersonList() {
       return mDB.getPersonList();
    }
}
