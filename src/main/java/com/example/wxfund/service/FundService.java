package com.example.wxfund.service;

import com.alibaba.fastjson.JSON;
import com.example.wxfund.entity.Fund;
import com.example.wxfund.util.Const;
import com.example.wxfund.util.HttpUtil;
import org.springframework.stereotype.Service;


@Service
public class FundService {
    public  String getFund() {
        String res = "";
        String urlFmt ="http://fundgz.1234567.com.cn/js/%s.js";
        for (int i = 0; i < Const.fundCodeArry.length;i++) {
            String url = String.format(urlFmt, Const.fundCodeArry[i]);
            String responseStr = HttpUtil.doGet(url);
            String resJson = responseStr.substring(8, responseStr.length() - 2);
            Fund fund = JSON.parseObject(resJson, Fund.class);
            String funRes = String.format("基金名：%s, 净值增长：%s \n", fund.getGztime(), fund.getName(), fund.getGszzl());
            res += funRes;
        }
        return res;
    }

}
