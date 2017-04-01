package tech.sejour.diamond.event.matcher.support;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import tech.sejour.diamond.event.matcher.annotation.TextEquals;

import java.util.Arrays;

/**
 * Created by Shuka on 2017/03/30.
 */
public class TextEqualsMatcher implements EventMatcher {

    private final String[] targets;

    public TextEqualsMatcher(TextEquals annotation) {
        this.targets = annotation.value();
    }

    @Override
    public boolean matching(Object arg) {
        if (arg instanceof MessageEvent) {
            arg = ((MessageEvent) arg).getMessage();
        }
        if (arg instanceof TextMessageContent) {
            arg = ((TextMessageContent) arg).getText();
        }
        if (arg instanceof String) {
            final String text = (String) arg;
            return Arrays.stream(targets).anyMatch(target -> target.equals(text));
        }

        return false;
    }

}
