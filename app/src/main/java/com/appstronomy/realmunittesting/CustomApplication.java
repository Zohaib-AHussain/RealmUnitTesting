package com.appstronomy.realmunittesting;

import android.app.Application;

import com.appstronomy.realmunittesting.db.DatabaseHelper;

import io.realm.Realm;

/**
 * Created by zohaibhussain on 2017-02-12.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    public DatabaseHelper getDatabaseHelper(){
        return new DatabaseHelper(this);
    }
}
