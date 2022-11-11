package org.flowwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.flowwork.mapper")
public class ProdMidManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdMidManagementApplication.class, args);
    }
}
