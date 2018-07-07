package gnova.data.index;

import gnova.annotation.Checked;
import gnova.annotation.NotNull;
import gnova.annotation.Unsupported;
import gnova.function.ObjectBuilder;

public interface UniqueIndexBuilder<E>
        extends ObjectBuilder<GeneralIndex<E>> {

    @NotNull
    UniqueIndex<E> build(boolean ordered, @Checked String ...fieldNames);

    @Override
    @Unsupported
    default UniqueIndex<E> build() {
        throw new UnsupportedOperationException("build without params");
    }

}