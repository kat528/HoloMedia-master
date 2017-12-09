package com.holomedia.holomedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class VideoFragment extends Fragment {

    int page, position;
    private String title;
    private int[] videos = new int[]{R.raw.butterfly, R.raw.heart, R.raw.earth};

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
        videoName.setText(page + " -- " + title);
        ImageButton imageView = view.findViewById(R.id.imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vid = 0;
                for(int i=0; i<videos.length; i++){
                    String title = view.getContext().getResources().getResourceName(videos[i]);
                    String name[] = title.split("/");
                    if (name[1] == title){
                        vid = videos[i];
                        break;
                    }
                }
                Intent a=new Intent(view.getContext(),PlayVideo.class);
                a.putExtra("videoSource",vid);
                startActivity(a);
            }
        });
        imageView.setImageResource(position);
        return view;
    }
}
