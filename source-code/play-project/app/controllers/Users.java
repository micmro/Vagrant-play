package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

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
        session("Username",username);
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
      return redirect(controllers.routes.Users.result(username, original, shortened));
    }

    return ok(views.html.user.shorten.render(username));
  }


  /*NOTE: This method is supposed to handle the 4 types of links!*/
  public Result result(String username, String original, String generated) {
      if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/shorten"))
        return forbidden("You are not allowed to access this route directly from the browser");
      else {
        URL url = new URL();
        url.set_owner(username);
        url.set_generated(generated);
        url.set_original(original);
        url.set_creation(LocalDate.now());
        if(request().getHeader("referer").contains("/shorten")) url.set_type('s');
        url.save();
        return ok(views.html.user.result.render(generated, username));
      }
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
    if(URL.already_generated(username, shovels))
      return redirect("http://" + URL.real_link(username, shovels));
    else
      return forbidden("There are still so much land to be digged...");
  }

}
