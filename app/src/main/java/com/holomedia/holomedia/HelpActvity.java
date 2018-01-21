package com.holomedia.holomedia;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HelpActvity extends AppCompatActivity {

Button youtubebtn,contbtn,donbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    youtubebtn=(Button) findViewById(R.id.hp_btn);
    contbtn=(Button) findViewById(R.id.cont_button);
    donbtn=(Button) findViewById(R.id.don_button);
        youtubebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToYoutube();
            }
        });

        contbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cont_button();
            }
        });


        donbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donatebtn();
            }
        });





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Help");
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }



    protected void sendToYoutube() {
        String url = "https://www.youtube.com/watch?v=BSGDJeI2vEU&t=769s"; // You could have this at the top of the class as a constant, or pass it in as a method variable, if you wish to send to multiple websites
        Intent i = new Intent(Intent.ACTION_VIEW); // Create a new intent - stating you want to 'view something'
        i.setData(Uri.parse(url));  // Add the url data (allowing android to realise you want to open the browser)
        startActivity(i); // Go go go!

    }

    protected void sendToPaypal() {
        String url = "https://www.paypal.com/signin?"; // You could have this at the top of the class as a constant, or pass it in as a method variable, if you wish to send to multiple websites
        Intent i = new Intent(Intent.ACTION_VIEW); // Create a new intent - stating you want to 'view something'
        i.setData(Uri.parse(url));  // Add the url data (allowing android to realise you want to open the browser)
        startActivity(i); // Go go go!

    }



    private void cont_button() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","Holomedia@suport.com", null));

        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }


    private void donatebtn(){
        AlertDialog.Builder alertDialogBuilder3 = new AlertDialog.Builder(this);
        alertDialogBuilder3.setMessage("Would you like to support the developers of this app?");
        alertDialogBuilder3.setPositiveButton(R.string.YesAlert,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        sendToPaypal();
                    }
                });

        alertDialogBuilder3.setNegativeButton(R.string.YesAlert,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendToPaypal();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder3.create();
        alertDialog.show();
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