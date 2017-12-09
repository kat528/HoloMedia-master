package com.holomedia.holomedia;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class VideoView extends FragmentActivity {

    private int [] videos = {R.drawable.butterfly, R.drawable.earth, R.drawable.heart};
    private ViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        mPager = (ViewPager) findViewById(R.id.pager);
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
            for (int i=0; i<videos.length; i++){
                if (position == i){
                    String title = context.getResources().getResourceName(videos[position]);
                    String name[] = title.split("/");
                    return new VideoFragment().newInstance(videos[position], i, name[1]);
                }
            }
            return null;
        }

        @Override
        public int getCount(){
            return videos.length;
        }
    }
}