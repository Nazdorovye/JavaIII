package models;

import java.util.ArrayList;

public class Graph<V, L> {
  private ArrayList<V> verts;
  private ArrayList<ArrayList<L>> adj;

  public Graph() {
    verts = new ArrayList<V>();
    adj = new ArrayList<ArrayList<L>>();
  }

  public void addVertice(V vertice) {
    if (verts == null || adj == null) {
      return;
    }

    verts.add(vertice);
    adj.add(new ArrayList<L>());

    for (int i = 0; i < verts.size(); i++) {
      for (int j = adj.get(i).size(); j < verts.size(); j++) {
        adj.get(i).add(null);
      }
    }
  }

  public void removeVertice(V vertice) {
    if (verts == null || adj == null) {
      return;
    }

    int i = verts.indexOf(vertice);
    if (i >= 0 && verts.remove(vertice)) {
      adj.remove(i); // remove matrix column for vertice
      for (ArrayList<L> r : adj) r.remove(i); // remove matrix rows for vertice
    }
  }

  public void setEdge(V from, V to, L weight, boolean bothSides) {
    if (verts == null || adj == null) {
      return;
    }

    int i = verts.indexOf(from);
    int j = verts.indexOf(to);

    if (i >= 0 && j >= 0) {
      adj.get(i).set(j, weight);
      if (bothSides) adj.get(j).set(i, weight);
    }
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
        result += ((adj.get(c).get(r) == null) ? "0" : adj.get(c).get(r).toString()) + "\t";
      }

      result += "\n";
    }

    return result;
  }
}
