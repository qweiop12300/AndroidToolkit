package com.example.demo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.william.toolkit.ToolkitPanel;
import com.william.toolkit.bean.ToolkitConfig;
import com.william.toolkit.net.ApiRecordInterceptor;
import com.william.toolkit.net.DecryptCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

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
            public String responseBodyDecrypt(String par) {
                try {
                    JSONObject responseJson = new JSONObject(par);
                    Object data = responseJson.get("data");
                    if (data instanceof String){
                        if (isBase64((String) data)){
                            Log.d("test111","1111");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return par;
            }

            @Override
            public String requestBodyDecrypt(String body) {
                Log.d("test111",body);
                return body;
            }
        })).build();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = new Request.Builder()
                        .url("https://img-home.csdnimg.cn/data_json/toolbar/toolbar1105.json")
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

    public boolean isBase64(String par){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(par);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}