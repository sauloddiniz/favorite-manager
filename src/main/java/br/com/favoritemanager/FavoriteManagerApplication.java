package br.com.favoritemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.favoritemanager.adapter.feign.client")
public class FavoriteManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FavoriteManagerApplication.class, args);
    }

}
