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
import java.util.ArrayList;

public class VideoView extends FragmentActivity {


    private ArrayList<Integer> videos = new ArrayList<>();
    String[] v = { "foo"};
    String text = "";
    private ViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;
    private Context context = this;
    boolean firstTime = true;

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
            text = readFromFile();
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
            }
            for (int i=0; i<videos.size(); i++){
                if (position == i){
                    String title = context.getResources().getResourceEntryName(videos.get(position));
                    return new VideoFragment().newInstance(videos.get(position), i, title, true);
                }
            }
            if(v[0] != "foo"){
                int j=0;
                for (int i=videos.size(); i<videos.size() + v.length; i++){
                    if (position == i){
                        String[] t = v[j].split("/");
                        String title = t[6];
                        return new VideoFragment().newInstance2(v[j], i, title, false);
                    }
                    j++;
                }
            }
            return null;
        }

        @Override
        public int getCount(){
            if (v != null)
                return videos.size() + v.length;
            else
                return videos.size();
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