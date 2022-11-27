package de.htwberlin;

import de.htwberlin.game.export.UserDoesNotExistException;
import de.htwberlin.game.impl.ManageGameImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationClient {
    private static ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("de.htwberlin");

    public static void main(String[] args) throws UserDoesNotExistException {
        ManageGameImpl manageGame = context.getBean(ManageGameImpl.class);
        System.out.println(manageGame.createGame(1,2));
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("vocabDuelPU");
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

}