package com.holomedia.holomedia;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";

    private Button src_btn;
    private Animation SrcAnimation;
    private Handler handler;
    BottomNavigationView bottomNavigationView ;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String speechResult;
    private int [] videos = {R.raw.butterfly, R.raw.earth, R.raw.heart};
    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        src_btn = findViewById(R.id.search_button);
        src_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askSpeechInput();
            }
        });

        bottomNavigationView = findViewById(R.id.down_toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favorite:
                                Intent l = null;
                                String file = readFromFile("config.txt");
                                if (file.length() < 1){
                                    no_favorite();
                                }else {
                                    String titles[] = file.split("\n");
                                    String title[] = new String[titles.length];
                                    String[] video = new String[titles.length];
                                    for (int i = 0; i < titles.length; i++) {
                                        video[i] = titles[i];
                                        if(video[i].contains("android.resource")){
                                            title[i] = getResources().getResourceEntryName(videos[i]);
                                        }else{
                                            title[i] = video[i].split("/")[6];
                                        }
                                    }
                                    l = new Intent(MainActivity.this, VideoView.class);
                                    l.putExtra("vid", video);
                                    if(title[0] != "foo") l.putExtra("title",title);
                                    Log.i(TAG, "onNavigationItemSelected: ");
                                    startActivity(l);
                                }
                                break;
                            case R.id.Gallery:
                                Intent k=new Intent(MainActivity.this,VideoView.class);
                                String title[];
                                String text = readFromFile("AddedVideos.txt");
                                if (text.length() > 1) {
                                    String[] v = text.split("\n");
                                    title = new String[videos.length+v.length];
                                    String[] video = new String[videos.length+v.length];
                                    int i = -1;
                                    for(int j=0; j<videos.length + v.length; j++){
                                        if(j<3) {
                                            video[j] = "android.resource://" + getPackageName() + "/" + videos[j];
                                            title[j] = getResources().getResourceEntryName(videos[j]);
                                        }else{
                                            i++;
                                            video[j] = v[i];
                                            title[j] = video[j].split("/")[6];
                                        }
                                    }
                                    k.putExtra("vid",video);
                                    k.putExtra("title",title);
                                }else {
                                    String[] video = new String[videos.length];
                                    title = new String[videos.length];
                                    for (int j = 0; j < videos.length; j++) {
                                        video[j] = "android.resource://" + getPackageName() + "/" + videos[j];
                                        title[j] = getResources().getResourceEntryName(videos[j]);
                                    }
                                    k.putExtra("vid",video);
                                    k.putExtra("title",title);
                                }
                                Log.i(TAG, "onNavigationItemSelected: ");
                                startActivity(k);
                                break;
                            case R.id.add:
                                Warning();

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


    private String readFromFile(String name){
        StringBuilder text = new StringBuilder();
        try {
            File file = new File(getFilesDir().getAbsolutePath(),name);
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


    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Please tell the Title of the Video");
        try {
            startActivityForResult(intent, 200);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {

                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                speechResult = result.get(0);
                Toast.makeText(getApplicationContext(),
                        speechResult,
                        Toast.LENGTH_SHORT).show();
                String title;
                boolean found = false;
                for (int i = 0; i < videos.length; i++) {
                    title = this.getResources().getResourceEntryName(videos[i]);
                    if (speechResult.equals(title)) {
                        found = true;
                    }
                }
                if (found) {
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + getResources().getIdentifier(speechResult, "raw", getPackageName()));
                    Intent a = new Intent(MainActivity.this, PlayVideo.class);
                    a.putExtra("uri", uri);
                    startActivity(a);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Try again...",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    protected void onStart(){
        super.onStart();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HoloMedia");
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        Log.i(TAG, "onStart: ");



    }


private void Warning() {
    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
    LayoutInflater adbInflater = LayoutInflater.from(MainActivity.this);
    View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
    dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
    adb.setView(eulaLayout);
    adb.setIcon(R.drawable.alert);
    adb.setTitle("Warning!!!");
    adb.setMessage("Be sure to click on the help menu for advice \\\"how to make hologram videos\\\" before you continue... ");
    adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String checkBoxResult = "NOT checked";
            if (dontShowAgain.isChecked())
                checkBoxResult = "checked";
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("skipMessage", checkBoxResult);
            // Commit the edits!
            editor.commit();
            Intent a=new Intent(MainActivity.this,Add_video.class);
            Log.i(TAG, "onNavigationItemSelected: ");
            startActivity(a);
        }
    });

    adb.setNegativeButton("Take me there!", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String checkBoxResult = "NOT checked";
            if (dontShowAgain.isChecked())
                checkBoxResult = "checked";
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("skipMessage", checkBoxResult);
            // Commit the edits!
            editor.commit();
            Intent a=new Intent(MainActivity.this,HelpActvity.class);
            Log.i(TAG, "onNavigationItemSelected: ");
            startActivity(a);

        }
    });
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    String skipMessage = settings.getString("skipMessage", "NOT checked");

    if (!skipMessage.equals("checked"))
        adb.show();
    else {
        Intent b=new Intent(MainActivity.this,Add_video.class);
        Log.i(TAG, "onNavigationItemSelected: ");
        startActivity(b);}

}



    private void no_favorite(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("No Favorites Yet");
        alertDialogBuilder.setIcon(R.drawable.no_fav_ic);
        alertDialogBuilder.setMessage("Please add some favorite videos first...   ");
        alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Log.i(TAG, "onNavigationItemSelected: ");

            }

        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
                Intent h=new Intent(this,AboutActivity.class);
                startActivity(h);
                return true;
            case R.id.exit:
                exitdialog();
                return true;
            case R.id.help:
                Intent b=new Intent(this,HelpActvity.class); //some code here
                startActivity(b);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}