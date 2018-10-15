package com.example.shays.webviewdownloadmanag;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;

    private LinearLayout mRootLayout;
    private WebView mWebView;

    private static final int MY_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        // Get the widget reference from xml layout
        mRootLayout = findViewById(R.id.root_layout);
        mWebView = findViewById(R.id.web_view);

        // Check permission for write external storage
        checkPermission();

        // The target url to surf using web view
        String url = "http://team-poisoncyber.blogspot.com";

        // Load the url in web view
        mWebView.loadUrl(url);

        // Enable java script on web view
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDescription,
                                        String mimetype, long contentLength) {
                /*
                    DownloadManager.Request
                        This class contains all the information necessary to request a new download.
                        The URI is the only required parameter. Note that the default download
                        destination is a shared volume where the system might delete your file
                        if it needs to reclaim space for system use. If this is a problem,
                        use a location on external storage (see setDestinationUri(Uri).
                */
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                /*
                    void allowScanningByMediaScanner ()
                        If the file to be downloaded is to be scanned by MediaScanner, this method
                        should be called before enqueue(Request) is called.
                */
                request.allowScanningByMediaScanner();

                /*
                    DownloadManager.Request setNotificationVisibility (int visibility)
                        Control whether a system notification is posted by the download manager
                        while this download is running or when it is completed. If enabled, the
                        download manager posts notifications about downloads through the system
                        NotificationManager. By default, a notification is shown only
                        when the download is in progress.

                        It can take the following values: VISIBILITY_HIDDEN, VISIBILITY_VISIBLE,
                        VISIBILITY_VISIBLE_NOTIFY_COMPLETED.

                        If set to VISIBILITY_HIDDEN, this requires the permission
                        android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.

                    Parameters
                        visibility int : the visibility setting value
                    Returns
                        DownloadManager.Request this object
                */
                request.setNotificationVisibility(
                        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                /*
                    DownloadManager
                        The download manager is a system service that handles long-running HTTP
                        downloads. Clients may request that a URI be downloaded to a particular
                        destination file. The download manager will conduct the download in the
                        background, taking care of HTTP interactions and retrying downloads
                        after failures or across connectivity changes and system reboots.
                */

                /*
                    String guessFileName (String url, String contentDisposition, String mimeType)
                        Guesses canonical filename that a download would have, using the URL
                        and contentDisposition. File extension, if not defined,
                        is added based on the mimetype

                    Parameters
                        url String : Url to the content
                        contentDisposition String : Content-Disposition HTTP header or null
                        mimeType String : Mime-type of the content or null

                    Returns
                        String : suggested filename
                */
                String fileName = URLUtil.guessFileName(url,contentDescription,mimetype);

                /*
                    DownloadManager.Request setDestinationInExternalPublicDir
                    (String dirType, String subPath)

                        Set the local destination for the downloaded file to a path within
                        the public external storage directory (as returned by
                        getExternalStoragePublicDirectory(String)).

                        The downloaded file is not scanned by MediaScanner. But it can be made
                        scannable by calling allowScanningByMediaScanner().

                    Parameters
                        dirType String : the directory type to pass to
                                         getExternalStoragePublicDirectory(String)
                        subPath String : the path within the external directory, including
                                         the destination filename

                    Returns
                        DownloadManager.Request this object

                    Throws
                        IllegalStateException : If the external storage directory cannot be
                                                found or created.
                */
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);

                DownloadManager dManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                /*
                    long enqueue (DownloadManager.Request request)
                        Enqueue a new download. The download will start automatically once the
                        download manager is ready to execute it and connectivity is available.

                    Parameters
                        request DownloadManager.Request : the parameters specifying this download

                    Returns
                        long : an ID for the download, unique across the system. This ID is used
                               to make future calls related to this download.
                */
                dManager.enqueue(request);
            }
        });
    }

    protected void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    // show an alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("Write external storage permission is required.");
                    builder.setTitle("Please grant permission");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    mActivity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSION_REQUEST_CODE
                            );
                        }
                    });
                    builder.setNeutralButton("Cancel",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                            mActivity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_CODE
                    );
                }
            }else {
                // Permission already granted
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case MY_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission granted
                }else {
                    // Permission denied
                }
            }
        }
    }
}