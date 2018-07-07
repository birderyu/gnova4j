package gnova.graph.io;

import gnova.graph.structure.Graph;

import java.io.IOException;

public interface GraphWriter {

    void write(Graph g) throws IOException;

}
