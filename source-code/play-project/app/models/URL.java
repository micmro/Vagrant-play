package models;

import java.util.*;
import java.time.LocalDate;
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
  /*(S)hort, (T)emporary, (P)rivate and (C)ustomized*/
  @Constraints.Required
  public char type;
  @Temporal(TemporalType.TIMESTAMP)
  public LocalDate creation;
  /*Used by temporary links*/
  @Temporal(TemporalType.TIMESTAMP)
  public LocalDate expiration;
  /*Used by private links*/
  public String password;

  public static Finder<String, URL> find = new Finder<String, URL>(String.class, URL.class);

  public static boolean already_generated(String username, String generated)
  {
    List<URL> list = find.where().like("owner", username).findList();

    for (int i = 0; i < list.size(); i++)
			if(generated.equals(list.get(i).get_generated())) return true;
    return false;
  }

  public static String real_link(String username, String generated)
  {
    int i;
    List<URL> list = find.where().like("owner", username).findList();

    for (i = 0; i < list.size(); i++)
			if(generated.equals(list.get(i).get_generated())) break;
    if(i < list.size()) return list.get(i).get_original();
    return null;
  }

  public static char is_of_type(String username, String generated)
  {
    int i;
    List<URL> list = find.where().like("owner", username).findList();

    for (i = 0; i < list.size(); i++)
			if(generated.equals(list.get(i).get_generated())) break;
    if(i < list.size()) return list.get(i).get_type();
    return 'u'; /*= undefined. Failed to retrieve an expected value*/
  }

  public static String access_granted(String username, String generated)
  {
    int i;
    List<URL> list = find.where().like("owner", username).findList();

    for (i = 0; i < list.size(); i++)
			if(generated.equals(list.get(i).get_generated())) break;
    if(i < list.size()) return list.get(i).get_password();
    return null;
  }

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

  public char get_type() {
    return type;
  }

  public void set_type(char type) {
    this.type = type;
  }

  public LocalDate get_creation() {
    return creation;
  }

  public void set_creation(LocalDate creation) {
    this.creation = creation;
  }

  public String get_password() {
    return password;
  }

  public void set_password(String password) {
    this.password = password;
  }
}
