package io.mincongh.maven.resolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Node presentation for a dependency, a simplified version of Maven resolution.
 *
 * <p>A node contains zero or more nodes.
 *
 * @author Mincong Huang
 */
public class Node {

  public enum Status {
    NORMAL, CONFLICT
  }

  private final List<Node> children = new ArrayList<>();

  private String name;

  private int version;

  /**
   * The distance to the root node.
   *
   * <p>By default, it is set to 0, which means the node is the root itself. When distance is
   * greater than 1, it means this node is a transitive dependency.
   */
  private int distance = 0;

  private Status status = Status.NORMAL;

  /** Creates a new node without copying its child nodes. */
  public static Node copy(Node node) {
    Objects.requireNonNull(node);
    return new Node(node.name, node.version, node.distance);
  }

  Node(String name, int version) {
    this.name = name;
    this.version = version;
  }

  private Node(String name, int version, int distance) {
    this.name = name;
    this.version = version;
    this.distance = distance;
  }

  public void addChild(Node node) {
    Objects.requireNonNull(node);
    node.distance = this.distance + 1;
    children.add(node);
  }

  public List<Node> getChildren() {
    return children;
  }

  public String getName() {
    return name;
  }

  public int getDistance() {
    return distance;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public boolean isFurtherThan(Node that) {
    return this.distance > that.distance;
  }

  @Override
  public String toString() {
    return "Node{" +
        "children=" + children +
        ", name='" + name + '\'' +
        ", version=" + version +
        ", distance=" + distance +
        ", status=" + status +
        '}';
  }
}
