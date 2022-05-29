package Main;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Set;

public class Animation {

    private static int sleepTime = 2000;
    public static void animateBFS(int source, int destination ,ArrayList<ArrayList<Integer>> layers, ArrayList<Integer> reversePath, AnchorPane graphArea) {
        System.out.println("Animating BFS");

        new Thread(() ->{
            // Looping through each layer one by one
            for (ArrayList<Integer> layer: layers)
            {
                // Make all the circles crimson in the current layer
                for (Integer node : layer)
                {
                    // Selecting the circle and coloring it
                    Circle currNode = (Circle) graphArea.lookup("#circle__" + node);

                    // Color the nodes depending on the condition
                    currNode.setFill(Color.CRIMSON);

                    currNode.setStroke(Color.BLACK);
                    currNode.setStrokeWidth(2);
                }

                // Sleep for one second
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Reset the circles in the current layer except the source and destination
                for (Integer node : layer)
                {
                    // Selecting the circle and coloring it
                    Circle currNode = (Circle) graphArea.lookup("#circle__" + node);
                    if(node != source && node != destination) {
                        currNode.setFill(Color.LIGHTSKYBLUE);
                        currNode.setStrokeWidth(0);
                    }
                    else if (node == destination){
                        currNode.setFill(Color.LIGHTGREEN);
                        currNode.setStrokeWidth(2);
                    }
                    else {
                        currNode.setFill(Color.YELLOW);
                        currNode.setStrokeWidth(2);
                    }
                }
            }

            // After animating the nodes in layers, we will animate the path
            for(int i = reversePath.size() - 2; i >= 0; i--)
            {
                // Getting the current and previous node in the path
                int curr = reversePath.get(i), pre = reversePath.get(i + 1);

                // Creating a store around the current node
                Circle currNode = (Circle) graphArea.lookup("#circle__" + curr);
                if(curr != source && curr != destination) currNode.setStroke(Color.ORANGE);
                currNode.setStrokeWidth(2);

                // Get the edge between the current and pre using the id, id can be in two forms pre-curr or curr-pre
                Line currEdge = (Line) graphArea.lookup("#line__" + curr + "__" + pre);
                if(currEdge == null)
                {
                    currEdge = (Line) graphArea.lookup("#line__" + pre + "__" + curr);
                }

                // Highlight the edge between the current and previous node in the path
                if(currEdge != null) currEdge.setStroke(Color.ORANGE);

                // Sleep for 500 millisecond after each path is highlighted
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void animateDFS(int source, int destination ,ArrayList<Integer> layer, AnchorPane graphArea) {
        System.out.println("Animating DFS");

        new Thread(() ->{
            // Make all the circles crimson in the layer

            for (Integer node : layer) {
                System.out.println(node);
                // Selecting the circle and coloring it
                Circle currNode = (Circle) graphArea.lookup("#circle__" + node);

                // Color the nodes in DFS traversal
                currNode.setFill(Color.CRIMSON);
                currNode.setStroke(Color.BLACK);
                currNode.setStrokeWidth(2);

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //making source and destination colors different for better visibility and resetting previous visited nodes
                if (node == source) currNode.setFill(Color.YELLOW);
                else if (node == destination) currNode.setFill(Color.LIGHTGREEN);
                else {
                    currNode.setFill(Color.LIGHTSKYBLUE);
                    currNode.setStrokeWidth(0);
                }

            }
        }).start();
    }

    public static void animateComponents(ArrayList<ArrayList<Integer>> components, AnchorPane graphArea) {
        String[] colors = {"#f58231", "#ffe119", "#3cb44b", "#4363d8", "#e6194b", "#911eb4", "#46f0f0", "#f032e6", "#bcf60c", "#fabebe", "#008080", "#e6beff", "#9a6324", "#fffac8", "#800000", "#aaffc3", "#808000", "#ffd8b1", "#000075", "#808080", "#ffffff", "#000000"};
        for (int i = 0; i < components.size(); i++)
        {
            for(Integer node: components.get(i))
            {
                Circle currNode = (Circle) graphArea.lookup("#circle__" + node);
                currNode.setFill(Paint.valueOf(colors[i]));
            }
        }
    }

    public static void animateDijkstra(int source, int destination, ArrayList<Integer> layer, ArrayList<Integer> reversePath, AnchorPane graphArea) {
        System.out.println("animating dijkstra");
        new Thread(() ->{
            // Looping through each layer one by one
            for (Integer node : layer) {
                System.out.println(node);
                // Selecting the circle and coloring it
                Circle currNode = (Circle) graphArea.lookup("#circle__" + node);

                // Color the nodes in DFS traversal
                currNode.setFill(Color.CRIMSON);
                currNode.setStroke(Color.BLACK);
                currNode.setStrokeWidth(2);

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //making source and destination colors different for better visibility and resetting previous visited nodes
                if (node == source) currNode.setFill(Color.YELLOW);
                else if (node == destination) currNode.setFill(Color.LIGHTGREEN);
                else {
                    currNode.setFill(Color.LIGHTSKYBLUE);
                    currNode.setStrokeWidth(0);
                }

            }

            // After animating the nodes in layers, we will animate the path
            for(int i = reversePath.size() - 2; i >= 0; i--)
            {
                // Getting the current and previous node in the path
                int curr = reversePath.get(i), pre = reversePath.get(i + 1);

                // Creating a store around the current node
                Circle currNode = (Circle) graphArea.lookup("#circle__" + curr);
                if(curr != source && curr != destination) currNode.setStroke(Color.ORANGE);
                currNode.setStrokeWidth(2);

                // Get the edge between the current and pre using the id, id can be in two forms pre-curr or curr-pre
                Line currEdge = (Line) graphArea.lookup("#line__" + curr + "__" + pre);
                if(currEdge == null)
                {
                    currEdge = (Line) graphArea.lookup("#line__" + pre + "__" + curr);
                }

                // Highlight the edge between the current and previous node in the path
                if(currEdge != null) {
                    currEdge.setStroke(Color.ORANGE);
                }

                // Sleep for 500 millisecond after each path is highlighted
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void animateSpanningTree(ArrayList<Pair<Integer, Integer>> components, AnchorPane graphArea) {
        // Animating Spanning Tree
        new Thread(() ->{
            for (Pair<Integer, Integer> edge: components)
            {
                Integer point1 = edge.getKey();
                Integer point2 = edge.getValue();

                Line currEdge = (Line) graphArea.lookup("#line__" + point1 + "__" + point2);
                if(currEdge == null)
                {
                    currEdge = (Line) graphArea.lookup("#line__" + point2 + "__" + point1);
                }

                // Highlight the edge between the current and previous node in the path
                if(currEdge != null) {
                    currEdge.setStroke(Color.ORANGE);
                }

                // Sleep for 500 millisecond after each path is highlighted
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void animateBridges(ArrayList<Pair<Integer, Integer>> bridges, AnchorPane graphArea) {
        // Animating Bridges
        new Thread(() ->{
            for (Pair<Integer, Integer> edge: bridges)
            {
                Integer point1 = edge.getKey();
                Integer point2 = edge.getValue();

                Line bridge = (Line) graphArea.lookup("#line__" + point1 + "__" + point2);
                if(bridge == null)
                {
                    bridge = (Line) graphArea.lookup("#line__" + point2 + "__" + point1);
                }

                // Highlight the edge between the current and previous node in the path
                if(bridge != null) {
                    bridge.setStroke(Color.RED);
                }

                // Sleep for 500 millisecond after each path is highlighted
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void animateArticulationPoints(Set<Integer> articulationPoints, AnchorPane graphArea) {
        // Articulation Points animation
        new Thread(() ->{
            for (Integer node: articulationPoints)
            {
                Circle currNode = (Circle) graphArea.lookup("#circle__" + node);
                currNode.setFill(Color.RED);

                // Sleep for 500 millisecond after each path is highlighted
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void setSliderSpeed(int value) {
        Animation.sleepTime = value;
    }
}
