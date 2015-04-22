package nl.rudidevries.regexquiz.question;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexQuestion that asks for the start positions of the
 * matches.
 */
public class RegexQuestionPosition implements RegexQuestion {

    private String regex;
    private String match;
    private String answer = "";

    /**
     * Constructor
     *
     * @param regex regex string.
     * @param match string to be matched.
     */
    public RegexQuestionPosition(String regex, String match) {
        this.regex = regex;
        this.match = match;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(match);

        while (matcher.find()) {
            answer += matcher.start() + " ";
        }
        answer = answer.trim();
    }

    /**
     * Answer the question. Return if answer was correct.
     *
     * @param answer The answer
     * @return correctly answered
     */
    public boolean answer(String answer) {
        return answer.equals(this.answer);
    }

    /**
     * Get the correct answer
     * @return correct answer
     */
    public String getCorrectAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        ResourceBundle questionBundle = ResourceBundle.getBundle("nl.rudidevries.regexquiz.question.QuestionBundle");

        return String.format(
                questionBundle.getString("regexQuestionPosition_question"),
                match,
                regex
        );
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof RegexQuestionPosition)
            && regex.equals(((RegexQuestionPosition) o).regex)
            && match.equals(((RegexQuestionPosition) o).match)
            && answer.equals(((RegexQuestionPosition) o).answer);
    }

    @Override
    public int hashCode() {
        return (regex.hashCode() + match.hashCode() + answer.hashCode()) ^ 31;
    }
}
