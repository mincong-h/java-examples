package io.mincongh.mongodb.mjs;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import io.mincongh.mongodb.spec.UpdateAbstractIT;
import java.net.InetSocketAddress;
import org.junit.Ignore;
import org.junit.Test;

public class UpdateMongoJavaServerIT extends UpdateAbstractIT {

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

  @Test
  @Override
  @Ignore("com.mongodb.MongoWriteException: The positional operator did not find the match needed from the query.")
  public void update_elemMatch_elementField() {}

  @Test
  @Override
  @Ignore("com.mongodb.MongoWriteException: The positional operator did not find the match needed from the query.")
  public void update_elemMatch_element() {}
}
