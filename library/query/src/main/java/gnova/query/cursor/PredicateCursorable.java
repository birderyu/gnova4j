package gnova.query.cursor;

import gnova.core.annotation.NotNull;

import java.util.function.Predicate;

public class PredicateCursorable<T>
        implements Cursorable<T> {

    private final Cursorable<T> cursorable;
    private final Predicate<T> predicate;

    public PredicateCursorable(@NotNull Cursorable<T> cursorable,
                               @NotNull Predicate<T> predicate) {
        this.cursorable = cursorable;
        this.predicate = predicate;
    }

    @Override
    public Cursor<T> cursor() {
        return new PredicateCursor<>(cursorable.cursor(), predicate);
    }
}
