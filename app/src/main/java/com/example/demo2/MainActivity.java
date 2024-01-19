package com.example.demo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.william.toolkit.ToolkitPanel;
import com.william.toolkit.bean.ToolkitConfig;
import com.william.toolkit.net.ApiRecordInterceptor;
import com.william.toolkit.net.DecryptCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToolkitPanel.showFloating(this,null);

        okHttpClient = new OkHttpClient.Builder().addInterceptor(new ApiRecordInterceptor(new DecryptCallBack() {
            @Override
            public String decrypt(String par) {
                return par;
            }
        })).build();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = new Request.Builder()
                        .url("https://w.wallhaven.cc/full/85/wallhaven-8586my.png")
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("test111",response.toString());
                    }
                });
            }
        });
    }
}