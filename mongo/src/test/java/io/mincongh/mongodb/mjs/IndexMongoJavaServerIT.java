package io.mincongh.mongodb.mjs;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import io.mincongh.mongodb.spec.IndexAbstractIT;
import java.net.InetSocketAddress;
import org.junit.Ignore;

public class IndexMongoJavaServerIT extends IndexAbstractIT {

  private MongoClient client;
  private MongoServer server;

  @Override
  protected MongoDatabase database() {
    return client.getDatabase("testdb");
  }

  @Override
  protected void preSetup() {
    server = new MongoServer(new MemoryBackend());
    InetSocketAddress serverAddress = server.bind();
    client = new MongoClient(new ServerAddress(serverAddress));
  }

  @Override
  protected void postTeardown() {
    client.close();
    server.shutdown();
  }

  /*
   * @bug one more index is created.
   *
   * Expecting:
   *   <["_id_", "name_1", "name_1"]>
   * to contain exactly (and in same order):
   *   <["_id_", "name_1"]>
   * but some elements were not expected:
   *   <["name_1"]>
   */
  @Ignore
  @Override
  public void createIndexes_successTwoCreations() {}
}
