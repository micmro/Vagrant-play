package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;

public class Users extends Controller {

  public Result create()
  {
    Form<User> form = Form.form(User.class);
    return ok(views.html.user.form.render(form));
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
