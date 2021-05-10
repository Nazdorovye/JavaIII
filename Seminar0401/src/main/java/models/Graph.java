package models;

import java.util.ArrayList;

public class Graph<V> {
  private ArrayList<V> verts;
  private ArrayList<ArrayList<Number>> adj;

  public Graph() {
    verts = new ArrayList<V>();
    adj = new ArrayList<ArrayList<Number>>();
  }

  /**
   * Adds specified vertex to graph
   * @param vertice
   * @return {@code true} on success, {@code false} if {@code verts} or {@code adj} were null
   */
  public boolean addVertice(V vertice) {
    if (verts == null || adj == null) {
      return false;
    }

    verts.add(vertice);
    adj.add(new ArrayList<Number>());

    for (int i = 0; i < verts.size(); i++) {
      for (int j = adj.get(i).size(); j < verts.size(); j++) {
        adj.get(i).add(null);
      }
    }

    return true;
  }

  /**
   * Removes specified vertice
   * @param vertice
   * @return {@code true} on success, {@code false} if {@code verts} or {@code adj} were null or {@code vertice} was not found in graph
   */
  public boolean removeVertice(V vertice) {
    if (verts == null || adj == null) {
      return false;
    }

    int idV = verts.indexOf(vertice);
    if (idV >= 0 && verts.remove(vertice)) {

      // remove matrix column for vertice
      adj.remove(idV);

      // remove matrix rows for vertice
      for (ArrayList<Number> r : adj) r.remove(idV);
    } else {
      return false;
    }

    return true;
  }

  /**
   * Modifies edge between vertices
   * @param from - origin vertex
   * @param to - destination vertex
   * @param weight - specific {@code Number} type weight, {@code null} if none/remove
   * @param bothSides - defines if edge flows both directions (undirected) or directed
   * @return {@code true} on success, {@code false} if {@code verts} or {@code adj} were null
   */
  public boolean setEdge(V from, V to, Number weight, boolean bothSides) {
    if (verts == null || adj == null) {
      return false;
    }

    int idF = verts.indexOf(from);
    int idT = verts.indexOf(to);

    if (idF >= 0 && idT >= 0) {
      adj.get(idF).set(idT, weight);
      if (bothSides) adj.get(idT).set(idF, weight);
    }

    return true;
  }

  /**
   * Returns arraylist of neighbour vertices
   * @param from - vertex, neighbours from which are needed
   * @return {@code ArrayList<V>}
   * @throws Exception thrown if vertex {@code from} was not found in graph
   */
  public ArrayList<V> getNeighbours(V from) throws Exception {
    int idF = verts.indexOf(from);

    if (idF < 0) {
      throw new Exception("Method argument { from }, to was not found in graph structure");
    }

    ArrayList<V> result = new ArrayList<>();

    for (int i = 0; i < verts.size(); i++) {
      if (adj.get(idF).get(i) != null) {
        result.add(verts.get(i));
      }
    }

    return result;
  }

  /**
   * Returns arraylist of neighbour vertex indices
   * @param from - vertex, neighbours from which are needed
   * @return {@code ArrayList<Integer>}
   * @throws IndexOutOfBoundsException thrown if vertex {@code from} was not found in graph
   */
  public ArrayList<Integer> getNeighbours(int from) throws IndexOutOfBoundsException {
    if (from < 0 || from > verts.size() - 1) {
      throw new IndexOutOfBoundsException("Method argument { from }, to was not found in graph structure");
    }

    ArrayList<Integer> result = new ArrayList<>();

    for (int i = 0; i < verts.size(); i++) {
      if (adj.get(from).get(i) != null) {
        result.add(i);
      }
    }

    return result;
  }


  /**
   * Returns filtered arraylist of graph vertices traversed in breadth first order
   * @return {@code ArrayList<V>}
   */
  public ArrayList<V> getVertsBreadthFirst() {
    ArrayList<V> result = new ArrayList<>();
    boolean[] visited = new boolean[verts.size()];

    // put first vertex as head
    visited[0] = true;
    result.add(verts.get(0));

    // move i as head of imaginary q in the result array
    for (int i = 0; i < verts.size(); i++) {
      // j iterates neighbours of current (q head) vertex in adj matrix
      for (int j = 0; j < verts.size(); j++) {
        if (adj.get(verts.indexOf(result.get(i))).get(j) != null && visited[j] == false) {
          visited[j] = true;
          result.add(verts.get(j));
        }
      }
    }

    return result;
  }

  /**
   * util function - recursive depth first order traversal
   * @param curIdx - index of vertex from which this recursion traverses
   * @param visited - array of visited vertices
   * @param writable - array of traversed results
   */
  private void depthRecur(int curIdx, boolean[] visited, ArrayList<V> writable) {
    visited[curIdx] = true;
    writable.add(verts.get(curIdx));

    for (int i = 0; i < verts.size(); i++) {
      if (adj.get(curIdx).get(i) != null && visited[i] == false) {
        depthRecur(i, visited, writable);
      }
    }
  }

  /**
   * Returns filtered arraylist of graph vertices traversed in depth first order
   * @return {@code ArrayList<V>}
   */
  public ArrayList<V> getVertsDepthFirst() {
    ArrayList<V> result = new ArrayList<>();
    boolean[] visited = new boolean[verts.size()];
    
    depthRecur(0, visited, result);

    return result;
  }

  /**
   * Returns filtered arraylist of graph vertices traversed in depth first order
   * @param from
   * @return {@code ArrayList<V>}
   */
  public ArrayList<V> getVertsDepthFirst(V from) {
    ArrayList<V> result = new ArrayList<>();
    boolean[] visited = new boolean[verts.size()];
    
    depthRecur(verts.indexOf(from), visited, result);

    return result;
  }

  /**
   * Add two weights
   * @param a
   * @param b
   * @return {@code Number}
   */
  private static Number addWeights(Number a, Number b) {
    if (a instanceof Integer && b instanceof Integer) {
      return Integer.class.cast(a) + Integer.class.cast(b);
    } 

    //TODO: complete for all Number child classes

    return null;
  }

  private static int compareWeights(Number a, Number b) {
    if (a instanceof Integer && b instanceof Integer) {
      return Integer.class.cast(a) - Integer.class.cast(b);
    }

    //TODO: complete for all Number child classes

    return 0;
  }

  /**
   * Returns shortest weighted path
   * @param from
   * @param to
   * @return {@code Number}
   * @throws IndexOutOfBoundsException if {@code from} or {@code to} vertices are not in the graph
   */
  public Number getShortestPath(V from, V to) throws IndexOutOfBoundsException {
    int cur = verts.indexOf(from);
    int dest = verts.indexOf(to);
    
    if (cur < 0 || dest < 0 || cur > verts.size() || dest > verts.size()) {
      throw new IndexOutOfBoundsException();
    }

    int[] prevs = new int[verts.size()];
    for (int i = 0; i < prevs.length; i++) { prevs[i] = -1; } // init to -1 ( no prev )

    boolean[] visited = new boolean[verts.size()];
    Number[] paths = new Number[verts.size()]; // indices parallel to unvisited indices
    paths[cur] = 0;

    Number shortest;
    int shortestIdx;
    ArrayList<Integer> neigh;
    
    // Iterate through all vertices starting with from vertex
    for (int v = 0; v < verts.size(); v++) {
      try { neigh = getNeighbours(cur); } 
      catch (IndexOutOfBoundsException e) { throw e; }

      visited[cur] = true;
      shortest = Integer.MAX_VALUE;
      shortestIdx = -1;

      // Get shortest paths from start for each neighbour
      for (Integer n : neigh) {
        if (visited[n]) continue;
        Number path = addWeights(adj.get(cur).get(n), paths[cur]);

        if (compareWeights(shortest, path) > 0) {
          shortest = path;
          shortestIdx = n;
        }

        if (paths[n] == null || compareWeights(paths[n], path) > 0) {
          paths[n] = path;
          prevs[n] = cur;
        }
      }

      if (shortestIdx < 0) {
        for (int i = 0; i < visited.length; i++) {
          if (visited[i]) continue;
          cur = i;
          break;
        }
      } else {
        cur = shortestIdx;
      }
    }

    return paths[dest];
  }

  /**
   * Returns weight from between specified vertices
   * @param from - origin vertex
   * @param to - destination vertex
   * @return {@code Number} type weight
   * @throws Exception thrown if either vertex {@code from} or {@code to} were not found in graph
   */
  public Number getWeight(V from, V to) throws Exception {
    int idF = verts.indexOf(from);
    int idT = verts.indexOf(to);

    if (idF < 0 && idT < 0) {
      throw new Exception("Method arguments { from, to } were not found in graph structure");
    } else if (idF < 0) {
      throw new Exception("Method argument { from }, to was not found in graph structure");
    } else if (idT < 0) {
      throw new Exception("Method argument { to }, to was not found in graph structure");
    }

    return adj.get(idF).get(idT);
  }

  /**
   * Empties graph
   */
  public void clear() {
    verts.clear();
    adj.clear();
  }

  public boolean isEmpty() { 
    return verts == null || verts.size() == 0; 
  }

  public String toString() {
    String result = "\t";

    for (V vertex : verts) {
      result += vertex.toString().substring(0, 6) + "\t";
    }

    result += "\n";

    for (int c = 0; c < verts.size(); c++) {
      result += verts.get(c).toString().substring(0, 6) + "\t";

      for (int r = 0; r < adj.get(c).size(); r++) {
        result += ((adj.get(c).get(r) == null) ? "-" : adj.get(c).get(r).toString()) + "\t";
      }

      result += "\n";
    }

    return result;
  }
}
