package com.theprogrammer.qrcoins;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markmamdouh on 7/23/2016.
 */

public class Winner3 extends AppCompatActivity {

    private ImageButton prev, next;
    private ImageView iv;
    private int max = -1;
    private List<File> files;
    private ListView listView;
    private ArrayList<String> names;

    //  go back to main activity when back button is pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_winner);

        prev = (ImageButton) findViewById(R.id.prev);
        next = (ImageButton) findViewById(R.id.next);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setBackgroundResource(R.drawable.third);
        listView = (ListView) findViewById(R.id.list);
        names = new ArrayList<>();

        File dir = Environment.getExternalStorageDirectory();
        files = getListFiles(new File(dir + "/QR Coins/Coins"));

        if(files.size() > 2){
            String fileName = "";
            for(int i=0 ; i<files.size() ; i++){
                int coins = getNum(i);
                if(coins > max){
                    max = coins;
                    fileName = files.get(i).getName().substring(0, files.get(i).getName().length()- 5);
                }
                else if(coins == max){
                    fileName += " & " + files.get(i).getName().substring(0, files.get(i).getName().length()- 5);
                }
            }
            int second;
            int max2 = max;
            max = -1;
            fileName = "";
            for(int i=0 ; i<files.size() ; i++){
                second = getNum(i);
                if(second > max && second != max2){
                    max = second;
                    fileName = files.get(i).getName().substring(0, files.get(i).getName().length()- 5);
                }
                else if(second == max && second != max2){
                    fileName += " & " + files.get(i).getName().substring(0, files.get(i).getName().length()- 5);
                }
            }
            int third;
            int max3 = max;
            max = -1;
            fileName = "";
            for(int i=0 ; i<files.size() ; i++){
                third = getNum(i);
                if(third > max && third != max2 && third !=max3){
                    max = third;
                    names.add(files.get(i).getName().substring(0, files.get(i).getName().length()- 5));
                }
                else if(third == max && third != max2 && third !=max3){
                    names.add(files.get(i).getName().substring(0, files.get(i).getName().length()- 5));
                }
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(arrayAdapter);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Winner3.this, Winner2.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Winner3.this, MainActivity.class));
            }
        });
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".json")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

    private int getNum(int index){
        StringBuilder text = new StringBuilder();
        int currentNum = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(files.get(index)));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            currentNum = Integer.parseInt(String.valueOf(text));
        }
        catch (IOException e) {
            Toast.makeText(this, "Error!!", Toast.LENGTH_LONG).show();
        }
        return currentNum;
    }
}
