package com.example.demo2;

import android.graphics.BitmapFactory;

import com.william.toolkit.ToolkitPanel;
import com.william.toolkit.bean.ToolkitConfig;

/**
 * Time:2024/1/18 14:42
 * Author:陈保鲁
 * Description:
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToolkitConfig config = new ToolkitConfig.Builder().setDebugMode(true)
                .setBitMap(BitmapFactory.decodeResource(getResources(), com.william.toolkit.R.drawable.toolkit_icon))
                .build();
        // init toolkit
        ToolkitPanel.init(this, config);
    }
}
