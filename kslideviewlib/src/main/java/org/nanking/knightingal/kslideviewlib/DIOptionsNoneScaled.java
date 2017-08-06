package org.nanking.knightingal.kslideviewlib;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


public class DIOptionsNoneScaled {
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(false)
            .cacheOnDisk(false)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.NONE)
            .build();

    private DIOptionsNoneScaled() {

    }

    public DisplayImageOptions getOptions() {
        return this.options;
    }
    private static DIOptionsNoneScaled self = null;
    public static DIOptionsNoneScaled getInstance() {
        if (self == null) {
            self = new DIOptionsNoneScaled();
        }
        return self;
    }
}
