package Main;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// Taking the size of the matrix as 30
public class Graph {
    private final int[][] matrix;
    private int vertices;
    Graph()
    {
        System.out.println("Graph Created");
        matrix = new int[30][30];
        vertices = 0;
    }

    Graph(int size)
    {
        System.out.println("Graph Created");
        matrix = new int[size][size];
        vertices = 0;
    }

    void insertVertex()
    {
        vertices++;
    }

    void insertEdge(int u, int v, int weight)
    {
        matrix[u][v] = weight;
        matrix[v][u] = weight;
    }

    void display()
    {
        for (int i = 0; i < vertices; i++)
        {
            for (int j = 0; j < vertices; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    ArrayList<Integer> dfs(int source,int destination) {

        ArrayList<Integer> layerdfs = new ArrayList<>();
        boolean[] visited = new boolean[vertices];

        Stack<Integer> s = new Stack<>();
        s.push(source);

        visited[source] = true;
        dfscall(source,visited,layerdfs);

        return layerdfs;
    }

    public void dfscall(int curr,boolean visited[],ArrayList<Integer> layer){
        visited[curr] = true;
        layer.add(curr);

        for (int i=0 ; i<vertices ; i++){
            if(!visited[i] && matrix[curr][i] !=0){
                dfscall(i,visited,layer);
            }
        }
    }
    Pair<ArrayList<ArrayList<Integer>>, ArrayList<Integer>> bfs(int source, int destination)
    {
        // We will be using array list to store each layer
        ArrayList<ArrayList<Integer>> layers = new ArrayList<>();
        ArrayList<Integer> layer = new ArrayList<>();

        boolean[] visited = new boolean[vertices];
        int[] parent = new int[vertices];
        parent[source] = -1;

        Queue<Integer> q = new LinkedList<>();
        q.add(source);

        visited[source] = true;
        int count=1,next_count=0;

        while (!q.isEmpty())
        {
            boolean isDestinationLayer = false;

            int curr = q.remove();
            layer.add(curr);
            count--;

            // If destination is found then mark isDestination layer as true
            if(curr == destination) isDestinationLayer = true;

            for(int i = 0; i < vertices; i++)
            {
                if (matrix[curr][i] != 0 && (!visited[i]))
                {
                    q.add(i);
                    next_count++ ;
                    parent[i] = curr;
                    visited[i] = true;
                }
            }

            if (count == 0) {
                // Add the current layer to the layers stack
                layers.add((ArrayList<Integer>) layer.clone());
                layer.clear();

                // Break from the loop if the current layer is the destination layer
                if(isDestinationLayer) break;

                count = next_count;
                next_count = 0;
            }
        }

        System.out.println("before path");

        ArrayList<Integer> reversePath = new ArrayList<>();
        int curr = destination;
        while(curr != -1)
        {
            reversePath.add(curr);
            curr = parent[curr];
        }

        System.out.println("After path");

        return new Pair<>(layers, reversePath);
    }

    public void component_dfs(int curr, int component, int[] visited)
    {
        visited[curr] = component;
        for(int i = 0; i < vertices; i++)
        {
            if (matrix[curr][i] != 0 && visited[i] == 0)
            {
                component_dfs(i, component, visited);
            }
        }
    }
    public ArrayList<ArrayList<Integer>> getComponents()
    {
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();

        // 0 means that it is not visited and other number means the component number
        int[] visited = new int[vertices];
        int component = 0;

        // Go through all the vertices and find all the components
        for(int i = 0; i < vertices; i++) {
            if (visited[i] == 0){
                component++;
                component_dfs(i, component, visited);

                // Check the visited array to find the nodes visited in current component of graph
                ArrayList<Integer> temp = new ArrayList<>();
                for(int node = 0; node < vertices; node++)
                {
                    if (visited[node] == component)
                    {
                        temp.add(node);
                    }
                }

                // Add the current component to components
                components.add(temp);
            }
        }
        return components;
    }

    public void reset()
    {
        vertices = 0;
        for (int i = 0; i < 30; i++)
        {
            for (int j = 0; j < 30; j++)
            {
                matrix[i][j] = 0;
            }
        }
    }
}
