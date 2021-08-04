package com.theprogrammer.qrcoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Scanned_Code extends AppCompatActivity {

    String scannedText;

    public void copyButtonClicked(View view) {
        setClipboard(this, scannedText);
        Toast.makeText(this, "Text Copied", Toast.LENGTH_LONG).show();
    }

    public void openLink(View view) {
        Intent intent = new Intent(Scanned_Code.this, Open_Link.class);
        intent.putExtra("URL", scannedText);
        startActivity(intent);
    }

    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned__code);

        Intent intent = getIntent();
        scannedText = intent.getStringExtra("Scanned Text");

        TextView textView = (TextView) findViewById(R.id.scannedText);
        textView.setText(scannedText);
    }
}