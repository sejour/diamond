package tech.sejour.diamond.scene;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * シーンを表すクラスの基底クラス
 */
public abstract class Scene {

    @Getter
    @Setter(AccessLevel.MODULE)
    private String sender;

}
