package com.example.wxfund;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class WxFundApplication {

    public static void main(String[] args) {
        Logger logger= LoggerFactory.getLogger(WxFundApplication.class);
        logger.debug("我们是一个测试的");
        logger.info("测试测试9999999999999999999999");
        SpringApplication.run(WxFundApplication.class, args);
    }

}
