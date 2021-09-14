package main.java;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInputUtil {

    // Unused
    public static int nextIntInRange(int min, int max) {
        Scanner input = new Scanner( System.in );
        int in = input.nextInt();
        while (!(in >= min && in <= max)) {
            in = input.nextInt();
        }
        return in;
    }

    // Asks for an integer in a range and displays messages
    public static int nextIntWithMessages(int min, int max, String prompt, String wrongAnswer) {
        Scanner input = new Scanner( System.in );
        System.out.print(prompt);
        int in = input.nextInt();
        while (!(in >= min && in <= max)) {
            System.out.print(wrongAnswer);
            in = input.nextInt();
        }
        return in;
    }

    // Lets the user pick from a list of actions
    public static int actionListPrompt(String prompt, String wrongAnswer, ArrayList<String> actions) {
        String actionDisplay = "";
        for (int i = 0; i < actions.size() - 1; i++) {
            actionDisplay += "[" + (i + 1) + "] " + actions.get(i) + ", ";

        }
        actionDisplay += "or [" + actions.size() + "] " + actions.get(actions.size() - 1);
        return nextIntWithMessages(1, actions.size(),  String.format(prompt, actionDisplay), String.format(wrongAnswer, actionDisplay));
    }
}
