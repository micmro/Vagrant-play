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

  public Result shorten(String username) {

    String shortened = null, original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    if(original != null && !original.isEmpty()) {
      Tunnel tnl = new Tunnel();
      shortened = tnl.dig(original, username);

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(shortened);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('s');
      url.save();

      return redirect(controllers.routes.Users.result(username, original, shortened));
    }

    return ok(views.html.shortlink.shorten.render(username));
  }

  public Result customize(String username) {

    String customized = Form.form().bindFromRequest().get("customizedLink");
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    /*TODO: check if customized is already in use here*/

    if(original != null && !original.isEmpty() && customized != null && !customized.isEmpty()) {

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(customized);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('c');
      url.save();

      return redirect(controllers.routes.Users.result(username, original, customized));
    }

    return ok(views.html.shortlink.customize.render(username));
  }

  public Result secure(String username) {

    String secured = Form.form().bindFromRequest().get("securedLink");
    String password = Form.form().bindFromRequest().get("goldenShovel");
    String original = Form.form().bindFromRequest().get("originalLink");

    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return forbidden("You are not allowed to access this route directly from the browser");

    /*TODO: check if secured is already in use here*/

    if(original != null && !original.isEmpty() && secured != null && !secured.isEmpty() && password != null && !password.isEmpty()) {

      URL url = new URL();
      url.set_owner(username);
      url.set_generated(secured);
      url.set_original(original);
      url.set_creation(LocalDateTime.now());
      url.set_type('p');
      url.set_password(password);
      url.save();

      return redirect(controllers.routes.Users.result(username, original, secured));
    }

    return ok(views.html.shortlink.secure.render(username));
  }

  public Result temporary(String username) {

    String timedSlug = Form.form().bindFromRequest().get("temporarySlug");
    String expiration = Form.form().bindFromRequest().get("lifetime");
    String original = Form.form().bindFromRequest().get("originalLink");
    String userPath = controllers.routes.Users.account(username).toString();

    System.out.println("timed : " + timedSlug);
    System.out.println("expiration : " + expiration);
    System.out.println("original : " + original);
    System.out.println("userPath : " + userPath);
    System.out.println("path : " + request().host());

    String generated_url = request().host() + userPath + '/' + timedSlug;
    System.out.println("generated url : " + generated_url);

  //   if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
  //     return forbidden("You are not allowed to access this route directly from the browser");

  //   /*TODO: check if timed is already in use here*/

  //   if(original != null && !original.isEmpty() && timedSlug != null && !timedSlug.isEmpty() && expiration != null && !expiration.isEmpty()) {
  //     LocalDateTime d;
  //     URL url = new URL();
  //     url.set_owner(username);
  //     url.set_generated(timed);
  //     url.set_original(original);
  //     url.set_creation(LocalDateTime.now());
  //     url.set_type('t');
  //     if(expiration.charAt(0) == 'm') {
  //       d = url.get_creation();
  //       url.set_expiration(d.plusMinutes(Long.parseLong(expiration.substring(1))));
  //     }
  //     else if(expiration.charAt(0) == 'h') {
  //       d = url.get_creation();
  //       url.set_expiration(d.plusHours(Long.parseLong(expiration.substring(1))));
  //     }
  //     else if(expiration.charAt(0) == 'd') {
  //       d = url.get_creation();
  //       url.set_expiration(d.plusDays(Long.parseLong(expiration.substring(1))));
  //     }
  //     url.save();

  //     return redirect(controllers.routes.Users.result(username, original, timed));
  //   }

  //   return ok(views.html.shortlink.temporary.render(username));
    return redirect(userPath);
  }

}