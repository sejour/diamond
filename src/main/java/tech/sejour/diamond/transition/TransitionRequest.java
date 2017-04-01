package tech.sejour.diamond.transition;

import tech.sejour.diamond.scene.SceneObject;
import tech.sejour.diamond.scene.SceneLoader;

import java.lang.reflect.InvocationTargetException;

/**
 * トランジションリクエスト
 */
public interface TransitionRequest {

    TransitionResult execute(SceneLoader sceneLoader, SceneObject currentScene) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException;

}
