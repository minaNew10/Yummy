package new10.example.com.myapplication.Fragment;

import android.annotation.SuppressLint;

import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import new10.example.com.myapplication.Activity.RecipeActivity;

import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeDetailsViewModel;

public class StepFragment extends Fragment {
    private static final String TAG = "StepFragment";
    ArrayList<Step> steps;
    int position;
    Step s;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private long playbackPosition ;
    private int currentWindow;
    private RecipeDetailsViewModel viewModel;
    @BindView(R.id.txtv_instruction) TextView tvInstruction;
    @BindView(R.id.video_view) PlayerView playerView;
    @BindView(R.id.imgv_prev) ImageView imgPrev;
    @BindView(R.id.imgv_next) ImageView imgNext;
    @BindView(R.id.txtv_short_desc) TextView txtvShortDesc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_detail,container,false);
        ButterKnife.bind(this,v);
        Bundle b = getArguments();
        position = b.getInt(getString(R.string.key_position)) -1;
        if(position == 0){
            imgPrev.setClickable(false);
            imgPrev.setAlpha(0.5f);
        }
        populateUi(position);

        return v;
    }


    private void populateUi(int pos){

        viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        MutableLiveData<Recipe> recipe = viewModel.getRecipe();
        recipe.observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                steps = (ArrayList) recipe.getSteps();
                s = steps.get(pos);
                tvInstruction.setText(s.getDescription());
                txtvShortDesc.setText(s.getShortDescription());
                if(pos == steps.size()-1){
                    imgNext.setClickable(false);
                    imgNext.setAlpha(0.5f);
                }

                initializePlayer();
            }
        });
    }
    private void initializePlayer() {
        if (player == null){
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        Uri uri = Uri.parse(s.getVideoURL());
//        MediaSource mediaSource = buildMediaSource(uri);
//        player.prepare(mediaSource, true, false);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "Yummy"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        // Prepare the player with the source.
        player.prepare(videoSource);
    }



    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
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
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @OnClick(R.id.imgv_next)
    public void nextStep(){
        imgPrev.setClickable(true);
        imgPrev.setAlpha(1.0f);
        if(position + 2 == steps.size()-1){
            imgNext.setClickable(false);
            imgNext.setAlpha(0.5f);
        }
        populateUi(++position);
    }

    @OnClick(R.id.imgv_prev)
    public void prevStep(){
        imgNext.setClickable(true);
        imgNext.setAlpha(1.0f);
        if(position - 2 == 0){
            imgPrev.setClickable(false);
            imgPrev.setAlpha(0.5f);
        }
        populateUi(--position);
    }

}
