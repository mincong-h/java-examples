package io.mincongh.json.jackson.so41655924;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * How can I achieve this using jackson jsonNode and get the desired output.
 *
 * @author Mincong Huang
 * @see https://stackoverflow.com/questions/41655924
 */
public class JsonUtilTest {

  private static final String JSON_STR =
      "{\"searchResults\":{\"@xmlns\":{\"dvi\":\"http://localhsot.com/ns/1.0\",\"$\":\"http://localhsot.com/ns/1.0\",\"a\":\"http://alternate.localhsot.com/ns/1.0\"},\"@totalCount\":\"2\",\"file\":[{\"link\":[{\"@rel\":\"file\",\"$\":\"file.url\"},{\"@rel\":\"content\",\"$\":\"content.url\"},{\"@rel\":\"repository\",\"$\":\"repository.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"320\",\"@a:height\":\"320\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"64\",\"@a:height\":\"64\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"24\",\"@a:height\":\"24\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"128\",\"@a:height\":\"128\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"}],\"name\":{\"$\":\"gullfoss.jpg\"},\"parentPath\":{\"$\":\"/\"},\"size\":{\"$\":\"91345\"},\"versionCreated\":{\"$\":\"2017-01-13T16:38:20.059Z\"},\"systemAttribute\":[{\"@name\":\"Geo-Latitude\",\"$\":\"47.6065\"},{\"@name\":\"Geo-Longitude\",\"$\":\"22.99514\"},{\"@name\":\"Height\",\"$\":\"579\"},{\"@name\":\"Mime-Type\",\"$\":\"image/jpeg\"},{\"@name\":\"Timeline-Date\",\"$\":\"-0091-03-27T04:26:40.000Z\"},{\"@name\":\"Width\",\"$\":\"680\"}],\"attributesChanged\":{\"$\":\"false\"},\"deleted\":{\"$\":\"false\"},\"repository\":{\"$\":\"VZMOBILE_13psb6j\"},\"checksum\":{\"$\":\"5d828c1d94b2569ff1bf60a9ebe9cd2de8ae8e6a9ad7d77d678c1766c78957c5\"},\"extension\":{\"$\":\"jpg\"},\"fileAttribute\":[{\"@name\":\"ContentPermissions\",\"$\":\"SHARE\"},{\"@name\":\"Width\",\"$\":\"680\"},{\"@name\":\"CI_COMPLETE\",\"$\":\"true\"},{\"@name\":\"DRM_PROTECTED\",\"$\":\"false\"},{\"@name\":\"Height\",\"$\":\"579\"}],\"contentToken\":{\"$\":\"AHYLsb1sK9iy1Rn3yS4mQiM5pFL--AyTjY3i5G-zzdOm68QzB_GILrt1pXgfdEkUcPZZ5tUpn1Ih3j9wyfywIVY~\"},\"contentAccessible\":{\"$\":\"true\"},\"contentChanged\":{\"$\":\"false\"}},{\"link\":[{\"@rel\":\"file\",\"$\":\"file.url\"},{\"@rel\":\"content\",\"$\":\"content.url\"},{\"@rel\":\"repository\",\"$\":\"repository.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"320\",\"@a:height\":\"320\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"64\",\"@a:height\":\"64\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"24\",\"@a:height\":\"24\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"},{\"@rel\":\"content.alternate\",\"@a:width\":\"128\",\"@a:height\":\"128\",\"@a:imageFormat\":\"jpeg\",\"@a:mimeType\":\"image/jpeg\",\"@a:size\":\"-1\",\"$\":\"some.url\"}],\"name\":{\"$\":\"gullfoss.jpg\"},\"parentPath\":{\"$\":\"/Folder\"},\"size\":{\"$\":\"91345\"},\"versionCreated\":{\"$\":\"2017-01-13T16:38:59.728Z\"},\"systemAttribute\":[{\"@name\":\"Geo-Latitude\",\"$\":\"47.6065\"},{\"@name\":\"Geo-Longitude\",\"$\":\"22.99514\"},{\"@name\":\"Height\",\"$\":\"579\"},{\"@name\":\"Mime-Type\",\"$\":\"image/jpeg\"},{\"@name\":\"Timeline-Date\",\"$\":\"-0091-03-27T04:26:40.000Z\"},{\"@name\":\"Width\",\"$\":\"680\"}],\"attributesChanged\":{\"$\":\"false\"},\"deleted\":{\"$\":\"false\"},\"repository\":{\"$\":\"VZMOBILE_13psb6j\"},\"checksum\":{\"$\":\"5d828c1d94b2569ff1bf60a9ebe9cd2de8ae8e6a9ad7d77d678c1766c78957c5\"},\"extension\":{\"$\":\"jpg\"},\"fileAttribute\":[{\"@name\":\"ContentPermissions\",\"$\":\"SHARE\"},{\"@name\":\"Width\",\"$\":\"680\"},{\"@name\":\"CI_COMPLETE\",\"$\":\"true\"},{\"@name\":\"DRM_PROTECTED\",\"$\":\"false\"},{\"@name\":\"Height\",\"$\":\"579\"}],\"contentToken\":{\"$\":\"AHYLsb1sK9iy1Rn3yS4mQiM5pFL--AyTjY3i5G-zzdOm68QzB_GILrt1pXgfdEkUcPZZ5tUpn1Ih3j9wyfywIVY~\"},\"contentAccessible\":{\"$\":\"true\"},\"contentChanged\":{\"$\":\"false\"}}]}}";

  /**
   * Get the totalCount from the given json string.
   *
   * @throws IOException
   * @throws JsonProcessingException
   */
  @Test
  public void testTotalCount() throws JsonProcessingException, IOException {
    assertEquals(2, JsonUtil.getTotalCount(JSON_STR));
  }

  /**
   * 
   * @throws JsonProcessingException
   * @throws IOException
   */
  @Test
  public void testIsJsonNodeArray() throws JsonProcessingException, IOException {
    assertEquals("content.url", JsonUtil.getContentUrl(JSON_STR, "VZMOBILE_13psb6j"));
  }
}
