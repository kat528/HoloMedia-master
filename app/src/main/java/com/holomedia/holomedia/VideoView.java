package com.holomedia.holomedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VideoView extends FragmentActivity {


    private String[] videos;
    //String[] v = { "foo"};
    //String text = "";
    private ViewPager mPager;
    private Context context = this;
    //boolean firstTime = true;
    String[] title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        Intent intent = getIntent();
        videos = intent.getStringArrayExtra("vid");
        if(intent.hasExtra("title"))
            title = intent.getStringArrayExtra("title");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        FragmentPagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
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

        private ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /*text = readFromFile();
            if(text.length() > 1){
                v = text.split("\n");
                if(firstTime) {
                    notifyDataSetChanged();
                    firstTime = false;
                }
            }else{
                if(firstTime) {
                    v = null;
                    notifyDataSetChanged();
                    firstTime = false;
                }
            }*/
            if (videos.length != 0/* && v != null*/) {
                int j = -1;
                for (int i = 0; i < videos.length; i++) {
                    if (position == i) {
                        return new VideoFragment().newInstance(videos[i], i, title[i]);
                    }
                }
            }/*else{
                for (int i = 0; i < videos.length; i++){
                    if (position == i) {
                        return new VideoFragment().newInstance(videos[i], i, title[i]);
                    }
                }
            }*/
            return null;
        }

        @Override
        public int getCount(){
            /*if (v != null)
                return videos.length + v.length;
            else*/
                return videos.length;
        }
    }

    private String readFromFile(){
        StringBuilder text = new StringBuilder();
        try {
            File file = new File(getFilesDir().getAbsolutePath(),"AddedVideos.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }catch(IOException e){
            Log.e("Exception", "File read failed: " + e.toString());
        }
        return String.valueOf(text);
    }

}