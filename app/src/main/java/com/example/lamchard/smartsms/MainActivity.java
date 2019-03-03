package com.example.lamchard.smartsms;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewpage_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Add fragments
        adapter.AddFragment(new FragmentDiscussion(), "DISCUSSION");
        adapter.AddFragment(new FragmentDiffusion(), "DIFFUSION");
        adapter.AddFragment(new FragmentContacts(), "CONTACTS");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.settings_id:
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.apropos_id:
                Toast.makeText(this, "A propos Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_id:
                Toast.makeText(this, "Search Icon Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
