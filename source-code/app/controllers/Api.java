package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import enums.ShortLink;
import services.Tunnel;

import java.io.*;

public class Api extends Controller {

  public Result shortlinkBase(String username, String slType) {

    System.out.println("Received type : " + slType);
    ShortLink sl = ShortLink.valueOfSl(slType);
    switch(sl) {
      case SHORTEN:
        return ok(views.html.shortlink.shorten.render(username));
      case TEMPORARY:
        return ok(views.html.shortlink.temporary.render(username));
      case CUSTOMIZE:
        return ok(views.html.shortlink.customize.render(username));
      case SECURE:
        return ok(views.html.shortlink.secure.render(username));
      default:
        return redirect(controllers.routes.Users.account(username));
    }
  }



  public Result secure(String username) {

    String secureSlug = Form.form().bindFromRequest().get("secureSlug");
    String password = Form.form().bindFromRequest().get("goldenShovel");
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return redirect(controllers.routes.Application.forbidden_act("You are not allowed to access this route directly from the browser"));

    if(original != null && !original.isEmpty() && secureSlug != null && !secureSlug.isEmpty() && password != null && !password.isEmpty()) {

      if(!original.contains("http://")) { 
        original = "http://" + original;
      }

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(secureSlug);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('p');
      url.set_password(password);
      url.save();

      return redirect(controllers.routes.Users.result(username, secureSlug));
    } else {
      return redirect(controllers.routes.Application.forbidden_act("Sorry! Something went wrong when you tried to short the link, please try again the link : " + original));
    }
  }

  public Result customize(String username) {

    String customizedSlug = Form.form().bindFromRequest().get("customizedSlug");
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      redirect(controllers.routes.Application.forbidden_act("You are not allowed to access this route directly from the browser"));

    if(original != null && !original.isEmpty() && customizedSlug != null && !customizedSlug.isEmpty()) {

      if(!original.contains("http://")) { 
        original = "http://" + original;
      }

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(customizedSlug);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('c');
      url.save();

      return redirect(controllers.routes.Users.result(username, customizedSlug));
    } else {
      return redirect(controllers.routes.Application.forbidden_act("Sorry! Something went wrong when you tried to short the link, please try again the link : " + original));
    }
  }

  public Result shorten(String username) {

    String shortenedSlug = null;
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return redirect(controllers.routes.Application.forbidden_act("You are not allowed to access this route directly from the browser"));
    if(original != null && !original.isEmpty()) {
      Tunnel tnl = new Tunnel();

      if(!original.contains("http://")) { 
        original = "http://" + original;
      }

      shortenedSlug = tnl.dig(original, username);

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(shortenedSlug);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('s');
      url.save();

      return redirect(controllers.routes.Users.result(username, shortenedSlug));
    } else {
      return redirect(controllers.routes.Application.forbidden_act("Sorry! Something went wrong when you tried to short the link, please try again the link : " + original));
    }
  }

  public Result temporary(String username) {

    String timedSlug = Form.form().bindFromRequest().get("temporarySlug");
    String expiration = Form.form().bindFromRequest().get("lifetime");
    String original = Form.form().bindFromRequest().get("originalLink");
    String userPath = controllers.routes.Users.account(username).toString();

    String generated_url = request().host() + userPath + '/' + timedSlug;

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return redirect(controllers.routes.Application.forbidden_act("You are not allowed to access this route directly from the browser"));
    /*TODO: check if timed is already in use here*/

    if(original != null && !original.isEmpty() && timedSlug != null && !timedSlug.isEmpty() && expiration != null && !expiration.isEmpty()) {

      if(!original.contains("http://")) { 
        original = "http://" + original;
      }

      LocalDateTime d;
      URL url = new URL();
      url.set_owner(username);
      url.set_generated(timedSlug);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('t');
      if(expiration.charAt(0) == 'm') {
        d = url.get_creation();
        url.set_expiration(d.plusMinutes(Long.parseLong(expiration.substring(1))));
      }
      else if(expiration.charAt(0) == 'h') {
        d = url.get_creation();
        url.set_expiration(d.plusHours(Long.parseLong(expiration.substring(1))));
      }
      else if(expiration.charAt(0) == 'd') {
        d = url.get_creation();
        url.set_expiration(d.plusDays(Long.parseLong(expiration.substring(1))));
      }
      url.save();

      return redirect(controllers.routes.Users.result(username, timedSlug));
    } else {
      return redirect(controllers.routes.Application.forbidden_act("Sorry! Something went wrong when you tried to short the link, please try again the link : " + original));
    }
  }

}