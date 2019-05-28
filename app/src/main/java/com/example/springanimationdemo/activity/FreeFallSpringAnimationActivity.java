package com.example.springanimationdemo.activity;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.DAMPING_RATIO_LOW_BOUNCY;
import static android.support.animation.SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_HIGH;
import static android.support.animation.SpringForce.STIFFNESS_LOW;
import static android.support.animation.SpringForce.STIFFNESS_MEDIUM;

public class FreeFallSpringAnimationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private final float FINAL_POSITION = 650f;

    private SpringAnimation mAnimRockLeft, mAnimRockMiddle, mAnimRockRight;
    private TextView tvFreeFall;
    private ImageView ivRockLeft, ivRockMiddle, ivRockRight, ivBack;
    private boolean isRockLeftAnimComplete, isRockMiddleAnimComplete, isRockRightAnimComplete, isRockAllAnimComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fall);

        // instantiate views
        initializeViews();
        initializeHandlers();
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        // header
        TextView tvHeader = findViewById(R.id.tv_title);
        tvHeader.setText(R.string.free_fall_animation);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);

        ivRockLeft = findViewById(R.id.iv_rock_left);
        ivRockMiddle = findViewById(R.id.iv_rock_middle);
        ivRockRight = findViewById(R.id.iv_rock_right);
        tvFreeFall = findViewById(R.id.tv_free_fall);

        // create SpringForce objects
        // left rock animation
        mAnimRockLeft = new SpringAnimation(ivRockLeft, DynamicAnimation.TRANSLATION_Y);
        mAnimRockLeft.setStartVelocity(getVelocity());
        mAnimRockLeft.setSpring(getSpringForce(DAMPING_RATIO_HIGH_BOUNCY, STIFFNESS_LOW, FINAL_POSITION));
        // middle rock animation
        mAnimRockMiddle = new SpringAnimation(ivRockMiddle, DynamicAnimation.TRANSLATION_Y);
        mAnimRockMiddle.setStartVelocity(getVelocity());
        mAnimRockMiddle.setSpring(getSpringForce(DAMPING_RATIO_MEDIUM_BOUNCY, STIFFNESS_MEDIUM, FINAL_POSITION));
        // right rock animation
        mAnimRockRight = new SpringAnimation(ivRockRight, DynamicAnimation.TRANSLATION_Y);
        mAnimRockRight.setStartVelocity(getVelocity());
        mAnimRockRight.setSpring(getSpringForce(DAMPING_RATIO_LOW_BOUNCY, STIFFNESS_HIGH, FINAL_POSITION));
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        ivBack.setOnClickListener(this);
        ivRockLeft.setOnClickListener(this);
        ivRockMiddle.setOnClickListener(this);
        ivRockRight.setOnClickListener(this);
        tvFreeFall.setOnClickListener(this);
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
            case R.id.iv_rock_left:
                if (isRockLeftAnimComplete) {
                    mAnimRockLeft.setSpring(getSpringForce(DAMPING_RATIO_HIGH_BOUNCY, STIFFNESS_LOW, 0));
                } else {
                    mAnimRockLeft.setSpring(getSpringForce(DAMPING_RATIO_HIGH_BOUNCY, STIFFNESS_LOW, FINAL_POSITION));
                }
                isRockLeftAnimComplete = !isRockLeftAnimComplete;
                mAnimRockLeft.start();
                break;
            case R.id.iv_rock_middle:
                if (isRockMiddleAnimComplete) {
                    mAnimRockMiddle.setSpring(getSpringForce(DAMPING_RATIO_MEDIUM_BOUNCY, STIFFNESS_MEDIUM, 0));
                } else {
                    mAnimRockMiddle.setSpring(getSpringForce(DAMPING_RATIO_MEDIUM_BOUNCY, STIFFNESS_MEDIUM, FINAL_POSITION));
                }
                isRockMiddleAnimComplete = !isRockMiddleAnimComplete;
                mAnimRockMiddle.start();
                break;
            case R.id.iv_rock_right:
                if (isRockRightAnimComplete) {
                    mAnimRockRight.setSpring(getSpringForce(DAMPING_RATIO_LOW_BOUNCY, STIFFNESS_HIGH, 0));
                } else {
                    mAnimRockRight.setSpring(getSpringForce(DAMPING_RATIO_LOW_BOUNCY, STIFFNESS_HIGH, FINAL_POSITION));
                }
                isRockRightAnimComplete = !isRockRightAnimComplete;
                mAnimRockRight.start();
                break;
            case R.id.tv_free_fall:
                if (isRockAllAnimComplete) {
                    mAnimRockLeft.setSpring(getSpringForce(DAMPING_RATIO_HIGH_BOUNCY, STIFFNESS_LOW, 0));
                    mAnimRockMiddle.setSpring(getSpringForce(DAMPING_RATIO_MEDIUM_BOUNCY, STIFFNESS_MEDIUM, 0));
                    mAnimRockRight.setSpring(getSpringForce(DAMPING_RATIO_LOW_BOUNCY, STIFFNESS_HIGH, 0));
                } else {
                    mAnimRockLeft.setSpring(getSpringForce(DAMPING_RATIO_HIGH_BOUNCY, STIFFNESS_LOW, FINAL_POSITION));
                    mAnimRockMiddle.setSpring(getSpringForce(DAMPING_RATIO_MEDIUM_BOUNCY, STIFFNESS_MEDIUM, FINAL_POSITION));
                    mAnimRockRight.setSpring(getSpringForce(DAMPING_RATIO_LOW_BOUNCY, STIFFNESS_HIGH, FINAL_POSITION));
                }
                isRockAllAnimComplete = !isRockAllAnimComplete;
                mAnimRockLeft.start();
                mAnimRockMiddle.start();
                mAnimRockRight.start();
                break;
            default:
                break;
        }
    }

    /**
     * Method is used to convert dp to px for velocity calculations
     *
     * @return Converted dp to px for velocity
     */
    private float getVelocity() {
        // velocity in pixels per second from dp per second
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.15f,
                getResources().getDisplayMetrics());
    }

    /**
     * Method is used to setup and retrieve spring force object
     *
     * @param dampingRatio The damping ratio
     * @param stiffness    The stiffness
     * @param finalPos     The final position
     * @return SpringForce object
     */
    private SpringForce getSpringForce(float dampingRatio, float stiffness, float finalPos) {
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(dampingRatio).setStiffness(stiffness);
        springForce.setFinalPosition(finalPos);
        return springForce;
    }
}
