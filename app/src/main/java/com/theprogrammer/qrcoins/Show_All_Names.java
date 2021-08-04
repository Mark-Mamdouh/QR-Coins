package com.theprogrammer.qrcoins;

import android.app.ListActivity;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by markmamdouh on 7/16/2016.
 */


public class Show_All_Names extends ListActivity {

	private List<String> files;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		File dir = Environment.getExternalStorageDirectory();
		files = getListFiles(new File(dir + "/QR Coins/Coins"));

		setListAdapter(new ArrayAdapter<>(this, R.layout.item_view2, files));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show coins of the name

				// get selected item name
				String name = parent.getItemAtPosition(position).toString();


				name = name.substring(4, name.length());

				File sdcard = Environment.getExternalStorageDirectory();

				//Get the text file
				File file = new File(sdcard + "/QR Coins/Coins",name + ".json");

				//Read text from file
				StringBuilder text = new StringBuilder();

				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;

					while ((line = br.readLine()) != null) {
						text.append(line);
					}
					br.close();
					Toast.makeText(Show_All_Names.this, "Coins = " + text, Toast.LENGTH_LONG).show();
				}
				catch (IOException e) {
					//You'll need to add proper error handling here
					Toast.makeText(Show_All_Names.this, "Unknown Error!! ", Toast.LENGTH_LONG).show();
				}
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