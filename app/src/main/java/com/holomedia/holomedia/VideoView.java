package com.holomedia.holomedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class VideoView extends FragmentActivity {

    private int [] videos = {R.drawable.butterfly, R.drawable.earth, R.drawable.heart};
    private ViewPager mPager;
    private FragmentPagerAdapter mPagerAdapter;
    private Context context = this;
    public String TAG="TEST";
    boolean showingFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.fav_toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {


                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favorite:
                                if(showingFirst==true) {
                                Context context = getApplicationContext();
                                CharSequence text = "Added to Favorites";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                showingFirst=false;
                                }
                                else {
                                    Context context = getApplicationContext();
                                    CharSequence text = "Deleted from Favorites";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    item.setIcon(R.drawable.heart_on);
                                    showingFirst=true;
                                }


                                break;


                            case R.id.home:
                                Intent k=new Intent(VideoView.this,MainActivity.class);
                                Log.i(TAG, "onNavigationItemSelected: ");
                                startActivity(k);
                                break;

                        }
                        return false;
                    }
                });
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