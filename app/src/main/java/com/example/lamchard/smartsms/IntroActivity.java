package com.example.lamchard.smartsms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.lamchard.smartsms.adapters.IntroViewPagerAdapter;
import com.example.lamchard.smartsms.models.ScreenItem;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabIndicator;
    Button btnNext;
    Button btnGetStart;
    int position = 0;
    Animation btnAnim;
    TextView txtSpik;
    Typeface typefaceOpenSans, typefaceRighteous,typefaceCormorantBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //verify the preferences
        if(restorePrefData()){

            Intent loginActivity = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }


        setContentView(R.layout.activity_intro);


        //set fonts
        typefaceOpenSans = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        typefaceRighteous = Typeface.createFromAsset(getAssets(),"fonts/Righteous-Regular.ttf");
        typefaceCormorantBold = Typeface.createFromAsset(getAssets(), "fonts/CormorantUpright-Bold.ttf");


        //hide the action bar
        //getSupportActionBar().hide();

        //ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStart = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        txtSpik = findViewById(R.id.txt_skip);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        Typeface typefaceOpenSansBold = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Bold.ttf");
        txtSpik.setTypeface(typefaceOpenSansBold);

        //fill list screenPager
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("SmartSMS","Souvent on s'est dit, vu la musique qu'il propose ( jugé par beaucoup moins bonne qu'a ses débuts ) et la poussé de la nouvelle génération, c'est bientôt la fin pour lui, mais non album après album toujours la même effervescence et je dirai même hystérie autour de lui.",R.drawable.hello));
        mList.add(new ScreenItem("Communauté","Souvent on s'est dit, vu la musique qu'il propose ( jugé par beaucoup moins bonne qu'a ses débuts ) et la poussé de la nouvelle génération, c'est bientôt la fin pour lui, mais non album après album toujours la même effervescence et je dirai même hystérie autour de lui.",R.drawable.commuvity));
        mList.add(new ScreenItem("Promotion","Souvent on s'est dit, vu la musique qu'il propose ( jugé par beaucoup moins bonne qu'a ses débuts ) et la poussé de la nouvelle génération, c'est bientôt la fin pour lui, mais non album après album toujours la même effervescence et je dirai même hystérie autour de lui.",R.drawable.promotion));
        mList.add(new ScreenItem("Service","Souvent on s'est dit, vu la musique qu'il propose ( jugé par beaucoup moins bonne qu'a ses débuts ) et la poussé de la nouvelle génération, c'est bientôt la fin pour lui, mais non album après album toujours la même effervescence et je dirai même hystérie autour de lui.",R.drawable.service_commication));
        mList.add(new ScreenItem("Campagne","Souvent on s'est dit, vu la musique qu'il propose ( jugé par beaucoup moins bonne qu'a ses débuts ) et la poussé de la nouvelle génération, c'est bientôt la fin pour lui, mais non album après album toujours la même effervescence et je dirai même hystérie autour de lui.",R.drawable.campagne));

        //setup viewpage
        screenPager = findViewById(R.id.screen_piewager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList,typefaceCormorantBold,typefaceOpenSans);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //next button click Listner
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = screenPager.getCurrentItem();
                if(position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position == mList.size()-1) {

                    loadLastScreen();
                }

            }
        });

        //tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == mList.size()-1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //skip button click listener
        txtSpik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = mList.size()-1;
                screenPager.setCurrentItem(position);
                loadLastScreen();

            }
        });


        //Get Started button click listener
        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open main activity
                Intent loginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginActivity);

                //Save preferences
                savePrefsData();
                finish();

            }
        });
    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    //show the GETSTARTED Button and hide the indicator and the next button
    private void loadLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        txtSpik.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStart.setVisibility(View.VISIBLE);

        //setup animation
        btnGetStart.setAnimation(btnAnim);
    }
}
