/*
John Benedict Labor
 */
package a;

import java.util.*;
import java.io.*;

public class Graph {
    private Map<String, List<String>> graph;

    // Constructor
    public Graph() {
        this.graph = new HashMap<>();
    }

    // Add an edge to the graph
    public void addEdge(String u, String v) {
        graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
    }

    // Construct graph from keyboard input
    public void constructGraphFromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of edges: ");
        int numEdges = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        for (int i = 0; i < numEdges; i++) {
            System.out.print("Enter edge (from to): ");
            String[] edge = scanner.nextLine().split(" ");
            addEdge(edge[0], edge[1]);
        }
    }

    // Construct graph from file
    public void constructGraphFromFile(String filePath) {
        try (Scanner scanner = new Scanner(new java.io.File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] edge = scanner.nextLine().split(" ");
                addEdge(edge[0], edge[1]);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    // Depth-First Search (DFS)
    public void dfs(String node, Set<String> visited) {
        if (!visited.contains(node)) {
            System.out.println("Visited " + node);
            visited.add(node);
            List<String> neighbors = graph.getOrDefault(node, new ArrayList<>());
            for (String neighbor : neighbors) {
                dfs(neighbor, visited);
            }
        }
    }

    // Breadth-First Search (BFS)
    public void bfs(String startNode) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            if (!visited.contains(node)) {
                System.out.println("Visited " + node);
                visited.add(node);
                queue.addAll(graph.getOrDefault(node, new ArrayList<>()));
            }
        }
    }

    // Traverse the graph
    public void traverse(String startNode, String method) {
        if (method.equalsIgnoreCase("dfs")) {
            Set<String> visited = new HashSet<>();
            dfs(startNode, visited);
        } else if (method.equalsIgnoreCase("bfs")) {
            bfs(startNode);
        } else {
            System.out.println("Invalid traversal method. Use 'dfs' or 'bfs'.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph();

        // Choose how to construct the graph
        System.out.print("Construct graph from (1) Keyboard or (2) File? Enter 1 or 2: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            graph.constructGraphFromInput();
        } else if (choice.equals("2")) {
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();
            graph.constructGraphFromFile(filePath);
        } else {
            System.out.println("Invalid choice. Exiting.");
            System.exit(1);
        }

        // Choose traversal method
        System.out.print("Enter the starting node: ");
        String startNode = scanner.nextLine();
        System.out.print("Enter traversal method (dfs or bfs): ");
        String method = scanner.nextLine();

        // Traverse the graph
        graph.traverse(startNode, method);
    }
}
