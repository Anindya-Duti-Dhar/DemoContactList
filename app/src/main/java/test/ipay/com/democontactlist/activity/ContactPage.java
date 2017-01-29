package test.ipay.com.democontactlist.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Vector;

import test.ipay.com.democontactlist.R;
import test.ipay.com.democontactlist.adapter.ContactsAdapter;
import test.ipay.com.democontactlist.db.UsersDbManager;
import test.ipay.com.democontactlist.model.SelectUser;
import test.ipay.com.democontactlist.utils.ItemClickSupport;

public class ContactPage extends AppCompatActivity {
    //Defining Variables
    // ArrayList
    ArrayList<SelectUser> selectUsers;
    ArrayList<SelectUser> dummy;
    RelativeLayout mLoadingProgress;
    ProgressBar mProgress;
    ContentResolver resolver;
    RecyclerView mRecyclerView;
    ContactsAdapter adapter;
    private UsersDbManager dbManger;
    static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        i = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_page);

        // Set up the toolbar.
        Toolbar contact_profile_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(contact_profile_toolbar);

        dbManger = UsersDbManager.getInstance(ContactPage.this);

        selectUsers = new ArrayList<SelectUser>();

        resolver = getContentResolver();
        mRecyclerView = (RecyclerView) findViewById(R.id.contact_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ContactPage.this));
        mLoadingProgress = (RelativeLayout) findViewById(R.id.loading_progress);
        mProgress = (ProgressBar) findViewById(R.id.progress);

        getContact();

        dummy = dbManger.getDataFromDB(i * 10);
        adapter = new ContactsAdapter(ContactPage.this, dummy);
        mRecyclerView.setAdapter(adapter);
        i++;

        // Select item on listclick
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Log.d("Button Click", String.valueOf(adapter.getItemCount()));

                        if (position == adapter.getItemCount()-1) {

                            Log.d("Button Click", "right");

                            if (adapter.getItemCount()-1 < selectUsers.size()) {

                                Log.d("Button Click", "Logic");

                                if ((i * 10) <= selectUsers.size()) {
                                    dummy = dbManger.getDataFromDB(i * 10);

                                    adapter = new ContactsAdapter(ContactPage.this, dummy);
                                    mRecyclerView.setAdapter(adapter);
                                    i++;
                                } else {
                                    dummy = dbManger.getDataFromDB(selectUsers.size());

                                    adapter = new ContactsAdapter(ContactPage.this, dummy);
                                    mRecyclerView.setAdapter(adapter);
                                }
                            }
                        }
                    }
                }
        );

    }

    public void getContact() {
        mLoadingProgress.setVisibility(View.VISIBLE);
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        Integer contactsCount = cursor.getCount();
        if (contactsCount > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                    while (pCursor.moveToNext()) {
                        int phoneType = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        String phoneNo = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        switch (phoneType) {
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                SelectUser selectUser = new SelectUser();
                                selectUser.setName(contactName);
                                selectUser.setPhone(phoneNo);
                                selectUsers.add(selectUser);
                                Log.e(contactName + ": TYPE_MOBILE", " " + phoneNo);
                                break;
                            default:
                                break;
                        }
                    }
                    pCursor.close();
                }
            }
            cursor.close();
            mLoadingProgress.setVisibility(View.GONE);
        }

        dbManger.saveAllUsers(selectUsers);
    }

}
