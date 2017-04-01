package tech.sejour.diamond.scene;

import lombok.Getter;
import org.springframework.context.ApplicationContext;
import tech.sejour.diamond.dialog.DialogClass;
import tech.sejour.diamond.dialog.DialogObject;

import java.lang.reflect.InvocationTargetException;

/**
 * シーンのインスタンスを表す
 */
public class SceneObject {

    final ApplicationContext applicationContext;

    final SceneClass sceneClass;
    final Scene scene;

    public final SceneObject parent;

    @Getter
    private DialogObject activeDialog;

    private SceneObject(ApplicationContext applicationContext, SceneObject parent, SceneClass sceneClass, Scene scene, Integer dialogId, Object... args) throws InvocationTargetException, IllegalAccessException {
        this.applicationContext = applicationContext;
        this.sceneClass = sceneClass;
        this.scene = scene;
        this.parent = parent;

        if (dialogId == null) {
            firstDialog(args);
        }
        else {
            jumpDialog(dialogId, args);
        }

        callEnteredMethod();
    }

    public static SceneObject newInstance(ApplicationContext applicationContext, String sender, SceneObject parent, SceneClass sceneClass, Integer dialogId, Object... args) throws InvocationTargetException, IllegalAccessException {
        return new SceneObject(applicationContext, parent, sceneClass, sceneClass.newInstance(applicationContext, sender), dialogId, args);
    }

    public String getSender() {
        return scene.getSender();
    }

    /**
     * Enteredメソッドを呼び出す
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void callEnteredMethod() throws InvocationTargetException, IllegalAccessException {
        sceneClass.callEnteredMethod(scene);
    }

    /**
     * Appearedメソッドを呼び出す
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void callAppearedMethod() throws InvocationTargetException, IllegalAccessException {
        sceneClass.callAppearedMethod(scene);
    }

    /**
     * 指定のIDのダイアログへジャンプする
     * @param id ダイアログID
     * @return
     */
    public SceneObject jumpDialog(int id, Object... args) {
        DialogClass dialogClass = sceneClass.getDialog(id);
        activeDialog = DialogObject.newInstance(applicationContext, scene, dialogClass, args);
        return this;
    }

    /**
     * 現在のダイアログIDの次のダイアログクラスへ遷移する
     * @return
     */
    public SceneObject nextDialog(Object... args) throws InvocationTargetException, IllegalAccessException {
        DialogClass dialogClass = sceneClass.getNextDialog(activeDialog);
        if (dialogClass == null) {
            callAppearedMethod();
            return parent == null ? null : parent.nextDialog();
        }

        activeDialog = DialogObject.newInstance(applicationContext, scene, dialogClass, args);

        return this;
    }

    /**
     * ファーストダイアログクラスへ遷移する
     * @return
     */
    public SceneObject firstDialog(Object... args) {
        DialogClass dialogClass = sceneClass.getFirstDialog();
        activeDialog = DialogObject.newInstance(applicationContext, scene, dialogClass, args);
        return this;
    }

    /**
     * シーンを終了する
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public SceneObject exit() throws InvocationTargetException, IllegalAccessException {
        callAppearedMethod();
        return parent == null ? null : parent.nextDialog();
    }

}
