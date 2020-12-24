package com.vinnick.cryptotrade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity {

    // debugging
    private static final String TAG = "MainActivity";

    // views
    private TextView text;

    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);

        createWebSocketClient();
    }


    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("wss://ws-feed.pro.coinbase.com");
        }
        catch (URISyntaxException e) {
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i(TAG, "Session is starting");
                webSocketClient.send("{\n" +
                        "    \"type\": \"subscribe\",\n" +
                        "    \"product_ids\": [\n" +
                        "        \"BTC-USD\"\n" +
                        "    ],\n" +
                        "    \"channels\": [\n" +
                        "        \"level2\"\n" +
                        "    ]\n" +
                        "}");
            }
            @Override
            public void onTextReceived(String s) {
                Log.i(TAG, "Message received");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            text.setText(message);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                Log.d(TAG, "onException: " + e.getMessage());
            }
            @Override
            public void onCloseReceived() {
                Log.d(TAG, "onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }
}