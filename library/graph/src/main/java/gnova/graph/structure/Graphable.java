package gnova.graph.structure;

import java.util.Collection;

/**
 * 图中的一个单元
 *
 * <p>图中的单元仅有两种结构：{@link Node 节点}和{@link Edge 边}
 *
 * @see Node
 * @see Edge
 * @author birderyu
 * @date 2017/4/22
 */
public interface Graphable {

    /**
     * 获取当前网络单元关联的对象
     *
     * @return
     */
    Object getObject();

    /**
     * 设置当前网络单元关联的对象
     *
     * @param obj
     */
    void setObject(Object obj);

    /**
     * 获取当前网络单元所邻接的单元
     *
     * @return
     */
    Collection<? extends Graphable> getRelated();

    /**
     * 是否与单元有邻接关系
     *
     * @param component
     * @return
     */
    boolean relateTo(Graphable component);
    
    /**
     * 获取当前单元的权重
     *
     * @return
     */
    int getWeight();

    /**
     * 设置当前单元的权重
     *
     * @param weight
     */
    void setWeight(int weight);

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);

}
