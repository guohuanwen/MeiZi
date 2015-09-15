package com.bcgtgjyb.huanwen.meizi.view.bean;

import java.util.List;

/**
 * Created by huanwen on 2015/9/14.
 */
public class EveryDayJson {
    private boolean error;
    private Result result;
    private List<Category> category;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private List<Detil> Android;
        private List<Detil> iOS;
        private List<Detil> expandText;
        private List<Detil> video;

        public List<Detil> getAndroid() {
            return Android;
        }

        public void setAndroid(List<Detil> android) {
            Android = android;
        }

        public List<Detil> getExpandText() {
            return expandText;
        }

        public void setExpandText(List<Detil> expandText) {
            this.expandText = expandText;
        }

        public List<Detil> getiOS() {
            return iOS;
        }

        public void setiOS(List<Detil> iOS) {
            this.iOS = iOS;
        }

        public List<Detil> getVideo() {
            return video;
        }

        public void setVideo(List<Detil> video) {
            this.video = video;
        }

        public class Detil{
            private String Who;
            private String publishedAt;
            private String desc;
            private String type;
            private String url;
            private String used;
            private String objectId;
            private String createdAt;
            private String updatedAt;

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUsed() {
                return used;
            }

            public void setUsed(String used) {
                this.used = used;
            }

            public String getWho() {
                return Who;
            }

            public void setWho(String who) {
                Who = who;
            }
        }




    }

    public class Category{
        private String categoryItem;

        public String getCategoryItem() {
            return categoryItem;
        }

        public void setCategoryItem(String categoryItem) {
            this.categoryItem = categoryItem;
        }
    }
}
