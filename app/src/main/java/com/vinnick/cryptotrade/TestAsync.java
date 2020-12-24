package com.vinnick.cryptotrade;

import android.os.AsyncTask;

import java.net.URI;
import java.net.URISyntaxException;

public class TestAsync extends AsyncTask<String, Void, String> {

    private MainActivity mainActivity;

    TestAsync(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://ws-feed.pro.coinbase.com"));

            /*
            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    mainActivity.updateText(message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"product_ids\": [\n" +
                    "        \"BTC-USD\"\n" +
                    "    ],\n" +
                    "    \"channels\": [\n" +
                    "        \"level2\"\n" +
                    "    ]\n" +
                    "}");

             */

        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }

        return null;
    }
}
