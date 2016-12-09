package io.mincongh.search;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Indexed
@Table(name = "CUSTOM_ENTITY")
public class CustomEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  @DocumentId
  private Long id;

  @Column(name = "NAME1", nullable = false)
  @NotEmpty
  @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
  private String name1;

  @Column(name = "NAME2", nullable = false)
  @NotEmpty
  @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
  private String name2;

  @Column(name = "NAME3", nullable = false)
  @NotEmpty
  @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
  private String name3;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName1() {
    return name1;
  }

  public void setName1(String name1) {
    this.name1 = name1;
  }

  public String getName2() {
    return name2;
  }

  public void setName2(String name2) {
    this.name2 = name2;
  }

  public String getName3() {
    return name3;
  }

  public void setName3(String name3) {
    this.name3 = name3;
  }

  @Override
  public String toString() {
    return "CustomEntity [id=" + id + ", name1=" + name1 + ", name2=" + name2 + ", name3=" + name3
        + "]";
  }
}
