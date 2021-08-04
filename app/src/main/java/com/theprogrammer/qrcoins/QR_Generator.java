package com.theprogrammer.qrcoins;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by markmamdouh on 7/1/2016.
 */

public class QR_Generator extends AppCompatActivity {

    private ImageView imageView;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__generator);

        try {
            final Bitmap bitmap = getIntent().getParcelableExtra("pic");
            final Bundle extras = getIntent().getExtras();

            imageView = (ImageView) findViewById(R.id.iv);
            imageView.setImageBitmap(bitmap);

            save = (Button) findViewById(R.id.save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OutputStream stream = null;
                    try {
                        stream = new FileOutputStream("/sdcard/QR Coins/Generated/" + extras.getString("Name")+".jpg");
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        Toast.makeText(QR_Generator.this, "Saved to /sdcard/QR Coins/Generated", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(QR_Generator.this, "Failed to save!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error! Please give storage permission", Toast.LENGTH_LONG).show();
        }
    }
}