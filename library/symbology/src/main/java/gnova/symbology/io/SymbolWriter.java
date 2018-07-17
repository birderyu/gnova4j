package gnova.symbology.io;

import gnova.symbology.Symbol;

public interface SymbolWriter {

    <T> T write(Symbol symbol, Class<? extends T> clazz);

}
