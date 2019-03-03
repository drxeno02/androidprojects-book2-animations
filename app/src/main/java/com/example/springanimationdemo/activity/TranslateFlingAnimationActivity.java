package com.example.springanimationdemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

@SuppressLint("ClickableViewAccessibility")
public class TranslateFlingAnimationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageView ivBball, ivBack;
    private int maxTranslationX, maxTranslationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fling);

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
        tvHeader.setText(R.string.fling_animation);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);

        final FrameLayout flWrapper = findViewById(R.id.fl_wrapper);
        flWrapper.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_bball_02));
        ivBball = findViewById(R.id.iv_bball);
        ivBball.setImageResource(R.drawable.bball);

        // register a callback to be invoked when the global layout state or the visibility of
        // views within the view tree changes
        flWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                maxTranslationX = flWrapper.getWidth() - ivBball.getWidth();
                maxTranslationY = flWrapper.getHeight() - ivBball.getHeight();
                // remove a previously installed global layout callback
                flWrapper.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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
        // onTouch listener
        final GestureDetector gestureDetector = new GestureDetector(this, mGestureListener);
        ivBball.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    /**
     * Interface definition for a callback to be invoked when a touch event is dispatched to this view
     */
    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        private static final int MIN_DISTANCE_MOVED = 50;
        private static final float MIN_TRANSLATION = 0;
        private static final float FRICTION = 1.5f;

        @Override
        public boolean onDown(MotionEvent arg0) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
            // downEvent : when user puts his finger down on the view
            // moveEvent : when user lifts his finger at the end of the movement
            float distanceInX = Math.abs(moveEvent.getRawX() - downEvent.getRawX());
            float distanceInY = Math.abs(moveEvent.getRawY() - downEvent.getRawY());

            if (distanceInX > MIN_DISTANCE_MOVED) {
                // fling right/left
                FlingAnimation flingX = new FlingAnimation(ivBball, DynamicAnimation.TRANSLATION_X);
                flingX.setStartVelocity(velocityX)
                        .setMinValue(MIN_TRANSLATION) // minimum translationX property
                        .setMaxValue(maxTranslationX)  // maximum translationX property
                        .setFriction(FRICTION)
                        .start();
            } else if (distanceInY > MIN_DISTANCE_MOVED) {
                // fling down/up
                FlingAnimation flingY = new FlingAnimation(ivBball, DynamicAnimation.TRANSLATION_Y);
                flingY.setStartVelocity(velocityY)
                        .setMinValue(MIN_TRANSLATION)  // minimum translationY property
                        .setMaxValue(maxTranslationY) // maximum translationY property
                        .setFriction(FRICTION)
                        .start();
            }
            return true;
        }
    };

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
