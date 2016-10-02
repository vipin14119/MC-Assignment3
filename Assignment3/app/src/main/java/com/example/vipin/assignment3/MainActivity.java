package com.example.vipin.assignment3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText file_name;
    private EditText file_description;
    private boolean canR, canW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        file_name = (EditText)findViewById(R.id.file_name_field);
        file_description = (EditText)findViewById(R.id.file_description_field);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_task_home) {
            Intent intent = new Intent(this, TaskHomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_account_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isExternalWritable(){
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    public boolean isExternalReadable(){
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    public void externalState(){
        if (isExternalReadable() && isExternalWritable()){
            canR = canW = true;
        }
        else if (isExternalReadable() && !isExternalWritable()){
            canR = true;
            canW = false;
        }
        else{
            canR = canW = false;
        }
    }
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            file.mkdirs();
            Toast.makeText(getApplicationContext(), "Directory not created", Toast.LENGTH_SHORT).show();
        }
        return file;
    }
    public File getAlbumStorageDirPrivate(String albumName) {
        File file = new File(getApplicationContext().getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            file.mkdirs();
            Toast.makeText(getApplicationContext(), "Directory not created", Toast.LENGTH_SHORT).show();
        }
        return file;
    }
    public void writeToPrivateExternal(View view){
        String fileName = file_name.getText().toString();
        String desc = file_description.getText().toString();
        File file;
        externalState();
        if(canW && canR){
            FileOutputStream outputStream;
            try {
                file = new File(getAlbumStorageDirPrivate("Files Generated"), fileName+".txt");

                outputStream = new FileOutputStream(file);
                outputStream.write(desc.getBytes());
                outputStream.close();
                Toast.makeText(getApplicationContext(), "File Written into Private External Storage", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Cannot Write to External Now", Toast.LENGTH_SHORT).show();
        }

    }
    public void writeToPublicExternal(View view){
        String fileName = file_name.getText().toString();
        String desc = file_description.getText().toString();
        File file;
        externalState();
        if(canW && canR){
            FileOutputStream outputStream;
            try {
                file = new File(getAlbumStorageDir("Files Generated"), fileName+".txt");

                outputStream = new FileOutputStream(file);
                outputStream.write(desc.getBytes());
                outputStream.close();
                Toast.makeText(getApplicationContext(), "File Written into Public External Storage", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Cannot Write to External Now", Toast.LENGTH_SHORT).show();
        }
    }
}
