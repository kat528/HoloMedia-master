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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class VideoFragment extends Fragment {


    public int page, position;
    private String title;
    boolean showingFirst;
    public String TAG = "TEST";

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
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + getResources().getIdentifier(title, "raw", getActivity().getPackageName()));
                Intent a = new Intent(view.getContext(), PlayVideo.class);
                a.putExtra("uri", uri);
                startActivity(a);
            }
        });

        imageView.setImageResource(position);

        BottomNavigationView bottomNavigationView;

        String file = readFromFile();
        if(file.contains(title)) {
            bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.fav_toolbar);
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.fav_menu2);
            showingFirst = false;
        }
        else {
            bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.fav_toolbar);
            showingFirst = true;
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.favorite:
                                if (showingFirst == true) {
                                    item.setTitle("Unfavorite");
                                    Context context = getActivity();
                                    CharSequence text = "Added to Favorites";
                                    int duration = Toast.LENGTH_SHORT;
                                    item.setIcon(R.drawable.heart_off);
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    writeToFile(title);
                                    showingFirst = false;
                                } else {
                                    item.setTitle("Add Favorite");
                                    Context context = getActivity();
                                    CharSequence text = "Deleted from Favorites";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    deleteToFile(title);
                                    item.setIcon(R.drawable.heart_wh);
                                    showingFirst = true;
                                }


                                break;


                            case R.id.home:
                                Intent k = new Intent(getActivity(), MainActivity.class);
                                Log.i(TAG, "onNavigationItemSelected: ");
                                startActivity(k);
                                break;

                        }
                        return false;
                    }
                });


        return view;

    }

    private String readFromFile(){
        StringBuilder text = new StringBuilder();
        try {
            File file = new File(getContext().getFilesDir().getAbsolutePath(),"config.txt");
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

    private void writeToFile(String g) {
        try {
            File file = new File(getContext().getFilesDir().getAbsolutePath(),"config.txt");
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            if (!file.exists()) {
                outputStreamWriter.write(g);
            }
            outputStreamWriter.append(g + "\n");
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private void deleteToFile(String g) {
        try {
            File file = new File(getContext().getFilesDir().getAbsolutePath(),"config.txt");

            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder text = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.equals(g) && !g.equals("\n")) {
                        text.append(line);
                        text.append('\n');
                    }
                }
                br.close();

                //start writing from here
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                outputStreamWriter.write(text.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();
            }

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
}