package com.ensias.moneyManager.mainActivityPack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ensias.moneyManager.R;

public class BrandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(BrandActivity.this,MainActivity.class));
                                    finish();
                                }
                            }
                ,3000 );
    }
}