package gnova.data.index;

import gnova.annotation.Checked;
import gnova.annotation.NotNull;
import gnova.annotation.Unsupported;
import gnova.function.ObjectBuilder;

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
