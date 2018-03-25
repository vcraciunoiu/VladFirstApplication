package com.vlad.example.vladfirstapplication;

import java.util.ArrayList;

/**
 * Created by vlad on 24.03.2018.
 */

public class ContactsList {

    public ArrayList<Contact> contactArrayList;

    ContactsList() {
        contactArrayList = new ArrayList<Contact>();
    }

    public int getCount() {
        return contactArrayList.size();
    }

    public void addContact(Contact contact) {
        contactArrayList.add(contact);
    }

    public void removeContact(Contact contact) {
        contactArrayList.remove(contact);
    }

    public Contact getContact(int id) {
        for (int i = 0; i < this.getCount(); i++) {
            if (Integer.parseInt(contactArrayList.get(i).id) == id)
                return contactArrayList.get(i);
        }
        return null;
    }

}
