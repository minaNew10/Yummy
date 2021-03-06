package new10.example.com.myapplication.Fragment.RecipeActivityFragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
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
import new10.example.com.myapplication.Activity.RecipeActivity;
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
    boolean isTablet;
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
        isTablet = getResources().getBoolean(R.bool.isTablet);
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
        setHasOptionsMenu(false);
        populateUi();
        return v;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


    }

    private void populateUi(){
        currStep = viewModel.getStep();
        //make ui components visible in case of being invisible due to rotation
        if(((RecipeActivity)getActivity()).getSupportActionBar() != null){
            ((RecipeActivity)getActivity()).getSupportActionBar().show();
        }
        tvInstruction.setVisibility(View.VISIBLE);
        txtvShortDesc.setVisibility(View.VISIBLE);
        imgNext.setVisibility(View.VISIBLE);
        imgPrev.setVisibility(View.VISIBLE);
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
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "Yummy"));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            if(((RecipeActivity)getActivity()).getSupportActionBar() != null){
                ((RecipeActivity)getActivity()).getSupportActionBar().hide();
            }
            tvInstruction.setVisibility(View.GONE);
            txtvShortDesc.setVisibility(View.GONE);
            imgNext.setVisibility(View.GONE);
            imgPrev.setVisibility(View.GONE);

            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(displayMetrics.widthPixels,displayMetrics.heightPixels);
            playerView.setLayoutParams(params);
        }
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
        int orientation = this.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }else {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
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
