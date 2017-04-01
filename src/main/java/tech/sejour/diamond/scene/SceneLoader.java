package tech.sejour.diamond.scene;

import tech.sejour.diamond.scene.annotation.DialogScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import tech.sejour.diamond.error.DiamondRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * シーンをロードするためのクラス
 */
@Component
public class SceneLoader {

    final ApplicationContext applicationContext;

    private Map<Class<? extends Scene>, SceneClass> sceneClassMap = new HashMap<>();

    @Autowired
    public SceneLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * ディスカッションの最初のシーンをロードする
     * @param sender 送信ユーザ
     * @param targetSceneClass ロードするシーンクラス
     * @param dialogId ダイアログID (nullで最初のダイアログから始める)
     * @param args ダイアログインスタンス生成時の引数リスト
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public SceneObject loadFirstScene(String sender, Class<? extends Scene> targetSceneClass, Integer dialogId, Object... args) throws InvocationTargetException, IllegalAccessException {
        if (targetSceneClass.getAnnotation(DialogScript.class) == null) {
            throw new DiamondRuntimeException(String.format("Can not make bean of scene because %s is not given @DialogScript annotation.", targetSceneClass.getTypeName()));
        }

        // ロード済みのシーンクラスを取得する。ロードされていなければロードする。
        SceneClass sceneClass = sceneClassMap.get(targetSceneClass);
        if (sceneClass == null) {
            sceneClass = new SceneClass(targetSceneClass);
            sceneClassMap.put(targetSceneClass, sceneClass);
        }

        // シーンのインスタンスを生成する
        return SceneObject.newInstance(applicationContext, sender, null, sceneClass, dialogId, args);
    }

    /**
     * ディスカッションの最初のシーンをロードする
     * @param sender 送信ユーザ
     * @param targetSceneClass ロードするシーンクラス
     * @param args ダイアログインスタンス生成時の引数リスト
     * @return
     */
    public SceneObject loadFirstScene(String sender, Class<? extends Scene> targetSceneClass, Object... args) throws InvocationTargetException, IllegalAccessException {
        return loadFirstScene(sender, targetSceneClass, null, args);
    }

    /**
     * シーンをロードする
     * @param currentScene 現時点まで実行していたシーン
     * @param targetSceneClass 遷移先のシーンクラス
     * @param called 呼び出しモードかどうか
     * @param dialogId ダイアログID (途中のダイアログから始める場合に指定)
     * @param args ダイアログインスタンス生成時の引数リスト
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public SceneObject loadScene(SceneObject currentScene, Class<? extends Scene> targetSceneClass, boolean called, Integer dialogId, Object... args) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        if (targetSceneClass.getAnnotation(DialogScript.class) == null) {
            throw new DiamondRuntimeException(String.format("Can not make bean of scene because %s is not given @DialogScript annotation.", targetSceneClass.getTypeName()));
        }

        // ロード済みのシーンクラスを取得する。ロードされていなければロードする。
        SceneClass sceneClass = sceneClassMap.get(targetSceneClass);
        if (sceneClass == null) {
            sceneClass = new SceneClass(targetSceneClass);
            sceneClassMap.put(targetSceneClass, sceneClass);
        }

        // シーンのインスタンスを生成する
        return SceneObject.newInstance(applicationContext, currentScene.getSender(), called ? currentScene : null, sceneClass, dialogId, args);
    }

    /**
     * シーンをロードする
     * @param currentScene 現時点まで実行していたシーン
     * @param targetSceneClass 遷移先のシーンクラス
     * @param called 呼び出しモードかどうか
     * @param args ダイアログインスタンス生成時の引数リスト
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public SceneObject loadScene(SceneObject currentScene, Class<? extends Scene> targetSceneClass, boolean called, Object... args) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        return loadScene(currentScene, targetSceneClass, called, null, args);
    }

}
