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
      return ok(views.html.index.render());
  }

  public Result start_dig() {
    System.out.println("At Start dig");
    return ok(views.html.start_dig.render());
  }
  
  public Result dig() {
    String shortened = null;
    String original = Form.form().bindFromRequest().get("originalLink");
    
    if(original != null && !original.isEmpty()) {
      shortened = tnl.dig(original, "");
      shortened = "http://" + request().host() + "/dig/" + shortened;
      
      return ok(views.html.dig_result.render(shortened));
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

  public Result tunnel(String generatedSlug) {
      String original = "http://" + tnl.end_point(generatedSlug);
      if(original != null && !original.isEmpty()) {
        return redirect(original);
      } 
      else {
        return redirect(controllers.routes.Application.forbidden_act("Sorry! The link you are trying to access is no longer available"));
      }
  }
}
