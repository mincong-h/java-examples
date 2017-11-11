package io.mincongh.json.jackson.so41655924;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * @author Mincong Huang
 * @see https://stackoverflow.com/questions/41655924
 */
public class JsonUtil {

  private JsonUtil() {
    // Utility class, do not instantiate
  }

  public static int getTotalCount(String jsonStr) throws IOException {
    JsonNode jsonNode = new ObjectMapper().readTree(jsonStr);
    return jsonNode.at("/searchResults/@totalCount").asInt();
  }

  public static String getContentUrl(String jsonStr, String targetRepoName) throws IOException {
    JsonNode jsonNode = new ObjectMapper().readTree(jsonStr);
    JsonNode filesNode = jsonNode.at("/searchResults/file");
    if (filesNode.isArray()) {
      for (JsonNode fileNode : filesNode) {
        String repoName = fileNode.at("/repository/$").asText();
        if (repoName.equals(targetRepoName)) {
          for (JsonNode linkNode : fileNode.at("/link")) {
            if ("content".equals(linkNode.get("@rel").asText())) {
              return linkNode.get("$").asText();
            }
          }
          return null;
        }
      }
    }
    return null;
  }
}
