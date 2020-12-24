package com.vinnick.cryptotrade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);


        new TestAsync(this).execute();
        /*
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://ws-feed.pro.coinbase.com"));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    //updateText(message);
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

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }*/
    }

    public void updateText(String message) {
        text.setText(message);
    }


}