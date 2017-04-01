package tech.sejour.diamond.event.matcher.annotation;

import tech.sejour.diamond.event.matcher.support.EventMatcher;

import java.lang.annotation.*;

/**
 * Created by Shuka on 2017/03/30.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MatcherImplementation {

    Class<? extends EventMatcher> value();

}
