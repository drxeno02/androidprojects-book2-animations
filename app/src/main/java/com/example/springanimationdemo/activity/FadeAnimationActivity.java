package com.example.springanimationdemo.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

public class FadeAnimationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private LinearLayout llWrapper;
    private ImageView ivGhost, ivBack;

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
        tvHeader.setText(R.string.fade_animation);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);

        llWrapper = findViewById(R.id.ll_wrapper);
        llWrapper.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_spooky_02));
        ivGhost = findViewById(R.id.iv_generic_object);
        ivGhost.setImageResource(R.drawable.ghost);

    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {
        ivBack.setOnClickListener(this);
        llWrapper.setOnClickListener(this);
    }

    /**
     * Method is used to apply fade animation to view
     *
     * @param isFadeIn True to apply 'fade in' animation, otherwise false
     */
    private void fadeAnimation(final boolean isFadeIn) {
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, isFadeIn ? R.anim.fade_in_500 : R.anim.fade_out_200);
        fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // set visibility
                if (isFadeIn) {
                    ivGhost.setVisibility(View.VISIBLE);
                } else {
                    ivGhost.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // do nothing
            }
        });
        if (!fadeAnimation.hasStarted()) {
            ivGhost.startAnimation(fadeAnimation);
        }
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
            case R.id.ll_wrapper:
                // fade animation
                fadeAnimation(ivGhost.getVisibility() != View.VISIBLE);
                break;
            default:
                break;
        }
    }

}
