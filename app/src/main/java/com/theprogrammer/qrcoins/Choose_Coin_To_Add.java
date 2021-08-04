package com.theprogrammer.qrcoins;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markmamdouh on 9/27/2016.
 */

public class Choose_Coin_To_Add extends AppCompatActivity {

    private Button gold, silver, bronze, other;
    private List<String> files;
    private String coins = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_name);

        gold = (Button) findViewById(R.id.gold);
        silver = (Button) findViewById(R.id.silver);
        bronze = (Button) findViewById(R.id.bronze);
        other = (Button) findViewById(R.id.other);

        try {
            File dir = Environment.getExternalStorageDirectory();
            files = getListFiles(new File(dir + "/QR Coins/Coins"));

            gold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(files.size() == 0 || files.get(0).equals("")){
                        Toast.makeText(Choose_Coin_To_Add.this, "There are no names!!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        coins = "3";
                        Intent intent = new Intent(Choose_Coin_To_Add.this, Select_Name.class);
                        intent.putExtra("coin", coins);
                        startActivity(intent);
                    }
                }
            });
            silver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(files.size() == 0 || files.get(0).equals("")){
                        Toast.makeText(Choose_Coin_To_Add.this, "There are no names!!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        coins = "2";
                        Intent intent = new Intent(Choose_Coin_To_Add.this, Select_Name.class);
                        intent.putExtra("coin", coins);
                        startActivity(intent);
                    }
                }
            });
            bronze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(files.size() == 0 || files.get(0).equals("")){
                        Toast.makeText(Choose_Coin_To_Add.this, "There are no names!!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        coins = "1";
                        Intent intent = new Intent(Choose_Coin_To_Add.this, Select_Name.class);
                        intent.putExtra("coin", coins);
                        startActivity(intent);
                    }
                }
            });
            other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(files.size() == 0 || files.get(0).equals("")){
                        Toast.makeText(Choose_Coin_To_Add.this, "There are no names!!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        startActivity(new Intent(Choose_Coin_To_Add.this, Other_Coins_add.class));
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error! Please give storage permission", Toast.LENGTH_LONG).show();
        }
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
