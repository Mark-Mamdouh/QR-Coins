package com.theprogrammer.qrcoins;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by markmamdouh on 9/27/2016.
 */


public class Delete extends ListActivity {

	private List<String> files;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			File dir = Environment.getExternalStorageDirectory();
			files = getListFiles(new File(dir + "/QR Coins/Coins"));

			setListAdapter(new ArrayAdapter<>(this, R.layout.item_view2, files));

			ListView listView = getListView();
			listView.setTextFilterEnabled(true);

			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					// get selected item name
					String name = parent.getItemAtPosition(position).toString();

					File dir = Environment.getExternalStorageDirectory();
					File file = new File(dir + "/QR Coins/Coins/" + name + ".json");
					boolean deleted = file.delete();
					if(deleted){
						Toast.makeText(Delete.this, "Deleted " + name, Toast.LENGTH_LONG).show();
						startActivity(new Intent(Delete.this, MainActivity.class));
					}
					else{
						Toast.makeText(Delete.this, "Error!!", Toast.LENGTH_LONG).show();
					}
					file = new File(dir + "/QR Coins/Images/" + name + ".jpg");
					file.delete();
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
        Collections.sort(inFiles);
		return inFiles;
	}
}