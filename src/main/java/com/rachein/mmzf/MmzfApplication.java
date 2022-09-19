package com.rachein.mmzf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rachein.mmzf.core.mapper")
public class MmzfApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmzfApplication.class, args);
    }

}
