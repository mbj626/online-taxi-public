package com.mbj.servicedriveruser.generator;

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

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8"
        ,"root","root")
                .globalConfig(
                        builder -> {
                            builder.author("mbj").fileOverride().outputDir("E:\\JAVA\\Project2\\online-taxi-2022-public\\online-taxi-public\\service-driver-user\\src\\main\\java");
                        }
                ).packageConfig(builder -> {
                    builder.parent("com.mbj.servicedriveruser").pathInfo(Collections.singletonMap(OutputFile.mapperXml,"E:\\JAVA\\Project2\\online-taxi-2022-public\\online-taxi-public\\service-driver-user\\src\\main\\java\\com\\mbj\\servicedriveruser\\mapper"));
                }).strategyConfig(builder -> {
                    builder.addInclude("driver_user_work_status");
                })
                .templateEngine(new FreemarkerTemplateEngine()).execute();
    }
}
