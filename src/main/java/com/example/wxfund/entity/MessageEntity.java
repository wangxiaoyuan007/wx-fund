package com.example.wxfund.entity;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageEntity {
    // 开发者微信号
    @XmlElement(name="FromUserName")
    protected String FromUserName;
    // 发送方帐号（一个OpenID）
    @XmlElement(name="ToUserName")
    protected String ToUserName;
    // 消息创建时间
    @XmlElement(name="CreateTime")
    protected Long CreateTime;
    /**
     * 消息类型
     * text 文本消息
     * image 图片消息
     * voice 语音消息
     * video 视频消息
     * music 音乐消息
     */
    @XmlElement(name="MsgType")
    protected String MsgType;
    // 消息id
    @XmlElement(name="MsgId")
    protected Long MsgId;
    // 文本内容
    @XmlElement(name="Content")
    private String Content;
    // 图片链接（由系统生成）
    @XmlElement(name="PicUrl")
    private String PicUrl;
    // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
    @XmlElement(name="MediaId")
    private String MediaId;
}
