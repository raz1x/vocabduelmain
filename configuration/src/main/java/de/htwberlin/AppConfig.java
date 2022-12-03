package de.htwberlin;

import de.htwberlin.userManager.export.UserAlreadyExistsException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("de.htwberlin")
public class AppConfig {

    private static ApplicationContext applicationContext;


    public static void main(String[] args) throws UserAlreadyExistsException {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        VocabUIController ui = (VocabUIController) applicationContext.getBean("vocabUIControllerImpl");
        ui.run();
        //ManageGameImpl manageGame = context.getBean(ManageGameImpl.class);
        //System.out.println(manageGame.createGame(1,2));
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName("vocabDuelPU");
        //emf.setDataSource(dataSource());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(additionalProperties());
        return emf;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

}