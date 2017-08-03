package vn.danhtran.baseproject.utils;

import android.net.Uri;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.activity.BaseAppCompatActivity;
import vn.danhtran.baseproject.enums.Hashtag;


/**
 * Created by SilverWolf on 25/06/2017.
 */

public class HashTagUtils {
    public interface HashTagListener {
        void setOnClickHashTag(List<HashTag> hashTags);
    }

    public static void setOnClickHashTag(TextView tv, MotionEvent event, HashTagListener hashTagListener) {
        Layout layout = tv.getLayout();
        if (layout != null) {
            int line = layout.getLineForVertical((int) event.getY());
            int offset = layout.getOffsetForHorizontal(line, event.getX());

            if (tv.getText() != null && tv.getText() instanceof Spanned) {
                Spanned spanned = (Spanned) tv.getText();

                ClickableSpan[] links = spanned.getSpans(offset, offset, ClickableSpan.class);
                try {
                    if (links.length > 0) {
                        String url = ((MyCustomSpannable) links[0]).getUrl();
                        hashTagListener.setOnClickHashTag(getHashTag(url));
                    }
                } catch (Exception ex) {
                    Logger.d(ex);
                }
            }
        }
    }

    public static void HashTagAction(BaseAppCompatActivity activity, HashTag hashTag) {
        switch (hashTag.getScheme()) {
            case SOIBAT:
                switch (hashTag.getHost()) {
                    case SEARCH:
                        break;
                    case DISCOVER:
                        switch (hashTag.getKeyword()) {
                            case GENRES:
                                Logger.d(hashTag.getKey().get(0));
                                break;
                        }
                        break;
                }
                break;
        }
    }

    public static void createSpannable(SpannableStringBuilder builder, String name, String key) {
        String url = Hashtag.Scheme.SOIBAT.toString() + "://" + Hashtag.Host.DISCOVER.toString()
                + "?" + Hashtag.Keyword.GENRES.toString() + "=" + key;
        int start = builder.length();
        int end = builder.length() + name.length();
        builder.append(name);
        HashTagUtils.MyCustomSpannable customSpannable = new HashTagUtils.MyCustomSpannable(url);
        builder.setSpan(customSpannable, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    //soibat://search?keyword=danh
    private static List<HashTag> getHashTag(String url) {
        Uri uri = Uri.parse(url);
        String scheme = uri.getScheme();
        String host = uri.getHost();
        Map<String, List<String>> queries = splitQuery(uri);
        Hashtag.Scheme scheme_ = Hashtag.Scheme.fromValue(scheme);
        Hashtag.Host host_ = Hashtag.Host.fromValue(host);
        return getKeywords(scheme_, host_, queries);
    }

    private static List<HashTag> getKeywords(Hashtag.Scheme scheme, Hashtag.Host host, Map<String, List<String>> queries) {
        List<HashTag> hashTags = new ArrayList<>();
        for (String key : queries.keySet()) {
            List<String> values = queries.get(key);
            Hashtag.Keyword keyword = Hashtag.Keyword.fromValue(key);
            hashTags.add(new HashTag(scheme, host, keyword, values));
        }
        return hashTags;
    }

    private static Map<String, List<String>> splitQuery(Uri uri) {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<>();
        final String[] pairs = uri.getQuery().split("&");
        try {
            for (String pair : pairs) {
                final int idx = pair.indexOf("=");
                final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                if (!query_pairs.containsKey(key)) {
                    query_pairs.put(key, new LinkedList<String>());
                }
                final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                query_pairs.get(key).add(value);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return query_pairs;
    }

    public static class HashTag {
        Hashtag.Scheme scheme;
        Hashtag.Host host;
        Hashtag.Keyword keyword;
        List<String> key;

        public HashTag(Hashtag.Scheme scheme, Hashtag.Host host, Hashtag.Keyword keyword, List<String> key) {
            this.scheme = scheme;
            this.host = host;
            this.keyword = keyword;
            this.key = key;
        }

        public Hashtag.Scheme getScheme() {
            return scheme;
        }

        public void setScheme(Hashtag.Scheme scheme) {
            this.scheme = scheme;
        }

        public Hashtag.Host getHost() {
            return host;
        }

        public void setHost(Hashtag.Host host) {
            this.host = host;
        }

        public Hashtag.Keyword getKeyword() {
            return keyword;
        }

        public void setKeyword(Hashtag.Keyword keyword) {
            this.keyword = keyword;
        }

        public List<String> getKey() {
            return key;
        }

        public void setKey(List<String> key) {
            this.key = key;
        }
    }

    public static class MyCustomSpannable extends ClickableSpan {
        String Url;

        public MyCustomSpannable(String Url) {
            this.Url = Url;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(DeprecatedUtil.getResourceColor(R.color.color_blue));
            ds.setFakeBoldText(true);
            ds.setStrikeThruText(false);
//            ds.setTypeface(Typeface.DEFAULT);
            ds.setUnderlineText(true);
            ds.setTextSize(DimensionUtils.dpToPx(16));
        }

        @Override
        public void onClick(View widget) {
        }

        public String getUrl() {
            return Url;
        }
    }
}
