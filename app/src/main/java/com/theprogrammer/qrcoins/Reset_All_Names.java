package com.theprogrammer.qrcoins;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by markmamdouh on 7/24/2016.
 */

public class Reset_All_Names extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder myAlert = new AlertDialog.Builder(Reset_All_Names.this);
        myAlert.setMessage("Do you want to delete all names with coins?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            File dir = new File(Environment.getExternalStorageDirectory() + "/QR Coins/Coins");
                            if (dir.isDirectory()) {
                                String[] children = dir.list();
                                for (int i = 0; i < children.length; i++) {
                                    new File(dir, children[i]).delete();
                                }
                            }

                            dir = new File(Environment.getExternalStorageDirectory() + "/QR Coins/Images");
                            if (dir.isDirectory()) {
                                String[] children = dir.list();
                                for (int i = 0; i < children.length; i++)
                                {
                                    new File(dir, children[i]).delete();
                                }
                            }

                            Toast.makeText(Reset_All_Names.this, "All Deleted", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Reset_All_Names.this, MainActivity.class));
                        } catch (Exception e) {
                            Toast.makeText(Reset_All_Names.this, "Error! Please give storage permission", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Reset_All_Names.this, MainActivity.class));
                    }
                })
                .setTitle("Confirmation!")
                .setIcon(R.drawable.dialog)
                .create();
        myAlert.show();
    }
}
