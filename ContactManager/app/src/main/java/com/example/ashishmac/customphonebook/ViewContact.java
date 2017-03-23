/*
Author : Ashish Mohapatra
NetID : axm160031
Assignment : 5

This java file show the detail of the contact selected from the List view.
It also gives option to edit the entry or Delete it completely.
*/

package com.example.ashishmac.customphonebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class ViewContact extends AppCompatActivity {
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Contact";
    public TextView txtView1,txtView2,txtView3,txtView4,txtView5;
    public Button Modify,Delete;
    AlertDialog dialog;
    int whichText=0;
    int linesCount=0;
    String line;
    String[] lineDetail = new String[5];
    String [] saveText = new String[5];
    EditText editText;
    File file =new File(path+"/savedFile.txt");
    File file2 =new File(path+"/Temp.txt");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        txtView1=(TextView)findViewById(R.id.txtView1);
        txtView2=(TextView)findViewById(R.id.txtView2);
        txtView3=(TextView)findViewById(R.id.txtView3);
        txtView4=(TextView)findViewById(R.id.txtView4);
        txtView5=(TextView)findViewById(R.id.txtView5);
        Modify = (Button) findViewById(R.id.Modify);
        Delete = (Button) findViewById(R.id.Delete);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_add:
                                Intent ToAdd = new Intent(ViewContact.this,NewContact.class);
                                startActivity(ToAdd);
                                break;
                            case R.id.navigation_search:
                                Intent ToSearch = new Intent(ViewContact.this,SearchContact.class);
                                startActivity(ToSearch);
                                break;
                            case R.id.navigation_contacts:
                                Intent ToHome = new Intent(ViewContact.this,MainActivity.class);
                                startActivity(ToHome);
                                break;
                        }
                        return false;
                    }
                });
        BufferedReader br = null;
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("key");
        String[] valueDetail = new String[3];
        valueDetail=value.split("\t");
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
            while ((line = br.readLine()) != null) {
                lineDetail = line.split("\t");
                if (valueDetail[0].equals(lineDetail[0])&&valueDetail[1].equals(lineDetail[1])) {
                    txtView1.setText(lineDetail[0]);
                    txtView2.setText(lineDetail[1]);
                    txtView3.setText(lineDetail[2]);
                    txtView4.setText(lineDetail[3]);
                    txtView5.setText(lineDetail[4]);
                    break;
                }
            }
            br.close();
            dialog = new AlertDialog.Builder(this).create();
            editText=new EditText(this);
            dialog.setTitle("Edit The text");
            dialog.setView(editText);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE,"Save", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if(whichText==1)
                        txtView1.setText(editText.getText());
                    if(whichText==2)
                        txtView2.setText(editText.getText());
                    if(whichText==3)
                        txtView3.setText(editText.getText());
                    if(whichText==4)
                        txtView4.setText(editText.getText());
                    if(whichText==5)
                        txtView5.setText(editText.getText());

                    Modify.setVisibility(View.VISIBLE);


                }
            });
            txtView1.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    editText.setText(txtView1.getText());
                    dialog.show();
                    whichText=1;
                }
            });
            txtView2.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    editText.setText(txtView2.getText());
                    dialog.show();
                    whichText=2;
                }
            });
            txtView3.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    editText.setText(txtView3.getText());
                    dialog.show();
                    whichText=3;
                }
            });
            txtView4.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    editText.setText(txtView4.getText());
                    dialog.show();
                    whichText=4;
                }
            });
            txtView5.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    editText.setText(txtView5.getText());
                    dialog.show();
                    whichText=5;
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void btnDelete(android.view.View view) {
        String selectedLine = line;
        if(file2.exists())
        {
            file2.delete();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 2);
            while ((line = br.readLine()) != null) {
                lineDetail = line.split("\t");
                if(selectedLine.equals(line))
                {
                    continue;
                }
                else{
                    SaveTemp(file2, lineDetail);
                }
            }
            br.close();
            } catch(UnsupportedEncodingException e){
                e.printStackTrace();
            } catch(FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                file.delete();
                file2.renameTo(file);
                Intent ToHome = new Intent(ViewContact.this,MainActivity.class);
                startActivity(ToHome);
        }
    }

    public void btnModify(android.view.View view)
    {
        saveText[0] = String.valueOf(txtView1.getText());
        saveText[1] = String.valueOf(txtView2.getText());
        if(saveText[1].length()==0)
            saveText[1]="N/A";
        saveText[2] = String.valueOf(txtView3.getText());
        if(saveText[2].length()==0)
            saveText[2]="N/A";
        saveText[3] = String.valueOf(txtView4.getText());
        if(saveText[3].length()==0)
            saveText[3]="N/A";
        saveText[4] = String.valueOf(txtView5.getText());
        if(saveText[4].length()==0)
            saveText[4]="N/A";
        if(saveText[0].length()==0)
        {
            txtView1.setBackgroundColor(Color.MAGENTA);
            Toast.makeText(getApplicationContext(),"First Name is Mandatory", Toast.LENGTH_LONG).show();
        }
        else {
            txtView1.setBackgroundColor(Color.WHITE);
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            Save(file, saveText);
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
                Intent ToHome = new Intent(ViewContact.this,MainActivity.class);
                startActivity(ToHome);
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
    private void SaveTemp(File file, String[] data) {
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
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
}
