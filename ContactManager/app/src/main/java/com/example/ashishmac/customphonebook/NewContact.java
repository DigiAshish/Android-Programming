/*
Author : Ashish Mohapatra
NetID : axm160031
Assignment : 5

This java file loads an activity to enter new contacts to the text file.
 */
package com.example.ashishmac.customphonebook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class NewContact extends AppCompatActivity {
    public EditText txtFirstName,txtLastName, txtPhone,txtEmail,txtDate;
    public Button Save, ClearAll;
    public ListView ContactlistView;
    String [] saveText = new String[5];
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Contact";
    private TextView mTextMessage;
    File file =new File(path+"/savedFile.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        Save = (Button) findViewById(R.id.Save);
        ClearAll = (Button) findViewById(R.id.ClearAll);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_add:
                                Intent ToAdd = new Intent(NewContact.this,NewContact.class);
                                startActivity(ToAdd);
                                break;
                            case R.id.navigation_search:
                                Intent ToSearch = new Intent(NewContact.this,SearchContact.class);
                                startActivity(ToSearch);
                                break;
                            case R.id.navigation_contacts:
                                Intent ToHome = new Intent(NewContact.this,MainActivity.class);
                                startActivity(ToHome);
                                break;
                        }
                        return false;
                    }
                });

    }
    public void btnClear(android.view.View view)
    {
        txtFirstName= (EditText) findViewById(R.id.txtFirstName);
        txtLastName= (EditText) findViewById(R.id.txtLastName);
        txtPhone= (EditText) findViewById(R.id.txtPhone);
        txtEmail= (EditText) findViewById(R.id.txtEmail);
        txtDate= (EditText) findViewById(R.id.txtDate);

        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtDate.setText("");
    }
    public void btnSave(android.view.View view)
    {
        txtFirstName= (EditText) findViewById(R.id.txtFirstName);
        txtLastName= (EditText) findViewById(R.id.txtLastName);
        txtPhone= (EditText) findViewById(R.id.txtPhone);
        txtEmail= (EditText) findViewById(R.id.txtEmail);
        txtDate= (EditText) findViewById(R.id.txtDate);
        saveText[0] = String.valueOf(txtFirstName.getText());
        saveText[1] = String.valueOf(txtLastName.getText());
        if(saveText[1].length()==0)
            saveText[1]="N/A";
        saveText[2] = String.valueOf(txtPhone.getText());
        if(saveText[2].length()==0)
            saveText[2]="N/A";
        saveText[3] = String.valueOf(txtEmail.getText());
        if(saveText[3].length()==0)
            saveText[3]="N/A";
        saveText[4] = String.valueOf(txtDate.getText());
        if(saveText[4].length()==0)
            saveText[4]="N/A";
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtDate.setText("");
        if(saveText[0].length()==0)
        {
            txtFirstName.setBackgroundColor(Color.MAGENTA);
            Toast.makeText(getApplicationContext(),"First Name is Mandatory", Toast.LENGTH_LONG).show();
        }
        else{
            txtFirstName.setBackgroundColor(Color.WHITE);
            Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_LONG).show();
            Save (file,saveText);
        }
    }

    private void Save(File file, String[] data) {
        FileOutputStream fos = null;
        OutputStreamWriter myOutWriter = null;
        try
        {
            fos = new FileOutputStream(file,true);
            myOutWriter = new OutputStreamWriter(fos);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for(int i=0;i<5;i++) {
                    myOutWriter.append(data[i]);
                    myOutWriter.append("\t");
                }
                myOutWriter.append("\n");
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                myOutWriter.close();
                fos.close();
                Intent ToHome = new Intent(NewContact.this,MainActivity.class);
                startActivity(ToHome);
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

}
