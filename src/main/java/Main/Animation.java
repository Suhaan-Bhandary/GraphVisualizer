package Main;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Animation {

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
                    if(node == source) currNode.setFill(Color.YELLOW);
                    else if(node == destination) currNode.setFill(Color.LIGHTGREEN);
                    else currNode.setFill(Color.CRIMSON);

                    currNode.setStroke(Color.BLACK);
                    currNode.setStrokeWidth(2);
                }

                // Sleep for one second
                try {
                    Thread.sleep(1000);
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
                }
            }

            // After animating the nodes in layers, we will animate the path
            for(int i = reversePath.size() - 2; i >= 0; i--)
            {
                // Getting the current and previous node in the path
                int curr = reversePath.get(i), pre = reversePath.get(i + 1);

                // Creating a store around the current node
                Circle currNode = (Circle) graphArea.lookup("#circle__" + curr);
                currNode.setStrokeWidth(1);

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
}
