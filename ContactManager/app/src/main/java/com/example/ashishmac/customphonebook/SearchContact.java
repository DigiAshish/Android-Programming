/*
Author : Ashish Mohapatra
NetID : axm160031
Assignment : 5

This java file show an activity to search a name and find all its entry in the text file.
Clicking on any searched result shows the detail of the person.

 */
package com.example.ashishmac.customphonebook;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class SearchContact extends AppCompatActivity {
    public EditText txtSearch;
    public Button Search;
    public ListView SearchlistView;
    String [] saveText = new String[5];
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Contact";
    File file =new File(path+"/savedFile.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_add:
                                Intent ToAdd = new Intent(SearchContact.this,NewContact.class);
                                startActivity(ToAdd);
                                break;
                            case R.id.navigation_search:
                                Intent ToSearch = new Intent(SearchContact.this,SearchContact.class);
                                startActivity(ToSearch);
                                break;
                            case R.id.navigation_contacts:
                                Intent ToHome = new Intent(SearchContact.this,MainActivity.class);
                                startActivity(ToHome);
                                break;
                        }
                        return false;
                    }
                });

    }
    public void btnSearch(android.view.View view) {
        txtSearch= (EditText) findViewById(R.id.txtSearch);
        SearchlistView = (ListView) findViewById(R.id.listViewSearch);
        Search = (Button) findViewById(R.id.Search);
        String keyword=String.valueOf(txtSearch.getText());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
            String line;
            String[] lineDetail = new String[5];
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                lineDetail=line.split("\t");
                if (keyword.equals(lineDetail[0])) {
                    lines.add(lineDetail[0]+"\t"+lineDetail[1]+"\t"+lineDetail[2]);
                }
            }
            br.close();
            SearchlistView.setVisibility(View.VISIBLE);
            if(lines.isEmpty())
            {
                lines.add("No Contacts Found");
            }
            Collections.sort(lines);
            Adapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lines);
            SearchlistView.setAdapter((ListAdapter)adapter); //Show Contact List
            SearchlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemposition=position;
                    String value=(String) SearchlistView.getItemAtPosition(position);
                    Intent ToView = new Intent(view.getContext(),ViewContact.class);
                    ToView.putExtra("key",value);
                    startActivity(ToView);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
