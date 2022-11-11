package org.flowwork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.flowwork.mapper")
public class ProdInfoUploadManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdInfoUploadManagementApplication.class, args);
    }
}
