package tech.sejour.diamond.scene;

import org.springframework.context.ApplicationContext;
import tech.sejour.diamond.dialog.DialogClass;
import tech.sejour.diamond.dialog.DialogObject;
import tech.sejour.diamond.dialog.annotation.Dialog;
import tech.sejour.diamond.error.DiamondRuntimeException;
import tech.sejour.diamond.scene.annotation.Appeared;
import tech.sejour.diamond.scene.annotation.DialogScript;
import tech.sejour.diamond.scene.annotation.Entered;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * シーンクラスを表す
 */
public class SceneClass {

    public final Class<? extends Scene> sceneClass;

    private final Method enterdMethod;
    private final Method appearedMethod;

    private List<DialogClass> dialogClasses;
    private Map<Integer, Integer> idSequenceMap = new HashMap<>();

    public SceneClass(Class<? extends Scene> sceneClass) {
        if (sceneClass.getAnnotation(DialogScript.class) == null) {
            throw new DiamondRuntimeException(String.format("Can not make bean of scene because %s is not given @DialogScript annotation.", sceneClass.getTypeName()));
        }
        this.sceneClass = sceneClass;

        Method[] methods = this.sceneClass.getDeclaredMethods();

        // @Entered
        List<Method> enteredMethods = Arrays.stream(methods).filter(method -> method.getAnnotation(Entered.class) != null).collect(Collectors.toList());
        if (enteredMethods.size() > 1) throw new DiamondRuntimeException(String.format("Method that is given @Entered must be unique. [%s]", sceneClass.getTypeName()));
        this.enterdMethod = enteredMethods.isEmpty() ? null : enteredMethods.get(0);

        // @Appeared
        List<Method> appearedMethods = Arrays.stream(methods).filter(method -> method.getAnnotation(Appeared.class) != null).collect(Collectors.toList());
        if (appearedMethods.size() > 1) throw new DiamondRuntimeException(String.format("Method that is given @Appeared must be unique. [%s]", sceneClass.getTypeName()));
        this.appearedMethod = appearedMethods.isEmpty() ? null : appearedMethods.get(0);

        // read dialog classes
        this.dialogClasses = readDialogClasses(this.sceneClass);

        // generate conversion map
        int size = dialogClasses.size();
        for (int i = 0; i < size; ++i) {
            idSequenceMap.put(this.dialogClasses.get(i).id, i);
        }
    }

    private static List<DialogClass> readDialogClasses(Class<? extends Scene> sceneClass) {
        // extract dialog classes
        List<DialogClass> dialogClasses = Arrays.stream(sceneClass.getDeclaredClasses())
                .filter(dialogClass -> dialogClass.getAnnotation(Dialog.class) != null)
                .map(DialogClass::new)
                .sorted((dialogClass1, dialogClass2) -> Integer.compare(dialogClass1.id, dialogClass2.id))
                .collect(Collectors.toList());

        if (dialogClasses.isEmpty()) {
            throw new DiamondRuntimeException("There are no dialogs in " + sceneClass.getName() + " scene class.");
        }

        return dialogClasses;
    }

    /**
     * インスタンスを生成する
     * @param applicationContext
     * @return
     */
    public Scene newInstance(ApplicationContext applicationContext, String sender) {
        Scene scene = applicationContext.getBean(sceneClass);
        scene.setSender(sender);
        return scene;
    }

    /**
     * Enteredメソッドを呼び出す
     * @param instance
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void callEnteredMethod(Scene instance) throws InvocationTargetException, IllegalAccessException {
        if (enterdMethod != null) {
            enterdMethod.invoke(instance);
        }
    }

    /**
     * Appearedメソッドを呼び出す
     * @param instance
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void callAppearedMethod(Scene instance) throws InvocationTargetException, IllegalAccessException {
        if (appearedMethod != null) {
            appearedMethod.invoke(instance);
        }
    }

    /**
     * ダイアログIDを指定してダイアログクラスを取得する
     * @param id ダイアログID
     * @return
     */
    public DialogClass getDialog(int id) {
        Integer sequenceNumber = idSequenceMap.get(id);
        if (sequenceNumber == null) throw new DiamondRuntimeException(String.format("A dialog with the specified id=%d does not exist.", id));
        return dialogClasses.get(sequenceNumber);
    }

    /**
     * 現在のダイアログIDの次のダイアログクラスを取得する
     * @param activeDialog 現在のダイアログ
     * @return
     */
    public DialogClass getNextDialog(DialogObject activeDialog) {
        if (activeDialog == null) return null;

        int sequenceNumber = idSequenceMap.get(activeDialog.getDialogId()) + 1;
        if (sequenceNumber >= dialogClasses.size()) return null;

        return dialogClasses.get(sequenceNumber);
    }

    /**
     * ファーストダイアログクラスを取得する
     * @return
     */
    public DialogClass getFirstDialog() {
        return dialogClasses.get(0);
    }

}
