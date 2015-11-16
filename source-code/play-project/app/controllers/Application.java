package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import java.util.Map;
import java.util.Arrays;
import services.Tunnel;
import views.html.*;

public class Application extends Controller {

  private Tunnel tnl = new Tunnel();

  public Result index() {
      return ok(index.render("Your new application is ready."));
  }

  public Result test() {
      return ok(test.render());
  }

  public Result start_dig() {
    return ok(views.html.dig.render());
  }
  // Arrumar esse método!
  
  public Result dig() {
    String shortened = null;
    String original = Form.form().bindFromRequest().get("originalLink");
    
    if(original != null && !original.isEmpty()) {
      shortened = tnl.dig(original, "");
      System.out.println("Em dig, : " + shortened);
      return redirect(controllers.routes.Application.tunnel(shortened));
    }
    return redirect(controllers.routes.Application.forbidden_act("Sorry! Something went wrong when you tried to short the link, please try again the link : " + original));
  }

  public Result forbidden_act(String errorMsg) {
    if(errorMsg == null) {
      errorMsg = "Something goes wrong, try to contact the support team.";

    } else if(errorMsg.isEmpty()) {
      errorMsg = "Something goes wrong, try to contact the support team.";
    }

    return ok(views.html.forbidden_act.render(errorMsg));
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
