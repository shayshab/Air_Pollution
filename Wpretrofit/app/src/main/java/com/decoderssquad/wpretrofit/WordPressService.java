package com.decoderssquad.wpretrofit;

import retrofit2.http.GET;

public interface WordPressService {


    @GET("wp/v2/posts")
    Call<List<Post>> getAllPost();
}
