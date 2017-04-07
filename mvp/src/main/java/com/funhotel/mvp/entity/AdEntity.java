package com.funhotel.mvp.entity;

import java.util.List;

/**
 *
 * Created by zhiyahan on 2017/3/27.
 */

public class AdEntity {


    /**
     * result : 0
     * adinfos : [{"time":15,"type":1,"url":"http://222.68.210.55:33500/AdPlatform/upload/20170323-1280x720-lanmuzhaoshang.jpg"}]
     */

    private int result;
    private List<AdinfosBean> adinfos;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<AdinfosBean> getAdinfos() {
        return adinfos;
    }

    public void setAdinfos(List<AdinfosBean> adinfos) {
        this.adinfos = adinfos;
    }

    public static class AdinfosBean {
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
    }
}
