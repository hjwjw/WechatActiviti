package com.hand.activiti.components;

/**
 * Created by HJW on 2017/7/29 0029.
 */
public class MusicMessage extends BaseMessage {
    private String Title;

    private String MediaIdc;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMediaIdc() {
        return MediaIdc;
    }

    public void setMediaIdc(String mediaIdc) {
        MediaIdc = mediaIdc;
    }
}
