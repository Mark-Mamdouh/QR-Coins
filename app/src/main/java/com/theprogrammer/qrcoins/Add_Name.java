package com.theprogrammer.qrcoins;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by markmamdouh on 7/16/2016.
 */

public class Add_Name extends AppCompatActivity {

    private Button enter;
    private TextView name;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_name);
        enter = (Button) findViewById(R.id.enter);
        name = (TextView) findViewById(R.id.editText);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text2QR = name.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
                hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

                // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
                hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                try{
                    ////////////////////Generate QRCode////////////////////////
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE, 500, 500, hintMap);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                    ////////////////////Generate QRCode////////////////////////
                    ////////////////////Save QRCode///////////////////////////
                    OutputStream stream = null;
                    try {
                        stream = new FileOutputStream("/sdcard/QR Coins/Images/" + text2QR.toString() + ".jpg");
                    } catch (FileNotFoundException e) {
                        Toast.makeText(Add_Name.this, "Failed to save! Please give storage permission", Toast.LENGTH_LONG).show();
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    ////////////////////Save QRCode///////////////////////////
                    //////////////////////create json file////////////////////////
                    try {
                        File root = new File(Environment.getExternalStorageDirectory(), "QR Coins/Coins");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File gpxfile = new File(root, text2QR + ".json");
                        if(gpxfile.exists()){
                            Toast.makeText(Add_Name.this, "This name is already exists!!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            FileWriter writer = new FileWriter(gpxfile);
                            writer.append("0");
                            writer.flush();
                            writer.close();
                            Toast.makeText(Add_Name.this, "Saved", Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(Add_Name.this, "Error saving coin!!", Toast.LENGTH_LONG).show();
                    }
                    //////////////////////create json file////////////////////////
                }catch (Exception e){
                    Toast.makeText(Add_Name.this, "ERROR!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
