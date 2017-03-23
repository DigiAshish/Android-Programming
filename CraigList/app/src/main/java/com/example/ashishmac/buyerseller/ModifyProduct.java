package com.example.ashishmac.buyerseller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Collections;

public class ModifyProduct extends AppCompatActivity {
    String usrName;
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BuyerSeller";
    File file =new File(path+"/Items.txt");
    File file2 =new File(path+"/Temp.txt");
    public ListView listView;
    AlertDialog dialog;
    EditText editText;
    public TextView textView;
    String [] saveText = new String[2];
    String[] lineDetail = new String[2];
    String value;
    String OldValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);
        Bundle extras = getIntent().getExtras();
        usrName = extras.getString("key");
        listView = (ListView) findViewById(R.id.listView);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
            String line;
            String[] lineDetail = new String[5];
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                lineDetail=line.split("\t");
                if(lineDetail[0].equals(usrName))
                    lines.add(lineDetail[1]);
                else
                   continue;
            }
            br.close();
            if(lines.isEmpty())
            {
                lines.add("No Items Found");
            }
            Collections.sort(lines);
            Adapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lines);
            listView.setAdapter((ListAdapter)adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemposition=position;
                    value=(String) listView.getItemAtPosition(position);
                    updateView(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateView(int index){
        setContentView(R.layout.update_product);
        textView = (TextView)findViewById(R.id.textView);
        textView.setText(value);
        View v = listView.getChildAt(index -
                listView.getFirstVisiblePosition());
        dialog = new AlertDialog.Builder(this).create();
        editText=new EditText(this);
        dialog.setTitle("Edit The text");
        dialog.setView(editText);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,"Save", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int i) {
                textView.setText(editText.getText());
            }
        });
        if(v == null)
            return;
        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                editText.setText(textView.getText());
                dialog.show();
                OldValue = String.valueOf(textView.getText());
            }
        });
    }
    public void btnUpdate(android.view.View view)
    {
        saveText[0] = usrName;
        saveText[1] = String.valueOf(textView.getText());
        Save (file,saveText);
        String selectedLine= saveText[0]+"\t"+OldValue+"\t";
        BufferedReader br = null;
        String line;
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
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            file.delete();
            file2.renameTo(file);
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
                for(int i=0;i<2;i++) {
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
                Intent ToModifyPage = new Intent(ModifyProduct.this,ModifyProduct.class);
                ToModifyPage.putExtra("key",usrName);
                startActivity(ToModifyPage);
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
                for(int i=0;i<2;i++) {
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
