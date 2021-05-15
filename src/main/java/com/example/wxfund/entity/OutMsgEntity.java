package com.example.wxfund.entity;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@Setter
@Getter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMsgEntity {
    // 发送方的账号
    protected String FromUserName;
    // 接收方的账号(OpenID)
    protected String ToUserName;
    // 消息创建时间
    protected Long CreateTime;
    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * music 音乐消息
     * news 图文消息
     */
    protected String MsgType;
    // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
    @XmlElementWrapper(name="Image")
    private String[] MediaId ;
    // 文本内容
    private String Content;

    @Override
    public String toString() {
        return "OutMsgEntity{" +
                "FromUserName='" + FromUserName + '\'' +
                ", ToUserName='" + ToUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", MsgType='" + MsgType + '\'' +
                ", MediaId=" + Arrays.toString(MediaId) +
                ", Content='" + Content + '\'' +
                '}';
    }
}
