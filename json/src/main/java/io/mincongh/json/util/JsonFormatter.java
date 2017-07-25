package io.mincongh.json.util;

/**
 * @author Mincong Huang
 */
public class JsonFormatter {

  private static final String LINE_SEPARATOR = System.lineSeparator();

  private static final int SPACES_PER_TAB = 2;

  private JsonFormatter() {
    // Utility class, do not instantiate.
  }

  public static String pretty(String in) {
    StringBuilder sb = new StringBuilder();
    boolean isKey = false; // true=key, false=value.
    int tab = 0;
    int i = 0;

    for (; i < in.length(); i++) {
      char c = in.charAt(i);

      if (c == '"') {
        isKey = !isKey;

        if (isKey) {
          sb.append(indentOf(tab));
        }

        do {
          if (in.charAt(i) == '\\') {
            sb.append(in.charAt(i++));
            sb.append(in.charAt(i++));
          } else {
            sb.append(in.charAt(i++));
          }
        } while (in.charAt(i) != '"');
        sb.append(in.charAt(i));

        if (!isKey) {
          if (i + 1 < in.length() && in.charAt(i + 1) == ',') {
            sb.append(',');
          }
          sb.append(LINE_SEPARATOR);
        }

      } else if (c == ':') {
        sb.append(": ");

      } else if (c == '{' || c == '[') {
        sb.append(indentOf(tab));
        sb.append(c).append(LINE_SEPARATOR);
        tab++;

      } else if (c == '}' || c == ']') {
        tab--;
        sb.append(indentOf(tab));
        sb.append(c);

        if (i + 1 < in.length() && in.charAt(i + 1) == ',') {
          sb.append(',');
        }
        sb.append(LINE_SEPARATOR);
      }
    }
    return sb.toString();
  }

  private static String indentOf(int tab) {
    return new String(new char[SPACES_PER_TAB * tab]).replace('\0', ' ');
  }

}
