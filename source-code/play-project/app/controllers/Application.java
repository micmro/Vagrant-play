package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;

public class Application extends Controller {

    private Tunnel tnl = new Tunnel();

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result test() {
        return ok(test.render());
    }

    public Result tunnel() {
      String shortened = null, original = Form.form().bindFromRequest().get("originalLink");
      if(original != null)
        shortened = tnl.dig(original);
      return ok(tunnel.render());
    }
}
