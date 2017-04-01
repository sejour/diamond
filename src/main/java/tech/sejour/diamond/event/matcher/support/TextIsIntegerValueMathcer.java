package tech.sejour.diamond.event.matcher.support;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import java.util.regex.Pattern;

/**
 * Created by Shuka on 2017/03/31.
 */
public class TextIsIntegerValueMathcer implements EventMatcher {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("\\A[-]?[0-9]+\\z");

    public TextIsIntegerValueMathcer() {}

    @Override
    public boolean matching(Object arg) {
        if (arg instanceof MessageEvent) {
            arg = ((MessageEvent) arg).getMessage();
        }
        if (arg instanceof TextMessageContent) {
            arg = ((TextMessageContent) arg).getText();
        }
        if (arg instanceof String) {
            return INTEGER_PATTERN.matcher((String) arg).matches();
        }

        return false;
    }

}
