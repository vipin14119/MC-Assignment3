package com.example.vipin.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText m_name;
    private EditText m_email;
    private EditText m_data;
    private TextView m_write;
    private TextView m_read;
    private Spinner spinner;    
    private String [] paths = {"Music", "Pictures", "Download"};
    private File path = null;
    private File file = null;
    private final String FILENAME = "myFile";
    private boolean canR, canW;
    public static String preference_file = "MySharedFile";
    SharedPreferences someData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        someData = getSharedPreferences(preference_file, MODE_PRIVATE);




//        loadData();
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        m_name = (EditText)findViewById(R.id.name);
        String f = m_name.getText().toString();
        file = getAlbumStorageDir("VIPIN ALBUM");
        externalState();
        if (canW && canR){

            try {
                InputStream is = getResources().openRawResource(R.raw.cute_puppy);
                OutputStream os = new FileOutputStream(file);
                byte[] data = new byte[is.available()];
                is.read(data);
                os.write(data);
                is.close();
                os.close();
                Toast.makeText(getApplicationContext(), "File has been saved", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Not Available", Toast.LENGTH_SHORT).show();
        }

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void test_write(){
        String filename = "myfile";
        String string = "Hello World!";
        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
            return collected;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        finally {
            try{
                assert fis != null;
                fis.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
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
        m_read = (TextView)findViewById(R.id.readState);
        m_write = (TextView)findViewById(R.id.writeState);
        if (isExternalReadable() && isExternalWritable()){
            m_read.setText("true");
            m_write.setText("true");
            canR = canW = true;
        }
        else if (isExternalReadable() && !isExternalWritable()){
            m_read.setText("true");
            m_write.setText("false");
            canR = true;
            canW = false;
        }
        else{
            m_read.setText("false");
            m_write.setText("false");
            canR = canW = false;
        }
    }
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
//            Log.e(LOG_TAG, "Directory not created");
            Toast.makeText(getApplicationContext(), "Directory not created", Toast.LENGTH_SHORT).show();
        }
        return file;
    }
    public File getAlbumStorageDirPrivate(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(getApplicationContext().getExternalFilesDir(
                Environment.DIRECTORY_MUSIC), albumName);
        if (!file.mkdirs()) {
//            Log.e(LOG_TAG, "Directory not created");
            Toast.makeText(getApplicationContext(), "Directory not created", Toast.LENGTH_SHORT).show();
        }
        return file;
    }
    public void loadData(){
        String data = showData();
        if (data == null){
            Toast.makeText(getApplicationContext(), "No Data to Load", Toast.LENGTH_SHORT).show();
        }
        else{
            m_data = (EditText)findViewById(R.id.data);
            m_data.setText(data);
            Toast.makeText(getApplicationContext(), "Data Loaded", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveData(View view){
        FileOutputStream fos;
        m_email = (EditText)findViewById(R.id.email);
        String email = m_email.getText().toString();

        m_name = (EditText)findViewById(R.id.name);
        String name = m_name.getText().toString();

        try{
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(name.getBytes());
            fos.write(email.getBytes());
            fos.close();
            Snackbar.make(view, "Data Written to Internal File", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            loadData();
        }
        catch (IOException e){
            e.printStackTrace();
            Snackbar.make(view, "SomeThing Went Wrong", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }


    }
    public void onClickLoad(View view){
        someData = getSharedPreferences(preference_file, MODE_PRIVATE);
        String name_returned = someData.getString("my_name", "Enter Your Name");
        String email_returned = someData.getString("my_email", "Enter Your Email");
        m_read = (TextView)findViewById(R.id.readState);
        m_write = (TextView)findViewById(R.id.writeState);
        m_read.setText(name_returned);
        m_write.setText(email_returned);
    }
    public void onClickSave(View view){
        m_name = (EditText)findViewById(R.id.name);
        m_email = (EditText)findViewById(R.id.email);
        SharedPreferences.Editor editor = someData.edit();
        editor.putString("my_name", m_name.getText().toString() );
        editor.putString("my_email", m_email.getText().toString() );
        editor.apply();
    }

}