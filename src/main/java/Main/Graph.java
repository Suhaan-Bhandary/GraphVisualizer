package Main;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
            int curr = q.remove();
            layer.add(curr);
            count--;

            for(int i = 0; i < vertices; i++)
            {
                if (matrix[curr][i] == 1 && (!visited[i]))
                {
                    q.add(i);
                    next_count++ ;
                    parent[i] = curr;
                    visited[i] = true;
                }
            }

            if (count == 0) {
                layers.add((ArrayList<Integer>) layer.clone());
                layer.clear();

                count = next_count;
                next_count = 0;
                System.out.println();
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
