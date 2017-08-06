package org.nanking.knightingal.kslideview;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.nanking.knightingal.kslideviewlib.YImageSlider;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements YImageSlider.ImgChangeListener{

    @Bind(R.id.image)
    public YImageSlider mImageSlider;

    private int index = 0;

//    private final static int pics[] = {R.mipmap.f14_1, R.mipmap.f14_2, R.mipmap.f14_3, R.mipmap.f14_4, R.mipmap.f14_1, R.mipmap.f14_2, R.mipmap.f14_3, R.mipmap.f14_4};
    private final static int pics[] = {
        R.mipmap.j20_1, R.mipmap.j20_2, R.mipmap.j20_3, R.mipmap.j20_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config);

        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.airForceLightGray));
        myToolbar.setTitleTextColor(getResources().getColor(R.color.airForceDarkGray));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.airForceDarkGray));

        //底部导航栏
        window.setNavigationBarColor(getResources().getColor(R.color.airForceDarkGray));

        ButterKnife.bind(this);
        mImageSlider.setImgChangeListener(this);
        mImageSlider.setHideLeftSrc(index);
        mImageSlider.setContentSrc(index);
        mImageSlider.setHideRightSrc(index);
    }

    @Override
    public String onGetBackImg(YImageSlider yImageSlider) {
        index--;
        return getImgSrcByIndex(index - 1, yImageSlider);
    }

    @Override
    public String onGetNextImg(YImageSlider yImageSlider) {
        index++;
        return getImgSrcByIndex(index + 1, yImageSlider);
    }

    @Override
    public String getImgSrcByIndex(int index, YImageSlider yImageSlider) {
        String img = getImgByIndex(index);
        if (img != null) {
            return ImageDownloader.Scheme.DRAWABLE.wrap(img);
        } else {
            return null;
        }
    }

    private String getImgByIndex(int index) {
        if (index >= 0 && index < pics.length) {
            return pics[index] + "";
        } else {
            return null;
        }
    }
}
