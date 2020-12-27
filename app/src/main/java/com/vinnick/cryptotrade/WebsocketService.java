package com.vinnick.cryptotrade;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.vinnick.cryptotrade.ActivitiesAndFragments.CurrencyFragment;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebsocketService extends Service {

    private boolean running = true;
    private OkHttpClient client;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Creating new thread for my service
        //ALWAYS write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();

        return Service.START_NOT_STICKY;
    }

    private void send(String msg) {
        Intent intent = new Intent();
        intent.setAction(CurrencyFragment.BROADCAST_FROM_SERVICE);
        intent.putExtra(CurrencyFragment.SERVICE_DATA, msg);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        running = false;
        super.onDestroy();
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"product_ids\": [\n" +
                    "        \"BTC-USD\"\n" +
                    "    ],\n" +
                    "    \"channels\": [\n" +
                    "        \"ticker\"\n" +
                    "    ]\n" +
                    "}");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            try {
                JSONObject json = new JSONObject(text);
                String price = parsePrice(json);
                send(price);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }
    }

    private void start() {
        Request request = new Request.Builder().url("wss://ws-feed.pro.coinbase.com").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    public String parsePrice(JSONObject json) {
        try {
            String type = json.getString("type");
            if (type.equals("ticker")) {
                 String price = json.getString("price");
                 return price;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // error, no price
        return "0.00";
    }
}
