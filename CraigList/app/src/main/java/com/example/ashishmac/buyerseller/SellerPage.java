package com.example.ashishmac.buyerseller;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SellerPage extends AppCompatActivity {
    public EditText textViewCreate,edt;
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BuyerSeller";
    File file =new File(path+"/Items.txt");
    String [] saveText = new String[2];
    String [] value = new String[100];
    String usrName;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_page);
        Bundle extras = getIntent().getExtras();
        usrName = extras.getString("key");

    }
    public void btnModify(android.view.View view) {
        Intent ToModifyPage = new Intent(SellerPage.this,ModifyProduct.class);
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
                Intent ToSellerPage = new Intent(SellerPage.this,SellerPage.class);
                ToSellerPage.putExtra("key",usrName);
                startActivity(ToSellerPage);

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
