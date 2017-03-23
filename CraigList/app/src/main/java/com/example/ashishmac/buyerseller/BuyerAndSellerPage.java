package com.example.ashishmac.buyerseller;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class BuyerAndSellerPage extends AppCompatActivity {
    public EditText textViewCreate,edt;
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BuyerSeller";
    File file =new File(path+"/Items.txt");
    String [] saveText = new String[2];
    public ListView listView;
    String usrName;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_and_seller_page);
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
                    continue;
                else
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
    public void btnModify(android.view.View view) {
        Intent ToModifyPage = new Intent(BuyerAndSellerPage.this,ModifyProduct.class);
        ToModifyPage.putExtra("key",usrName);
        startActivity(ToModifyPage);
    }
    public void btnCreate(android.view.View view) {
        textViewCreate= (EditText) findViewById(R.id.textViewCreate);
        String str = String.valueOf(textViewCreate.getText());
        int myNum = Integer.parseInt(str);
        count=myNum;
        LinearLayout.LayoutParams lParamsMW = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i=0; i<myNum;i++) {
            EditText edtView = new EditText(this);
            edtView.setHint("EditText: "+i);
            edtView.setId(i);
            edtView.setLayoutParams(lParamsMW);
            linearLayout.addView(edtView);
        }
        Button myButton = new Button(this);
        myButton.setText("Publish");
        linearLayout.addView(myButton, lParamsMW);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                for(int i=0; i<count;i++) {
                    edt = (EditText) findViewById(i);
                    saveText[0] = usrName;
                    saveText[1] = String.valueOf(edt.getText());
                    Save (file,saveText);
                }
                Toast.makeText(getApplicationContext(), "Published", Toast.LENGTH_LONG).show();
                Intent ToBuyerSellerPage = new Intent(BuyerAndSellerPage.this,BuyerAndSellerPage.class);
                ToBuyerSellerPage.putExtra("key",usrName);
                startActivity(ToBuyerSellerPage);
            }
        });
        this.setContentView(linearLayout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

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
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
}
