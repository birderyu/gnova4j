package gnova.symbology;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Coordinate;

/**
 * 符号
 */
public interface Symbol {

    /**
     * 设置符号的原点
     *
     * @return
     */
    @NotNull
    Coordinate getOrigin();

    /**
     * 获取符号的原点
     *
     * @param origin
     */
    void setOrigin(@NotNull Coordinate origin);

}
