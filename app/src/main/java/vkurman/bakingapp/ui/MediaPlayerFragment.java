package vkurman.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vkurman.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaPlayerFragment extends Fragment {

    private String mVideoUrl;

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

        // TODO
        // Get a reference to the ImageView in the fragment layout
        final TextView textView = rootView.findViewById(R.id.mp_media_player);
        textView.setText("Insert media player here instead text:" + mVideoUrl);

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