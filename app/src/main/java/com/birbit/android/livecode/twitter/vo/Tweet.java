package com.birbit.android.livecode.twitter.vo;



// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.birbit.android.livecode.twitter.App;
import com.birbit.android.livecode.twitter.Config;
import com.birbit.android.livecode.twitter.model.UserModel;
import com.birbit.android.livecode.twitter.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
// KEEP INCLUDES END
/**
 * Entity mapped to table TWEET.
 */
public class Tweet extends TweetBase  implements com.birbit.android.livecode.twitter.vo.CachesUIData {
    // KEEP FIELDS - put your custom fields here
    public static final String LOCAL_ID_PREFIX = "local_";
    @SerializedName("created_at")
    private String createdAtString;
    private Spanned uiSpanned;
    private User user;
    // KEEP FIELDS END
    public Tweet() {
    }

    public Tweet(Long localId) {
        super(localId);
    }

    public Tweet(Long localId, String id, Boolean retweeted, String text, java.util.Date createdAt, String userId, Boolean favorited) {
        super(localId, id, retweeted, text, createdAt, userId, favorited);
    }

    // KEEP METHODS - put your custom methods here
    public boolean isRetweeted() {
        return Boolean.TRUE.equals(retweeted);
    }

    public boolean didFavorite() {
        return Boolean.TRUE.equals(favorited);
    }

    public User getUser() {
        if(user == null && userId != null) {
            user = App.get(UserModel.class).get(userId);
        }
        return user;
    }

    public Spanned getUiSpanned() {
        if(uiSpanned == null) {
            getUser();
            StringBuilder builder = new StringBuilder();
            if(user != null && user.getScreenName() != null) {
                builder.append("<b>").append(Html.fromHtml(Html.escapeHtml(user.getScreenName()))).append("</b>:");
            } else {
                builder.append("<b>unknown</b>");
            }
            builder.append(text);
            uiSpanned = Html.fromHtml(builder.toString());

        }
        return uiSpanned;
    }

    @Override
    public void onBeforeSave() {
        if(createdAt == null && createdAtString != null) {
            createdAt = DateUtil.parseDate(createdAtString);
        }
        if(userId == null && user != null) {
            userId = user.getId();
        }
        super.onBeforeSave();
    }

    @Override
    public void cacheUIData(Context context) {
        getUser();
        getUiSpanned();
    }

    public static String createLocalId(String identifier) {
        return LOCAL_ID_PREFIX + identifier;
    }

    public static boolean isLocalId(String id) {
        return id.startsWith(LOCAL_ID_PREFIX);
    }

    public boolean isLocal() {
        return isLocalId(id);
    }

    public boolean isMine() {
        return Config.USER_ID.equals(userId);
    }
    // KEEP METHODS END

}
