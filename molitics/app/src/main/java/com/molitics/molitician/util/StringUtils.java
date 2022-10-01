/*
 *
 *  Proprietary and confidential.
 *
 */

package com.molitics.molitician.util;

import androidx.annotation.Nullable;

import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class useful when dealing with string objects. This class is a
 * collection of static functions it is not allowed to create instances of this
 * class
 */
public abstract class StringUtils {

    private static final String LOG_TAG = "StringUtils";

    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String MIME_TEXT_HTML = "text/html";

    public static final String EMAIL_REGEX = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * @param pStr String object to be tested.
     * @returns true if the given string is null or empty or contains spaces
     * only.
     */
    public static boolean isNullOrEmpty(final String pStr) {
        return pStr == null || pStr.trim().length() == 0 || pStr.trim().equalsIgnoreCase("null");
    }

    /**
     * @param pEmail
     * @param pAllowBlank
     * @return true if pEmail matches with {@link StringUtils#EMAIL_REGEX},
     * false otherwise
     */
    public static boolean isValidEmail(String pEmail, boolean pAllowBlank) {
        if (pAllowBlank && isNullOrEmpty(pEmail)) {
            return true;
        }
        Pattern validRegexPattern = Pattern.compile(EMAIL_REGEX);
        return validRegexPattern.matcher(pEmail).matches();
    }

    /**
     * @param pEmail
     * @return true if pEmail matches with {@link StringUtils#EMAIL_REGEX},
     * false otherwise
     */
    public static boolean isValidEmailOnly(String pEmail) {
        Pattern validRegexPattern = Pattern.compile(EMAIL_REGEX);
        return validRegexPattern.matcher(pEmail).matches();
    }


    public static ArrayList<News> convertNewsMobile(List<NewsVideoModel> news_video) {
        ArrayList<News> send_news_list = new ArrayList<>();

        for (int i = 0; i < news_video.size(); i++) {
            News mNews = new News();

            mNews.setDisplayType(news_video.get(i).getDisplayType());
            mNews.setContent(news_video.get(i).getContent());
            mNews.setTime(news_video.get(i).getTime());
            mNews.setType(news_video.get(i).getType());
            mNews.setSource(news_video.get(i).getSource());
            mNews.setHeading(news_video.get(i).getHeading());
            mNews.setId(news_video.get(i).getId());
            mNews.setImage(news_video.get(i).getImage());
            mNews.setLink(news_video.get(i).getLink());
            mNews.setSourceLogo(news_video.get(i).getSourceLogo());
            mNews.setSurveyId(news_video.get(i).getSurveyId());
            mNews.setVideoLink(news_video.get(i).getVideoLink());
            send_news_list.add(mNews);
        }
        return send_news_list;
    }

    public static String customizeListTags(@Nullable String html) {
        if (html == null) {
            return null;
        }
        html = html.replace("<ul", "<" + "UL");
        html = html.replace("</ul>", "</" + "UL" + ">");
        html = html.replace("<ol", "<" + "OL");
        html = html.replace("</ol>", "</" + "OL" + ">");
        html = html.replace("<dd", "<" + "DD");
        html = html.replace("</dd>", "</" + "DD" + ">");
        html = html.replace("<li", "<" + "LI");
        html = html.replace("</li>", "</" + "LI" + ">");
        return html;
    }

    public static String replaceHtml(String html) {
        html = html.replaceFirst("<li>", "\u25A0 ");
        html = html.replace("<li>", "<br><br> \u25A0 ");
        return html;
    }
}
