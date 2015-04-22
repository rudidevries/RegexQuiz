package nl.rudidevries.regexquiz.question;

import java.util.ListResourceBundle;

public class QuestionBundle extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"regexQuestionPosition_question", "Regex positions: what are the starting position for all matches? (separate with one space):\n [ %s ] matched with [ %s ]"},
        };
    }
}
