package com.example.vipin.assignment3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by vipin on 2/10/16.
 */
public class SettingActivity extends AppCompatActivity {
    private EditText city;
    private EditText pincode;
    private final String FILENAME = "mySettingsFile";
    private String MSG = "ANDROID :: ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        city = (EditText)findViewById(R.id.setting_city);
        pincode = (EditText)findViewById(R.id.setting_pincode);
        loadData();
    }
    public String showData(){
        String collected = null;
        FileInputStream fis = null;
        try{
            fis = openFileInput(FILENAME);
            byte [] dataArray = new byte[fis.available()];
            while (fis.read(dataArray) != -1){
                collected = new String(dataArray);
            }
            Log.d(MSG, "======================== YES I HAVE BEEEN  CALLED IN SHOWDATA ===============================");
            fis.close();
            return collected;
        } catch (IOException e){
            e.printStackTrace();
            Log.d(MSG, "======================== YES I HAVE IN EXCEPTION  CALLED IN SHOWDATA ===============================");
            return null;
        }

    }
    public void loadData(){
        Log.d(MSG, "======================== YES I HAVE BEEEN  CALLED IN LOADDATA ===============================");
        String data = showData();
        if (data == null){
            Toast.makeText(getApplicationContext(), "No Data to Load", Toast.LENGTH_SHORT).show();
        }
        else{
            String [] data2 = data.split(";");
            city.setText(data2[0]);
            pincode.setText(data2[1]);
            Toast.makeText(getApplicationContext(), "Data Loaded", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveNewData(View view){
        Log.d(MSG, "======================== YES I HAVE BEEEN  CALLED ===============================");
        FileOutputStream fos;
        String t_city = city.getText().toString();
        String t_pincode = pincode.getText().toString();

        try{
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            if(!t_city.equals("") && !t_pincode.equals("")){
                fos.write(t_city.getBytes());
                fos.write(";".getBytes());
                fos.write(t_pincode.getBytes());
                fos.close();
                Toast.makeText(getApplicationContext(), "Data Written to Internal File mySettingsFile", Toast.LENGTH_SHORT).show();
                loadData();
            }
            else{
                Toast.makeText(getApplicationContext(), "Blank Values are not accepted", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
