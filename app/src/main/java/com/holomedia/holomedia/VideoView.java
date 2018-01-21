package com.holomedia.holomedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class VideoView extends FragmentActivity {


    private ArrayList<Integer> videos = new ArrayList<>();
    private ViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        Intent intent = getIntent();
        videos = intent.getIntegerArrayListExtra("vid");
        if (videos.size() == 0) videos.add(R.drawable.no_favorites);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0){
            super.onBackPressed();
        }else{
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            for (int i=0; i<videos.size(); i++){
                if (position == i){
                    String title = context.getResources().getResourceEntryName(videos.get(position));
                    return new VideoFragment().newInstance(videos.get(position), i, title);
                }
            }
            return null;
        }

        @Override
        public int getCount(){
            return videos.size();
        }
    }


}