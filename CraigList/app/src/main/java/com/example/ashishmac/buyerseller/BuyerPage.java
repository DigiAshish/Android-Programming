package com.example.ashishmac.buyerseller;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class BuyerPage extends AppCompatActivity {
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BuyerSeller";
    File file =new File(path+"/Items.txt");
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_page);
        listView = (ListView) findViewById(R.id.listView);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
            String line;
            String[] lineDetail = new String[5];
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                lineDetail=line.split("\t");
                lines.add("@"+lineDetail[0]+"\t"+"->"+"\t"+lineDetail[1]);
            }
            br.close();
            if(lines.isEmpty())
            {
                lines.add("No Items Found");
            }
            Collections.sort(lines);
            Adapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lines);
            listView.setAdapter((ListAdapter)adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
