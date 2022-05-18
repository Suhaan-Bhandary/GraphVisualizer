package Main;

import java.util.ArrayList;

public class DisjointUnionSet {
    ArrayList<Integer> parent, rank;
    Integer nodes;

    DisjointUnionSet(int n)
    {
        // Initializing the number of nodes in the graph
        nodes = n;

        // Creating Parent and rank array
        parent = new ArrayList<>();
        rank = new ArrayList<>();

        for (int i = 0; i < n; i++)
        {
            parent.add(i);
            rank.add(0);
        }
    }

    Integer getParent(Integer node)
    {
        if(node.equals(parent.get(node))) return node;

        parent.set(node, getParent(parent.get(node)));
        return parent.get(node);
    }

    void union(Integer u, Integer v)
    {
        u = getParent(u);
        v = getParent(v);

        if (rank.get(u) < rank.get(v))
        {
            parent.set(u, v);
        }
        else if(rank.get(u) > rank.get(v))
        {
            parent.set(v, u);
        }
        else
        {
            parent.set(u, v);
            rank.set(u, rank.get(u) + 1);
        }
    }

    Boolean isFromSameComponent(Integer u, Integer v)
    {
        return getParent(u).equals(getParent(v));
    }
}
