package gnova.graph.io;

import gnova.graph.structure.Graph;

import java.io.IOException;

public interface GraphReader {

    Graph read() throws IOException;

}
