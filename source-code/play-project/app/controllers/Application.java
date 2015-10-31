package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import java.util.Map;
import java.util.Arrays;

import views.html.*;

public class Application extends Controller {

    private Tunnel tnl = new Tunnel();

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result test() {
        return ok(test.render());
    }

    public Result dig() {
      String shortened = null, original = Form.form().bindFromRequest().get("originalLink");
      if(original != null) {
        shortened = tnl.dig(original);
        return redirect(controllers.routes.Application.tunnel(shortened));
      }
      return ok(dig.render());
    }

    public Result tunnel(String shovels) {
        return ok(tunnel.render(shovels));
    }

    public Result shovel(String shovels) {
      if(!tnl.have(shovels)) {
        return forbidden();
      }
      else {
        //TODO: Redirect me to the external URL stored in tnl.end_point(shovels) 
        return ok(tnl.end_point(shovels));
      }
    }
}
