/**
 * Given the following classes, which of the follwing snippets can be inserted in place of {@code
 * INSERT IMPORTS HERE} and have the code compile? (Choose all that apply)
 * <p>
 * <pre>
 * package aquarium;
 * public class Water {
 *   boolean salty = false;
 * }
 *
 * package aquarium.jellies;
 * public class Water {
 *   boolean salty = true;
 * }
 *
 * package employee;
 * INSERT IMPORTS HERE
 * public class WaterFilter {
 *   Water water
 * }
 * </pre>
 * <p>
 * The answer is BC. Importing by class names takes precedence over wildcards, so these compile.
 * <p>
 * B.
 * <pre>
 * import aquarium.Water;
 * import aquarium.jellies.*;
 * </pre>
 * <p>
 * C.
 * <pre>
 * import aquarium.*;
 * import aquarium.jellies.Water;
 * </pre>
 *
 * @author Mincong Huang
 */
package io.mincong.ocajp.chapter1.question7;
