/*
Author : Ashish Mohapatra
NetID : axm160031
Assignment : 5

This java file contains the main activity and loads all the contacts in the text file to a List view.
*/

package com.example.ashishmac.customphonebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {
    public ListView ContactlistView;
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Contact"; //Path to the folder where the file is stored
    File file =new File(path+"/savedFile.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactlistView = (ListView) findViewById(R.id.listView1);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100); //Open the file to read
            String line;
            String[] lineDetail = new String[3];
            ArrayList<String> lines = new ArrayList<String>(); //Array list to store each line from the file
            while ((line = br.readLine()) != null) { //Read each Line from the file
                lineDetail=line.split("\t"); //Split the line by tab and store in a string array
                lines.add(lineDetail[0]+"\t"+lineDetail[1]+"\t"+lineDetail[2]); //Display only First Name, Last name and Phone Number
            }
            br.close();//Close the Buffer reader
            Collections.sort(lines);//Sort all the lines
            Adapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lines); //Display all the lines in the List View
            ContactlistView.setAdapter((ListAdapter)adapter); //Show Contact List
            //If any conatct is clicked the n do the following operation
            ContactlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemposition=position;
                    String value=(String) ContactlistView.getItemAtPosition(position); //Get the value which is clicked
                                Intent myIntent = new Intent(view.getContext(),ViewContact.class); //Go to another activity to show contact detail
                                myIntent.putExtra("key",value);//Send 
                                startActivity(myIntent);
                }
            });
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.navigation_add:
                                    Intent ToAdd = new Intent(MainActivity.this,NewContact.class);
                                    startActivity(ToAdd);
                                    break;
                                case R.id.navigation_search:
                                    Intent ToSearch = new Intent(MainActivity.this,SearchContact.class);
                                    startActivity(ToSearch);
                                    break;
                                case R.id.navigation_contacts:
                                    Intent ToHome = new Intent(MainActivity.this,MainActivity.class);
                                    startActivity(ToHome);
                                    break;
                            }
                            return false;
                        }
                    });
    }
}