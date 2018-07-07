package gnova.graph.traverse;

import gnova.graph.structure.Graphable;

import java.util.Collection;

/**
 * 图步行者
 *
 * 图步行者的作用是在一条图追踪的轨迹中行走，它包含一个walk方法，其作用为
 * 根据当前轨迹中的上一个单元和当前单元，判断接下来行走的方向。
 * 相当于一个回调函数
 * 
 * @author birderyu
 *
 */
@FunctionalInterface
public interface GraphWalker<T extends Graphable> {

    /**
     * 收集当前单元的轨迹
     */
    int COLLECT_TRACE = 1;

    /**
     * 继续向下追踪
     */
    int PROCESS = 2;

    /**
     * 杀掉当前分支
     */
    int KILL_BRANCH = 4;

    /**
     * 立刻停止追踪
     */
    int STOP = 8;

    /**
     * 继续向下追踪
     * 并收集当前单元的轨迹
     */
    int PROCESS_AND_COLLECT_TRACE = PROCESS | COLLECT_TRACE;

    /**
     * 杀掉当前分支
     * 并收集当前单元的轨迹
     */
    int KILL_BRANCH_AND_COLLECT_TRACE = KILL_BRANCH | COLLECT_TRACE;

    /**
     * 立刻停止追踪
     * 并收集当前单元的轨迹
     */
    int STOP_AND_COLLECT_TRACE = STOP | COLLECT_TRACE;
	
	/**
	 * 在当前的行走轨迹中，根据访问当前单元，并根据上一个单元与当前单元，判断下一步将要访问的单元
	 *
	 * @param last 当前单元在当前轨迹中的上一个单元
	 * @param current 当前单元
	 * @param nexts 即将访问的单元，由用户指定
	 * @return 判断是否需要继续行走
	 */
    int walk(T last, T current, Collection<T> nexts);
}
