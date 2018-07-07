package gnova.graph;

import gnova.graph.structure.Graph;
import gnova.graph.structure.Graphable;
import gnova.graph.structure.basic.BasicEdge;
import gnova.graph.structure.basic.BasicNode;
import gnova.graph.build.GraphBuilder;
import gnova.graph.build.basic.BasicGraphGenerator;
import gnova.graph.traverse.*;

import java.util.Collection;

public class App {

    /**
     * 光路
     */
    public static class Path {

        /**
         * 光缆的ID
         */
        private String[] cables;

        public Path(String... cables) {
            this.cables = cables;
        }

        public String[] getCables() {
            return cables;
        }

        public void setCables(String... cables) {
            this.cables = cables;
        }
    }

    public static class CableGraphGenerator
            implements BasicGraphGenerator {

        @Override
        public Graphable get(Object obj) {
            if (!(obj instanceof Path)) {

            }
            return null;
        }

        @Override
        public Graphable add(Object obj) {
            return null;
        }

        @Override
        public Graphable remove(Object obj) {
            return null;
        }

        public BasicNode get(String cableId) {
            // TODO
            return null;
        }

        @Override
        public GraphBuilder<BasicNode, BasicEdge> getGraphBuilder() {
            return null;
        }

        @Override
        public Graph<BasicNode, BasicEdge> build() {
            return null;
        }
    }

    public static void main(String[] args) {
        Path[] paths = new Path[] {
                new Path("x1", "x2", "x3"),
                new Path("x1", "x2", "x4"),
                new Path("x1", "x2", "x4")
        };

        CableGraphGenerator graphGenerator = new CableGraphGenerator();
        for (Path path : paths) {
            graphGenerator.add(path);
        }
        Graph graph = graphGenerator.build();

        // 构造一个追踪器
        final BasicNode end = graphGenerator.get("x3");

        GraphIterator iterator = new BreadthFirstIterator(); // 广度优先
        GraphWalker walker = new GraphWalker() {

            @Override
            public int walk(Graphable last,
                            Graphable current,
                            Collection nexts) {
                if (end.equals(current)) {
                    return GraphWalker.STOP_AND_COLLECT_TRACE;
                }
                return GraphWalker.PROCESS;
            }
        };

        GraphTracker tracker = new SimpleGraphTracker();
        tracker.beforeTrack();
        tracker.track(graphGenerator.get("x1"), iterator, walker);

    }

}
