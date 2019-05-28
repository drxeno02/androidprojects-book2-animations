package com.example.springanimationdemo.activity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

@SuppressLint("ClickableViewAccessibility")
public class ScaleSpringAnimationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final float DEFAULT_SCALE = 1.0f;
    private float mScaleFactor = 1.0f; // default

    private SpringAnimation mScaleXAnimation, mScaleYAnimation;
    private ScaleGestureDetector mScaleGestureDetector;
    private ImageView ivMario, ivBack;
    private MediaPlayer mMediaPlayer;

    /**
     * Interface definition for a callback to be invoked when a touch event is dispatched to this view
     */
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                mScaleXAnimation.start();
                mScaleYAnimation.start();
            } else {
                // cancel animations so we can grab the view during previous animation
                mScaleXAnimation.cancel();
                mScaleYAnimation.cancel();
                // pass touch event to ScaleGestureDetector
                mScaleGestureDetector.onTouchEvent(event);
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        // instantiate views
        initializeViews();
        initializeHandlers();
        initializeListeners();
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        // header
        TextView tvHeader = findViewById(R.id.tv_title);
        tvHeader.setText(R.string.scale_spring_animation);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);

        LinearLayout llWrapper = findViewById(R.id.ll_wrapper);
        llWrapper.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_game_02));
        ivMario = findViewById(R.id.iv_generic_object);
        ivMario.setOnTouchListener(onTouchListener);
        ivMario.setImageResource(R.drawable.mario);

        // create SpringForce objects and apply animation settings
        mScaleXAnimation = new SpringAnimation(ivMario, SpringAnimation.SCALE_X);
        SpringForce springForceX = new SpringForce(DEFAULT_SCALE);
        springForceX.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceX.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        mScaleXAnimation.setSpring(springForceX);

        mScaleYAnimation = new SpringAnimation(ivMario, SpringAnimation.SCALE_Y);
        SpringForce springForceY = new SpringForce(DEFAULT_SCALE);
        springForceY.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceY.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        mScaleYAnimation.setSpring(springForceY);

        // instantiate media player
        mMediaPlayer = MediaPlayer.create(this, R.raw.smb_powerup);
        mMediaPlayer.setVolume((float) .5, (float) .5);
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        ivBack.setOnClickListener(this);
    }

    /**
     * Initialize custom listeners
     */
    private void initializeListeners() {
        // scaleGestureDetector
        mScaleGestureDetector = new ScaleGestureDetector(this,
                new ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        mScaleFactor *= detector.getScaleFactor();
                        ivMario.setScaleX(ivMario.getScaleX() * mScaleFactor);
                        ivMario.setScaleY(ivMario.getScaleY() * mScaleFactor);

                        if (mScaleFactor > DEFAULT_SCALE) {
                            // play mario sound effect
                            playSound();
                        }
                        return true;
                    }
                }
        );
    }

    /**
     * Method is used for playing sound bytes
     */
    public void playSound() {
        // play sound byte again only if it is not already playing
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        // releases resources associated with this MediaPlayer object
        mMediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (!Utils.isViewClickable()) {
            return;
        }

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
