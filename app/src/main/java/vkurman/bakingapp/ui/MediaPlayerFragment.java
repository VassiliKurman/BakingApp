/*
* Copyright (C) 2018 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package vkurman.bakingapp.ui;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import vkurman.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaPlayerFragment extends Fragment implements Player.EventListener {

    /**
     * Key to save exoplayer playing position
     */
    private static final String EXOPLAYER_POSITION_KEY = "position_key";
    /**
     * Key to save exoplayer playing state
     */
    private static final String EXOPLAYER__STATE_KEY = "state_key";
    /**
     * Key to save videoUrl
     */
    private static final String VIDEO_URL_KEY = "videoUrl";
    /**
     * Key for window saving state
     */
//    private final String STATE_RESUME_WINDOW = "resumeWindow";
    /**
     * Key for full screen saving state
     */
//    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
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
    private PlayerView mPlayerView;
    /**
     * Playback state builder
     */
    private PlaybackStateCompat.Builder mStateBuilder;
    /**
     * Exoplayer video position on device state change
     */
    private long mVideoPosition;
    /**
     * Exoplayer state on device state change
     */
    private boolean mVideoState;
    /**
     * Dialog for full screen player view
     */
//    private Dialog mFullScreenDialog;
    /**
     * Full screen icon
     */
//    private ImageView mFullScreenIcon;
    /**
     *
     */
//    private boolean mFullScreen;
    /**
     *
     */
//    private int mResumeWindow;

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
            mVideoUrl = savedInstanceState.getString(VIDEO_URL_KEY);
            mVideoPosition = savedInstanceState.getLong(EXOPLAYER_POSITION_KEY);
            mVideoState = savedInstanceState.getBoolean(EXOPLAYER__STATE_KEY);
            // Full screen state
//            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
//            mFullScreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        // Inflate the MediaPlayerFragment fragment layout
        View rootView = inflater.inflate(R.layout.fragment_media_player, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);

        // Load the video camera as the background image
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
        currentState.putString(VIDEO_URL_KEY, mVideoUrl);
        currentState.putLong(EXOPLAYER_POSITION_KEY, mVideoPosition);
        currentState.putBoolean(EXOPLAYER__STATE_KEY, mVideoState);
        // Full screen state
//        currentState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
//        currentState.putBoolean(STATE_PLAYER_FULLSCREEN, mFullScreen);

//        super.onSaveInstanceState(currentState);
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
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);
            mExoPlayer.prepare(mediaSource);
            // Resume playing state and playing position
            if (mVideoPosition != 0) {
                mExoPlayer.seekTo(mVideoPosition);
                mExoPlayer.setPlayWhenReady(mVideoState);
            } else {
                // If position is 0 than video is never played and should start by default
                mExoPlayer.setPlayWhenReady(true);
            }
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
     * Release the player when the activity is paused.
     */
    @Override
    public void onPause() {
        super.onPause();

        if (mExoPlayer != null) {
            mVideoState = mExoPlayer.getPlayWhenReady();
            mVideoPosition = mExoPlayer.getCurrentPosition();

//            mResumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initialisePlayer(Uri.parse(mVideoUrl));
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {}
    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}
    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == Player.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(
                    PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(),
                    1f);

            // TODO show full screen on landscape mode

        } else if(playbackState == Player.STATE_READY) {
            mStateBuilder.setState(
                    PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(),
                    1f);
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {}
    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}
    @Override
    public void onPlayerError(ExoPlaybackException error) {}
    @Override
    public void onPositionDiscontinuity(int reason) {}
    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
    @Override
    public void onSeekProcessed() {}

//    private void initFullscreenDialog() {
//        mFullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
//            public void onBackPressed() {
//                if (mFullScreen)
//                    closeFullscreenDialog();
//                super.onBackPressed();
//            }
//        };
//    }
//
//    private void openFullscreenDialog() {
//        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
//        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.collapse_icon));
//        mFullScreen = true;
//        mFullScreenDialog.show();
//    }
//
//    private void closeFullscreenDialog() {
//        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
//        ((FrameLayout) getActivity().findViewById(R.id.playerview_container)).addView(mPlayerView);
//        mFullScreen = false;
//        mFullScreenDialog.dismiss();
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.expand_icon));
//    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}