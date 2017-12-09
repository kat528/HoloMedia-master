package com.holomedia.holomedia;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    private boolean phoneDevice = true;

    private Button src_btn;
    private Animation SrcAnimation;
    private Handler handler;
    BottomNavigationView bottomNavigationView ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE )
            phoneDevice = false;

        if (phoneDevice)
            setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.down_toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favorite:
                                Intent i=new Intent(MainActivity.this,VideoView.class);
                                Log.i(TAG, "onNavigationItemSelected: ");
                                startActivity(i);
                                break;
                            case R.id.Gallery:
                                Intent k=new Intent(MainActivity.this,VideoView.class);
                                Log.i(TAG, "onNavigationItemSelected: ");
                                startActivity(k);
                                break;
                            case R.id.add:
                                Intent a=new Intent(MainActivity.this,Add_video.class);
                            Log.i(TAG, "onNavigationItemSelected: ");
                            startActivity(a);
                            break;
                        }
                        return false;
                    }
                });



        handler=new Handler();
        SrcAnimation= AnimationUtils.loadAnimation(this,R.anim.search_anim);
        SrcAnimation.start();



        Log.i(TAG, "onCreate: ");




    }

    protected void onStart(){
        super.onStart();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HoloMedia");
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        Log.i(TAG, "onStart: ");



    }











    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Display display = ((WindowManager)
                getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        Point screenSize = new Point();
        Log.i(TAG, "onCreateOptionsMenu: ");
        display.getRealSize(screenSize);
        if (screenSize.x < screenSize.y) // x είναι το πλάτος,  y είναι το ύψος
        {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            // διογκώνει το μενού
            return true;
        } else
            return false;

    }


    private void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.Qalert);
        alertDialogBuilder.setPositiveButton(R.string.YesAlert,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        System.exit(0);
                    }
                });

        alertDialogBuilder.setNegativeButton(R.string.NoAlert,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    private void exitdialog(){
        open();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu:
                Log.i(TAG, "onOptionsItemSelected: ");
                Intent h=new Intent(this,MainActivity.class);
                startActivity(h);
                return true;
            case R.id.exit:
                exitdialog();
                return true;




            default:
                return super.onOptionsItemSelected(item);


        }
    }



}
