package com.xiaoyi.ezyreader;
/**
 * Created by xiaoyi on 7/6/16.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xiaoyi.ezyreader.model.RssFeedItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rss_feed_list;
    private static String TAG = "MainActivity";
    RssItemAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rss_feed_list = (RecyclerView)findViewById(R.id.rss_feed_list);
        rss_feed_list.setHasFixedSize(true);
        rss_feed_list.addItemDecoration(new MarginDecoration(this));
        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        //GridLayoutManager   layoutMgr = new GridLayoutManager(this, 2);
        rss_feed_list.setLayoutManager(layoutMgr);

        /*
        // Code to test UI without actually invoking
        RssDataProvider provider = new RssDataProvider();
        rssFeedItemList = provider.getRssData();
        RssItemAdapter rssItemAdapter = new RssItemAdapter(rssFeedItemList);
        rss_feed_list.setAdapter(rssItemAdapter);
        */
        List<RssFeedItem> items =  new ArrayList<RssFeedItem>();
        adapter = new RssItemAdapter(items);
        rss_feed_list.setAdapter(adapter);
        startService();

    }

    private void startService() {
        Intent intent = new Intent(getApplicationContext(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        getApplicationContext().startService(intent);
    }

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            List<RssFeedItem> items = (List<RssFeedItem>) resultData.getSerializable(RssService.ITEMS);
            if (items != null) {
                adapter = new RssItemAdapter(items);
                //adapter.clearData();
                //adapter.setData(items);
                rss_feed_list.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "Error while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    public class MarginDecoration extends RecyclerView.ItemDecoration {
        private int margin;

        public MarginDecoration(Context context) {
            margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
        }

        @Override
        public void getItemOffsets(
                Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(margin, margin, margin, margin);
        }
    }
}
