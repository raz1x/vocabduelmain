package de.htwberlin;

import de.htwberlin.game.export.UserDoesNotExistException;
import de.htwberlin.userManager.export.UserAlreadyExistsException;
import de.htwberlin.userManager.impl.ManageUserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class SpringConfiguration {
    private static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) throws UserDoesNotExistException, UserAlreadyExistsException {
        System.out.println("test");
        ManageUserImpl manageUser = context.getBean(ManageUserImpl.class);
        manageUser.registerUser("Florian", "12354");
        manageUser.registerUser("Toan", "12354");
        manageUser.registerUser("Friedrich", "12354");
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