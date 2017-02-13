package com.appstronomy.realmunittesting.db.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by zohaibhussain on 2017-02-12.
 */

public class Person extends RealmObject {
    String id;
    String name;
    String age;
    RealmList<Person> friends;


    public Person() {
    }

    public Person(String id, String name, String age, RealmList<Person> friends) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.friends = friends;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(RealmList<Person> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return getId() +" "+ getName() +" "+ getAge();
    }
}
