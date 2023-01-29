package com.mbj.serviceprice.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-28 17:48
 * @Description:
 * @Version:
 */
public class MysqlGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-price?characterEncoding=utf-8&serverTimezone=GMT%2B8"
        ,"root","root")
                .globalConfig(
                        builder -> {
                            builder.author("mbj").fileOverride().outputDir("E:\\JAVA\\Project2\\online-taxi-2022-public\\online-taxi-public\\service-price\\src\\main\\java");
                        }
                ).packageConfig(builder -> {
                    builder.parent("com.mbj.servicepriceuser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,"E:\\JAVA\\Project2\\online-taxi-2022-public\\online-taxi-public\\service-price\\src\\main\\java\\com\\mbj\\servicepriceuser\\mapper"));
                }).strategyConfig(builder -> {
                    builder.addInclude("price_rule");
                })
                .templateEngine(new FreemarkerTemplateEngine()).execute();
    }
}
