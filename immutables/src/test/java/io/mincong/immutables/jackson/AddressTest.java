package io.mincong.immutables.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

  private final ObjectMapper mapper = new ObjectMapper();
  private final String content =
      "{\"address\":\"55 Rue du Faubourg Saint-Honoré\",\"city\":\"Paris\",\"zipcode\":\"75008\"}";

  @Test
  void itCanSerialize() throws Exception {
    var elysee =
        ImmutableAddress.builder()
            .address("55 Rue du Faubourg Saint-Honoré")
            .city("Paris")
            .postalCode("75008")
            .build();

    var json = mapper.writeValueAsString(elysee);
    assertThat(json).isEqualTo(content);
  }

  @Test
  void itCanDeserialize() throws Exception {
    var actual = mapper.readValue(content, ImmutableAddress.class);
        var expected = ImmutableAddress.builder()
            .address("55 Rue du Faubourg Saint-Honoré")
            .city("Paris")
            .postalCode("75008")
            .build();

    assertThat(actual).isEqualTo(expected);
  }
}
