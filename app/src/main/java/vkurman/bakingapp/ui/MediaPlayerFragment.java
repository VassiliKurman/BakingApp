package vkurman.bakingapp.ui;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import vkurman.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaPlayerFragment extends Fragment implements ExoPlayer.EventListener {
    /**
     * URL for the video.
     */
    private String mVideoUrl;
    /**
     * Exoplayer to play videos
     */
    private SimpleExoPlayer mExoPlayer;
    /**
     * UI element for ExoPlayer
     */
    private SimpleExoPlayerView mPlayerView;

    /**
     * Empty constructor
     */
    public MediaPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load the saved state (the string videoUrl) if there is one
        if(savedInstanceState != null) {
            mVideoUrl = savedInstanceState.getString("videoUrl");
        }
        // Inflate the MediaPlayerFragment fragment layout
        View rootView = inflater.inflate(R.layout.fragment_media_player, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);

        // Load the question mark as the background image until the user answers the question.
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.videocam));

        // Initialise the player.
        initialisePlayer(Uri.parse(mVideoUrl));

        return rootView;
    }

    /**
     * Set videoUrl for player to retrieve
     *
     * @param videoUrl - link to video
     */
    public void setVideoUrl(String videoUrl) {
        this.mVideoUrl = videoUrl;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putString("videoUrl", mVideoUrl);
    }

    /**
     * Initialising ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initialisePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), getResources().getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            Toast.makeText(getContext(), "Playing", Toast.LENGTH_SHORT).show();
        } else if(playbackState == ExoPlayer.STATE_READY) {
            Toast.makeText(getContext(), "Paused", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {}

    @Override
    public void onPositionDiscontinuity() {}

//    /**
//     * Media Session Callbacks, where all external clients control the player.
//     */
//    private class MySessionCallback extends MediaSessionCompat.Callback {
//        @Override
//        public void onPlay() {
//            mExoPlayer.setPlayWhenReady(true);
//        }
//
//        @Override
//        public void onPause() {
//            mExoPlayer.setPlayWhenReady(false);
//        }
//
//        @Override
//        public void onSkipToPrevious() {
//            mExoPlayer.seekTo(0);
//        }
//    }
}