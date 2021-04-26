package service;

import models.Graph;

public class S0401 {
  public static void main( String[] args ) {
    Graph<String, Integer> test = new Graph<>();

    test.addVertice("Austin");
    test.addVertice("Atlanta");
    test.addVertice("Chicago");
    test.addVertice("Dallas");
    test.addVertice("Denver");
    test.addVertice("Houston");
    test.addVertice("Washington");

    test.setEdge("Dallas", "Austin", 200, true);
    test.setEdge("Dallas", "Denver", 780, false);
    test.setEdge("Dallas", "Chicago", 900, false);
    test.setEdge("Austin", "Houston", 160, false);
    test.setEdge("Chicago", "Denver", 1000, true);
    test.setEdge("Denver", "Atlanta", 1400, false);
    test.setEdge("Washington", "Dallas", 1300, false);
    test.setEdge("Washington", "Atlanta", 600, true);
    test.setEdge("Houston", "Atlanta", 800, true);

    System.out.print(test.toString());
  }
}
