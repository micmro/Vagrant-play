package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "URLs")
public class URL extends Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column
  @Constraints.Required
  public String owner;  /*generated URL owners username*/
  @Constraints.Required
  public String generated;
  @Constraints.Required
  public String original;

  public static Finder<String, URL> find = new Finder<String, URL>(String.class, URL.class);

  public Long get_id() {
    return id;
  }

  public void set_id(Long id) {
    this.id = id;
  }

  public String get_owner() {
    return owner;
  }

  public void set_owner(String owner) {
    this.owner = owner;
  }

  public String get_generated() {
    return generated;
  }

  public void set_generated(String generated) {
    this.generated = generated;
  }

  public String get_original() {
    return original;
  }

  public void set_original(String original) {
    this.original = original;
  }
}
