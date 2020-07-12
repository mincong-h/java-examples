package io.mincong.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import io.mincong.protobuf.Messages.UserCreateRequest;
import io.mincong.protobuf.Messages.UserCreateResponse;
import org.junit.jupiter.api.Test;

class ProtoTest {

  @Test
  void userCreateRequest() {
    var request =
        UserCreateRequest.newBuilder().setFirstName("First").setLastName("Last").setAge(28).build();

    assertThat(request.getAge()).isEqualTo(28);
    assertThat(request.getFirstName()).isEqualTo("First");
    assertThat(request.getLastName()).isEqualTo("Last");
  }

  @Test
  void userCreateResponse() {
    var response =
        UserCreateResponse.newBuilder()
            .setId("1")
            .setFirstName("First")
            .setLastName("Last")
            .setEmail("first.last@example.com")
            .setAge(28)
            .build();

    assertThat(response.getId()).isEqualTo("1");
    assertThat(response.getAge()).isEqualTo(28);
    assertThat(response.getFirstName()).isEqualTo("First");
    assertThat(response.getLastName()).isEqualTo("Last");
    assertThat(response.getEmail()).isEqualTo("first.last@example.com");
  }
}
