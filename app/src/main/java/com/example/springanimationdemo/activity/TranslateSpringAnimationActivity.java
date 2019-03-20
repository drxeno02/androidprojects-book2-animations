package com.example.springanimationdemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

@SuppressLint("ClickableViewAccessibility")
public class TranslateSpringAnimationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TRANSLATION_X = "translationX";
    private static final String TRANSLATION_Y = "translationY";

    private SpringAnimation mSpringTranslationXAnimation, mSpringTranslationYAnimation;
    private ImageView ivPunchingBag, ivBack;
    private float mXDiffInTouchPoint, mYDiffInTouchPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

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
        tvHeader.setText(R.string.translate_spring_animation);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);

        LinearLayout llWrapper = findViewById(R.id.ll_wrapper);
        llWrapper.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_boxring_02));
        ivPunchingBag = findViewById(R.id.iv_generic_object);
        ivPunchingBag.setOnTouchListener(onTouchListener);
        ivPunchingBag.setImageResource(R.drawable.punching_bag);

        // final position provided is 0, because we want to ensure translationX/translationY of view
        // from wherever the view moved due to touch gesture to come back to its initial
        // translationX/translationY position which is 0.
        mSpringTranslationXAnimation = new SpringAnimation(ivPunchingBag,
                new FloatPropertyCompat<View>(TRANSLATION_X) {

                    @Override
                    public float getValue(View view) {
                        return view.getTranslationX();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationX(value);
                    }
                });

        mSpringTranslationYAnimation = new SpringAnimation(ivPunchingBag,
                new FloatPropertyCompat<View>(TRANSLATION_Y) {

                    @Override
                    public float getValue(View view) {
                        return view.getTranslationY();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationY(value);
                    }
                });

        // create SpringForce objects and apply animation settings
        SpringForce springForceX = new SpringForce(0f);
        springForceX.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceX.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        mSpringTranslationXAnimation.setSpring(springForceX);

        SpringForce springForceY = new SpringForce(0f);
        springForceY.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceY.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        mSpringTranslationYAnimation.setSpring(springForceY);
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        ivBack.setOnClickListener(this);
    }

    /**
     * Interface definition for a callback to be invoked when a touch event is dispatched to this view
     */
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // calculate the difference between touch point(event.getRawX()) on view & view's top left corner(v.getX())
                    mXDiffInTouchPoint = event.getRawX() - v.getX();
                    mYDiffInTouchPoint = event.getRawY() - v.getY();
                    // cancel spring animations
                    mSpringTranslationXAnimation.cancel();
                    mSpringTranslationYAnimation.cancel();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newTopLeftX = event.getRawX() - mXDiffInTouchPoint;
                    float newTopLeftY = event.getRawY() - mYDiffInTouchPoint;
                    ivPunchingBag.setX(newTopLeftX);
                    ivPunchingBag.setY(newTopLeftY);
                    break;
                case MotionEvent.ACTION_UP:
                    mSpringTranslationXAnimation.start();
                    mSpringTranslationYAnimation.start();
                    break;
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
