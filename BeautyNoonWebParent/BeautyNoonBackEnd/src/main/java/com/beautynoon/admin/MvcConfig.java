package com.beautynoon.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        Path userPhotosDir = Paths.get(dirName);

        String usersPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        System.out.println(userPhotosDir.toFile());
        System.out.println(usersPhotosPath);

        registry.addResourceHandler("/", dirName + "/**").addResourceLocations("file:" + usersPhotosPath + "/");
    }
}
