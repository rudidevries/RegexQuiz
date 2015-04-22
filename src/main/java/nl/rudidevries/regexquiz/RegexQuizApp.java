package nl.rudidevries.regexquiz;


import nl.rudidevries.regexquiz.configuration.FileLoader;
import nl.rudidevries.regexquiz.configuration.ConfigFileWatcher;
import nl.rudidevries.regexquiz.question.Question;
import nl.rudidevries.regexquiz.question.QuestionBank;
import nl.rudidevries.regexquiz.util.AnsiColor;

import java.nio.file.*;

public class RegexQuizApp {

    public static void main(String[] args) {
        QuestionBank bank = new QuestionBank();
        FileLoader loader = new FileLoader(bank);
        Path dir = Paths.get("./config");

        ConfigFileWatcher configFileWatcher = new ConfigFileWatcher(loader, dir);
        Thread fileThread = new Thread(configFileWatcher);
        fileThread.setDaemon(true);
        fileThread.start();

        Question question;
        while((question = bank.getQuestion()) != null) {
            System.out.println(question);
            String answer = System.console().readLine();
            if (question.answer(answer)) {
                System.out.println(AnsiColor.GREEN.apply("Correct!"));
            }
            else {
                System.out.println(AnsiColor.RED.apply("Incorrect!") + " The correct answer is [ " + question.getCorrectAnswer() + " ]");
            }
        }

    }
}
