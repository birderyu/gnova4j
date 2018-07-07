package gnova.data.index;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.core.annotation.Unsupported;
import gnova.core.function.ObjectBuilder;

public interface GeneralIndexBuilder<E>
        extends ObjectBuilder<GeneralIndex<E>> {

    @NotNull
    GeneralIndex<E> build(boolean ordered, @Checked String ...fieldNames);

    @Override
    @Unsupported
    default GeneralIndex<E> build() {
        throw new UnsupportedOperationException("build without params");
    }

}
