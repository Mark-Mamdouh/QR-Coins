package com.theprogrammer.qrcoins;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markmamdouh on 7/16/2016.
 */

public class MainActivity extends AppCompatActivity {

    private List<Buttons> myButtons = new ArrayList<>();
    private List<String> files;

    // Defining Permission codes.
    // We can give any value
    // but unique for each permission.
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        populateButtonList();
        populateListView();
        registerClickCallback();

        String path = Environment.getExternalStorageDirectory().toString();
        String dirPath = path + "/QR Coins/";
        File dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        dirPath = path + "/QR Coins/Coins";
        dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        dirPath = path + "/QR Coins/Images";
        dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        dirPath = path + "/QR Coins/Generated";
        dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
    }

    private void populateButtonList() {
        myButtons.add(new Buttons("Add Name", R.drawable.add));
        myButtons.add(new Buttons("Add Coins", R.drawable.coins));
        myButtons.add(new Buttons("Remove Coins", R.drawable.coins2));
        myButtons.add(new Buttons("Get 3 Winners", R.drawable.get));
        myButtons.add(new Buttons("Show All Names", R.drawable.show));
        myButtons.add(new Buttons("Delete A Name", R.drawable.delete));
        myButtons.add(new Buttons("Reset Coins", R.drawable.coins3));
        myButtons.add(new Buttons("Reset", R.drawable.delete_all));
        myButtons.add(new Buttons("QR Scanner and Generator", R.drawable.generate));
        myButtons.add(new Buttons("Exit", R.drawable.exit));
    }

    private void populateListView() {
        ArrayAdapter<Buttons> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.ButtonsListView);
        if (list != null) {
            list.setAdapter(adapter);
        }
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.ButtonsListView);
        if (list != null) {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                    String clickedCar = myButtons.get(position).getName();
                    switch (clickedCar){
                        case "Add Name" : {
                            startActivity(new Intent(MainActivity.this, Add_Name.class));
                            break;
                        }
                        case "Add Coins" : {
                            startActivity(new Intent(MainActivity.this, Add_Coins.class));
                            break;
                        }
                        case "Remove Coins" : {
                            startActivity(new Intent(MainActivity.this, Remove_Coins.class));
                            break;
                        }
                        case "Get 3 Winners" : {
                            startActivity(new Intent(MainActivity.this, Winner1.class));
                            break;
                        }
                        case "Show All Names" : {
                            File dir = Environment.getExternalStorageDirectory();
                            files = getListFiles(new File(dir + "/QR Coins/Coins"));
                            if(files.size() == 0 || files.get(0).equals("")){
                                Toast.makeText(MainActivity.this, "There are no names", Toast.LENGTH_LONG).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this, Show_All_Names.class));
                            }
                            break;
                        }
                        case "Delete A Name" : {
                            startActivity(new Intent(MainActivity.this, Delete.class));
                            break;
                        }
                        case "Reset Coins" : {
                            startActivity(new Intent(MainActivity.this, Reset_All_Coins.class));
                            break;
                        }
                        case "Reset" : {
                            startActivity(new Intent(MainActivity.this, Reset_All_Names.class));
                            break;
                        }
                        case "QR Scanner and Generator" : {
                            startActivity(new Intent(MainActivity.this, Generator_And_Scanner.class));
                            break;
                        }
                        case "Exit" : {
                            finishAffinity();
                            break;
                        }
                    }
                }
            });
        }
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
            // Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            // do nothing
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do nothing
            } else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    // ************************************************************************************************* //

    private class MyListAdapter extends ArrayAdapter<Buttons> {

        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, myButtons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }
            // Find the button to work with.
            Buttons currentButton = myButtons.get(position);
            // Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentButton.getIconID());
            // Make:
            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
            makeText.setText(currentButton.getName());
            return itemView;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }

    private List<String> getListFiles(File parentDir) {
        ArrayList<String> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".json")){
                    inFiles.add(file.getName().substring(0, file.getName().length()- 5));
                }
            }
        }
        return inFiles;
    }
}
