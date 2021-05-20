package optional.util;

import optional.entity.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph
{
    private int n;
    private List<String> nodes;
    private Map<String, List<String>> edges;
    private Map<String, Boolean> viz;
    private Map<String, Boolean> viz2;
    private Map<String, Integer> tin;
    private Map<String, Integer> up;
    private int t;

    public Graph(Iterable<Person> persons, int n)
    {
        this.n = n;
        nodes = new ArrayList<>();
        edges = new HashMap<>();
        viz = new HashMap<>();
        viz2 = new HashMap<>();
        tin = new HashMap<>();
        up = new HashMap<>();
        for (var person : persons)
        {
            nodes.add(person.getName());
            edges.put(person.getName(), new ArrayList<>(person.getFriendsNum()));
            for (var friend : person.getFriends())
                edges.get(person.getName()).add(friend.getName());
            for (var friend : person.getFriendsOf())
                edges.get(person.getName()).add(friend.getName());
            viz.put(person.getName(), false);
            viz2.put(person.getName(), false);
            tin.put(person.getName(), 0);
            up.put(person.getName(), 0);
        }
    }

    public int getN()
    {
        return n;
    }

    public Map<String, List<String>> getEdges()
    {
        return edges;
    }

    private void DFS(String node, String root, String parent, List<String> cutVertices)
    {
        t++;
        tin.put(node,t);
        up.put(node,Integer.MAX_VALUE);
        viz.put(node,true);
        int children = 0;
        for (var friend : edges.get(node))
        {
            if (friend.equals(parent))
                continue;
            if (viz.get(friend))
            {
                up.put(node, Math.min(up.get(node), tin.get(friend)));
                continue;
            }
            children++;
            DFS(friend, root, node, cutVertices);
            up.put(node, Math.min(up.get(node), up.get(friend)));
            if (!node.equals(root) && up.get(friend) >= tin.get(node) && !viz2.get(node))
            {
                viz2.put(node, true);
                cutVertices.add(node);
            }
        }
        if (node.equals(root) && children >= 2)
            cutVertices.add(node);
    }

    public List<String> getCutVertices()
    {
        t = 0;
        List<String> cutVertices = new ArrayList<>();
        for (String name : nodes)
            if (!viz.get(name))
                DFS(name, name, "", cutVertices);
        return cutVertices;
    }
}
