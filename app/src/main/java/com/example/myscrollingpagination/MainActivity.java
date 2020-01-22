package com.example.myscrollingpagination;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static String url = "https://www.gookkis.com/hello.html";
    private static String TAG = "MainActivity";
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        button = findViewById(R.id.btn_proses);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat instance baru
                OkHttpClient client = new OkHttpClient();

                //membuat catch agar hemat bandwith
                client.cache();

                //membuat request
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                //membuat async untuk mengambil response
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(getApplicationContext(), "HTTP request Failur..", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //jika code http ddresponse bukan 200
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code "+ response);
                        }
                        //merubah response body menjadi string
                        final String responseData = response.body().string();

                        //menampilkan string dalam textView
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(responseData);
                            }
                        });
                    }
                });

            }
        });
    }
}
