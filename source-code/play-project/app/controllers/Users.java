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
      user.save();
      return redirect(controllers.routes.Users.create());
    }
  }
}
