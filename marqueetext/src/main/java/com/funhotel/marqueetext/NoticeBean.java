package com.funhotel.marqueetext;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiyahan on 2017/4/19.
 */

public class NoticeBean implements Serializable{




    /**
     * result : 0
     * message : 成功
     * datas : [{"id":36,"title":"请注意，请注意，酒店员工会议今天举行，今天举行","cycleTimes":4,"isForce":"0","isDelete":"0","icon":"","media":"","beginTime":"2017-04-14 18:17:39","endTime":"2017-04-14 18:17:43","createTime":null,"updateTime":null}]
     */

    private String result;
    private String message;
    private List<DatasBean> datas;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable {
        /**
         * id : 36
         * title : 请注意，请注意，酒店员工会议今天举行，今天举行
         * cycleTimes : 4
         * isForce : 0
         * isDelete : 0
         * icon :
         * media :
         * beginTime : 2017-04-14 18:17:39
         * endTime : 2017-04-14 18:17:43
         * createTime : null
         * updateTime : null
         */

        private int id;
        private String title;
        private int cycleTimes;
        private String isForce;
        private String isDelete;
        private int priority;
        private int restMill;
        private String icon;
        private String media;
        private String beginTime;
        private String endTime;
        private Object createTime;
        private Object updateTime;

        public int getRestMill() {
            return restMill;
        }

        public void setRestMill(int restMill) {
            this.restMill = restMill;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCycleTimes() {
            return cycleTimes;
        }

        public void setCycleTimes(int cycleTimes) {
            this.cycleTimes = cycleTimes;
        }

        public String getIsForce() {
            return isForce;
        }

        public void setIsForce(String isForce) {
            this.isForce = isForce;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", cycleTimes=" + cycleTimes +
                    ", isForce='" + isForce + '\'' +
                    ", isDelete='" + isDelete + '\'' +
                    ", icon='" + icon + '\'' +
                    ", media='" + media + '\'' +
                    ", beginTime='" + beginTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", datas=" + datas +
                '}';
    }
}
