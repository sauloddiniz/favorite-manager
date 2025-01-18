package br.com.favoritmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.favoritmanager.adapter.feign.client")
public class FavoritManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FavoritManagerApplication.class, args);
    }

}
