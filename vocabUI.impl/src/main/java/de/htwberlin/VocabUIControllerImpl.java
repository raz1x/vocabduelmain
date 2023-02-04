package de.htwberlin;

import de.htwberlin.manageGame.export.*;
import de.htwberlin.manageGame.rest_client.ManageGameRestServiceClientAdapter;
import de.htwberlin.manageVocab.export.*;
import de.htwberlin.userManager.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private User currentUser;

    @Override
    public void run() {
        try {
            List<Game> games = manageGame.getAllOngoingGamesForUser(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showLogin() {
        try {
            System.out.println("Please enter your username: ");
            String username = readUserInput();
            System.out.println("Please enter your password: ");
            String password = readUserInput();
            currentUser = manageUser.loginUser(username, password);
        } catch (UserNotFoundException e) {
            System.out.println("Could not login with given credentials. Please try again.");
            return;
        } catch (WrongPasswordException e) {
            System.out.println("Wrong password!");
            return;
        }
        showManagerMenu();
    }

    @Override
    public void showRegister() {
        try {
            System.out.println("Please enter your username: ");
            String username = readUserInput();
            if(manageUser.userExists(username)) {
                throw new UserAlreadyExistsException("User already exists!");
            }
            System.out.println("Please enter your password: ");
            String password = readUserInput();
            currentUser = manageUser.registerUser(username, password);
        } catch (UserAlreadyExistsException e) {
            System.out.println("User already exists!");
            return;
        }
        showManagerMenu();
    }

    @Override
    public void showMainMenu() {
        System.out.println("Welcome to the Vocab Quiz Duel!");
        int choice = 0;

        do {
            System.out.println("Please choose an option:");
            System.out.println("[1] Login");
            System.out.println("[2] Register");
            System.out.println("[3] Exit");
            choice = readUserSelection();
            switch (choice) {
                case 1 -> showLogin();
                case 2 -> showRegister();
                case 3 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 3);
        System.exit(0);
    }

    @Override
    public void showManagerMenu() {
        int choice = 0;
        do {
            try {
            System.out.println("Please choose an option:");
            System.out.println("[1] Manage Vocabulary");
            System.out.println("[2] Manage Games");
            System.out.println("[3] Manage Users");
            System.out.println("[4] Logout");
            System.out.println("[5] Exit");
            choice = readUserSelection();
            switch (choice) {
                case 1 -> manageVocabMenu();
                case 2 -> manageGameMenu();
                case 3 -> manageUserMenu();
                case 4 -> {
                    manageUser.logoutUser(currentUser.getUserId());
                    showMainMenu();
                }
                case 5 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice!");
            }
            } catch (UserNotFoundException e) {
                System.out.println("User not found!");
            }
        } while (choice != 5);
        System.exit(0);
    }

    @Override
    public void manageUserMenu() {
        int choice = 0;

        do {
            System.out.println("Please choose an option:");
            System.out.println("[1] Create a new User");
            System.out.println("[2] Delete a User");
            System.out.println("[3] Return");
            choice = readUserSelection();
            switch (choice) {
                case 1 -> showRegister();
                case 2 -> showDeleteUser();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }

    @Override
    public void showDeleteUser() {
        try {
            System.out.println("Please enter the username of the user you want to delete: ");
            String username = readUserInput();
            User user = manageUser.getByName(username);
            manageUser.deleteUser(user.getUserId());
        } catch (UserNotFoundException e) {
            System.out.println("User not found!");
        }
    }

    @Override
    public void manageVocabMenu() {
        int choice = 0;

        do {
            System.out.println("Please choose an option:");
            System.out.println("[1] Import a new Vocab List");
            System.out.println("[2] Return");
            choice = readUserSelection();
            if (choice == 1) {
                showImportVocabList();
            } else {
                System.out.println("Invalid choice!");
            }
        } while (choice != 2);
    }

    @Override
    public void showImportVocabList() {
        int choice = 0;

        File folder = new File("textFiles");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("[" + (i + 1) + "] " + listOfFiles[i].getName());
            }
        }
        System.out.println("Please choose a file: ");
        choice = readUserSelection();
        File file;
        try {
            file = listOfFiles[choice - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid choice!");
            return;
        }
        try {
            manageVocab.parseVocabList(file);
        } catch (IOException | VocabDAOException e) {
            System.out.println("Could not import file!");
        }
    }

    @Override
    public void manageGameMenu() {
        int choice = 0;

        do {
            System.out.println("Please choose an option:");
            System.out.println("[1] Create a new Game");
            System.out.println("[2] Continue playing a game");
            System.out.println("[3] Return");
            choice = readUserSelection();
            switch (choice) {
                case 1 -> showCreateGame();
                case 2 -> showGamesOfUser();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }

    @Override
    public void showCreateGame() {
        List<User> opponents;
        try {
            opponents = manageUser.getOpponents(currentUser.getUserId());
        } catch (UserNotFoundException e) {
            System.out.println("No opponents found!");
            return;
        }
        if (opponents.size() == 0) {
            System.out.println("Not enough users found to create a game!");
            return;
        }
        for (int i = 0; i < opponents.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + opponents.get(i).getUserName());
        }
        try {
            System.out.println("Please choose a user to play against: ");
            int index = readUserSelection();
            User opponent = opponents.get(index - 1);
            Game game = manageGame.createGame(currentUser.getUserId(), opponent.getUserId());
            showCreateRound(game);
        } catch (UserDoesNotExistException | UserNotFoundException e) {
            System.out.println("User not found!");
        }
    }

    @Override
    public void showCreateRound(Game game) {
        List<Category> categories;
        Round round;
        try {
            categories = manageVocab.getAllCategories();
        } catch (CategoryNotFoundException e) {
            System.out.println("Could not find any Categories!");
            return;
        }
        System.out.println("Please choose a category: ");
        while (true) {
            for (int i = 0; i < categories.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + categories.get(i).getCategoryName());
            }
            int index = readUserSelection();
            try {
                Category category = categories.get(index - 1);
                int roundNumber = manageGame.getLatestRoundForGame(game.getGameId()) + 1;
                manageGame.createRound(game.getGameId(), roundNumber, category.getCategoryId());
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice!");
            } catch (GameDoesNotExistException | CategoryNotFoundException | VocabNotFoundException |
                     GameQuestionDoesNotExistException | VocabListNotFoundException | TranslationNotFoundException e) {
                System.out.println("Could not create round!");
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
            System.out.println("No games found!");
            return;
        } catch (UserDoesNotExistException | UserNotFoundException e) {
            System.out.println("User not found!");
            return;
        }
        try {
            for (int i = 0; i < games.size(); i++) {
                System.out.print("[" + (i + 1) + "] " + games.get(i).getGameId());
                System.out.println(" - vs. " + manageUser.getById(games.get(i).getOtherUser(currentUser.getUserId())).getUserName());
            }
        } catch (UserNotFoundException e) {
            System.out.println("Could not find opponent for the game!");
        }
        while(true) {
            System.out.println("Please choose a game: ");
            int index = readUserSelection();
            try {
                game = games.get(index - 1);
                playRoundUI(game);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice!");
            }
        }
    }

    @Override
    public void playRoundUI(Game game) {
        Round round;
        try {
            round = manageGame.getOngoingRoundForGame(game.getGameId());
        } catch (RoundDoesNotExistException e) {
            System.out.println("Round not found!");
            return;
        }
        List<GameQuestion> gameQuestion;
        try {
            gameQuestion = manageGame.getGameQuestionsForRound(game.getGameId(), round.getRoundNumber());
        } catch (RoundDoesNotExistException e) {
            System.out.println("Round not found!");
            return;
        } catch (GameQuestionDoesNotExistException e) {
            System.out.println("No questions found for this round!");
            return;
        } catch (GameDoesNotExistException e) {
            System.out.println("Could not find the game for this round!");
            return;
        }
        // display all questions with their answers
        for (GameQuestion question : gameQuestion) {
            System.out.println(question.getVocab().getVocab());
            try {
                List<GameAnswer> gameAnswers = manageGame.getGameAnswersForGameQuestion(question.getGameQuestionId());
                for (int i = 0; i < gameAnswers.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + gameAnswers.get(i).getTranslation().getTranslation());
                }
                System.out.println("Please enter your answer: ");
                // display all answers for the question
                while(true) {
                    int index = readUserSelection();
                    try {
                        GameAnswer gameAnswer = gameAnswers.get(index - 1);
                        boolean isCorrect;
                        if (gameAnswer.getTranslation().getTranslationId() == question.getTrueAnswer().getTranslationId()) {
                            isCorrect = true;
                            System.out.println("Correct!");
                        } else {
                            isCorrect = false;
                            System.out.println("Wrong!");
                        }
                        manageGame.lockInAnswer(gameAnswer.getGameAnswerId(), currentUser.getUserId(), isCorrect);
                        break;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid choice!");
                    } catch (UserNotFoundException e) {
                        System.out.println("User not found!");
                    }
                }
            } catch (GameAnswerDoesNotExistException e) {
                System.out.println("Could not find game answers for this question!");
            }
        }
        // if the user started the round, set him as player1
        if (game.getUserStartingRound() == currentUser.getUserId()) {
            round.setPlayer1Answered(true);
            game.setCurrentUser(game.getOtherUser(currentUser.getUserId()));
            try {
                game = manageGame.updateGame(game);
            } catch (GameDoesNotExistException e) {
                System.out.println("Could not update game!");
                return;
            }
            System.out.println("Please wait for the other player to finish!");
        } else {
            round.setPlayer2Answered(true);
            game.setUserStartingRound(game.getOtherUser(currentUser.getUserId()));
        }
        try {
            round = manageGame.updateRound(round);
        } catch (RoundDoesNotExistException e) {
            System.out.println("Could not update round!");
        }
        try {
            game = manageGame.updateGame(game);
        } catch (GameDoesNotExistException e) {
            System.out.println("Could not update game!");
        }
        // if both players have answered, finish the round
        if (round.isPlayer1Answered() && round.isPlayer2Answered()) {
            try {
                manageGame.endRound(round.getRoundId());
            } catch (RoundDoesNotExistException e) {
                System.out.println("Could not end round!");
            }
            if (round.getRoundNumber() == 2) {
                try {
                    manageGame.endGame(game.getGameId());
                    showGameResults(game);
                } catch (GameDoesNotExistException e) {
                    System.out.println("Could not end game!");
                }
            } else {
                try {
                    game.setUserStartingRound(currentUser.getUserId());
                    game = manageGame.updateGame(game);
                    showCreateRound(game);
                } catch (GameDoesNotExistException e) {
                    System.out.println("Could not update game!");
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
            System.out.println("Could not find users for this game!");
            return;
        }
        try {
            int resultsUser1 = manageGame.getScoreForUser(game.getUser1Id(), game.getGameId());
            int resultsUser2 = manageGame.getScoreForUser(game.getUser2Id(), game.getGameId());
            System.out.println(user1.getUserName() + ": " + resultsUser1);
            System.out.println(user2.getUserName() + ": " + resultsUser2);
            if (resultsUser1 > resultsUser2) {
                System.out.println(user1.getUserName() + " won!");
            } else if (resultsUser1 < resultsUser2) {
                System.out.println(user2.getUserName() + " won!");
            } else {
                System.out.println("Draw!");
            }
        } catch (GameDoesNotExistException e) {
            System.out.println("No games found!");
        } catch (UserNotFoundException | UserDoesNotExistException e) {
            System.out.println("User not found!");
        } catch (RoundResultDoesNotExistException e) {
            System.out.println("No results found!");
        }
    }

    @Override
    public int readUserSelection() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Please enter your choice [1-9]: ");
            return input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number!");
            return readUserSelection();
        }
    }

    @Override
    public String readUserInput() {
        Scanner input = new Scanner(System.in);
        System.out.print("Input: ");
        return input.nextLine();
    }

}

