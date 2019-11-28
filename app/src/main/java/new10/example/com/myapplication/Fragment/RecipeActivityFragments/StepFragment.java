package new10.example.com.myapplication.Fragment.RecipeActivityFragments;

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
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeActivityViewModels.StepFragmentViewModel;

public class StepFragment extends Fragment {
    private static final String TAG = "StepFragment";
    List<Step> stepList;
    Step currStep;
    int position = -1;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private long playbackPosition ;
    private int currentWindow;
    private StepFragmentViewModel viewModel;
    Bundle b;
    @BindView(R.id.txtv_instruction) public TextView tvInstruction;
    @BindView(R.id.video_view) public PlayerView playerView;
    @BindView(R.id.imgv_prev) public ImageView imgPrev;
    @BindView(R.id.imgv_next) public ImageView imgNext;
    @BindView(R.id.txtv_short_desc) public TextView txtvShortDesc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_detail,container,false);
        ButterKnife.bind(this,v);
        b = getArguments();
        stepList = b.getParcelableArrayList(getString(R.string.key_steps));
        viewModel = ViewModelProviders.of(getActivity()).get(StepFragmentViewModel.class);
        if(savedInstanceState == null) {
            position = b.getInt(getString(R.string.key_position)) - 1;
            viewModel.setPos(position);
            viewModel.setStepList(stepList);
        }
        position = viewModel.getPos();

        populateUi();
        return v;
    }




    private void populateUi(){
        currStep = viewModel.getStep();
        imgNext.setClickable(true);
        imgNext.setAlpha(1.0f);
        imgPrev.setClickable(true);
        imgPrev.setAlpha(1.0f);
        if(position == 0){
            imgPrev.setClickable(false);
            imgPrev.setAlpha(0.5f);
        }
        if(position == stepList.size()-1){
            imgNext.setClickable(false);
            imgNext.setAlpha(0.5f);
        }

        tvInstruction.setText(currStep.getDescription());
        txtvShortDesc.setText(currStep.getShortDescription());

        initializePlayer();
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
        Uri uri = Uri.parse(currStep.getVideoURL());
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

        viewModel.setPos(++position);
        populateUi();
    }

    @OnClick(R.id.imgv_prev)
    public void prevStep(){

        viewModel.setPos(--position);
        populateUi();
    }

}
