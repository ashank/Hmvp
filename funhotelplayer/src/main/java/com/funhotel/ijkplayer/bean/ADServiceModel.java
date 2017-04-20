package com.funhotel.ijkplayer.bean;

import java.util.List;

/**
 * @Title: ADService
 * @Description: 广告model
 * @author: Cao
 * @data: 2016/10/11 11:47
 */
public class ADServiceModel {

    /**
     * result : 0
     * adinfos : [{"type":1,"time":10,"url":"http:xxxxx/xxxx/1.jgp"},{"type":2,"time":10,"url":"http://xxxx/xxxx/1.flv"}]
     */

    private int result;
    /**
     * type : 1
     * time : 10
     * url : http:xxxxx/xxxx/1.jgp
     */

    private List<AdinfosEntity> adinfos;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<AdinfosEntity> getAdinfos() {
        return adinfos;
    }

    public void setAdinfos(List<AdinfosEntity> adinfos) {
        this.adinfos = adinfos;
    }

    public static class AdinfosEntity {
        private int type;
        private int time;
        private String url;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
