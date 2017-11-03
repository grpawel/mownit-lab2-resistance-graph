import java.util.Scanner;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */

public class GraphReader {
    private final String filePath;

    public GraphReader(final String filePath) {
        this.filePath = filePath;
    }

    public Graph readFromFile() {
        final Graph graph = new Graph();
        final Scanner scanner = new Scanner(filePath);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            final String[] splitLine = line.split(" ");
            final String firstNode = splitLine[0];
            final String secondNode = splitLine[1];
            final double value = Double.parseDouble(splitLine[2]);
            final Edge.Type type = scanner.hasNextLine() ? Edge.Type.RESISTANCE : Edge.Type.EMF;
            final Edge edge = new Edge(type, value);
            graph.addEdge(firstNode, secondNode, edge);
        }
        return graph;
    }
}
