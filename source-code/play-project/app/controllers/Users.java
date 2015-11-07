package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.List;
import java.util.ArrayList;

public class Users extends Controller {

  private List<String> online = new ArrayList<String>();

  public Result create()
  {
    Form<User> form = Form.form(User.class);
    return ok(views.html.user.form.render(form));
  }

  public Result signin()
  {
    String username = Form.form().bindFromRequest().get("username");
    String password = Form.form().bindFromRequest().get("password");
    if(username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
      if(User.authenticate(username, password)) {
        if(!online.contains(username)) online.add(username);
        return redirect(controllers.routes.Users.account(username));
      }
      else
        /*TODO: Raise in thw view Wrong username/password message*/
        flash("login_error","Nome de usuário ou senha inválidos!");
        return redirect("/signin");
    }
    return ok(views.html.user.signin.render());
  }

  public Result account(String username)
  {
    Form<User> form = Form.form(User.class);
    if(request().getHeader("referer") == null)
      /*TODO: Raise "You are not allowed to access this route directly from browser"*/
      return forbidden("You are not allowed to access this route directly from the browser");
    else return ok(views.html.user.actions.render(username));
  }

  public Result postForm()
  {
    Form<User> userForm = Form.form(User.class).bindFromRequest();

    if(userForm.hasErrors())
      return badRequest(views.html.user.form.render(userForm));
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
}
