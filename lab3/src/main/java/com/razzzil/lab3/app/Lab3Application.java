package com.razzzil.lab3.app;

import com.razzzil.lab3.models.Resource;
import com.razzzil.lab3.models.Role;
import javafx.application.Application;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.razzzil.lab3")
public class Lab3Application {

    @Autowired
    private Environment environment;

    final static List<Resource> resources = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

    @EventListener({ApplicationReadyEvent.class})
    public void applicationReadyEvent() {
        LoggerFactory.getLogger(Application.class).info("Launching browser");
        browse("http://localhost:" + environment.getProperty("local.server.port") + "/");
    }

    @Bean
    @Scope("singleton")
    public List<Resource> resourcesDatasource(){
        synchronized (resources) {
            resources.add(new Resource(0, "Mouse", "https://upload.wikimedia.org/wikipedia/commons/0/0d/%D0%9C%D1%8B%D1%88%D1%8C_2.jpg", new HashSet<>(Arrays.asList(Role.ADMIN, Role.GUEST, Role.STUDENT, Role.MANAGER, Role.TEACHER))));
            resources.add(new Resource(1, "Cat", "https://thumbs.dreamstime.com/b/portrait-gray-tabby-cat-white-background-lovely-pet-portrait-gray-tabby-cat-white-background-126243718.jpg", new HashSet<>(Arrays.asList(Role.ADMIN, Role.STUDENT, Role.MANAGER, Role.TEACHER))));
            resources.add(new Resource(2, "Dog", "https://previews.123rf.com/images/isselee/isselee1510/isselee151000207/46944905-golden-retriever-sitting-in-front-of-a-white-background.jpg", new HashSet<>(Arrays.asList(Role.ADMIN, Role.MANAGER, Role.TEACHER))));
        }
        return resources;
    }

    public static void browse(String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
