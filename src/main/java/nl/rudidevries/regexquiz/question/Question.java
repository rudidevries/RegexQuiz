package nl.rudidevries.regexquiz.question;

/**
 * Question interface.
 */
public interface Question {
    /**
     * Give the answer to the question and determine if it was right.
     * @param answer The answer
     * @return Answered correctly
     */
    boolean answer(String answer);

    /**
     * Get the correct answer to the question.
     * @return The answer
     */
    String getCorrectAnswer();
}
