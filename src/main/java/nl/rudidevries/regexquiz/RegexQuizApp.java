package nl.rudidevries.regexquiz;


import nl.rudidevries.regexquiz.configuration.FileLoader;
import nl.rudidevries.regexquiz.configuration.ConfigFileWatcher;
import nl.rudidevries.regexquiz.question.Question;
import nl.rudidevries.regexquiz.question.QuestionBank;
import nl.rudidevries.regexquiz.util.AnsiColor;

import java.nio.file.*;
import java.util.ResourceBundle;

public class RegexQuizApp {

    public static void main(String[] args) {
        ResourceBundle regexQuizBundle = ResourceBundle.getBundle("nl.rudidevries.regexquiz.RegexQuizBundle");

        QuestionBank bank = new QuestionBank();
        FileLoader loader = initFileLoader(bank, regexQuizBundle);
        initConfigFileWatcher(loader);

        runQuestions(bank);
    }

    private static FileLoader initFileLoader(QuestionBank bank, final ResourceBundle regexQuizBundle) {
        FileLoader loader = new FileLoader(bank);

        loader.addEventHandler(new FileLoader.LoadedEventHandler() {
            @Override
            public void handle(QuestionBank bank) {
                System.out.println(AnsiColor.GREEN.apply(
                        String.format(regexQuizBundle.getString("bankQuestionLoaded"), bank.size())
                ));
            }
        });

        return loader;
    }

    private static void initConfigFileWatcher(FileLoader loader) {
        Path dir = Paths.get("./config");
        ConfigFileWatcher configFileWatcher = new ConfigFileWatcher(loader, dir);
        Thread fileThread = new Thread(configFileWatcher);
        fileThread.setDaemon(true);
        fileThread.start();
    }

    private static void runQuestions(QuestionBank bank) {
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
