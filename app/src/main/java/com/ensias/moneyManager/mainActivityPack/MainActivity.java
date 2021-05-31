package com.ensias.moneyManager.mainActivityPack;

import android.content.Intent;
import android.graphics.Color;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ensias.moneyManager.R;
import com.ensias.moneyManager.authenticationActivity.AuthorizationActivity;
import com.ensias.moneyManager.authenticationActivity.NewUserActivity;
import com.ensias.moneyManager.data.DataBaseDbHelper;





public class MainActivity extends AppCompatActivity {
    public static final int RC_CODE = 555;
    public static final String AUTH = "authorization";
    private boolean isLogin = false;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private DataBaseDbHelper mDataBaseDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar_main_activity);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.my_tab_layout);

        mDataBaseDbHelper = new DataBaseDbHelper(getApplicationContext());
        if (mDataBaseDbHelper.getUserPassCode().equals("")) {
            startActivity(new Intent(this, NewUserActivity.class));
        }
        else
            startActivityForResult(new Intent(this, AuthorizationActivity.class), RC_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CODE && resultCode == RESULT_OK){
            if (data.getBooleanExtra(AUTH, false)) {
                isLogin = true;
                mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getResources());
                mViewPager.setAdapter(mPagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLogin) {
            mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getResources());
            mViewPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
        else if (mDataBaseDbHelper.getUserPassCode().equals("")) {
            startActivity(new Intent(this, NewUserActivity.class));
        }
        else {
            startActivityForResult(new Intent(this, AuthorizationActivity.class), RC_CODE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_user:
                mDataBaseDbHelper.mDataBaseItems.execSQL(mDataBaseDbHelper.SQL_DELETE_USERS_TABLE);
                mDataBaseDbHelper.mDataBaseItems.execSQL(mDataBaseDbHelper.SQL_CREATE_USERS_TABLE);
                startActivity(new Intent(this, NewUserActivity.class));
                return true;

            case R.id.exit:
                MainActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
