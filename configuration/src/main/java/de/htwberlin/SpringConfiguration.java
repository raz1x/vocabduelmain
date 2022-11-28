package de.htwberlin;

import de.htwberlin.game.export.UserDoesNotExistException;
import de.htwberlin.game.impl.ManageGameImpl;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.impl.ManageUserImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@EnableTransactionManagement
public class ConfigurationClient {
    private static ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("de.htwberlin");

    public static void main(String[] args) throws UserDoesNotExistException, UserAlreadyExistsException {
        ManageUserImpl manageUser = context.getBean(ManageUserImpl.class);
        manageUser.registerUser("Raz", "12354");
        manageUser.registerUser("Daimox", "12354");
        //ManageGameImpl manageGame = context.getBean(ManageGameImpl.class);
        //System.out.println(manageGame.createGame(1,2));
    }

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean = new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("vocabDuelPU");
        return localEmfBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}