package com.example.wxfund.controller;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.example.wxfund.WxFundApplication;
import com.example.wxfund.entity.MessageEntity;
import com.example.wxfund.entity.OutMsgEntity;
import com.example.wxfund.service.FundService;
import com.example.wxfund.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.MessageUtils;
import com.dianping.cat.message.Transaction;
import java.util.Date;

@RestController
public class WxController {
    @Autowired
    FundService fundService;

    @GetMapping("/wx/exchange")
    public String receive( @RequestParam(value = "signature", required = true) String signature,
                           @RequestParam(value = "timestamp", required = true) String timestamp,
                           @RequestParam(value = "nonce", required = true) String nonce,
                           @RequestParam(value = "echostr", required = true) String echostr) {
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "";
    }

    @PostMapping(value = "/wx/exchange", produces = "application/xml;charset=UTF-8")
    public Object reply(@RequestBody MessageEntity msg) {
        Logger logger= LoggerFactory.getLogger(WxFundApplication.class);
        logger.debug(msg.toString());
        //创建消息响应对象
        OutMsgEntity out = new OutMsgEntity();
        //把原来的发送方设置为接收方
        out.setToUserName(msg.getFromUserName());
        //把原来的接收方设置为发送方
        out.setFromUserName(msg.getToUserName());
        //获取接收的消息类型
        String msgType = msg.getMsgType();
        //设置消息的响应类型
        out.setMsgType(msgType);
        //设置消息创建时间
        out.setCreateTime(new Date().getTime());
        logger.debug(out.toString());
        //根据类型设置不同的消息数据
        if("text".equals(msgType)){
            out.setContent(fundService.getFund());
        }else if("image".equals(msgType)){
           out.setContent("消息类型不合适");
        }
        return out;
    }
    @GetMapping(value = "/wx/hello")
    public Object hello(){
        return fundService.getFund();
    }

    @GetMapping(value = "/wx/report")
    public Object hello2(){
        Transaction t = Cat.newTransaction(CatConstants.TYPE_REMOTE_CALL, "/wx/report");
        try {

            t.setStatus(Transaction.SUCCESS);
            return "success";
        } catch (Exception e) {
            Cat.getProducer().logError(e);
            t.setStatus(e);
            throw e;
        } finally {
            t.complete();
        }
    }
}
