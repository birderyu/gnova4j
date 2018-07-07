package gnova.query.cursor;

import gnova.core.annotation.NotNull;
import gnova.core.function.Action;

public class IterableCursorable<T>
        implements Cursorable<T> {

    private final Iterable<T> iterable;
    private final Action closeAction;

    public IterableCursorable(@NotNull Iterable<T> iterable) {
        this(iterable, null);
    }

    /**
     *
     * @param iterable
     * @param closeAction 可以为null
     */
    public IterableCursorable(@NotNull Iterable<T> iterable, Action closeAction) {
        this.iterable = iterable;
        this.closeAction = closeAction;
    }

    @Override
    public Cursor<T> cursor() {
        return new IteratorCursor<>(iterable.iterator(), closeAction);
    }
}
