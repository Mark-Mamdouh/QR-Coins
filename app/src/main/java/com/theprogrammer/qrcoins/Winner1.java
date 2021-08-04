package com.theprogrammer.qrcoins;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

public class Winner1 extends AppCompatActivity {

    private ImageButton prev, next;
    private ImageView iv;
    private int max = -1;
    private List<File> files;
    private ListView listView;
    private ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_winner);

        prev = (ImageButton) findViewById(R.id.prev);
        next = (ImageButton) findViewById(R.id.next);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setBackgroundResource(R.drawable.first);
        listView = (ListView) findViewById(R.id.list);
        names = new ArrayList<>();

        try {
            File dir = Environment.getExternalStorageDirectory();
            files = getListFiles(new File(dir + "/QR Coins/Coins"));

            if(files.size() == 1){
                names.add(files.get(0).getName().substring(0, files.get(0).getName().length()- 5));
            }
            else if(files.size() == 2){
                int num1 = getNum(0);
                int num2 = getNum(1);
                if(num1 > num2){
                    names.add(files.get(0).getName().substring(0, files.get(0).getName().length()- 5));
                }
                else if(num1 < num2){
                    names.add(files.get(1).getName().substring(0, files.get(1).getName().length()- 5));
                }
                else{
                    names.add(files.get(0).getName().substring(0, files.get(0).getName().length()- 5) + " & " + files.get(1).getName().substring(0, files.get(1).getName().length()- 5));
                }
            }
            else{
                for(int i=0 ; i<files.size() ; i++){
                    int coins = getNum(i);
                    if(coins > max){
                        max = coins;
                        names.add(files.get(i).getName().substring(0, files.get(i).getName().length()- 5));
                    }
                    else if(coins == max){
                        names.add(files.get(i).getName().substring(0, files.get(i).getName().length()- 5));
                    }
                }
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
            listView.setAdapter(arrayAdapter);

            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Winner1.this, MainActivity.class));
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Winner1.this, Winner2.class));
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Error! Please give storage permission", Toast.LENGTH_LONG).show();
        }


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
