package tech.sejour.diamond.event.matcher.support;

import tech.sejour.diamond.util.Pair;
import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.event.matcher.annotation.MatcherImplementation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodEventMatcherSupport {

    private final List<EventMatcher> matchers;

    public MethodEventMatcherSupport(Method method) {
        this.matchers = Arrays.stream(method.getAnnotations())
                .map(annotation -> {
                    // メソッドアノテーションからMatcherImplementationを取得
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    MatcherImplementation matcherImplementation = annotationType.getAnnotation(MatcherImplementation.class);
                    if (matcherImplementation == null) return null;

                    // Mathcerクラスを取得
                    Class matcherClass = matcherImplementation.value();

                    // Matcherクラスのコンストラクタを取得
                    List<Pair<Constructor, Class[]>> matcherConstructors = Arrays.stream(matcherClass.getConstructors())
                            .map(constructor -> new Pair<>(constructor, constructor.getParameterTypes()))
                            .collect(Collectors.toList());

                    if (matcherConstructors.size() != 1) throw new DiamondRuntimeException(String.format("Unique constructor of matcher class (%s) is not found.", matcherClass.getTypeName()));

                    // Matcherクラスのインスタンスを生成
                    // Mathcerクラスのコンストラクタは引数無しか、対象のアノテーションを1つ受け取る。
                    Pair<Constructor, Class[]> matcherConstructor = matcherConstructors.get(0);
                    if (matcherConstructor.snd.length == 0) {
                        try {
                            return (EventMatcher) matcherConstructor.fst.newInstance();
                        } catch (Throwable e) {
                            throw new DiamondRuntimeException(e);
                        }
                    }
                    else if (matcherConstructor.snd.length == 1 && matcherConstructor.snd[0] == annotationType) {
                        try {
                            return (EventMatcher) matcherConstructor.fst.newInstance(annotation);
                        } catch (Throwable e) {
                            throw new DiamondRuntimeException(e);
                        }
                    }

                    throw new DiamondRuntimeException(String.format("Constructor of matcher class (%s) must has zero or one argment that type is target annotation.", matcherClass.getTypeName()));
                })
                .filter(matcher -> matcher != null)
                .collect(Collectors.toList());
    }

    public boolean matching(Object arg) {
        return matchers.stream().allMatch(matcher -> matcher.matching(arg));
    }

}
