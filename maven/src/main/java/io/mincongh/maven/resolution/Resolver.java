package io.mincongh.maven.resolution;

import io.mincongh.maven.resolution.Node.Status;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simplified Maven dependency resolver.
 *
 * @author Mincong Huang
 */
public class Resolver {

  /** Dependency candidates */
  private List<Node> candidates = new ArrayList<>();

  /** Dependencies (key: name, value: node) */
  private Map<String, Node> dependencies = new HashMap<>();

  /**
   * Resolve dependencies for a given tree.
   *
   * @return a resolved dependency tree
   */
  public static Collection<Node> resolve(Node root) {
    Resolver r = new Resolver();

    for (Node child : root.getChildren()) {
      r.visit(child);
    }

    Map<String, Node> map = r.dependencies;
    for (Node candidate : r.candidates) {
      String name = candidate.getName();
      if (!map.containsKey(name)) {
        map.put(name, candidate);
        continue;
      }
      Node existent = map.get(name);
      // distance is priority
      if (existent.isFurtherThan(candidate)) {
        map.put(name, candidate);
      }
      if (existent.getDistance() == candidate.getDistance()) {
        existent.setStatus(Status.CONFLICT);
      }
    }
    return map.values();
  }

  private void visit(Node node) {
    candidates.add(Node.copy(node));
    for (Node child : node.getChildren()) {
      visit(child);
    }
  }
}
