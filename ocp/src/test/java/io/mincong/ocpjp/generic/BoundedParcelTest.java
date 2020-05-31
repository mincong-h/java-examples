package io.mincong.ocpjp.generic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/** @author Mincong Huang */
public class BoundedParcelTest {

  @Test
  public void getShipInfo_phone() throws Exception {
    BoundedParcel<Phone, Exchangeable> phoneParcel = new BoundedParcel<>();
    phoneParcel.set(new Phone());
    assertThat(phoneParcel.getShipInfo()).isEqualTo("Ship by courier XYZ");
  }

  @Test
  public void getShipInfo_book() throws Exception {
    BoundedParcel<Book, Exchangeable> bookParcel = new BoundedParcel<>();
    bookParcel.set(new Book());
    assertThat(bookParcel.getShipInfo()).isEqualTo("Ship by courier ABC");
  }
}
