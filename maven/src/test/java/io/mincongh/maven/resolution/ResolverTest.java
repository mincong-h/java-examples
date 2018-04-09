package io.mincongh.maven.resolution;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests resolver.
 *
 * @author Mincong Huang
 */
public class ResolverTest {

  /**
   * Given the following graph to resolve:
   *
   * <pre>
   * root
   * +-- a1
   * +-- b1
   *     +-- a2
   *     +-- c1
   * +-- b2
   * </pre>
   *
   * Asserts that the resolution is correct.
   */
  @Test
  public void resolve() {
    Node nodeA1 = new Node("a", 1);
    Node nodeB1 = new Node("b", 1);
    Node nodeB2 = new Node("b", 2);
    Node nodeA2 = new Node("a", 2);
    Node nodeC1 = new Node("c", 1);

    Node root = new Node("root", 1);
    root.addChild(nodeA1);
    root.addChild(nodeB1);
    root.addChild(nodeB2);
    nodeB1.addChild(nodeA2);
    nodeB1.addChild(nodeC1);

    Collection<Node> dependencies = Resolver.resolve(root);
    List<String> results = dependencies.stream().map(Node::toString).collect(Collectors.toList());
    assertThat(results)
        .containsExactlyInAnyOrder(
            "Node{children=[], name='a', version=1, distance=1, status=NORMAL}",
            "Node{children=[], name='b', version=1, distance=1, status=CONFLICT}",
            "Node{children=[], name='c', version=1, distance=2, status=NORMAL}");
  }
}
