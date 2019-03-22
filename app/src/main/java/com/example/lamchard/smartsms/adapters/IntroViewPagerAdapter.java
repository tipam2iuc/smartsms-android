package com.example.lamchard.smartsms.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamchard.smartsms.Models.ScreenItem;
import com.example.lamchard.smartsms.R;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<ScreenItem> mListScreen;
    Typeface typefaceTitle, typefaceDesc;

    public IntroViewPagerAdapter(Context context, List<ScreenItem> mListScreen) {
        this.mContext = context;
        this.mListScreen = mListScreen;
    }

    public IntroViewPagerAdapter(Context context, List<ScreenItem> mListScreen,Typeface typefaceTitle, Typeface typefaceDesc) {
        this(context,mListScreen);
        this.typefaceTitle = typefaceTitle;
        this.typefaceDesc = typefaceDesc;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen, null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_description);

        title.setTypeface(typefaceTitle);
        description.setTypeface(typefaceDesc);

        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;

    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}
