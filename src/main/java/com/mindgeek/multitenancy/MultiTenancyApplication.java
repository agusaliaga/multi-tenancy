package com.mindgeek.multitenancy;

import com.mindgeek.multitenancy.domain.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableJpaRepositories
@EntityScan(basePackageClasses = {Employee.class})
public class MultiTenancyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiTenancyApplication.class, args);
    }

}
