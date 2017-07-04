package com.example.administrator.payalibaba;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String orderInfo = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void payTest(View view) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("http://www.emoreedu.com/PayService/servlet/PayTest");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();
                    InputStream is = con.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String content = "";
                    while ((content = br.readLine()) != null) {
                        orderInfo += content;
                    }
                    Log.d("wang","order is " + orderInfo);
                    payAli();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//
//            }
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
    }
    public void payAli(){
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        PayTask alipay = new PayTask(MainActivity.this);
        Map<String, String> result = alipay.payV2(orderInfo, true);
        Log.i("wang", result.toString());
        Log.i("wang", "order " + orderInfo);
    }
}
