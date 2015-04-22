package nl.rudidevries.regexquiz;

import java.util.ListResourceBundle;

public class RegexQuizBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"bankQuestionLoaded", "There are now %d questions available."}
        };
    }
}
