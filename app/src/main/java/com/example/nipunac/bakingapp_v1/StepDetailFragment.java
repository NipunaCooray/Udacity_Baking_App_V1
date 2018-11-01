package com.example.nipunac.bakingapp_v1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nipunac.bakingapp_v1.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StepDetailFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Step> mSteps;

    PlayerView playerView;

    SimpleExoPlayer mPlayer;

    TextView mStepDescription;

    boolean playWhenReady = true;

    int currentWindow = 0;

    long playbackPosition = 0;

    TextView emptyMessage;

    Button previousStep;

    Button nextStep;

    int currentIndex;

    boolean isTablet = false;

    private static final String TAG = "StepDetailFragment";

    FrameLayout descriptionFrame;

    LinearLayout buttonFrame;

    public StepDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail,container,false);

        if(savedInstanceState == null){

            Bundle extra = getArguments();
            mSteps = extra.getParcelableArrayList("steps");
            currentIndex = extra.getInt("clicked_position");

            isTablet = RecipeDetailsActivity.isTablet;



        }else{
            mSteps = savedInstanceState.getParcelableArrayList("steps");
            currentIndex = savedInstanceState.getInt("index");
            isTablet = savedInstanceState.getBoolean("tablet");
        }

        playerView = rootView.findViewById(R.id.playerView);
        emptyMessage = rootView.findViewById(R.id.empty_message);

        previousStep = rootView.findViewById(R.id.previous_btn);
        nextStep = rootView.findViewById(R.id.next_btn);

        descriptionFrame = rootView.findViewById(R.id.description_view);
        buttonFrame = rootView.findViewById(R.id.button_frame);
        descriptionFrame.setVisibility(View.VISIBLE);
        mStepDescription = rootView.findViewById(R.id.step_description);

        previousStep.setOnClickListener(this);
        nextStep.setOnClickListener(this);

        if(! isTablet && isLandscape() ){
            descriptionFrame.setVisibility(View.GONE);
        }

        if(isTablet){
            buttonFrame.setVisibility(View.GONE);
        }


        return rootView;
    }



    public void initializePlayer() {
        if (mPlayer == null ){

            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(mPlayer);
            mPlayer.setPlayWhenReady(playWhenReady);
            //mPlayer.seekTo(currentWindow, playbackPosition);

        }

        if(mSteps.get(currentIndex).getVideoURL().isEmpty()  && mSteps.get(currentIndex).getThumbnailURL().isEmpty() ){
            playerView.setVisibility(View.GONE);
            emptyMessage.setVisibility(View.VISIBLE);
        }else{

            playerView.setVisibility(View.VISIBLE);
            emptyMessage.setVisibility(View.GONE);

            String videoUrl;

            if(! mSteps.get(currentIndex).getVideoURL().isEmpty()){
                videoUrl = mSteps.get(currentIndex).getVideoURL();
            }else{
                videoUrl = mSteps.get(currentIndex).getThumbnailURL();
            }

            MediaSource mediaSource = buildMediaSource(Uri.parse(videoUrl));
            mPlayer.prepare(mediaSource, true, false);
        }

        mStepDescription.setText(mSteps.get(currentIndex).getDescription());

    }



    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("ua")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            initializePlayer();
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void releasePlayer() {
        if (mPlayer != null) {
//            playbackPosition = mPlayer.getCurrentPosition();
//            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private boolean isLandscape()
    {
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.previous_btn) {
            if(currentIndex ==0)
                return;
            currentIndex--;
            replaceFragment();

        } else if (id == R.id.next_btn) {
            if (currentIndex == mSteps.size() - 1)
                return;
            currentIndex++;
            replaceFragment();
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",currentIndex);
        outState.putParcelableArrayList("steps",mSteps);
        outState.putBoolean("tablet",isTablet);
    }

    void replaceFragment()
    {

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("clicked_position", currentIndex);
        bundle.putParcelableArrayList("steps",mSteps);
        stepDetailFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.step_details_fragment_container, stepDetailFragment).commit();
    }
}
