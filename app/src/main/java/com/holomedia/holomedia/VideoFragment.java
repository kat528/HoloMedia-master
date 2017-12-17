package com.holomedia.holomedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class VideoFragment extends Fragment {


    int page, position;
    private String title;
    private int[] videos = new int[]{R.raw.butterfly, R.raw.earth, R.raw.heart};
    boolean showingFirst = true;
    public String TAG="TEST";

    public static VideoFragment newInstance(int position, int page, String title) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putInt("somePosition", position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        position = getArguments().getInt("somePosition");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        TextView videoName = (TextView) view.findViewById(R.id.videoname);
        videoName.setText(page + 1 + " -- " + title);
        ImageButton imageView = view.findViewById(R.id.imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + videos[page]);
                Intent a=new Intent(view.getContext(),PlayVideo.class);
                a.putExtra("uri",uri);
                startActivity(a);
            }
        });


        imageView.setImageResource(position);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                view.findViewById(R.id.fav_toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {


                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favorite:
                                if(showingFirst==true) {
                                    Context context = getActivity();
                                    CharSequence text = "Added to Favorites";
                                    int duration = Toast.LENGTH_SHORT;
                                    item.setIcon(R.drawable.heart_off);
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    showingFirst=false;
                                }
                                else {
                                    Context context = getActivity();
                                    CharSequence text = "Deleted from Favorites";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    item.setIcon(R.drawable.heart_wh);
                                    showingFirst=true;
                                }


                                break;


                            case R.id.home:
                                Intent k=new Intent(getActivity(),MainActivity.class);
                                Log.i(TAG, "onNavigationItemSelected: ");
                                startActivity(k);
                                break;

                        }
                        return false;
                    }
                });


        return view;

    }



}