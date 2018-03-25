package com.vlad.example.vladfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

public class ResultActivity extends AppCompatActivity {

    private ContactsListAdapter contactsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String searchString = intent.getStringExtra("GIGI");

        ListView contactsChooser = (ListView) findViewById(R.id.list_contact);
        Button btnDone = (Button) findViewById(R.id.button_done);

        contactsListAdapter = new ContactsListAdapter(this, new ContactsList());

        contactsChooser.setAdapter(contactsListAdapter);

        loadContacts(searchString);
    }

    private void loadContacts(String filter) {
        if (filter == null) filter = "";

        ContactsLoaderSync contactsLoader = new ContactsLoaderSync(this, contactsListAdapter);

        contactsLoader.aduBahDatele(filter);
    }

}
