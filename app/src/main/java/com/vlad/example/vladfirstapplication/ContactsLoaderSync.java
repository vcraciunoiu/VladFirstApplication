package com.vlad.example.vladfirstapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by vlad on 24.03.2018.
 */

public class ContactsLoaderSync {

    private ContactsListAdapter contactsListAdapter;
    Context context;
    private ArrayList<Contact> tempContactHolder;
    private int totalContactsCount, loadedContactsCount;

    ContactsLoaderSync(Context context, ContactsListAdapter contactsListAdapter) {
        this.context = context;
        this.contactsListAdapter = contactsListAdapter;
        this.tempContactHolder = new ArrayList<>();
        loadedContactsCount = 0;
        totalContactsCount = 0;
    }

    protected Void aduBahDatele(String filters) {
        boolean weHaveMoreStringsToSearch = false;
        String[] searchStrings = new String[1];

        if (filters.contains(" ")) {
            weHaveMoreStringsToSearch = true;
            searchStrings = filters.split(" ");
        }

        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };

        String selection = ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
        if (weHaveMoreStringsToSearch) {
            for (int i = 1; i < searchStrings.length; i++) {
                selection += (" OR " + ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?");
            }
        }

        String[] selectionArgs = new String[searchStrings.length];
        if (weHaveMoreStringsToSearch) {
            for (int i = 0; i < searchStrings.length; i++) {
                selectionArgs[i] = "%" + searchStrings[i] + "%";
            }
        } else {
            selectionArgs[0] = "%" + filters + "%";
        }

        Cursor cursor = contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        );

        totalContactsCount = cursor.getCount();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                    ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                            new String[] {id},
                            null
                    );

                    if (phoneCursor != null && phoneCursor.getCount() > 0) {

                        while (phoneCursor.moveToNext()) {
                            String phId = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));

                            String customLabel = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));

                            String label = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.getResources(),
                                    phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)),
                                    customLabel
                            );

                            if (label.equals("Mobile")) {
                                String phNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                tempContactHolder.add(new Contact(phId, name, phNo, label));
                            }
                        }
                        phoneCursor.close();
                    }
                }
                loadedContactsCount++;
            }
            cursor.close();
        }

        contactsListAdapter.addContacts(tempContactHolder);
        tempContactHolder.clear();

        return null;
    }

}
