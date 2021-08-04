package com.theprogrammer.qrcoins;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by markmamdouh on 9/27/2016.
 */


public class Select_Name extends ListActivity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		final File dir = Environment.getExternalStorageDirectory();
        List<String> files = getListFiles(new File(dir + "/QR Coins/Coins"));

		setListAdapter(new ArrayAdapter<>(this, R.layout.item_view2, files));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);


		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                //Alert
                AlertDialog.Builder myAlert = new AlertDialog.Builder(Select_Name.this);
                myAlert.setMessage("Are You Sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //get coins from previous activity
                                Intent intent = getIntent();
                                String message = intent.getStringExtra("coin");
                                int choice = Integer.parseInt(message);

                                // get selected item name
                                String name = parent.getItemAtPosition(position).toString();
                                name = name.substring(4, name.length());

                                File dir = Environment.getExternalStorageDirectory();
                                //Get the text file
                                File file = new File(dir + "/QR Coins/Coins", name + ".json");
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
                                        Toast.makeText(Select_Name.this, "Error!!", Toast.LENGTH_LONG).show();
                                    }
                                    int coins = Integer.parseInt(String.valueOf(text));
                                    coins += choice;
                                    try {
                                        File root = new File(Environment.getExternalStorageDirectory(), "QR Coins/Coins");
                                        File gpxfile = new File(root, name + ".json");
                                        FileWriter writer = new FileWriter(gpxfile);
                                        writer.append(String.valueOf(coins));
                                        writer.flush();
                                        writer.close();
                                    } catch (IOException e) {
                                        Toast.makeText(Select_Name.this, "Error saving coin!!", Toast.LENGTH_LONG).show();
                                    }
                                    Toast.makeText(Select_Name.this, "Coins Updated", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNeutralButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmation!")
                        .setIcon(R.drawable.dialog)
                        .create();
                myAlert.show();
			}
		});

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
        Collections.sort(inFiles);
        for(int i=0; i<inFiles.size(); i++){
            if(i%9 == i){
                inFiles.set(i, "0" + (i + 1) + "- " + inFiles.get(i));
            }
            else {
                inFiles.set(i, i + 1 + "- " + inFiles.get(i));
            }
        }
		return inFiles;
	}
}