package com.xiaoyi.ezyreader;
/**
 * Created by xiaoyi on 7/6/16.
 */

import com.xiaoyi.ezyreader.model.RssFeed;

import retrofit2.Call;
import retrofit2.http.GET;

interface RssRetrofitAdapter {
    @GET("http://feeds.feedburner.com//TechCrunch/social")
    Call<RssFeed> getItems();
}
