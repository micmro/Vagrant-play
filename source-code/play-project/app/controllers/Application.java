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
    if(original != null && !original.isEmpty()) {
      shortened = tnl.dig(original, "");
      return redirect(controllers.routes.Application.tunnel(shortened));
    }
    return ok(dig.render());
  }

  public Result tunnel(String shovels) {
      if(!tnl.have(shovels)) return forbidden();
      else return ok(tunnel.render(shovels));
  }

  public Result shovel(String shovels) {
    if(!tnl.have(shovels)) return forbidden();
    else return redirect("http://" + tnl.end_point(shovels));
  }
}
