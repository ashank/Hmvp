package com.funhotel.mvp.entity;

/**
 * Created by zhiyahan on 2017/3/27.
 */

public class Admodel {
        /**
         * time : 15
         * type : 1
         * url : http://222.68.210.55:33500/AdPlatform/upload/20170323-1280x720-lanmuzhaoshang.jpg
         */
        private int time;
        private int type;
        private String url;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "AdinfosBean{" +
                    "time=" + time +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    '}';
        }

}
