package com.example.purrfectpics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
        private String imageUrl;

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                WebView webView = findViewById(R.id.web_view);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSafeBrowsingEnabled(true);
                webView.loadUrl("file:///android_asset/index.html");

                findViewById(R.id.cat_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                imageUrl = "https://dog.ceo/api/breeds/image/random";
                                shareImage();
                        }
                });
        }

        @SuppressLint("QueryPermissionsNeeded")
        private void shareImage() {
                // Your code to fetch the image URL here
                // ...

                // Create the Intent to share the image
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUrl));
                String chooserTitle = "Share image using";

                // Create the Intent for SMS
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:"));
                smsIntent.putExtra("sms_body", "Here is your shared pet image: " + imageUrl);

                // Create the chooser Intent
                Intent chooserIntent = Intent.createChooser(shareIntent, chooserTitle);
                Intent[] intents = new Intent[]{smsIntent};
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);

                // Check if there is an Activity that can handle the chooser Intent
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooserIntent);
                }
        }

}
