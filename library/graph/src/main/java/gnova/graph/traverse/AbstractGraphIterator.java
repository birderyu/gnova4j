package gnova.graph.traverse;

import java.util.Deque;
import java.util.LinkedList;

import gnova.graph.structure.Graphable;

public abstract class AbstractGraphIterator<T extends Graphable>
	implements GraphIterator<T> {
	
	private T source;
	
	/**
	 * 邻接集合
	 * 邻接集合表示即将访问的图元的集合，邻接集合中的第一个元素，就是即将访问到的下一个图元
	 */
	protected Deque<T> nextSet;
	
	public AbstractGraphIterator() {
		nextSet = new LinkedList<T>();
	}

	@Override
	public void initialize(T source) {
		this.source = source;
	}

	@Override
	public T getSource() {
		return source;
	}

	@Override
	public void killBranch(GraphTracker<T> tracker, T current) {
		// do nothing
	}

}
