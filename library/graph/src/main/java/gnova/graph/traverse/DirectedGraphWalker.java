package gnova.graph.traverse;

import gnova.graph.structure.DirectedGraphable;

/**
 * 有向图步行者
 *
 * @author birderyu
 *
 */
@FunctionalInterface
public interface DirectedGraphWalker<T extends DirectedGraphable>
        extends GraphWalker<T> {

}
