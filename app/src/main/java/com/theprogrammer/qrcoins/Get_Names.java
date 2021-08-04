package com.theprogrammer.qrcoins;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markmamdouh on 7/21/2016.
 */

public class Get_Names extends AppCompatActivity {

    private int max = -1;
    private List<File> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_name);

        TextView tv1 = (TextView)findViewById(R.id.tv1);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        TextView tv3 = (TextView)findViewById(R.id.tv3);

        File dir = Environment.getExternalStorageDirectory();
//        File file = new File(dir + "/QR Names/", "Marktxt.txt");
        files = getListFiles(new File(dir + "/QR Coins/Coins"));

        if(files.size() == 0){
            tv1.setText("NONE");
            tv2.setText("NONE");
            tv3.setText("NONE");
        }
        else if(files.size() == 1){
            tv1.setText(files.get(0).getName().substring(0, files.get(0).getName().length()- 5));
        }
        else if(files.size() == 2){
            int num1 = getNum(0);
            int num2 = getNum(1);
            if(num1 > num2){
                tv1.setText(files.get(0).getName().substring(0, files.get(0).getName().length()- 5));
                tv2.setText(files.get(1).getName().substring(0, files.get(0).getName().length()- 5));
            }
            else if(num1 < num2){
                tv1.setText(files.get(1).getName().substring(0, files.get(0).getName().length()- 5));
                tv2.setText(files.get(0).getName().substring(0, files.get(0).getName().length()- 5));
            }
            else{
                tv1.setText(files.get(0).getName().substring(0, files.get(0).getName().length()- 5) + "AND" + files.get(1).getName().substring(0, files.get(0).getName().length()- 5));
            }
        }
        else{
            String fileName = "";
            for(int i=0 ; i<files.size() ; i++){
                int coins = getNum(i);
                if(coins > max){
                    max = coins;
                    fileName = files.get(i).getName().substring(0, files.get(0).getName().length()- 5);
                }
                else if(coins == max){
                    fileName += " AND " + files.get(i).getName().substring(0, files.get(0).getName().length()- 5);
                }
            }
            tv1.setText(fileName);
            int second;
            int max2 = max;
            max = -1;
            fileName = "";
            for(int i=0 ; i<files.size() ; i++){
                second = getNum(i);
                if(second > max && second != max2){
                    max = second;
                    fileName = files.get(i).getName().substring(0, files.get(0).getName().length()- 5);
                }
                else if(second == max && second != max2){
                    fileName += " AND " + files.get(i).getName().substring(0, files.get(0).getName().length()- 5);
                }
            }
            if(!fileName.equals("")){
                tv2.setText(fileName);
            }
            int third;
            int max3 = max;
            max = -1;
            fileName = "";
            for(int i=0 ; i<files.size() ; i++){
                third = getNum(i);
                if(third > max && third != max2 && third !=max3){
                    max = third;
                    fileName = files.get(i).getName().substring(0, files.get(0).getName().length()- 5);
                }
                else if(third == max && third != max2 && third !=max3){
                    fileName += " AND " + files.get(i).getName().substring(0, files.get(0).getName().length()- 5);
                }
            }
            if(!fileName.equals("")){
                tv3.setText(fileName);
            }
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
