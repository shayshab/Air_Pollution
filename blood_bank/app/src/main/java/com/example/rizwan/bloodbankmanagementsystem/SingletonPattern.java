package com.example.rizwan.bloodbankmanagementsystem;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rizwan on 31/8/17.
 */

public class SingletonPattern {
        private static SingletonPattern mInstance;
        private RequestQueue mRequestQueue;
        private static Context mCtx;

        private SingletonPattern(Context context) {
            mCtx = context;
            mRequestQueue = getRequestQueue();
        }

        public static synchronized SingletonPattern getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new SingletonPattern(context);
            }
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
            return mRequestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }

}
