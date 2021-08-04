package com.theprogrammer.qrcoins;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markmamdouh on 9/27/2016.
 */

public class Remove_Coins extends AppCompatActivity {

    private List<Buttons> myButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_coins);

        populateButtonList();
        populateListView();
        registerClickCallback();
    }

    private void populateButtonList() {
        myButtons.add(new Buttons("Scan Name", R.drawable.scan));
        myButtons.add(new Buttons("Select Name", R.drawable.show));
    }

    private void populateListView() {
        ArrayAdapter<Buttons> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {
                String clickedCar = myButtons.get(position).getName();
                switch (clickedCar){
                    case "Scan Name" : {
                        startActivity(new Intent(Remove_Coins.this, Scan_Name_To_Remove.class));
                        break;
                    }
                    case "Select Name" : {
                        startActivity(new Intent(Remove_Coins.this, Choose_Coin_To_Remove.class));
                    }
                }
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Buttons> {
        public MyListAdapter() {
            super(Remove_Coins.this, R.layout.item_view, myButtons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }
            // Find the car to work with.
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("Generator_And_Scanner", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("Generator_And_Scanner", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
