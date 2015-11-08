package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Users extends Controller {

  private List<String> online = new ArrayList<String>();

  public Result create()
  {
    Form<User> form = Form.form(User.class);
    return ok(views.html.user.signup.render(form));
  }

  public Result signin()
  {
    String username = Form.form().bindFromRequest().get("username");
    String password = Form.form().bindFromRequest().get("password");

    if(username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
      if(User.authenticate(username, password)) {
        if(!online.contains(username)) online.add(username);
        session().remove("Username"); //Para que o session username seja atualizado caso novo usuario de sign in
        String username_when_logged = "Hello, " + username + "!";
        session("Username",username_when_logged);
        return redirect(controllers.routes.Users.account(username));
      }
      else
        flash("login_error","Nome de usuário ou senha inválidos!");
        return redirect("/signin");
    }
    return ok(views.html.user.signin.render());
  }

  public Result shorten(String username)
  {
    String shortened = null, original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    if(original != null && !original.isEmpty()) {
      Tunnel tnl = new Tunnel();
      shortened = tnl.dig(original, username);

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(shortened);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('s');
      url.save();

      return redirect(controllers.routes.Users.result(username, original, shortened));
    }

    return ok(views.html.user.shorten.render(username));
  }

  public Result customize(String username)
  {
    String customized = Form.form().bindFromRequest().get("customizedLink");
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    /*TODO: check if customized is already in use here*/

    if(original != null && !original.isEmpty() && customized != null && !customized.isEmpty()) {

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(customized);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('c');
      url.save();

      return redirect(controllers.routes.Users.result(username, original, customized));
    }

    return ok(views.html.user.customize.render(username));
  }

  public Result secure(String username)
  {
    String secured = Form.form().bindFromRequest().get("securedLink");
    String password = Form.form().bindFromRequest().get("goldenShovel");
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    /*TODO: check if secured is already in use here*/

    if(original != null && !original.isEmpty() && secured != null && !secured.isEmpty() && password != null && !password.isEmpty()) {

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(secured);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('p');
      url.set_password(password);
      url.save();

      return redirect(controllers.routes.Users.result(username, original, secured));
    }

    return ok(views.html.user.secure.render(username));
  }

  public Result list_all(String username)
  {
    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    List<URL> listing = URL.find.where().like("owner",username).findList();

    return ok(views.html.user.list_all.render(listing,username));
  }

  public Result temporary(String username)
  {
    String timed = Form.form().bindFromRequest().get("timedLink");
    String expiration = Form.form().bindFromRequest().get("lifetime");
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    /*TODO: check if timed is already in use here*/

    if(original != null && !original.isEmpty() && timed != null && !timed.isEmpty() && expiration != null && !expiration.isEmpty()) {
      LocalDateTime d;

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(timed);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('t');
      if(expiration.charAt(0) == 'm') {
        d = url.get_creation();
        url.set_expiration(d.plusMinutes(Long.parseLong(expiration.substring(1))));
      }
      else if(expiration.charAt(0) == 'h') {
        d = url.get_creation();
        url.set_expiration(d.plusHours(Long.parseLong(expiration.substring(1))));
      }
      else if(expiration.charAt(0) == 'd') {
        d = url.get_creation();
        url.set_expiration(d.plusDays(Long.parseLong(expiration.substring(1))));
      }
      url.save();

      return redirect(controllers.routes.Users.result(username, original, timed));
    }

    return ok(views.html.user.temporary.render(username));
  }


  /*NOTE: This method is supposed to handle the 4 types of links!*/
  public Result result(String username, String original, String generated) {
      if(request().getHeader("referer") == null ||
        (!request().getHeader("referer").contains("/shorten") && !request().getHeader("referer").contains("/customize") &&
         !request().getHeader("referer").contains("/secure") && !request().getHeader("referer").contains("/temporary")))
            return forbidden("You are not allowed to access this route directly from the browser");
      else
        return ok(views.html.user.result.render(generated, username));
  }


  public Result signup()
  {
    Form<User> userForm = Form.form(User.class).bindFromRequest();

    if(request().getHeader("referer") == null)
      return forbidden();
    if(userForm.hasErrors())
      return badRequest(views.html.user.signup.render(userForm));
    else {
      User user = userForm.get();
      if(user.username_available(user.username)) {
        user.save();
        /*TODO: Raise message that user was successuly created*/
        return redirect(controllers.routes.Application.test());
      }
      else {
        /*TODO: Raise error to the view: "Username already in use. Select another!"*/
        return redirect(controllers.routes.Users.create());
      }
    }
  }

  public Result signout(String username)
  {
    online.remove(username);          //Remove da lista de users online
    session().remove("Username");     //Remove o cookie
    return redirect(controllers.routes.Application.test());
  }

  public Result account(String username)
  {
    Form<User> form = Form.form(User.class);
    if(request().getHeader("referer") == null || (!request().getHeader("referer").contains("/signin") && !request().getHeader("referer").contains("/u/" + username)))
      /*TODO: Raise "You are not allowed to access this route directly from browser"*/
      return forbidden("You are not allowed to access this route directly from the browser");
    else return ok(views.html.user.actions.render(username));
  }

  public Result redir(String username, String shovels)
  {
    String real_link = URL.real_link(username, shovels);
    char c = URL.is_of_type(username, shovels);
    if(URL.already_generated(username, shovels) && real_link != null && c != 'u') {
      if(c == 'p')
        return ok(views.html.user.ward.render());
      if(c == 't' && URL.is_expired(username, shovels))
        return forbidden("Sorry! Ours dwarves lost themselves in the tunnels and couldn't find out the exit to you!");
      return redirect("http://" + real_link);
    }
    else
      return forbidden("Sorry! Ours dwarves lost themselves in the tunnels and couldn't find out the exit to you!");
  }

  public Result ward()
  {
    String golden_shovel = Form.form().bindFromRequest().get("goldenShovel");
    String username = Form.form().bindFromRequest().get("username");
    String shovels = Form.form().bindFromRequest().get("shovels");

    if(!golden_shovel.isEmpty() && !username.isEmpty()) {
      String pass = URL.access_granted(username, shovels);
      if(pass != null && golden_shovel.equals(pass))
        return redirect(controllers.routes.Users.access_granted(username, shovels));
    }
    /*TODO: Raise "You have failed to break the ward"*/
    return redirect(controllers.routes.Application.test());
  }

  public Result access_granted(String username, String shovels)
  {
    String real_link = URL.real_link(username, shovels);
    if(request().getHeader("referer") == null || username.isEmpty() || shovels.isEmpty() || real_link == null)
      return forbidden("You are not allowed to access this route directly from the browser");
    return redirect("http://" + real_link);
  }

}
