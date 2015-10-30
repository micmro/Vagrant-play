package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    Tunnel tnl = new Tunnel();

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result test() {
        return ok(test.render());
    }

    public Result tunnel() {
      String new_url = tnl.dig("here_is_my_link.com");
      System.out.println("AQUI:" + new_url);
      return ok(tunnel.render());
    }
}
