package com.vlad.example.vladfirstapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * lalala
 *
 * Created by vlad on 24.03.2018.
 */

public class ContactsListAdapter extends BaseAdapter {

    private Context context;
    public  ContactsList contactsList, selectedContactsList;
    private String filterContactName;

    public ContactsListAdapter(Context context, ContactsList contactsList) {
        super();
        this.context = context;
        this.contactsList = contactsList;
        this.selectedContactsList = new ContactsList();
        this.filterContactName = "";
    }

    @Override
    public int getCount() {
        return contactsList.getCount();
    }

    @Override
    public Contact getItem(int position) {
        return contactsList.contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.getItem(position).id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.contact_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.chkContact = (CheckBox) convertView.findViewById(R.id.chk_contact);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.chkContact.setText(this.contactsList.contactArrayList.get(position).toString());
        viewHolder.chkContact.setId(Integer.parseInt(this.contactsList.contactArrayList.get(position).id));
        viewHolder.chkContact.setChecked(alreadySelected(contactsList.contactArrayList.get(position)));

        viewHolder.chkContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Contact contact = contactsList.getContact(buttonView.getId());

                if (contact != null && isChecked && !alreadySelected(contact)) {
                    selectedContactsList.addContact(contact);
                } else if (contact != null && !isChecked) {
                    selectedContactsList.removeContact(contact);
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        CheckBox chkContact;
    }

    private boolean alreadySelected(Contact contact) {
        if (this.selectedContactsList.getContact(Integer.parseInt(contact.id)) != null)
            return true;

        return false;
    }

    public void addContacts(ArrayList<Contact> contacts) {
        this.contactsList.contactArrayList.addAll(contacts);
    }

}
