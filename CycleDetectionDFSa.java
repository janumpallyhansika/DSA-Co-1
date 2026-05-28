import java.util.*;

public class CycleDetectionDFSa {

    // Vertex colors
    static final int WHITE = 0; // Unvisited
    static final int GREY = 1;  // Currently visiting
    static final int BLACK = 2; // Fully processed

    private Map<String, List<String>> graph;
    private Map<String, Integer> color;

    public CycleDetectionDFS() {
        graph = new HashMap<>();
        color = new HashMap<>();
    }

    // Add job node
    public void addJob(String job) {
        graph.putIfAbsent(job, new ArrayList<>());
        color.put(job, WHITE);
    }

    // Add dependency edge
    public void addDependency(String from, String to) {
        graph.get(from).add(to);
    }

    // DFS function
    public boolean dfs(String node) {

        color.put(node, GREY);
        System.out.println("Visiting: " + node);

        Collections.sort(graph.get(node)); // alphabetical order

        for (String neighbor : graph.get(node)) {

            // If GREY → cycle found
            if (color.get(neighbor) == GREY) {
                System.out.println(
                    "Cycle detected at edge: " +
                    node + " -> " + neighbor
                );
                return true;
            }

            // Visit WHITE nodes
            if (color.get(neighbor) == WHITE) {
                if (dfs(neighbor))
                    return true;
            }
        }

        color.put(node, BLACK);
        System.out.println("Finished: " + node);

        return false;
    }

    public boolean detectCycle() {

        for (String node : graph.keySet()) {

            if (color.get(node) == WHITE) {

                if (dfs(node))
                    return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        CycleDetectionDFS workflow =
                new CycleDetectionDFS();

        // Jobs
        workflow.addJob("build");
        workflow.addJob("test");
        workflow.addJob("integration");
        workflow.addJob("deploy");

        // Dependencies
        workflow.addDependency("deploy", "build");
        workflow.addDependency("build", "test");
        workflow.addDependency("test", "integration");
        workflow.addDependency("integration", "build");

        boolean cycle =
                workflow.detectCycle();

        if (cycle)
            System.out.println("\nWorkflow Invalid: Cycle Found");
        else
            System.out.println("\nWorkflow Valid");
    }
}