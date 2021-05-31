package com.ensias.moneyManager.addActivityPack;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ensias.moneyManager.R;
import com.ensias.moneyManager.Item;
import com.ensias.moneyManager.data.DataBaseDbHelper;



public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "extra_type";
    public static final int RC_ADD_ITEM = 333;
    public static final String RESULT_ITEM = "result_item";

    private Toolbar mToolbar;
    private EditText mItemName;
    private EditText mItemPrice;
    private ImageButton mAddButton;
    private String mType;
    private Item mItem;
    private DataBaseDbHelper mDataBaseDbHelper;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mDataBaseDbHelper = new DataBaseDbHelper(getApplicationContext());


        mToolbar = findViewById(R.id.toolbar_add_activity);
        mToolbar.setTitle("Add item");

        setSupportActionBar(mToolbar);

        mType = getIntent().getStringExtra(EXTRA_TYPE);

        mItemName = findViewById(R.id.item_name_edit_text);
        mItemPrice = findViewById(R.id.item_price_edit_text);

        mAddButton = findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(mItemName) || isEmpty(mItemPrice)) {
                    Toast.makeText(AddActivity.this, R.string.toast_isEmpty, Toast.LENGTH_SHORT).show();

                }
                else {
                    addItem();

                    finish();

                }
            }
        });

    }

    private void addItem() {
        mItem = new Item(mItemName.getText().toString(),
                Integer.parseInt(mItemPrice.getText().toString()),
                mType,
                mDataBaseDbHelper.dataBaseSize()+1);

        Intent intent = new Intent();
        intent.putExtra(RESULT_ITEM, mItem);
        setResult(RESULT_OK, intent);

    }

    private boolean isEmpty (EditText editText) {
        if (editText.getText().length() > 0)
            return false;
        else
            return true;

    }

}
