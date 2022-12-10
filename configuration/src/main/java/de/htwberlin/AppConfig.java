package de.htwberlin;

import de.htwberlin.game.export.GameDoesNotExistException;
import de.htwberlin.game.export.RoundDoesNotExistException;
import de.htwberlin.game.export.UserDoesNotExistException;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.export.UserDAO;
import de.htwberlin.userManager.impl.UserDAOImpl;
import de.htwberlin.vocab.export.CategoryNotFoundException;
import de.htwberlin.vocab.export.VocabListNotFoundException;
import de.htwberlin.vocab.export.VocabNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("de.htwberlin")
public class AppConfig {

    private static ApplicationContext applicationContext;


    public static void main(String[] args) throws Exception {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        VocabUIController ui = (VocabUIController) applicationContext.getBean("vocabUIControllerImpl");
        ui.run();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName("vocabDuelPU");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

}