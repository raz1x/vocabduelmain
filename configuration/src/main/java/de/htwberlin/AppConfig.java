package de.htwberlin;

import de.htwberlin.game.export.ManageGame;
import de.htwberlin.game.export.UserDoesNotExistException;
import de.htwberlin.game.impl.ManageGameImpl;
import de.htwberlin.userManager.export.ManageUser;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.impl.ManageUserImpl;
import de.htwberlin.vocab.export.AccessVocab;
import de.htwberlin.vocab.impl.AccessVocabImpl;
import de.htwberlin.vocab.impl.ManageVocabImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("de.htwberlin")
public class AppConfig {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) throws UserDoesNotExistException, UserAlreadyExistsException {
        applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        displayAllBeans();

        ManageUserImpl manageUser =  (ManageUserImpl)applicationContext.getBean("manageUserImpl", ManageUserImpl.class);
        manageUser.registerUser("Florian", "12354");
        manageUser.registerUser("Toan", "12354");
        manageUser.registerUser("Friedrich", "12354");
        //ManageGameImpl manageGame = context.getBean(ManageGameImpl.class);
        //System.out.println(manageGame.createGame(1,2));
    }

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
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