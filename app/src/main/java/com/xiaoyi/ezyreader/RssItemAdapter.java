package com.xiaoyi.ezyreader;
/**
 * Created by xiaoyi on 7/6/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.xiaoyi.ezyreader.model.RssFeedItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

class RssItemAdapter extends RecyclerView.Adapter<RssItemAdapter.RssItemHolder> {
    private static final String TAG="RssItemAdapter";
    private List<RssFeedItem> rssFeedItems;


    RssItemAdapter(List<RssFeedItem> feed) {
        this.rssFeedItems = feed;
    }

    @Override
    public RssItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.rss_feed_item, viewGroup, false);
        return new RssItemHolder(v);
    }

    @Override
    public int getItemCount() {
        return rssFeedItems.size();
    }

    public void clearData() {
        // clear the data
        rssFeedItems.clear();
    }

    public void setData(List<RssFeedItem> feed) {
        this.rssFeedItems = feed;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final RssItemHolder holder, int pos) {

        RssFeedItem item = rssFeedItems.get(pos);

        holder.titleTextField.setText(item.getTitle());
        Document doc = Jsoup.parse(item.getDescription());
        Element imageElement = doc.select("img").first();
        if ( imageElement != null ) {
            String absoluteUrl = imageElement.absUrl("src");
            if (absoluteUrl != null) {
                UrlImageViewHelper.setUrlDrawable(holder.imageView, absoluteUrl,
                        android.R.drawable.gallery_thumb);
                holder.descriptionTextField.setText(doc.body().text());
            }
        }
        holder.publicationDateTextField.setText(item.getPublicationDate());
    }

    public static class RssItemHolder extends RecyclerView.ViewHolder {
        private TextView titleTextField;
        private TextView descriptionTextField;

        private ImageView imageView;
        private TextView publicationDateTextField;
        RssItemHolder(View itemView) {
            super(itemView);
            titleTextField = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            descriptionTextField = (TextView) itemView.findViewById(R.id.description);
            publicationDateTextField = (TextView) itemView.findViewById(R.id.pubdate);
        }
    }
}
