package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import java.io.*;

public class Users extends Controller {

  private List<String> online = new ArrayList<String>();

  public Result create()
  {
    Form<User> form = Form.form(User.class);
    return ok(views.html.user.signup.render(form));
  }

  public Result signin()
  {
    String username = session("Username");
    if(username != null && online.contains(username))
    {
      return redirect(controllers.routes.Application.test());
    } else {
      return ok(views.html.user.signin.render());
    }
  }

  public Result login()
  {
    String username = Form.form().bindFromRequest().get("username");
    String password = Form.form().bindFromRequest().get("password");

    if(username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
      if(User.authenticate(username, password)) {
        if(!online.contains(username)) online.add(username);
        session().remove("Username"); //Para que o session username seja atualizado caso novo usuario de sign in
        session("Username",username);
        return redirect(controllers.routes.Users.account(username));
      }
      else
        flash("login_error","Nome de usuário ou senha inválidos!");
        return redirect("/signin");
    }
    return ok(views.html.user.signin.render());
  }


  public Result list_all(String username)
  {
    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    List<URL> listing = URL.find.where().like("owner",username).findList();

    return ok(views.html.user.list_all.render(listing,username));
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
    if(username != null && online.contains(username)) {
      return ok(views.html.user.actions.render());
    } else {
      flash("login_error","You must be logged in to do that!");
      return redirect(controllers.routes.Users.signin());
    }

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

  public Result logout()
  {
    String username = session("Username");
    if(username != null) {
      if(online.contains(username)) {
        online.remove(username);
      } 
      session().clear();
      return redirect(controllers.routes.Application.test());
    } else {
      flash("login_error","You must be logged in to do that!");
      return redirect(controllers.routes.Users.signin());
    }
  } 

}
