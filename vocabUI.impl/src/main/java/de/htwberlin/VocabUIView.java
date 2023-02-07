package de.htwberlin;

import java.util.InputMismatchException;
import java.util.Scanner;

public class VocabUIView {

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

    public String readUserInput(String prompt) {
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        System.out.print("Input: ");
        return input.nextLine();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showMenu(String[] menuItems) {
        System.out.println("Please choose an option:");
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println("[" + (i + 1) + "] " + menuItems[i]);
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showMessageWithoutNewLine(String message) {
        System.out.print(message);
    }
}
