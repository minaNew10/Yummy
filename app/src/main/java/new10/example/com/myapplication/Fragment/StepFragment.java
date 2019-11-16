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

import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;

public class StepFragment extends Fragment {
    private static final String TAG = "StepFragment";
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    ArrayList<Step> parcelableSteps;
    int position;
    Step s;
    String oldTitle;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private long playbackPosition ;
    private int currentWindow;
    @BindView(R.id.txtv_instruction) TextView tvInstruction;
    @BindView(R.id.video_view) PlayerView playerView;
    @BindView(R.id.imgv_prev) ImageView imgPrev;
    @BindView(R.id.imgv_next) ImageView imgNext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_detail,container,false);

        ButterKnife.bind(this,v);
        Bundle b = getArguments();
        if( b.getParcelableArrayList(getString(R.string.key_steps)) != null)
          parcelableSteps = b.getParcelableArrayList(getString(R.string.key_steps));

        position = b.getInt(getString(R.string.key_position));
        s = parcelableSteps.get(position-1);
        tvInstruction.setText(s.getDescription());
        if(position-1 == 0){
            imgPrev.setClickable(false);
            imgPrev.setAlpha(0.5f);
        }
        if(position-1 == parcelableSteps.size()-1){
            imgNext.setClickable(false);
            imgNext.setAlpha(0.5f);
        }

//        Uri videoUri = Uri.parse(s.getVideoURL());



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         oldTitle = ((RecipeActivity) getActivity()).getSupportActionBar().getTitle().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }

        ((RecipeActivity)getActivity()).setActionBarTitle(oldTitle + " " + "(" + parcelableSteps.get(position-1).getShortDescription() + ")");
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
        Fragment currFrag = getFragmentManager().findFragmentByTag(getString(R.string.tag_step_fragment));
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().addToBackStack(null);
        fragmentTransaction.detach(currFrag);
        currFrag.getArguments().putInt(getString(R.string.key_position),position+1);
        fragmentTransaction.attach(currFrag);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.imgv_prev)
    public void prevStep(){
        Fragment currFrag = getFragmentManager().findFragmentByTag(getString(R.string.tag_step_fragment));
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().addToBackStack(null);
        fragmentTransaction.detach(currFrag);
        currFrag.getArguments().putInt(getString(R.string.key_position),position-1);
        fragmentTransaction.attach(currFrag);
        fragmentTransaction.commit();
    }

}
