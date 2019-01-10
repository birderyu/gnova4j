package gnova.graph.traverse;

import java.util.*;

import gnova.graph.structure.Graphable;
import gnova.graph.util.GraphPath;
import gnova.graph.util.SimpleGraphPath;

/**
 * 单源广度优先遍历
 * 广度优先遍历内部维护多条轨迹
 * 
 * @author birderyu
 *
 */
public class BreadthFirstIterator<T extends Graphable>
	extends AbstractGraphIterator<T> {
	
	/**
	 * 存储轨迹
	 */
	private Map<T, GraphPath<T>> paths;
	
	/**
	 * 存储轨迹的长度
	 */
	private Map<T, Integer> pathLengths;
	
	public BreadthFirstIterator() {
		super();
		paths = new HashMap<>();
		pathLengths = new HashMap<>();
	}
	
	@Override
	public void initialize(T source) {
		super.initialize(source);
		nextSet.clear();
		nextSet.addLast(getSource());
		paths.clear();
		pathLengths.clear();
	}
	
	@Override
	public T next(GraphTracker<T> tracker) {
		while (!nextSet.isEmpty()) {
			// 从邻接集合中找到一个未访问的图元
			T next = nextSet.pollFirst();
			if (!tracker.isWalked(next)) {
				return next;
			}
		}
		return null;
	}
	
	@Override
	public void process(GraphTracker<T> tracker, T current, Collection nexts) {
		for (Object o : nexts) {
			T component = (T) o;
			if (tracker.isWalked(component)) {
				continue;
			}
			nextSet.addLast(component);
		}
	}

    @Override
    public void addToPath(T component, Collection relateds) {
        if (getPath(component) != null) {
            return;
        }

        // 找到与之相邻的最短轨迹
        List<GraphPath<T>> relatedPaths = new ArrayList<GraphPath<T>>();
        List<Integer> relatedPathLengths = new ArrayList<Integer>();
        List<Integer> relatedWeights = new ArrayList<Integer>();

        int mixPathLength = -1;
        if (relateds != null && !relateds.isEmpty()) {
            for (Object o : relateds) {
                T related = (T) o;
                GraphPath<T> path = getPath(related);
                if (path == null) {
                    continue;
                }
                int pathLength = getLength(related);

                if (mixPathLength == -1) {
                    mixPathLength = pathLength;
                } else {
                    mixPathLength = pathLength < mixPathLength ? pathLength : mixPathLength;
                }
                relatedPaths.add(path);
                relatedPathLengths.add(pathLength);
                relatedWeights.add(related.getWeight());
            }
        }

        if (mixPathLength == -1) {
            paths.put(component, new SimpleGraphPath<T>(component));
            pathLengths.put(component, 0);
            return;
        }

        // 从最短的轨迹中选出一条最优轨迹
        GraphPath<T> bestPath = null;
        int minWeight = -1;
        for (int i = 0; i < relatedPaths.size(); i++) {
            if (mixPathLength == relatedPathLengths.get(i)) {
                if (minWeight == -1) {
                    minWeight = relatedWeights.get(i);
                    bestPath = relatedPaths.get(i);
                } else {
                    if (minWeight > relatedWeights.get(i)) {
                        minWeight = relatedWeights.get(i);
                        bestPath = relatedPaths.get(i);
                    }
                }
            }
        }

        GraphPath<T> newPath = new SimpleGraphPath<T>(bestPath);
        newPath.push(component);
        int newPathLength = mixPathLength + component.getWeight();
        paths.put(component, newPath);
        pathLengths.put(component, newPathLength);
    }

	@Override
	public T getLast(T component)
			throws IllegalArgumentException {
		GraphPath<T> path = getPath(component);
		return path.top2();
	}
	
	@Override
	public GraphPath<T> getPath(T component)
			throws IllegalArgumentException {
		if (null == component) {
			throw new IllegalArgumentException("component should not be null.");
		}
		return paths.get(component);
	}
	
	private int getLength(T component)
			throws IllegalArgumentException {
		if (null == component) {
			throw new IllegalArgumentException("component should not be null.");
		}
		return pathLengths.get(component);
	}
}
