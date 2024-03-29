package de.htwberlin;

import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.concurrent.TimeUnit;


@Configuration
@ComponentScan("de.htwberlin")
public class AppConfig {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        VocabUIController ui = (VocabUIController) applicationContext.getBean("vocabUIControllerImpl");
        ui.run();
    }

}