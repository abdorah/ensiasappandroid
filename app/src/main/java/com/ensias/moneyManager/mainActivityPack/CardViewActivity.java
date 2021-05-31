package com.ensias.moneyManager.mainActivityPack;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import com.ensias.moneyManager.R;


public class CardViewActivity extends AppCompatActivity {
    CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardView = findViewById(R.id.card_view);
        setContentView(mCardView);
    }
}
