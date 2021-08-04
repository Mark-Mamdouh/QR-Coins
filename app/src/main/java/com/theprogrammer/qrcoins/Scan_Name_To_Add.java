package com.theprogrammer.qrcoins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by markmamdouh on 7/16/2016.
 */

public class Scan_Name_To_Add extends AppCompatActivity {

    private Button gold, silver, bronze, other;
    private final Activity activity = this;
    private int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_name);

        gold = (Button) findViewById(R.id.gold);
        silver = (Button) findViewById(R.id.silver);
        bronze = (Button) findViewById(R.id.bronze);
        other = (Button) findViewById(R.id.other);

        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
                choice = 3;
            }
        });
        silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
                choice = 2;
            }
        });
        bronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
                choice = 1;
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
                choice = 1;
            }
        });
    }

    private void scan(){
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan Name");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                File dir = Environment.getExternalStorageDirectory();
                //Get the text file
                File file = new File(dir + "/QR Coins/Coins", result.getContents() + ".json");
                if (file.exists())   // check if file exist
                {
                    //Read text from file
                    StringBuilder text = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;

                        while ((line = br.readLine()) != null) {
                            text.append(line);
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, "Error!!", Toast.LENGTH_LONG).show();
                    }
                    int coins = Integer.parseInt(String.valueOf(text));
                    coins += choice;
                    try {
                        File root = new File(Environment.getExternalStorageDirectory(), "QR Coins/Coins");
                        File gpxfile = new File(root, result.getContents() + ".json");
                        FileWriter writer = new FileWriter(gpxfile);
                        writer.append(String.valueOf(coins));
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        Toast.makeText(Scan_Name_To_Add.this, "Error saving coin!!", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(Scan_Name_To_Add.this, "Coins Added", Toast.LENGTH_LONG).show();
                } else {
                    // This is important, otherwise the result will not be passed to the fragment
                    Toast.makeText(Scan_Name_To_Add.this, "Error!! This name isn't exists", Toast.LENGTH_LONG).show();
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }

        }
    }
}
