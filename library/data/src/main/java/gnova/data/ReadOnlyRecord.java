package gnova.data;

import gnova.annotation.NotNull;
import gnova.util.Dictionary;
import gnova.function.Getter;

import java.io.Serializable;

/**
 * 只读的记录
 */
public interface ReadOnlyRecord
        extends Keyer, Getter, Dictionary, Cloneable, Serializable {

    /**
     * 克隆该条记录
     *
     * @return 克隆的记录
     */
    @NotNull
    ReadOnlyRecord clone();

    /**
     * 将该记录转换为一个可写的记录
     *
     * @return 可写的记录
     */
    @NotNull
    Record toWritable();

}
