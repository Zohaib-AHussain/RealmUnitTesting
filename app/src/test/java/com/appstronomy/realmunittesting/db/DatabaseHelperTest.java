package com.appstronomy.realmunittesting.db;

import android.content.Context;

import com.appstronomy.realmunittesting.BuildConfig;
import com.appstronomy.realmunittesting.CustomApplicationTest;
import com.appstronomy.realmunittesting.db.model.Person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by zohaibhussain on 2017-02-12.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = CustomApplicationTest.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "javax.crypto.","java.security.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmConfiguration.class,
        RealmQuery.class, RealmResults.class, RealmCore.class, RealmLog.class})
public class DatabaseHelperTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private DatabaseHelper dB;

    private Realm realmMock;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        mockStatic(RealmCore.class);
        mock(DatabaseHelper.class);

        final Realm mockRealm = PowerMockito.mock(Realm.class);
        realmMock = mockRealm;

        final RealmConfiguration mockRealmConfig = PowerMockito.mock(RealmConfiguration.class);

        doNothing().when(RealmCore.class);
        RealmCore.loadLibrary(any(Context.class));

        whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);
        when(Realm.getInstance(any(RealmConfiguration.class))).thenReturn(mockRealm);
        when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        when(Realm.getDefaultInstance()).thenReturn(realmMock);

        when(realmMock.createObject(Person.class)).thenReturn(new Person());

        Person person = new Person();
        person.setId("2");
        person.setName("Jerry");
        person.setAge("25");

        Person person2 = new Person();
        person.setId("3");
        person.setName("Tom");
        person.setAge("22");

        List<Person> personsList = new ArrayList<>();
        personsList.add(person);
        personsList.add(person2);

        RealmQuery<Person> personRealmQuery = mockRealmQuery();
        when(realmMock.where(Person.class)).thenReturn(personRealmQuery);

        RealmResults<Person> personRealmResults = mockRealmResults();
        when(realmMock.where(Person.class).findAll()).thenReturn(personRealmResults);
        when(personRealmResults.iterator()).thenReturn(personsList.iterator());
        when(personRealmResults.size()).thenReturn(personsList.size());

        when(realmMock.copyFromRealm(personRealmResults)).thenReturn(personsList);

        realmMock = mockRealm;
        dB = new DatabaseHelper(realmMock);
    }


    @Test
    public void insertingPerson(){
        doCallRealMethod().when(realmMock).executeTransaction(any(Realm.Transaction.class));

        Person person = mock(Person.class);
        when(realmMock.createObject(Person.class)).thenReturn(person);

        dB.putPersonData();

        verify(realmMock, times(1)).createObject(Person.class);
        verify(person, times(1)).setId(anyString());
    }


    @Test
    public void testExistingData(){
        List<Person> personList = dB.getPersonList();
        //NPE if checking person object properties i.e name, id. Only list size is available why?
        Assert.assertEquals(2, personList.size());

    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }


}