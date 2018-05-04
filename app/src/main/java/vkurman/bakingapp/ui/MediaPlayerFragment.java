package vkurman.bakingapp.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import vkurman.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaPlayerFragment extends Fragment {

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

        mPlayerView = container.findViewById(R.id.playerView);

        // Load the question mark as the background image until the user answers the question.
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.videocam));

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
}