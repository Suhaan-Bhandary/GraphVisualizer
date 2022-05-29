package Main;

import javafx.util.Pair;

import java.util.*;

// Taking the size of the matrix as 30
public class Graph {
    private final int[][] matrix;
    private int vertices;

    // Variable which is used for finding articulation point and bridges
    private int counter;
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

    Pair<ArrayList<Integer>, Boolean> dfs(int source,int destination) {
        ArrayList<Integer> layersDFS = new ArrayList<>();
        boolean[] visited = new boolean[vertices];

        visited[source] = true;
        dfsCall(source,visited,layersDFS);

        Boolean destinationFound = visited[destination];

        return new Pair<>(layersDFS, destinationFound);
    }

    public void dfsCall(int curr, boolean[] visited, ArrayList<Integer> layer){
        visited[curr] = true;
        layer.add(curr);

        for (int i=0 ; i<vertices ; i++){
            if(!visited[i] && matrix[curr][i] !=0){
                dfsCall(i,visited,layer);
            }
        }
    }
    public Pair<ArrayList<ArrayList<Integer>>, ArrayList<Integer>> bfs(int source, int destination)
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
        if (!visited[destination])
        {
            return new Pair<>(layers, reversePath);
        }

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

    public Pair<ArrayList<Integer>, ArrayList<Integer>> dijkstra(int source, int destination){
        boolean[] visited = new boolean[vertices];
        int[] distance = new int[vertices];
        for (int i=0;i<vertices;i++){
            distance[i] = Integer.MAX_VALUE;
        }

        distance[source] = 0;

        int[] parent = new int[vertices];
        parent[source] = -1;

        PriorityQueue<Node> pq = new PriorityQueue<>(vertices,new Node());
        pq.add(new Node(source, distance[source]));


        ArrayList<Integer> layer = new ArrayList<>();

        while (!pq.isEmpty()){
            Node curr = pq.poll();
            int currNode = curr.node;
            int currDist = curr.cost;

            visited[currNode] = true;
            layer.add(currNode);

            if(currNode == destination)
                break;

            if (currDist > distance[currNode])
            {
                System.out.println(currNode + " " + currDist);
                continue;
            }

            for(int i = 0; i < vertices; i++)
            {
                if (matrix[currNode][i] != 0 && !visited[i] && distance[currNode] + matrix[currNode][i] < distance[i])
                {
                    parent[i] = currNode;
                    distance[i] =  distance[currNode] + matrix[currNode][i];
                    pq.add(new Node(i,distance[i]));
                }
            }

        }
        ArrayList<Integer> reversePath = new ArrayList<>();
        if (!visited[destination])
        {
            return new Pair<>(layer, reversePath);
        }

        System.out.println("before path");

        int curr = destination;
        while(curr != -1)
        {
            reversePath.add(curr);
            System.out.println(curr);
            curr = parent[curr];
        }

        System.out.println("After path");

        return new Pair<>(layer, reversePath);
    }

    // Adding Kruskal algorithm to get minimum spanning tree
    // This function returns all the edges which will make a spanning tree
    public ArrayList<Pair<Integer, Integer>> getSpanningTree()
    {
        // Array list to store all the edges
        ArrayList<Pair<Integer, Integer>> edgeList = new ArrayList<>();

        // default value of Boolean is false
        boolean[] visited = new boolean[vertices];

        // Create a priority queue to store the edge weight and the edge pair
        PriorityQueue<Edge> pq = new PriorityQueue<>((vertices * vertices)/2,new Edge());

        // Now First store all the edges from the lower or upper half triangle in the matrix
        for (int i = 0; i < vertices; i++)
        {
            for (int j = i; j < vertices; j++)
            {
                if(matrix[i][j] > 0)
                {
                    pq.add(new Edge(i, j, matrix[i][j]));
                }
            }
        }

        // Create a disjoint set with the help of custom class with vertices count
        DisjointUnionSet disjointUnionSet = new DisjointUnionSet(vertices);

        int count = vertices - 1;
        while(count != 0 && pq.size() > 0)
        {
            Edge currEdge = pq.poll();
            if(!disjointUnionSet.isFromSameComponent(currEdge.source, currEdge.destination))
            {
                // Insert the edge in the solution
                edgeList.add(new Pair<>(currEdge.source, currEdge.destination));

                // Make the source and destination in one component
                disjointUnionSet.union(currEdge.source, currEdge.destination);

                visited[currEdge.source] = true;
                visited[currEdge.destination] = true;
                count--;
            }
        }

        // Check if the graph is connected or not
        for(int i = 0; i < vertices; i++)
        {
            if (!visited[i]) {
                System.out.println(edgeList);
                System.out.println(i);
                return new ArrayList<>();
            }
        }
        return edgeList;
    }

    // Bridges in the Graph
    public ArrayList<Pair<Integer, Integer>> getBridges()
    {
        ArrayList<Pair<Integer, Integer>> bridges = new ArrayList<>();

        int[] tim = new int[vertices];
        int[] low = new int[vertices];
        boolean[] visited = new boolean[vertices];

        // Going through all the components of the graph
        for(int i = 0; i < vertices; i++)
        {
            if (!visited[i])
            {
                counter = 0;
                dfsOfBridge(i, -1 , tim, low, visited, bridges);
            }
        }

        return bridges;
    }

    private void dfsOfBridge(int curr, int parent, int[] tim, int[] low, boolean[] visited, ArrayList<Pair<Integer, Integer>> bridges) {
        tim[curr] = low[curr] = counter;
        counter++;
        visited[curr] = true;

        for (int i = 0; i < vertices; i++)
        {
            if(matrix[curr][i] == 0 || i == parent) continue;

            if (!visited[i])
            {
                dfsOfBridge(i, curr, tim, low, visited, bridges);

                // Update the low here
                if(low[i] < low[curr]) {
                    low[curr] = low[i];
                }

                if (low[i] > tim[curr])
                {
                    bridges.add(new Pair<>(curr, i));
                }
            }
            else
            {
                if(tim[i] < low[curr]) {
                    low[curr] = tim[i];
                }
            }
        }
    }

    // Function to find articulation points
    public Set<Integer> getArticulationPoints() {
        Set<Integer> articulationPoints = new HashSet<>();

        int[] tim = new int[vertices];
        int[] low = new int[vertices];
        boolean[] visited = new boolean[vertices];

        // Going through all the components of the graph
        for(int i = 0; i < vertices; i++)
        {
            if (!visited[i])
            {
                counter = 0;
                dfsOfArticulation(i, -1 , tim, low, visited, articulationPoints);
            }
        }

        return articulationPoints;
    }

    private void dfsOfArticulation(int curr, int parent, int[] tim, int[] low, boolean[] visited, Set<Integer> articulationPoints) {
        tim[curr] = low[curr] = counter;
        counter++;
        visited[curr] = true;

        int child = 0;
        for (int i = 0; i < vertices; i++)
        {
            if(matrix[curr][i] == 0 || i == parent) continue;

            if (!visited[i])
            {
                // Child is calculated for the special case
                child++;

                dfsOfArticulation(i, curr, tim, low, visited, articulationPoints);

                // Update the low here
                if(low[i] < low[curr]) {
                    low[curr] = low[i];
                }

                // The root node has its own special case
                if (low[i] >= tim[curr] && parent != -1)
                {
                    System.out.println("point : " +  curr);
                    articulationPoints.add(curr);
                }
            }
            else
            {
                if(tim[i] < low[curr]) {
                    low[curr] = tim[i];
                }
            }
        }

        // Here child represents the individual Child Components
        if (parent == -1 && child > 1)
        {
            articulationPoints.add(curr);
        }
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

    public int[][] getMatrix() {
        return matrix;
    }

    public int getVerticesCount() {
        return vertices;
    }
}

class Node implements Comparator<Node>
{
    public int node;
    public int cost;

    public Node()
    {
    }

    public Node(int node, int cost)
    {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2)
    {
        return (node1.cost - node2.cost);
    }
}

// The Edge class is used in spanning tree to get the minimum edges
class Edge implements Comparator<Edge>
{
    public int source, destination, weight;

    public Edge()
    {

    }
    public Edge(int source, int destination, int weight)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compare(Edge edge1, Edge edge2)
    {
        return (edge1.weight - edge2.weight);
    }
}



