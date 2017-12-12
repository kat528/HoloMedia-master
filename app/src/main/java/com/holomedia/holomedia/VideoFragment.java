package com.holomedia.holomedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class VideoFragment extends Fragment {


    int page, position;
    private String title;
    private int[] videos = new int[]{R.raw.butterfly, R.raw.earth, R.raw.heart};
    private Toolbar toolbar;

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
                Intent a=new Intent(view.getContext(),PlayVideo.class);
                a.putExtra("videoSource",page);
                startActivity(a);
            }
        });


        imageView.setImageResource(position);




        return view;

    }



        }







