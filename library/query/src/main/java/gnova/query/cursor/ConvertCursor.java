package gnova.query.cursor;

import gnova.core.annotation.NotNull;
import gnova.core.ConvertIterator;

import java.util.function.Function;

public class ConvertCursor<F, T>
        extends ConvertIterator<F, T> implements Cursor<T> {

    public ConvertCursor(@NotNull Cursor<F> cursor,
                         @NotNull Function<? super F, ? extends T> converter) {
        super(cursor, converter);
    }

    @Override
    public void close() {
        ((Cursor<F>) iterator).close();
    }
}
