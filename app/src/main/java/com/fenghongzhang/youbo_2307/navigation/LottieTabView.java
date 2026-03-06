package com.fenghongzhang.youbo_2307.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.fenghongzhang.youbo_2307.R;
import com.fenghongzhang.youbo_2307.utils.AutoSizeUtil;


/**
 * @Author: xueshijie
 * @CreateDate: 2019-12-30 16:38
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class LottieTabView extends FrameLayout {

    private int mTextNormalColor;
    private int mTextSelectColor;
    private float mTextSize;
    private String mTabName;
    private Drawable mIconNormal;
    private String mAnimationPath;
    private LottieAnimationView mLottieView;
    private TextView mTabNameView;
    private boolean isSelected;
    private boolean isBulge;

//    private TabAnimView tabAnimView;

    private int startRun = 1;


    public LottieTabView(Context context) {
        super(context);
    }

    public LottieTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LottieTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Ui_LottieTabView);
        mTextNormalColor = ta.getColor(R.styleable.Ui_LottieTabView_text_normal_color, Color.BLACK);
        mTextSelectColor = ta.getColor(R.styleable.Ui_LottieTabView_text_selected_color, Color.BLUE);
        mTextSize = ta.getDimension(R.styleable.Ui_LottieTabView_text_size, AutoSizeUtil.dp2px(5));
        mIconNormal = ta.getDrawable(R.styleable.Ui_LottieTabView_icon_normal);
        mAnimationPath = ta.getString(R.styleable.Ui_LottieTabView_lottie_path);
        mTabName = ta.getString(R.styleable.Ui_LottieTabView_tab_name);
        isSelected = ta.getBoolean(R.styleable.Ui_LottieTabView_tab_selected, false);
        isBulge = ta.getBoolean(R.styleable.Ui_LottieTabView_top_bulge, false);

        ta.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View containView = LayoutInflater.from(context).inflate(R.layout.lottie_tab_view, null, false);
        mLottieView = containView.findViewById(R.id.animation_view);
        mLottieView.setImageAssetsFolder("images111/");
        mLottieView.setSpeed(1.5f);
        mLottieView.setRepeatCount(0);
        mTabNameView = containView.findViewById(R.id.tab_name);
//        tabAnimView = containView.findViewById(R.id.tab_anim_view);
        mTabNameView.setTextSize(TypedValue.COMPLEX_UNIT_PT, mTextSize);
        mTabNameView.setTextColor(mTextNormalColor);
        mTabNameView.setText(mTabName);
        this.addView(containView);
        if (isSelected) {
            selected();
        } else {
            unSelected();
        }
    }

    public void selected() {
        if (TextUtils.isEmpty(mAnimationPath)) {
            throw new NullPointerException("ainmation path must be not empty");
        } else {
            if (startRun == 1) {
                mLottieView.setAnimation(mAnimationPath);
                mLottieView.playAnimation();
                if (isBulge) {
//                    tabAnimView.startAnim();
                }
//              startImgAnim(mLottieView);
                mTabNameView.setTextColor(mTextSelectColor);

                startRun = 2;
            }
        }
    }

    public void unSelected() {
        startRun = 1;
        mTabNameView.setTextColor(mTextNormalColor);
        mLottieView.clearAnimation();
//        overAnim(mLottieView);
        if (isBulge) {
//            tabAnimView.resetAnim();
        }
        mLottieView.setImageDrawable(mIconNormal);
    }
}