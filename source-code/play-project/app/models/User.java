package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name = "Users")
public class User extends Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column
  @Constraints.Required
  public String username;
  public String password;

  public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);

  public Long get_id() {
    return id;
  }

  public void set_id(Long id) {
    this.id = id;
  }

  public String get_username() {
    return username;
  }

  public void set_username() {
    this.username = username;
  }

  public String get_password() {
    return password;
  }

  public void set_password(String password) {
    this.password = password;
  }
}
