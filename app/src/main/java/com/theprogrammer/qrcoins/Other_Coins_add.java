package com.theprogrammer.qrcoins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by markmamdouh on 7/16/2016.
 */

public class Other_Coins_add extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_coins);

        final EditText num = (EditText) findViewById(R.id.num);
        Button ok = (Button) findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Other_Coins_add.this, Select_Name.class);
                intent.putExtra("coin", num.getText().toString());
                startActivity(intent);
            }
        });
    }

}
