package gnova.graph.traverse;

import gnova.graph.structure.DirectedGraphable;
import gnova.graph.util.GraphPath;

import java.util.Map;
import java.util.Objects;

public interface DirectedGraphTracker<T extends DirectedGraphable>
        extends GraphTracker<T> {

    /**
     * 单源正向追踪
     *
     * @param source
     */
    default void positiveTrack(T source,
                               GraphIterator<T> iterator,
                               DirectedGraphWalker<T> walker)  {
        positiveTrack(source, iterator, walker, null);
    }

    /**
     * 单源正向追踪并收集轨迹
     *
     * @param source
     * @param traces
     */
    void positiveTrack(T source,
                       GraphIterator<T> iterator,
                       DirectedGraphWalker<T> walker,
                       Map<T, GraphPath<T>> traces);

    /**
     * 多源正向追踪
     *
     * @param sources
     */
    default void positiveTrack(T[] sources,
                               GraphIterator<T> iterator,
                               DirectedGraphWalker<T> walker) {
        positiveTrack(sources, iterator, walker, null);
    }

    /**
     * 多源正向追踪并收集轨迹
     *
     * @param sources
     * @param traces
     */
    default void positiveTrack(T[] sources,
                               GraphIterator<T> iterator,
                               DirectedGraphWalker<T> walker,
                               Map<T, GraphPath<T>> traces) {
        Objects.requireNonNull(sources);
        for (T source : sources) {
            positiveTrack(source, iterator, walker, traces);
        }
    }

    /**
     * 单源逆向追踪
     *
     * @param source
     */
    default void negativeTrack(T source,
                               GraphIterator<T> iterator,
                               DirectedGraphWalker<T> walker) {
        negativeTrack(source, iterator, walker, null);
    }

    /**
     * 单源逆向追踪并收集轨迹
     *
     * @param source
     * @param traces
     */
    void negativeTrack(T source,
                       GraphIterator<T> iterator,
                       DirectedGraphWalker<T> walker,
                       Map<T, GraphPath<T>> traces);

    /**
     * 多源逆向追踪
     *
     * @param sources
     */
    default void negativeTrack(T[] sources,
                               GraphIterator<T> iterator,
                               DirectedGraphWalker<T> walker) {
        negativeTrack(sources, iterator, walker, null);
    }

    /**
     * 多源逆向追踪并收集轨迹
     *
     * @param sources
     * @param traces
     */
    default void negativeTrack(T[] sources,
                               GraphIterator<T> iterator,
                               DirectedGraphWalker<T> walker,
                               Map<T, GraphPath<T>> traces) {
        Objects.requireNonNull(sources);
        for (T source : sources) {
            negativeTrack(source, iterator, walker, traces);
        }
    }

}
