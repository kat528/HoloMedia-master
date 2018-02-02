package com.holomedia.holomedia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;


public class PlayVideo extends Activity {

    private static final String TAG ="TEST" ;
    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (mediaControls == null) {
            mediaControls = new MediaController(PlayVideo.this);
        }


        final ProgressDialog progressDialog = new ProgressDialog(PlayVideo.this);
        // Set progressbar title
        progressDialog.setTitle("Please set the pyramid");
        // Set progressbar message
        progressDialog.setMessage("Loading...");
        // Show progressbar
        progressDialog.show();



        // Find your VideoView in your video_main.xml layout
        myVideoView = (VideoView) findViewById(R.id.video_view);

        Uri uri = getIntent().getParcelableExtra("uri");

        try {
            myVideoView.setMediaController(mediaControls);
            myVideoView.setVideoURI(uri);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        myVideoView.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {


               if (progressDialog.isShowing()) {

                    Runnable progressRunnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                            myVideoView.seekTo(position);
                            if (position == 0) {
                                myVideoView.start();
                            } else {
                                myVideoView.pause();
                            }
                        }
                    };
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 3000);
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }

}




