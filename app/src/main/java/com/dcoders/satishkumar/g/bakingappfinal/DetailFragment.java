package com.dcoders.satishkumar.g.bakingappfinal;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dcoders.satishkumar.g.bakingappfinal.DataClasses.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.dcoders.satishkumar.g.bakingappfinal.ReceipeDetailListActivity.STEP;
import static com.dcoders.satishkumar.g.bakingappfinal.StepDetailActivity.POSITIONKEY;

public class DetailFragment extends Fragment {
    public static final String CURRPOS="CURRENTPOSITION";
    public static final String PLAYBACKPOS="PLAYBACKPOSITION";
    public static final String PLAYWHENREADY="PLAYWHENREADY";
    ImageButton backButton,nextButton;
    ImageView defaultImageView;
    PlayerView playerView;
    SimpleExoPlayer player;
    TextView description;
    String video_url;
    List<Step> stepList,temp;
    long playerStopPosition;
    long playbackPosition;
    boolean playWhenReady=true;
    boolean stopPlay;
    private int current_position;
    int total_items=0;
    
    public DetailFragment()
    {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        temp=new ArrayList<>();
        View v = inflater.inflate(R.layout.detail, container, false);
        if(getArguments()!=null){
            stepList= (List<Step>) getArguments().getSerializable(STEP);
            current_position=getArguments().getInt(POSITIONKEY,0);
            temp=stepList;

        }

        description = v.findViewById(R.id.step_textview);
        backButton = v.findViewById(R.id.backbutton);
        nextButton = v.findViewById(R.id.forwardbutton);
        defaultImageView = v.findViewById(R.id.default_image);
        playerView = v.findViewById(R.id.myplayer);
        playbackPosition = C.TIME_UNSET;
        defaultImageView.setVisibility(View.GONE);
        if(savedInstanceState!=null)
        {
            playWhenReady = savedInstanceState.getBoolean(PLAYWHENREADY);
            current_position = savedInstanceState.getInt(CURRPOS);
            playbackPosition = savedInstanceState.getLong(PLAYBACKPOS, C.TIME_UNSET);

        }
        total_items = stepList.size();
        video_url = stepList.get(current_position).getVideoURL();
        description.setText(stepList.get(current_position).getDescription());
        hideUnhideExo();
        if(current_position == 0)
        {
            backButton.setVisibility(View.GONE);
        }
        if(current_position == (total_items-1))
        {
            nextButton.setVisibility(View.GONE);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                backButton.setVisibility(View.VISIBLE);
                current_position++;
                description.setText(stepList.get(current_position).getDescription());
                video_url = stepList.get(current_position).getVideoURL();
                if(current_position==total_items-1)
                {
                    nextButton.setVisibility(View.GONE);
                }
                hideUnhideExo();
                initializePlayer();
                player.seekTo(0);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                nextButton.setVisibility(View.VISIBLE);
                current_position--;
                description.setText(stepList.get(current_position).getDescription());
                if(current_position == 0)
                {
                    backButton.setVisibility(View.GONE);
                }
                video_url = stepList.get(current_position).getVideoURL();
                hideUnhideExo();
                initializePlayer();
                player.seekTo(0);
            }
        });

        return v;
    }
    private void initializePlayer()
    {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        Uri uri = Uri.parse(video_url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

        player.setPlayWhenReady( playWhenReady);
        if(playbackPosition!=0&&!stopPlay)
        {
            player.seekTo(playbackPosition);
        }
        else {
            player.seekTo(playerStopPosition);
        }
    }
    private MediaSource buildMediaSource(Uri uri)
    {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("SATISH"))
                .createMediaSource(uri);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if (Util.SDK_INT > 23)
        {
            initializePlayer();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if ((Util.SDK_INT <= 23 || player == null))
        {
            initializePlayer();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (player != null) {
                playerStopPosition = player.getCurrentPosition();
                stopPlay = true;
                releasePlayer();
            }

        }
    }
    private void releasePlayer()
    {
        if (player != null)
        {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void hideUnhideExo()
    {
        if(TextUtils.isEmpty(video_url))
        {
            playerView.setVisibility(View.INVISIBLE);
            defaultImageView.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(stepList.get(current_position).getThumbnailURL()))
            {
                Glide.with(this).load(stepList.get(current_position).getThumbnailURL()).into(defaultImageView);
            }

        }
        else
        {
            playerView.setVisibility(View.VISIBLE);
            defaultImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);;
        //storing current position value here

        outState.putInt(CURRPOS,current_position);
        outState.putLong(PLAYBACKPOS,player.getCurrentPosition());
        outState.putBoolean(PLAYWHENREADY,player.getPlayWhenReady());
    }
}
