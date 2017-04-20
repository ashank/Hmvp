package com.funhotel.tvllibrary.application;

import java.util.List;

/**
 * @Title: BaseModel
 * @Description:
 * @author: Zhang Yetao
 * @data: 2016/9/27 12:31
 */
public class BaseModel {

    /**
     * result : 0
     * message : 成功
     * datas : [{"id":12,"name":"酒店概况","resourceName":"酒店概况","position":1,"text":"酒店概况","href":"","beforeImgUrl":null,"backImgUrl":null,"mediaUrl":null,"showType":3,"sort":null,"type":null,"templateId":null,"templateName":null,"extention1":"5","extention2":"5"},{"id":13,"name":"客房服务","resourceName":"客房服务","position":2,"text":"客房服务","href":"","beforeImgUrl":null,"backImgUrl":null,"mediaUrl":null,"showType":3,"sort":null,"type":null,"templateId":null,"templateName":null,"extention1":"5","extention2":"5"},{"id":14,"name":"餐饮","resourceName":"餐饮","position":3,"text":"餐饮","href":"","beforeImgUrl":null,"backImgUrl":null,"mediaUrl":null,"showType":3,"sort":null,"type":null,"templateId":null,"templateName":null,"extention1":"5","extention2":"5"},{"id":15,"name":"健身休闲","resourceName":"健身休闲","position":4,"text":"健身休闲","href":"","beforeImgUrl":null,"backImgUrl":null,"mediaUrl":null,"showType":3,"sort":null,"type":null,"templateId":null,"templateName":null,"extention1":"5","extention2":"5"},{"id":42,"name":"周边","resourceName":"周边","position":5,"text":"周边","href":"","beforeImgUrl":null,"backImgUrl":null,"mediaUrl":null,"showType":3,"sort":null,"type":null,"templateId":null,"templateName":null,"extention1":"5","extention2":"5"}]
     */

    private String result;
    private String message;
    /**
     * id : 12
     * name : 酒店概况
     * resourceName : 酒店概况
     * position : 1
     * text : 酒店概况
     * href :
     * beforeImgUrl : null
     * backImgUrl : null
     * mediaUrl : null
     * showType : 3
     * sort : null
     * type : null
     * templateId : null
     * templateName : null
     * extention1 : 5
     * extention2 : 5
     */

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

    public static class DatasBean {
        private int id;
        private String name;
        private String resourceName;
        private Integer position;
        private String text;
        private String href;
        private String beforeImgUrl;
        private String backImgUrl;
        private String mediaUrl;
        private Integer showType;
        private Integer sort;
        private String type;
        private Integer templateId;
        private String templateName;
        private String extention1;
        private String extention2;
        private String cls;
        private String pck;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getBeforeImgUrl() {
            return beforeImgUrl;
        }

        public void setBeforeImgUrl(String beforeImgUrl) {
            this.beforeImgUrl = beforeImgUrl;
        }

        public String getBackImgUrl() {
            return backImgUrl;
        }

        public void setBackImgUrl(String backImgUrl) {
            this.backImgUrl = backImgUrl;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public Integer getShowType() {
            return showType;
        }

        public void setShowType(Integer showType) {
            this.showType = showType;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Integer templateId) {
            this.templateId = templateId;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public String getExtention1() {
            return extention1;
        }

        public void setExtention1(String extention1) {
            this.extention1 = extention1;
        }

        public String getExtention2() {
            return extention2;
        }

        public void setExtention2(String extention2) {
            this.extention2 = extention2;
        }

        public String getCls() {
            return cls;
        }

        public void setCls(String cls) {
            this.cls = cls;
        }

        public String getPck() {
            return pck;
        }

        public void setPck(String pck) {
            this.pck = pck;
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", resourceName='" + resourceName + '\'' +
                    ", position=" + position +
                    ", text='" + text + '\'' +
                    ", href='" + href + '\'' +
                    ", beforeImgUrl='" + beforeImgUrl + '\'' +
                    ", backImgUrl='" + backImgUrl + '\'' +
                    ", mediaUrl='" + mediaUrl + '\'' +
                    ", showType=" + showType +
                    ", sort=" + sort +
                    ", type='" + type + '\'' +
                    ", templateId=" + templateId +
                    ", templateName='" + templateName + '\'' +
                    ", extention1='" + extention1 + '\'' +
                    ", extention2='" + extention2 + '\'' +
                    ", cls='" + cls + '\'' +
                    ", pck='" + pck + '\'' +
                    '}';
        }
    }
}
