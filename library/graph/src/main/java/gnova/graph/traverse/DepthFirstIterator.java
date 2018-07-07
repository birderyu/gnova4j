package gnova.graph.traverse;

import java.util.*;

import gnova.graph.structure.Graphable;
import gnova.graph.util.GraphPath;
import gnova.graph.util.SimpleGraphPath;

/**
 * 单源深度优先遍历
 * @author birderyu
 *
 */
public class DepthFirstIterator<T extends Graphable>
	extends AbstractGraphIterator<T> {
	
	/**
	 * 存储当前的轨迹
	 * 当前的轨迹是一个栈
	 */
	private GraphPath<T> currentPath;
	
	/**
	 * 是否开始一条新的轨迹
	 */
	private boolean startTrace = true;
	
	public DepthFirstIterator() {
		super();
		currentPath = new SimpleGraphPath<T>();
		startTrace = true;
	}
	
	@Override
	public void initialize(T source) {
		super.initialize(source);
		nextSet.clear();
		currentPath.clear();
		nextSet.addLast(getSource());
		startTrace = true;
	}

	@Override
	public T next(GraphTracker<T> tracker) {
		while (!nextSet.isEmpty()) {
			// 从邻接集合中找到一个未访问的图元
			T next = nextSet.pollLast();
			if (!tracker.isWalked(next)) {
				if (startTrace) {
					// 开始一条新的轨迹，需要回滚当前的轨迹
					T last = currentPath.top();
					while (null != last) {
						if (next.relateTo(last)) {
							// 若next与last相临接，则回滚结束
							break;
						}
						// 将要素弹出
						currentPath.pop();
						last = currentPath.top();
					}
				}
				return next;
			}
		}
		return null;
	}

	@Override
	public void process(GraphTracker tracker, Graphable current, Collection nexts) {
		startTrace = true;
		for (Object o : nexts) {
			T component = (T) o;
			if (tracker.isWalked(component)) {
				continue;
			}
			nextSet.addLast(component);
			startTrace = false;
		}
	}
	
	@Override
	public void addToPath(T component, Collection relateds) {
		currentPath.push(component);
	}

	@Override
	public T getLast(T component) {
		return currentPath.top2();
	}

	@Override
	public GraphPath<T> getPath(T component) {
		return currentPath;
	}
}
