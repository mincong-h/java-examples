package io.mincongh.jgit;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.RefFilter;

/**
 * Ref filter based on regular expression.
 *
 * @author Mincong Huang
 * @since 1.0
 */
public class RegexRefFilter implements RefFilter {

  private final Pattern pattern;

  public RegexRefFilter(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public Map<String, Ref> filter(Map<String, Ref> map) {
    return map.entrySet()
        .stream()
        .filter(e -> pattern.matcher(e.getKey()).matches())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }
}
