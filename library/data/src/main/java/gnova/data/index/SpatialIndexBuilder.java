package gnova.data.index;

import gnova.core.annotation.NotNull;
import gnova.core.annotation.Unsupported;
import gnova.core.function.ObjectBuilder;

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
