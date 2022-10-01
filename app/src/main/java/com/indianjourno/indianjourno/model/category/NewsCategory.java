package com.indianjourno.indianjourno.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.indianjourno.indianjourno.utils.DateUtils;


public class NewsCategory {

        @SerializedName("news_id")
        @Expose
        private String newsId;
        @SerializedName("news_category")
        @Expose
        private String newsCategory;
        @SerializedName("news_sub_category")
        @Expose
        private String newsSubCategory;
        @SerializedName("news_slug")
        @Expose
        private String newsSlug;
        @SerializedName("news_title")
        @Expose
        private String newsTitle;
        @SerializedName("news_content")
        @Expose
        private String newsContent;
        @SerializedName("news_tag")
        @Expose
        private String newsTag;
        @SerializedName("news_photo")
        @Expose
        private String newsPhoto;
        @SerializedName("news_reporter")
        @Expose
        private String newsReporter;
        @SerializedName("news_schedule")
        @Expose
        private String newsSchedule;
        @SerializedName("news_source")
        @Expose
        private String newsSource;
        @SerializedName("news_location")
        @Expose
        private String newsLocation;
        @SerializedName("news_state")
        @Expose
        private String newsState;
        @SerializedName("news_country")
        @Expose
        private String newsCountry;
        @SerializedName("is_featured")
        @Expose
        private String isFeatured;
        @SerializedName("news_status")
        @Expose
        private String newsStatus;
        @SerializedName("news_meta_title")
        @Expose
        private String newsMetaTitle;
        @SerializedName("news_meta_keyword")
        @Expose
        private String newsMetaKeyword;
        @SerializedName("news_meta_description")
        @Expose
        private String newsMetaDescription;
        @SerializedName("news_created")
        @Expose
        private String newsCreated;

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public String getNewsCategory() {
            return newsCategory;
        }

        public void setNewsCategory(String newsCategory) {
            this.newsCategory = newsCategory;
        }

        public String getNewsSubCategory() {
            return newsSubCategory;
        }

        public void setNewsSubCategory(String newsSubCategory) {
            this.newsSubCategory = newsSubCategory;
        }

        public String getNewsSlug() {
            return newsSlug;
        }

        public void setNewsSlug(String newsSlug) {
            this.newsSlug = newsSlug;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }

        public String getNewsTag() {
            return newsTag;
        }

        public void setNewsTag(String newsTag) {
            this.newsTag = newsTag;
        }

        public String getNewsPhoto() {
            return newsPhoto;
        }

        public void setNewsPhoto(String newsPhoto) {
            this.newsPhoto = newsPhoto;
        }

        public String getNewsReporter() {
            return newsReporter;
        }

        public void setNewsReporter(String newsReporter) {
            this.newsReporter = newsReporter;
        }

        public String getNewsSchedule() {
            return DateUtils.yyyy_MM_dd_HH_mm_ss(newsSchedule);
        }

        public void setNewsSchedule(String newsSchedule) {
            this.newsSchedule = newsSchedule;
        }

        public String getNewsSource() {
            return newsSource;
        }

        public void setNewsSource(String newsSource) {
            this.newsSource = newsSource;
        }

        public String getNewsLocation() {
            return newsLocation;
        }

        public void setNewsLocation(String newsLocation) {
            this.newsLocation = newsLocation;
        }

        public String getNewsState() {
            return newsState;
        }

        public void setNewsState(String newsState) {
            this.newsState = newsState;
        }

        public String getNewsCountry() {
            return newsCountry;
        }

        public void setNewsCountry(String newsCountry) {
            this.newsCountry = newsCountry;
        }

        public String getIsFeatured() {
            return isFeatured;
        }

        public void setIsFeatured(String isFeatured) {
            this.isFeatured = isFeatured;
        }

        public String getNewsStatus() {
            return newsStatus;
        }

        public void setNewsStatus(String newsStatus) {
            this.newsStatus = newsStatus;
        }

        public String getNewsMetaTitle() {
            return newsMetaTitle;
        }

        public void setNewsMetaTitle(String newsMetaTitle) {
            this.newsMetaTitle = newsMetaTitle;
        }

        public String getNewsMetaKeyword() {
            return newsMetaKeyword;
        }

        public void setNewsMetaKeyword(String newsMetaKeyword) {
            this.newsMetaKeyword = newsMetaKeyword;
        }

        public String getNewsMetaDescription() {
            return newsMetaDescription;
        }

        public void setNewsMetaDescription(String newsMetaDescription) {
            this.newsMetaDescription = newsMetaDescription;
        }

        public String getNewsCreated() {
            return newsCreated;
        }

        public void setNewsCreated(String newsCreated) {
            this.newsCreated = newsCreated;
        }

}
