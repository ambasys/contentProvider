package com.awesometeach.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listView;
Button contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        contact=findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewContact();
            }
        });

    }

    private void ViewContact() {
    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!=
            PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
    }
    else{
        ArrayList<String>contact=new ArrayList<>();
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        ContentResolver cr=getContentResolver();
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                String contactName=cursor.getString(cursor.getColumnIndex(ContactsContract
                         .CommonDataKinds.Phone.DISPLAY_NAME));
                String contactNumber=cursor.getString(cursor.getColumnIndex(ContactsContract
                        .CommonDataKinds.Phone.NUMBER));
                contact.add(contactName+"\n"+contactNumber);
                listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contact));
            }
        }
        }
    }
}
