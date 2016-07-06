package com.xiaoyi.ezyreader.model;
/**
 * Created by xiaoyi on 7/6/16.
 */

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;
@Root(strict = false)
public class RssChannel implements Serializable {
    @ElementList(name = "item", required = true, inline = true)
    private List<RssFeedItem> itemList;

    public List<RssFeedItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<RssFeedItem> itemList) {
        this.itemList = itemList;
    }

    public RssChannel(List<RssFeedItem> mFeedItems) {
        this.itemList = mFeedItems;
    }

    public RssChannel() {
    }
}
