package com.mindgeek.multitenancy.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class MultitenantConfiguration {

    @Value("${defaultTenant}")
    private String defaultTenant;

    @Bean
    @SuppressWarnings("unchecked")
    Tenants tenants(Environment environment) {
        return new Tenants(environment.getRequiredProperty("datasource.tenants.names", Set.class));
    }

    @Bean
    DataSource dataSource(@Qualifier("targetDataSources") Map<Object, Object> targetDataSources) {
        final var tenantSelector = new TenantSelector();

        tenantSelector.setTargetDataSources(targetDataSources);
        tenantSelector.setDefaultTargetDataSource(targetDataSources.get(defaultTenant));
        tenantSelector.afterPropertiesSet();

        return tenantSelector;
    }

    @Bean("targetDataSources")
    Map<Object, Object> targetDataSources(Tenants tenants, Environment environment) {
        final var dataSources = new HashMap<>();

        for (final var tenant : tenants.getNames()) {
            final var dataSource = new HikariDataSource();

            dataSource.setJdbcUrl(environment.getRequiredProperty("datasource.tenant." + tenant + ".url"));
            dataSource.setUsername(environment.getRequiredProperty("datasource.tenant." + tenant + ".username"));
            dataSource.setPassword(environment.getRequiredProperty("datasource.tenant." + tenant + ".password"));
            dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));

            dataSources.put(tenant, dataSource);
        }

        return dataSources;
    }

    private static final class TenantSelector extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return TenantContext.getCurrentTenant();
        }
    }

}