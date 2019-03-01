package com.example.springanimationdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;
    private LinearLayout llFade, llFreeFall, llScaleSpring, llChainedSpring, llTranslateSpring, llFling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate views
        initializeViews();
        initializeHandlers();
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {
        // instantiate context and activity instance
        mContext = MainActivity.this;
        mActivity = MainActivity.this;

        llFade = findViewById(R.id.category_fade);
        llFreeFall = findViewById(R.id.category_free_fall);
        llScaleSpring = findViewById(R.id.category_scale_spring);
        llChainedSpring = findViewById(R.id.category_chained_spring);
        llTranslateSpring = findViewById(R.id.category_translate_spring);
        llFling = findViewById(R.id.category_fling);

        TextView tvFade = llFade.findViewById(R.id.tv_title);
        tvFade.setText(getResources().getString(R.string.fade_animation));
        TextView tvFreeFall = llFreeFall.findViewById(R.id.tv_title);
        tvFreeFall.setText(getResources().getString(R.string.free_fall_animation));
        TextView tvScaleSpring = llScaleSpring.findViewById(R.id.tv_title);
        tvScaleSpring.setText(getResources().getString(R.string.scale_spring_animation));
        TextView tvChainedSpring = llChainedSpring.findViewById(R.id.tv_title);
        tvChainedSpring.setText(getResources().getString(R.string.chained_spring_animation));
        TextView tvTranslateSpring = llTranslateSpring.findViewById(R.id.tv_title);
        tvTranslateSpring.setText(getResources().getString(R.string.translate_spring_animation));
        TextView tvFling = llFling.findViewById(R.id.tv_title);
        tvFling.setText(getResources().getString(R.string.fling_animation));

        ImageView ivFade = llFade.findViewById(R.id.iv_category);
        ivFade.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bball_01));
        ImageView ivFreeFall = llFreeFall.findViewById(R.id.iv_category);
        ivFreeFall.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_nature_01));
        ImageView ivScaleSpring = llScaleSpring.findViewById(R.id.iv_category);
        ivScaleSpring.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_game_01));
        ImageView ivChainedSpring = llChainedSpring.findViewById(R.id.iv_category);
        ivChainedSpring.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_game_03));
        ImageView ivTranslateSpring  = llTranslateSpring .findViewById(R.id.iv_category);
        ivTranslateSpring .setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_boxring_01));
        ImageView ivFling = llFling.findViewById(R.id.iv_category);
        ivFling.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bball_01));
    }

    /**
     * Method is used to initialize click listeners
     */
    private void initializeHandlers() {


    }

    @Override
    public void onClick(View v) {
        if (!Utils.isViewClickable()) {
            return;
        }

        switch (v.getId()) {
            case R.id.category_fade:
                break;
            case R.id.category_free_fall:
                break;
            case R.id.category_scale_spring:
                break;
            case R.id.category_chained_spring:
                break;
            case R.id.category_translate_spring:
                break;
            case R.id.category_fling:
                break;
            default:
                break;
        }

    }
}
