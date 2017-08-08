package org.nanking.knightingal.kslideviewlib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class YImageSlider extends ViewGroup implements YImageView.EdgeListener {
    private static final String TAG = "YImageSlider";
    public YImageSlider(Context context) {
        super(context);
        init(context);
    }

    public final static int SPLITE_W = 48;

    public YImageSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YImageSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    protected void init(Context context) {
        contentView = new YImageView(context, this, 0, 0);
        contentView.setEdgeListener(this);
        hideLeft = new YImageView(context, this, -1, 1);
        hideRight = new YImageView(context, this, 1, 2);

        hideLeft.setEdgeListener(this);
        hideRight.setEdgeListener(this);

        backButton = new ImageView(context);
        nextButton = new ImageView(context);


        backButton.setImageResource(R.drawable.ic_keyboard_arrow_left_black_48dp);
        nextButton.setImageResource(R.drawable.ic_keyboard_arrow_right_black_48dp);
        backButton.setBackgroundColor(Color.parseColor("#80000000"));
        nextButton.setBackgroundColor(Color.parseColor("#80000000"));

        backButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contentView.doNextImgAnim();
            }
        });

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contentView.doBackImgAnim();
            }
        });

        addView(contentView);
        addView(hideLeft);
        addView(hideRight);

        addView(backButton);
        addView(nextButton);
    }

    private YImageView contentView, hideLeft, hideRight;

    public YImageView getHideLeft() {
        return hideLeft;
    }

    public YImageView getHideRight() {
        return hideRight;
    }

    private ImageView backButton;

    private ImageView nextButton;

    public YImageView getContentView() {
        return contentView;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        contentView.layout(0, 0, width, height);
        hideLeft.layout(0, 0, width, height);
        hideRight.layout(0, 0, width, height);

        backButton.layout(0, height / 2 - 24, 48, height / 2 + 24);
        nextButton.layout(width - 48, height / 2 - 24, width, height / 2 + 24);

//        line31.layout(width / 3, 0, width / 3 + 1, height);
//        line32.layout(width * 2 / 3, 0, width * 2 / 3 + 1, height);
    }

    @Override
    public void onXEdge(YImageView yImageView) {

    }

    @Override
    public void onYEdge(YImageView yImageView) {

    }

    public void setHideLeftSrc(int index) {
        if (imgChangeListener != null) {
            String src = imgChangeListener.getImgSrcByIndex(index - 1, this);
            YImageView yImageView = getHideLeft();
            if (src != null) {
                ImageLoader.getInstance().displayImage(src, yImageView, DIOptionsNoneScaled.getInstance().getOptions());
                yImageView.setDisplay(true);
            } else {
                yImageView.setDisplay(false);
            }
        }
    }

    public void setContentSrc(int index) {

        if (imgChangeListener != null) {
            String src = imgChangeListener.getImgSrcByIndex(index, this);
            YImageView yImageView = getContentView();
            if (src != null) {
                ImageLoader.getInstance().displayImage(src, yImageView, DIOptionsNoneScaled.getInstance().getOptions());
                yImageView.setDisplay(true);
            } else {
                yImageView.setDisplay(false);
            }
        }
    }

    public void setHideRightSrc(int index) {
        if (imgChangeListener != null) {
            String src = imgChangeListener.getImgSrcByIndex(index + 1, this);
            YImageView yImageView = getHideRight();
            if (src != null) {
                ImageLoader.getInstance().displayImage(src, yImageView, DIOptionsNoneScaled.getInstance().getOptions());
                yImageView.setDisplay(true);
            } else {
                yImageView.setDisplay(false);
            }
        }
    }

    public interface ImgChangeListener {
        String onGetBackImg(YImageSlider yImageSlider);

        String onGetNextImg(YImageSlider yImageSlider);

        String getImgSrcByIndex(int index, YImageSlider yImageSlider);
    }

    public void setImgChangeListener(ImgChangeListener imgChangeListener) {
        this.imgChangeListener = imgChangeListener;
    }

    ImgChangeListener imgChangeListener = null;

    @Override
    public void onGetBackImg(YImageView yImageView) {
        Log.d(TAG, "onGetBackImg");
        YImageView tmp = contentView;
        contentView = hideLeft;
        hideLeft = hideRight;
        hideRight = tmp;

        contentView.setLocationIndex(0);
        hideLeft.setLocationIndex(-1);
        hideRight.setLocationIndex(1);
        alingLeftOrRight = 0;
        String imgUrl = null;

        if (imgChangeListener != null) {
            imgUrl = imgChangeListener.onGetBackImg(this);
        }
        if (imgUrl != null) {
            if (nextButton.getVisibility() == View.INVISIBLE) {
                nextButton.setVisibility(View.VISIBLE);
            }
            ImageLoader.getInstance().displayImage(imgUrl, getHideLeft(), DIOptionsNoneScaled.getInstance().getOptions());
            getHideLeft().setDisplay(true);
        } else {
            backButton.setVisibility(View.INVISIBLE);
            ImageLoader.getInstance().displayImage(null, getHideLeft());
            getHideLeft().setDisplay(false);
        }

    }

    @Override
    public void onGetNextImg(YImageView yImageView) {
        Log.d(TAG, "onGetNextImg");
        YImageView tmp = contentView;
        contentView = hideRight;
        hideRight = hideLeft;
        hideLeft = tmp;

        contentView.setLocationIndex(0);
        hideLeft.setLocationIndex(-1);
        hideRight.setLocationIndex(1);
        alingLeftOrRight = 1;
        String imgUrl = null;
        if (imgChangeListener != null) {
            imgUrl = imgChangeListener.onGetNextImg(this);
        }
        if (imgUrl != null) {
            if (backButton.getVisibility() == View.INVISIBLE) {
                backButton.setVisibility(View.VISIBLE);
            }
            ImageLoader.getInstance().displayImage(imgUrl, hideRight, DIOptionsNoneScaled.getInstance().getOptions());
            hideRight.setDisplay(true);
        } else {
            nextButton.setVisibility(View.INVISIBLE);
            ImageLoader.getInstance().displayImage(null, hideRight);
            hideRight.setDisplay(false);
        }
    }

    public int getAlingLeftOrRight() {
        return alingLeftOrRight;
    }

    private int alingLeftOrRight = 0;


}
