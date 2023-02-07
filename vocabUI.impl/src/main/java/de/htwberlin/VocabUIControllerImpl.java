package de.htwberlin;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageGame.rest_client.ManageGameRestServiceClientAdapter;
import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Controller
public class VocabUIControllerImpl implements VocabUIController {

    @Autowired
    private ManageUser manageUser;

    @Autowired
    private ManageGame manageGame;

    @Autowired
    private ManageVocab manageVocab;

    @Autowired
    ResourceLoader resourceLoader;

    private User currentUser;

    private VocabUIView view;

    @Override
    public void run() {
        view = new VocabUIView();
        showMainMenu();
    }

    @Override
    public void showLogin() {
        try {
            String username = view.readUserInput("Please enter your username: ");
            String password = view.readUserInput("Please enter your password: ");
            currentUser = manageUser.loginUser(username, password);
        } catch (UserNotFoundException e) {
            view.showError("Could not login with given credentials. Please try again.");
            return;
        } catch (WrongPasswordException e) {
            view.showError("Wrong password!");
            return;
        }
        showManagerMenu();
    }

    @Override
    public void showRegister() {
        try {
            String username = view.readUserInput("Please enter your username: ");
            if(manageUser.userExists(username)) {
                throw new UserAlreadyExistsException("User already exists!");
            }
            String password = view.readUserInput("Please enter your password: ");
            currentUser = manageUser.registerUser(username, password);
        } catch (UserAlreadyExistsException e) {
            view.showError("User already exists!");
            return;
        }
        showManagerMenu();
    }

    @Override
    public void showMainMenu() {
        view.showMessage("Welcome to the Vocab Quiz Duel!");
        int choice = 0;
        String[] menuItems = {"Login", "Register", "Exit"};
        do {
            view.showMenu(menuItems);
            choice = view.readUserSelection();
            switch (choice) {
                case 1 -> showLogin();
                case 2 -> showRegister();
                case 3 -> view.showMessage("Goodbye!");
                default -> view.showMessage("Invalid choice!");
            }
        } while (choice != 3);
        System.exit(0);
    }

    @Override
    public void showManagerMenu() {
        int choice = 0;
        String[] menuItems = {"Manage Vocabulary", "Manage Games", "Manage Users", "Logout", "Exit"};
        do {
            try {
            view.showMenu(menuItems);
            choice = view.readUserSelection();
            switch (choice) {
                case 1 -> manageVocabMenu();
                case 2 -> manageGameMenu();
                case 3 -> manageUserMenu();
                case 4 -> {
                    manageUser.logoutUser(currentUser.getUserId());
                    showMainMenu();
                }
                case 5 -> view.showMessage("Goodbye!");
                default -> view.showMessage("Invalid choice!");
            }
            } catch (UserNotFoundException e) {
                view.showError("User not found!");
            }
        } while (choice != 5);
        System.exit(0);
    }

    @Override
    public void manageUserMenu() {
        int choice = 0;
        String[] menuItems = {"Create a new User", "Delete a User", "Return"};
        do {
            view.showMenu(menuItems);
            choice = view.readUserSelection();
            switch (choice) {
                case 1 -> showRegister();
                case 2 -> showDeleteUser();
                default -> view.showMessage("Invalid choice!");
            }
        } while (choice != 3);
    }

    @Override
    public void showDeleteUser() {
        try {
            String username = view.readUserInput("Please enter the username of the user you want to delete: ");
            User user = manageUser.getByName(username);
            manageUser.deleteUser(user.getUserId());
        } catch (UserNotFoundException e) {
            view.showError("User not found!");
        }
    }

    @Override
    public void manageVocabMenu() {
        int choice = 0;
        String[] menuItems = {"Import a Vocab List", "Return"};
        do {
            view.showMenu(menuItems);
            choice = view.readUserSelection();
            if (choice == 1) {
                showImportVocabList();
            } else {
                view.showError("Invalid choice!");
            }
        } while (choice != 2);
    }

    @Override
    public void showImportVocabList() {
        int choice = 0;

        Resource resource = resourceLoader.getResource("classpath:textFiles");
        File folder = null;
        try {
            folder = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File[] listOfFiles = folder.listFiles();
        String[] fileNames = new String[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames[i] = listOfFiles[i].getName();
            }
        }
        view.showMenu(fileNames);
        choice = view.readUserSelection();
        File file;
        try {
            file = listOfFiles[choice - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            view.showError("Invalid choice!");
            return;
        }
        try {
            manageVocab.parseVocabList(file);
        } catch (IOException | VocabDAOException e) {
            view.showError("Could not import file!");
        }
    }

    @Override
    public void manageGameMenu() {
        int choice = 0;
        String[] menuItems = {"Create a new Game", "Continue playing a game", "Return"};
        do {
            view.showMenu(menuItems);
            choice = view.readUserSelection();
            switch (choice) {
                case 1 -> showCreateGame();
                case 2 -> showGamesOfUser();
                default -> view.showMessage("Invalid choice!");
            }
        } while (choice != 3);
    }

    @Override
    public void showCreateGame() {
        List<User> opponents;
        try {
            opponents = manageUser.getOpponents(currentUser.getUserId());
        } catch (UserNotFoundException e) {
            view.showError("No opponents found!");
            return;
        }
        if (opponents.size() == 0) {
            view.showError("Not enough users found to create a game!");
            return;
        }
        for (int i = 0; i < opponents.size(); i++) {
            view.showMessage("[" + (i + 1) + "] " + opponents.get(i).getUsername());
        }
        try {
            view.showMessage("Please choose a user to play against: ");
            User opponent = null;
            try {
                int index = view.readUserSelection();
                opponent = opponents.get(index - 1);
            } catch (IndexOutOfBoundsException e) {
                view.showError("Invalid choice!");
                return;
            }
            Game game = manageGame.createGame(currentUser.getUserId(), opponent.getUserId());
            showCreateRound(game);
        } catch (UserDoesNotExistException | UserNotFoundException e) {
            view.showError("User not found!");
        }
    }

    @Override
    public void showCreateRound(Game game) {
        List<Category> categories;
        Round round;
        try {
            categories = manageVocab.getAllCategories();
        } catch (CategoryNotFoundException e) {
            view.showError("Could not find any Categories!");
            return;
        }
        view.showMessage("Please choose a category: ");
        while (true) {
            for (int i = 0; i < categories.size(); i++) {
                view.showMessage("[" + (i + 1) + "] " + categories.get(i).getCategoryName());
            }
            int index = view.readUserSelection();
            try {
                Category category = categories.get(index - 1);
                int roundNumber = manageGame.getLatestRoundForGame(game.getGameId()) + 1;
                manageGame.createRound(game.getGameId(), roundNumber, category.getCategoryId());
                break;
            } catch (IndexOutOfBoundsException e) {
                view.showError("Invalid choice!");
            } catch (GameDoesNotExistException | CategoryNotFoundException | VocabNotFoundException |
                     GameQuestionDoesNotExistException | VocabListNotFoundException | TranslationNotFoundException e) {
                view.showError("Could not create round!");
                return;
            }
        }
        playRoundUI(game);
    }

    @Override
    public void showGamesOfUser() {
        List<Game> games;
        Game game;
        try {
            games = manageGame.getAllOngoingGamesForUser(currentUser.getUserId());
        } catch (GameDoesNotExistException e) {
            view.showError("No games found!");
            return;
        } catch (UserDoesNotExistException | UserNotFoundException e) {
            view.showError("User not found!");
            return;
        }
        try {
            for (int i = 0; i < games.size(); i++) {
                view.showMessageWithoutNewLine("[" + (i + 1) + "] " + games.get(i).getGameId());
                view.showMessage(" - vs. " + manageUser.getById(games.get(i).getOtherUser(currentUser.getUserId())).getUsername());
            }
        } catch (UserNotFoundException e) {
            view.showError("Could not find opponent for the game!");
        }
        while(true) {
            view.showMessage("Please choose a game: ");
            int index = view.readUserSelection();
            try {
                game = games.get(index - 1);
                playRoundUI(game);
                break;
            } catch (IndexOutOfBoundsException e) {
                view.showError("Invalid choice!");
            }
        }
    }

    @Override
    public void playRoundUI(Game game) {
        Round round;
        try {
            round = manageGame.getOngoingRoundForGame(game.getGameId());
        } catch (RoundDoesNotExistException e) {
            view.showError("Round not found!");
            return;
        }
        List<GameQuestion> gameQuestion;
        try {
            gameQuestion = manageGame.getGameQuestionsForRound(game.getGameId(), round.getRoundNumber());
        } catch (RoundDoesNotExistException e) {
            view.showError("Round not found!");
            return;
        } catch (GameQuestionDoesNotExistException e) {
            view.showError("No questions found for this round!");
            return;
        } catch (GameDoesNotExistException e) {
            view.showError("Could not find the game for this round!");
            return;
        }
        // display all questions with their answers
        for (GameQuestion question : gameQuestion) {
            view.showMessage(question.getVocab().getVocab());
            try {
                List<GameAnswer> gameAnswers = manageGame.getGameAnswersForGameQuestion(question.getGameQuestionId());
                for (int i = 0; i < gameAnswers.size(); i++) {
                    view.showMessage("[" + (i + 1) + "] " + gameAnswers.get(i).getTranslation().getTranslation());
                }
                view.showMessage("Please enter your answer: ");
                // display all answers for the question
                while(true) {
                    int index = view.readUserSelection();
                    try {
                        GameAnswer gameAnswer = gameAnswers.get(index - 1);
                        boolean isCorrect;
                        if (gameAnswer.getTranslation().getTranslationId() == question.getTrueAnswer().getTranslationId()) {
                            isCorrect = true;
                            view.showMessage("Correct!");
                        } else {
                            isCorrect = false;
                            view.showMessage("Wrong!");
                        }
                        manageGame.lockInAnswer(gameAnswer.getGameAnswerId(), currentUser.getUserId(), isCorrect);
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        view.showError("Invalid choice!");
                    } catch (UserNotFoundException e) {
                        view.showError("User not found!");
                    }
                }
            } catch (GameAnswerDoesNotExistException e) {
                view.showError("Could not find game answers for this question!");
            }
        }
        // if the user started the round, set him as player1
        if (game.getUserStartingRound() == currentUser.getUserId()) {
            round.setPlayer1Answered(true);
            game.setCurrentUser(game.getOtherUser(currentUser.getUserId()));
            try {
                game = manageGame.updateGame(game);
            } catch (GameDoesNotExistException e) {
                view.showError("Could not update game!");
                return;
            }
            view.showMessage("Please wait for the other player to finish!");
        } else {
            round.setPlayer2Answered(true);
            game.setUserStartingRound(game.getOtherUser(currentUser.getUserId()));
        }
        try {
            round = manageGame.updateRound(round);
        } catch (RoundDoesNotExistException e) {
            view.showError("Could not update round!");
        }
        // if both players have answered, finish the round
        if (round.isPlayer1Answered() && round.isPlayer2Answered()) {
            try {
                manageGame.endRound(round.getRoundId());
            } catch (RoundDoesNotExistException e) {
                view.showError("Could not end round!");
            }
            if (round.getRoundNumber() == 2) {
                try {
                    manageGame.endGame(game.getGameId());
                    showGameResults(game);
                } catch (GameDoesNotExistException e) {
                    view.showError("Could not end game!");
                }
            } else {
                try {
                    game.setUserStartingRound(currentUser.getUserId());
                    game = manageGame.updateGame(game);
                    showCreateRound(game);
                } catch (GameDoesNotExistException e) {
                    view.showError("Could not update game!");
                }
            }
        }
    }

    @Override
    public void showGameResults(Game game) {
        User user1;
        User user2;
        try {
            user1 = manageUser.getById(game.getUser1Id());
            user2 = manageUser.getById(game.getUser2Id());
        } catch (UserNotFoundException e) {
            view.showError("Could not find users for this game!");
            return;
        }
        try {
            int resultsUser1 = manageGame.getScoreForUser(game.getUser1Id(), game.getGameId());
            int resultsUser2 = manageGame.getScoreForUser(game.getUser2Id(), game.getGameId());
            view.showMessage(user1.getUsername() + ": " + resultsUser1);
            view.showMessage(user2.getUsername() + ": " + resultsUser2);
            if (resultsUser1 > resultsUser2) {
                view.showMessage(user1.getUsername() + " won!");
            } else if (resultsUser1 < resultsUser2) {
                view.showMessage(user2.getUsername() + " won!");
            } else {
                view.showMessage("Draw!");
            }
        } catch (GameDoesNotExistException e) {
            view.showError("No games found!");
        } catch (UserNotFoundException | UserDoesNotExistException e) {
            view.showError("User not found!");
        } catch (RoundResultDoesNotExistException e) {
            view.showError("No results found!");
        }
    }

}

