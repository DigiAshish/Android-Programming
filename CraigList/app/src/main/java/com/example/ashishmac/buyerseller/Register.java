package com.example.ashishmac.buyerseller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    public EditText usrName,emailID, password1,password2;
    public CheckBox buyCheck , sellCheck;
    String [] saveText = new String[5];
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BuyerSeller";
    File folder = new File (path);
    File userInfo =new File(path+"/UserInfo.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(!folder.exists())
        {
            folder.mkdirs();
        }
    }
    public void btnCreateProfile(android.view.View view)
    {
        usrName= (EditText) findViewById(R.id.displayName);
        emailID= (EditText) findViewById(R.id.email);
        password1= (EditText) findViewById(R.id.password);
        password2= (EditText) findViewById(R.id.password2);
        buyCheck = (CheckBox) findViewById(R.id.checkBox1);
        sellCheck = (CheckBox) findViewById(R.id.checkBox2);
        String isBuyer,isSeller;
        saveText[0] = String.valueOf(usrName.getText());
        saveText[1] = String.valueOf(emailID.getText());
        boolean isValidID = isValidEmail(saveText[1]);
        saveText[2] = String.valueOf(password1.getText());
        saveText[3] = String.valueOf(password2.getText());
            if (saveText[0].length() == 0) {
                usrName.setBackgroundColor(Color.MAGENTA);
                Toast.makeText(getApplicationContext(), "User Name is Mandatory", Toast.LENGTH_LONG).show();
            } else if (saveText[1].length() == 0) {
                emailID.setBackgroundColor(Color.MAGENTA);
                Toast.makeText(getApplicationContext(), "Email is Mandatory", Toast.LENGTH_LONG).show();
            } else if (isValidID==false) {
                emailID.setBackgroundColor(Color.MAGENTA);
                Toast.makeText(getApplicationContext(),"Invalid Email ID", Toast.LENGTH_LONG).show();
            }else if (!saveText[2].equals(saveText[3])) {
                password1.setBackgroundColor(Color.MAGENTA);
                password2.setBackgroundColor(Color.MAGENTA);
                Toast.makeText(getApplicationContext(), "Password didn't match", Toast.LENGTH_LONG).show();
            } else {
                if (buyCheck.isChecked() && sellCheck.isChecked()) {
                    saveText[4] = "Buyer/Seller";
                    Save(userInfo, saveText);
                } else if (buyCheck.isChecked()) {
                    saveText[4] = "Buyer";
                    Save(userInfo, saveText);
                } else if (sellCheck.isChecked()) {
                    saveText[4] = "Seller";
                    Save(userInfo, saveText);
                } else {
                    Toast.makeText(getApplicationContext(), "No check box selected", Toast.LENGTH_LONG).show();
                }
            }
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


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
                Toast.makeText(getApplicationContext(),"Account Created", Toast.LENGTH_LONG).show();
                Intent ToHome = new Intent(Register.this,LoginActivity.class);
                startActivity(ToHome);
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
}
