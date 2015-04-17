package nl.rudidevries.regexquiz.question;

import java.util.*;

/**
 * The question bank is the container of all question during the running
 * of the program.
 *
 * Questions can be shuffled.
 */
public class QuestionBank {

    private List<Question> questions;
    Iterator<Question> questionIterator;

    /**
     * Constructor
     */
    public QuestionBank() {
        questions = new ArrayList<>();
    }

    /**
     * Add a question to the bank. If question is already in the list
     * the question will not be added.
     *
     * @param question Question
     * @return If question was added.
     */
    public synchronized boolean add(Question question) {
        return (!questions.contains(question)) && questions.add(question);
    }

    /**
     * Remove question from the bank.
     * @param question Question
     * @return If the question was removed.
     */
    public synchronized boolean remove(Question question) {
        return questions.remove(question);
    }

    /**
     * Shuffle the questions. Iterator is reset.
     */
    public synchronized void shuffle() {
        Collections.shuffle(questions);
        questionIterator = null;
    }

    /**
     * Get the next random question.
     * @return question
     */
    public synchronized Question getQuestion() {
        if (questionIterator == null) {
            questionIterator = questions.iterator();
        }

        if (questionIterator.hasNext()) {
            return questionIterator.next();
        }

        return null;
    }

    /**
     * Get the number of available questions.
     * @return size
     */
    public int size() {
        return questions.size();
    }
}
