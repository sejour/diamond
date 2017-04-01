package tech.sejour.diamond.event.matcher.annotation;

import tech.sejour.diamond.event.matcher.support.TextEqualsMatcher;

import java.lang.annotation.*;

/**
 * Created by Shuka on 2017/03/30.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MatcherImplementation(TextEqualsMatcher.class)
public @interface TextEquals {

    String[] value();

}
