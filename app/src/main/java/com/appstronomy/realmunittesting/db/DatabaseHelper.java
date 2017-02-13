package com.appstronomy.realmunittesting.db;

import android.content.Context;

import com.appstronomy.realmunittesting.db.model.Person;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zohaibhussain on 2017-02-12.
 */

public class DatabaseHelper {

    private static RealmConfiguration mRealmConfig;
    private Context mContext;
    private Realm realm;

    public DatabaseHelper(Context context) {
        this.mContext = context;
        this.realm = getNewRealmInstance();
    }

    public DatabaseHelper(Realm realm) {
        this.realm = realm;
    }

    public Realm getNewRealmInstance() {
        if (mRealmConfig == null) {
            mRealmConfig = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
        }
        return Realm.getInstance(mRealmConfig);
    }

    public Realm getRealmInstance() {
        return realm;
    }

    public void putPersonData() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setId("1");
                person.setName("John Smith");
                person.setAge("30");
                realm.copyToRealmOrUpdate(person);
            }
        });
    }

    public List<Person> getPersonList(){
        return realm.where(Person.class).findAll();
    }

    public boolean isPersonDatabaseEmpty(){
        return realm.where(Person.class).findAll() == null || realm.where(Person.class).findAll().size() == 0;
    }

}
