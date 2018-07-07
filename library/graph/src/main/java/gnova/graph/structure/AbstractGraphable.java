package gnova.graph.structure;

import java.util.Objects;

/**
 * 抽象图单元
 * 
 * @author birderyu
 * @date 2017/4/22
 */
public abstract class AbstractGraphable
    implements Graphable {

    /**
     * 图单元所关联的对象
     */
    private Object object = null;

    /**
     * 当前图单元的ID
     */
    private final int id;

    /**
     * 当前图单元的权重
     */
    private int weight = 1;

    public AbstractGraphable(int id) {
        this.id = id;
    }

    @Override
    public Object getObject() {
        return this.object;
    }

    @Override
    public void setObject(Object obj) {
        this.object = obj;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractGraphable that = (AbstractGraphable) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + "[" + object + "]";
    }

}
