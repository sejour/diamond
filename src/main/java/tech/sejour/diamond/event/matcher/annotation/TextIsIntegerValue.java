package tech.sejour.diamond.event.matcher.annotation;

import tech.sejour.diamond.event.matcher.support.TextIsIntegerValueMathcer;

import java.lang.annotation.*;

/**
 * Created by Shuka on 2017/03/30.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MatcherImplementation(TextIsIntegerValueMathcer.class)
public @interface TextIsIntegerValue {
}
