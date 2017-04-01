package tech.sejour.diamond.event.matcher.support;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import tech.sejour.diamond.event.matcher.annotation.TextRegexEquals;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Shuka on 2017/03/30.
 */
public class TextRegexEqualsMatcher implements EventMatcher {

    private final List<Pattern> patterns;

    public TextRegexEqualsMatcher(TextRegexEquals annotation) {
        this.patterns = Arrays.stream(annotation.value())
                            .map(Pattern::compile)
                            .collect(Collectors.toList());
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
            return patterns.stream().anyMatch(pattern -> pattern.matcher(text).matches());
        }

        return false;
    }

}
