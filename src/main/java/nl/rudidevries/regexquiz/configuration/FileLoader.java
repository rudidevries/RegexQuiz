package nl.rudidevries.regexquiz.configuration;

import nl.rudidevries.regexquiz.question.Question;
import nl.rudidevries.regexquiz.question.QuestionBank;
import nl.rudidevries.regexquiz.question.RegexQuestionPosition;
import nl.rudidevries.regexquiz.util.AnsiColor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileLoader class is responsible for reading question configuration
 * from a file, and adding the questions to the question bank.
 */
public class FileLoader {

    private QuestionBank bank;

    private List<EventHandler> eventHandlers;

    /**
     * Constructor
     *
     * @param bank QuestionBank to be filled by this FileLoader instance.
     */
    public FileLoader(QuestionBank bank) {
        this.bank = bank;
        eventHandlers = new ArrayList<>();
    }

    /**
     * Add an event handler
     * @param handler An event handler
     */
    public void addEventHandler(EventHandler handler) {
        eventHandlers.add(handler);
    }

    /**
     * Private interface to mark EventHandlers
     */
    private interface EventHandler {}

    /**
     * LoadedEventHandler interface
     *
     * EventHandlers of this type can be added, and will be called
     * when new Questions are loaded into the bank.
     */
    public interface LoadedEventHandler extends EventHandler {
        void handle(QuestionBank bank);
    }

    /**
     * Read the file, create questions and add them to the question bank.
     * @param questionFile path of the file.
     */
    public void readFile(Path questionFile) throws IOException {

        try(FileReader reader = new FileReader(questionFile.toFile()); BufferedReader buffer = new BufferedReader(reader)) {
            if (!hasCorrectFileFormat(buffer.readLine())) {
                throw new IOException();
            }

            String line;
            while ((line = buffer.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length != 2) {
                    throw new IOException();
                }

                Question question = new RegexQuestionPosition(
                        parts[1],
                        parts[0]
                );
                bank.add(question);
            }
        }

        bank.shuffle();
        triggerLoadedEvent();
    }

    /**
     * Check if the file has a correct file format. The first line of the
     * file should contain some information on how the file should be parsed.
     *
     * @param firstLine contents of the first line of the file.
     * @return the format is correct.
     */
    private boolean hasCorrectFileFormat(String firstLine) {
        return true;
    }

    /**
     * Trigger loaded event. This event gets triggered when
     * new questions are added to the bank.
     */
    private void triggerLoadedEvent() {
        for (EventHandler handler: eventHandlers) {
            if (handler instanceof LoadedEventHandler) {
                ((LoadedEventHandler)handler).handle(bank);
            }
        }
    }

}
