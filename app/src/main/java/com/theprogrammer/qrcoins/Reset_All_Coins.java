package com.theprogrammer.qrcoins;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by markmamdouh on 10/4/2016.
 */

public class Reset_All_Coins extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder myAlert = new AlertDialog.Builder(Reset_All_Coins.this);
        myAlert.setMessage("Do you want to make all coins = 0?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            File dir = new File(Environment.getExternalStorageDirectory() + "/QR Coins/Coins");
                            if (dir.isDirectory()) {
                                String[] children = dir.list();
                                String name;
                                for (int i = 0; i < children.length; i++) {
                                    name = children[i].substring(0, children[i].length()-5);
                                    int coins = 0;
                                    try {
                                        File root = new File(Environment.getExternalStorageDirectory(), "QR Coins/Coins");
                                        File gpxfile = new File(root, name + ".json");
                                        FileWriter writer = new FileWriter(gpxfile);
                                        writer.append(String.valueOf(coins));
                                        writer.flush();
                                        writer.close();
                                    } catch (IOException e) {
                                        Toast.makeText(Reset_All_Coins.this, "Error resetting Coins!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            Toast.makeText(Reset_All_Coins.this, "All Coins were reset", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Reset_All_Coins.this, MainActivity.class));
                        } catch (Exception e) {
                            Toast.makeText(Reset_All_Coins.this, "Error! Please give storage permission", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Reset_All_Coins.this, MainActivity.class));
                    }
                })
                .setTitle("Confirmation!")
                .setIcon(R.drawable.dialog)
                .create();
        myAlert.show();
    }
}
