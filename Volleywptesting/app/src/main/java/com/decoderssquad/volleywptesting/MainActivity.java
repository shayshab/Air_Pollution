package com.decoderssquad.volleywptesting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    public static String TAG="WPAPP";
    public List<Post> mPosts;
    public Button btnGetPost;
    public RecyclerView recyclerView;
    public PostAdapter postAdapter;
    public int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        recyclerView= (RecyclerView) findViewById(R.id.recyclerHome);
        postAdapter=new PostAdapter(mPosts,this,false,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getPost();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getPost() {
        mPosts = new ArrayList<Post>();
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, Config.base_url+"wp/v2/posts/?page="+page, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
// display response
                        Log.d(TAG, response.toString() + "Size: "+response.length());
                        for(int i=0;i<response.length();i++){
                            Post post=new Post();
                            try {
                                Log.d(TAG,"Object at " + i+ response.get(i));
                                JSONObject obj=response.getJSONObject(i);
                                post.setId(obj.getInt("id"));
                                post.setCreatedAt(obj.getString("date"));
                                post.setPostURL(obj.getString("link"));
                                JSONObject titleObj=obj.getJSONObject("title");
                                post.setTitle(titleObj.getString("rendered"));
                                mPosts.add(post);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setData(mPosts);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }
        ) ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(getRequest);
    }
    public void setData(List<Post> posts){
        recyclerView.setAdapter(postAdapter);
        postAdapter.setData(mPosts);
        postAdapter.notifyDataSetChanged();
    }
}