package com.example.cjcucsie.okhttp;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import static android.Manifest.permission.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.text);
        final OkHttpClient okHttpClient = new OkHttpClient();
        final ExecutorService service = Executors.newSingleThreadExecutor();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service.submit(new Runnable() {
                    @Override
                    public void run() {

                        Request request = new Request.Builder()
                                .url("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, final IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView.setText(e.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                final String str = response.body().string();
                                //final List<JsonData> jsonData = new Gson().fromJson(str, new TypeToken<List<JsonData>>() {
                                // }.getType());
                                Gson gson = new GsonBuilder().registerTypeAdapter(Weather.class, new Convert()).create();

                                final Weather weather = gson.fromJson(str, Weather.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StringBuffer sb = new StringBuffer();

                                        sb.append("pressure:");
                                        sb.append(weather.pressure);
                                        sb.append("\n");
                                        sb.append("temp:");
                                        sb.append(weather.temp);
                                        sb.append("\n");

                                        textView.setText(sb.toString());
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });
    }
}
