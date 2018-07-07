package gnova.graph.util;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import gnova.graph.structure.Graphable;

public class SimpleGraphPath<T extends Graphable>
	implements GraphPath<T> {

	private Deque<T> components;

	public SimpleGraphPath() {
		components = new LinkedList<T>();
	}

	public SimpleGraphPath(T component) {
		this();
		push(component);
	}

	public SimpleGraphPath(GraphPath<T> path) {
		this();
		append(path);
	}

	@Override
	public boolean isEmpty() {
		return components.isEmpty();
	}

	@Override
	public int size() {
		return components.size();
	}

	@Override
	public void push(T component) {
		components.addLast(component);
	}

	@Override
	public T pop() {
		return components.pollLast();
	}

	@Override
	public void append(GraphPath<T> path) {
		for (T component : path) {
			push(component);
		}
	}

	
	@Override
	public T top() {
		return components.peekLast();
	}
	
	@Override
	public T top2() {
		T top = pop();
		T top2 = top();
		if (null != top) {
			push(top);
		}
		return top2;
	}

	@Override
	public void clear() {
		components.clear();
	}

	@Override
	public Iterator<T> iterator() {
		return components.iterator();
	}
	
}
