package de.demmer.dennis.odrauserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableZuulProxy
@EntityScan(basePackageClasses = {
        OdraUserServiceApplication.class,
        Jsr310JpaConverters.class
})
public class OdraUserServiceApplication extends SpringBootServletInitializer {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(OdraUserServiceApplication.class, args);
    }

}
