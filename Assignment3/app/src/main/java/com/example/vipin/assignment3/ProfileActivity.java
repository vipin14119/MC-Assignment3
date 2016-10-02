package com.example.vipin.assignment3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText contact;

    public static String preference_file = "MyProfileData";
    SharedPreferences userProfileData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        userProfileData = getSharedPreferences(preference_file, MODE_PRIVATE);
        loadData();
        Toast.makeText(getApplicationContext(), "Data Successfully Loaded", Toast.LENGTH_SHORT).show();
    }
    public void loadData(){
        userProfileData = getSharedPreferences(preference_file, MODE_PRIVATE);
        String name_returned = userProfileData.getString("my_name", "Dummy Dummy");
        String email_returned = userProfileData.getString("my_email", "dummy@dummy.com");
        String contact_returned = userProfileData.getString("my_contact", "XXXXXXXXXX");
        name = (EditText) findViewById(R.id.profile_name);
        email = (EditText) findViewById(R.id.profile_email);
        contact = (EditText) findViewById(R.id.profile_contact);
        name.setText(name_returned);
        email.setText(email_returned);
        contact.setText(contact_returned);
    }

    public void editData(View view){
        name = (EditText) findViewById(R.id.profile_name);
        email = (EditText) findViewById(R.id.profile_email);
        contact = (EditText) findViewById(R.id.profile_contact);
        SharedPreferences.Editor editor = userProfileData.edit();
        String t_name, t_email, t_contact;
        t_name = name.getText().toString();
        t_email = email.getText().toString();
        t_contact = contact.getText().toString();
        if (!t_name.equals("") && !t_email.equals("") && !t_contact.equals("")){
            editor.putString("my_name", t_name);
            editor.putString("my_email", t_email);
            editor.putString("my_contact", t_contact);
            editor.apply();
            loadData();
            Toast.makeText(getApplicationContext(), "Data Successfully Updated", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Please Feed Correct Input", Toast.LENGTH_SHORT).show();
        }
    }
}
