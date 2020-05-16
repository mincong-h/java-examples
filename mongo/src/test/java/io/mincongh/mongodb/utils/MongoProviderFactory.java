package io.mincongh.mongodb.utils;

import com.github.fakemongo.junit.FongoRule;
import java.util.Objects;

public class MongoProviderFactory {
  private static final String FAKE_MONGO = "FakeMongo";
  private static final String REAL_MONGO = "RealMongo";
  private static final String MONGO_JAVA_SERVER = "MongoJavaServer";

  public static Object[] implementations() {
    return new Object[] {FAKE_MONGO, REAL_MONGO, MONGO_JAVA_SERVER};
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private FongoRule fakeFongoRule;
    private FongoRule realFongoRule;
    private String providerName;

    public Builder fakeMongoRule(FongoRule rule) {
      this.fakeFongoRule = rule;
      return this;
    }

    public Builder realMongoRule(FongoRule rule) {
      this.realFongoRule = rule;
      return this;
    }

    public Builder providerName(String name) {
      this.providerName = name;
      return this;
    }

    public MongoProvider createProvider() {
      Objects.requireNonNull(providerName);
      switch (providerName) {
        case FAKE_MONGO:
          Objects.requireNonNull(fakeFongoRule);
          return new FongoProvider(fakeFongoRule.getDatabase());
        case REAL_MONGO:
          Objects.requireNonNull(realFongoRule);
          return new FongoProvider(realFongoRule.getDatabase());
        case MONGO_JAVA_SERVER:
          return new MongoJavaServerProvider();
        default:
          throw new IllegalArgumentException("Unknown provider " + providerName);
      }
    }
  }
}
