package gnova.data.index;

import gnova.annotation.NotNull;
import gnova.annotation.Unsupported;
import gnova.function.ObjectBuilder;

public interface SpatialIndexBuilder<E>
        extends ObjectBuilder<SpatialIndex<E>> {

    @NotNull
    SpatialIndex<E> build(@NotNull String fieldName);

    @Override
    @Unsupported
    default SpatialIndex<E> build() {
        throw new UnsupportedOperationException("build without params");
    }

}
