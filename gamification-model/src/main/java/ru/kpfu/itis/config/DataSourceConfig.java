package ru.kpfu.itis.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.net.URISyntaxException;

/**
 * Created by Дамир on 16.01.2015.
 */
@Configuration
@PropertySource(value = "classpath:database.properties", ignoreResourceNotFound = true)
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() throws PropertyVetoException, URISyntaxException {
        String username, password, dbUrl;
        username = env.getProperty("jdbc.user");
        password = env.getProperty("jdbc.password");
        dbUrl = env.getProperty("jdbc.url");

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
