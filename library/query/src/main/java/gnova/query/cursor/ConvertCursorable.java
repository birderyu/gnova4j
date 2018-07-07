package gnova.query.cursor;

import gnova.annotation.NotNull;

import java.util.function.Function;

public class ConvertCursorable<F, T>
        implements Cursorable<T> {

    private final Cursorable<F> cursorable;
    private final Function<F, T> converter;

    public ConvertCursorable(@NotNull Cursorable<F> cursorable,
                             @NotNull Function<F, T> converter) {
        this.cursorable = cursorable;
        this.converter = converter;
    }

    @Override
    public Cursor<T> cursor() {
        return new ConvertCursor<>(cursorable.cursor(), converter);
    }
}
