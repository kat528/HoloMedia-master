package com.holomedia.holomedia;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Add_video extends AppCompatActivity {

    public  static final int  MY_PERMISSIONS_REQUEST_STORAGE = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final String TAG = "TEST";
    Button LoadVidGal;
    Button LoadVidInt;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }




        LoadVidGal = (Button)findViewById(R.id.LoadVidGal);
        LoadVidGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });

        LoadVidInt = (Button)findViewById(R.id.LoadVidInt);
        LoadVidInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
                startActivity(i);
            }
        });




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HoloMedia");
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Uri uri = data.getData();                                                       //Videoplay
            Intent i = new Intent(this, PlayVideo.class);
            i.putExtra("uri",uri);
            startActivity(i);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Display display = ((WindowManager)
                getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        Point screenSize = new Point();
        Log.i(TAG, "onCreateOptionsMenu: ");
        display.getRealSize(screenSize);
        if (screenSize.x < screenSize.y) // x είναι το πλάτος,  y είναι το ύψος             //Resolution
        {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            // διογκώνει το μενού
            return true;
        } else
            return false;

    }




//Toolbar Settings------------------------------------------------------------------------------------



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


    @Override
    public boolean onSupportNavigateUp() {              //Back Button
        onBackPressed();
        return true;
    }



    private void exitdialog(){
        open();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.about_menu:
                Log.i(TAG, "onOptionsItemSelected: ");
                Intent a=new Intent(this,AboutActivity.class); //some code here
                startActivity(a);
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