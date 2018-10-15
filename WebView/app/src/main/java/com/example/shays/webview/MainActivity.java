package com.example.shays.webview;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        InterstitialAd mInterstitialAd;
        private InterstitialAd interstitial;



        private static final int MY_PERMISSION_REQUEST_CODE = 123;
    BoomMenuButton bmb;
    ImageButton btn;
    WebView webView;
    ArrayList<Integer>imageIdList;
    ArrayList<String>titleList;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    ProgressBar prg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prg=findViewById(R.id.progressb);
        bmb=(BoomMenuButton) findViewById(R.id.bmb);
        btn=findViewById(R.id.imgbtn);
        imageIdList=new ArrayList<>();
        titleList=new ArrayList<>();
        setInitialData();


        prg.setMax(100);

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.one:
                        Toast.makeText(MainActivity.this, "category1", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.two:
                        Toast.makeText(MainActivity.this, "category2", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.three:
                        Toast.makeText(MainActivity.this, "category3", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                       break;


                }
                return true;


            }
        });






        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            int position =i;
            if(position==0){
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(imageIdList.get(i))
                        .normalText(titleList.get(i))

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {


                                Intent intent = new Intent(MainActivity.this,Help.class);
                                startActivity(intent);
                                setTitle("Help");


                        }

                        });
                bmb.addBuilder(builder);
            }
            else if(position==1) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(imageIdList.get(i))
                        .normalText(titleList.get(i))

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {



                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("about");
                                builder

                                        .setMessage("made by Shayshab")
                                        .setCancelable(false)
                                        .setPositiveButton("ok",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }

                        });
                bmb.addBuilder(builder);
            }
            else if(position==2) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(imageIdList.get(i))
                        .normalText(titleList.get(i))

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {


                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                            }

                        });
                bmb.addBuilder(builder);
            }
            else if(position==3) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(imageIdList.get(i))
                        .normalText(titleList.get(i))

                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                finish();

                            }

                        });
                bmb.addBuilder(builder);
            }


        }





        webView = (WebView) findViewById(R.id.webView);
         WebSettings webSettings=webView.getSettings();
         webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                prg.setVisibility(View.VISIBLE );
                setTitle("welcome");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                prg.setVisibility(View.GONE);

            }
        });
        webView.loadUrl("http://shahedandroid.000webhostapp.com/");
        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Going back if canGoBack true
                if(webView.canGoBack()){
                    webView.goBack();
                }
            }
        });



        AdRequest adRequest = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
// Call displayInterstitial() function
                displayInterstitial();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
        {
            webView.goBack();
        }
        else
            super.onBackPressed();
    }



    private void setInitialData() {

        imageIdList.add(R.drawable.ic_help_black_24dp);
        imageIdList.add(R.drawable.ic_more_black_24dp);
        imageIdList.add(R.drawable.ic_share_black_24dp);
        imageIdList.add(R.drawable.ic_exit_to_app_black_24dp);
        titleList.add("Help");
        titleList.add("about");
        titleList.add("share");
        titleList.add("exit");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                webView = (WebView) findViewById(R.id.webView);
                WebSettings webSettings=webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient()
                {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        prg.setVisibility(View.VISIBLE );
                        setTitle("welcome");
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        prg.setVisibility(View.GONE);

                    }
                });
                webView.loadUrl("http://shahedandroid.000webhostapp.com/");
                webView.setDownloadListener(new DownloadListener() {
                    public void onDownloadStart(String url, String userAgent,
                                                String contentDisposition, String mimetype,
                                                long contentLength) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                finish();
                break;
            default:
                break;
        }




        if (t.onOptionsItemSelected(item))
           return true;
        return super.onOptionsItemSelected(item);



    }
  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.action_menu, menu);



      return true;


  }

    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }



}
