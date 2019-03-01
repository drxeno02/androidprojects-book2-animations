package com.example.springanimationdemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

@SuppressLint("ClickableViewAccessibility")
public class ChainedSpringAnimationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private SpringAnimation mAnimFirstGhostX, mAnimFirstGhostY, mAnimSecondGhostX, mAnimSecondGhostY;
    private ImageView ivPacman, ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chained_spring);

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
        tvHeader.setText(R.string.chained_spring_animation);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);

        ivPacman = findViewById(R.id.iv_pacman);
        ImageView ivGhost01 = findViewById(R.id.iv_ghost_01);
        ImageView ivGhost02 = findViewById(R.id.iv_ghost_02);

        // create SpringForce objects and apply to translation animations
        mAnimFirstGhostX = new SpringAnimation(ivGhost01, DynamicAnimation.TRANSLATION_X);
        mAnimFirstGhostY = new SpringAnimation(ivGhost01, DynamicAnimation.TRANSLATION_Y);
        mAnimSecondGhostX = new SpringAnimation(ivGhost02, DynamicAnimation.TRANSLATION_X);
        mAnimSecondGhostY = new SpringAnimation(ivGhost02, DynamicAnimation.TRANSLATION_Y);
    }

    /**
     * Initialize custom listeners
     */
    private void initializeListeners() {
        // Update listener
        mAnimFirstGhostX.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                mAnimSecondGhostX.animateToFinalPosition(value);
            }
        });
        mAnimFirstGhostY.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                mAnimSecondGhostY.animateToFinalPosition(value);
            }
        });

        // OnTouch listener
        ivPacman.setOnTouchListener(new View.OnTouchListener() {
            float lastX, lastY;

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    // the MotionEvent object containing full information about the event.
                    float deltaX = motionEvent.getRawX() - lastX;
                    float deltaY = motionEvent.getRawY() - lastY;

                    // set translation position (x,y)
                    ivPacman.setTranslationX(deltaX + ivPacman.getTranslationX());
                    ivPacman.setTranslationY(deltaY + ivPacman.getTranslationY());
                    // animate to final position
                    mAnimFirstGhostX.animateToFinalPosition(ivPacman.getTranslationX());
                    mAnimFirstGhostY.animateToFinalPosition(ivPacman.getTranslationY());
                }

                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
                return true;
            }
        });
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        ivBack.setOnClickListener(this);
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
