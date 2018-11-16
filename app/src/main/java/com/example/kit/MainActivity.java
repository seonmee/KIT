package com.example.kit;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kit.Adapter.FragementAdapter;

public class MainActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* toolbar 설정 */
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("");

        FragmentPagerAdapter mFragmentPagerAdapter = new FragementAdapter(getSupportFragmentManager());
        ViewPager mViewPager =  (ViewPager)findViewById(R.id.viewpager);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        /* tab 설정 */
        TabLayout mTabLayout = (TabLayout)findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    /* toolbar 메뉴 버튼 추가 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return  true;
    }

    /* toolbar 클릭시 event */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_bar:
                Toast.makeText(getApplicationContext(),"메뉴 버튼",Toast.LENGTH_LONG).show();
                return true;

            default:
                Toast.makeText(getApplicationContext(),"나머지 버튼",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
}
