package com.lamdayne.minihrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class MiniHrmApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // config timezone cho jvm
        SpringApplication.run(MiniHrmApplication.class, args);
    }

}
