package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import java.io.*;

public class Users extends Controller {

  private List<String> online = new ArrayList<String>();

  public Result create()
  {
    Form<User> form = Form.form(User.class);
    return ok(views.html.user.signup.render(form));
  }

  public Result signin()
  {
    String username = session("Username");
    if(username != null && online.contains(username))
    {
      return redirect(controllers.routes.Application.test());
    } else {
      return ok(views.html.user.signin.render());
    }
  }

  public Result login()
  {
    String username = Form.form().bindFromRequest().get("username");
    String password = Form.form().bindFromRequest().get("password");

    if(username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
      if(User.authenticate(username, password)) {
        if(!online.contains(username)) online.add(username);
        session().remove("Username"); //Para que o session username seja atualizado caso novo usuario de sign in
        session("Username",username);
        return redirect(controllers.routes.Users.account(username));
      }
      else
        flash("login_error","Nome de usuário ou senha inválidos!");
        return redirect("/signin");
    }
    return ok(views.html.user.signin.render());
  }


  public Result list_all(String username)
  {
    if(request().getHeader("referer") == null || !request().getHeader("referer").contains("/" + username))
      return redirect(controllers.routes.Application.forbidden_act("You are not allowed to access this route directly from the browser"));

    List<URL> listing = URL.find.where().like("owner",username).findList();
    List<String> generatedUrlList = new ArrayList<>();

    for(URL url : listing) {
      generatedUrlList.add(get_generated_url(url.get_generated(), url.get_owner()));
    }
    return ok(views.html.user.list_all.render(listing,generatedUrlList,username));
  }

  /*NOTE: This method is supposed to handle the 4 types of links!*/
  public Result result(String username, String generatedSlug) {
      String generatedUrl;
      System.out.println("Estou no result!");
      System.out.println("Generated slug : " + generatedSlug);
      System.out.println("Header : " + request().getHeader("referer"));

      if(
          request().getHeader("referer") == null || 
          (
            !request().getHeader("referer").contains("slType=shorten") && 
            !request().getHeader("referer").contains("slType=customize") && 
            !request().getHeader("referer").contains("slType=secure") && 
            !request().getHeader("referer").contains("slType=temporary")
          )
        ) {
        System.out.println("Deu errado no result!");
        return redirect(controllers.routes.Application.forbidden_act("You are not allowed to access this route directly from the browser"));
      }
      else {
        System.out.println("Deu certo no result!");
        generatedUrl = get_generated_url(username, generatedSlug);
        return ok(views.html.user.result.render(generatedUrl, username));
      }
  }


  public Result signup()
  {
    Form<User> userForm = Form.form(User.class).bindFromRequest();

    if(request().getHeader("referer") == null)
      return redirect(controllers.routes.Application.forbidden_act(""));
    if(userForm.hasErrors())
      return badRequest(views.html.user.signup.render(userForm));
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

  public Result signout(String username)
  {
    online.remove(username);          //Remove da lista de users online
    session().remove("Username");     //Remove o cookie
    return redirect(controllers.routes.Application.test());
  }

  public Result account(String username)
  {
    if(username != null && online.contains(username)) {
      return ok(views.html.user.actions.render());
    } else {
      return redirect(controllers.routes.Application.forbidden_act("You must be logged in to do that!"));
    }

  }

  // increase the count +1
  public Result redir(String username, String generatedSlug)
  {
    String real_link = URL.real_link(username, generatedSlug);
    char c = URL.is_of_type(username, generatedSlug);
    String generatedUrl = get_generated_url(username, generatedSlug);
    
    if(URL.already_generated(username, generatedSlug) && real_link != null && c != 'u') {
      
      if(c == 'p') {
        return ok(views.html.user.barrier.render(username, generatedSlug));
      }
      if(c == 't' && URL.is_expired(username, generatedSlug)) {
        System.out.println("Link expirado!!!!");
        return redirect(controllers.routes.Application.forbidden_act("Sorry! Your temporary link is expired, this is your original link : " + real_link));
      }

      return redirect(real_link);
    }
    else {
      return redirect(controllers.routes.Application.forbidden_act("Sorry! Ours dwarves lost themselves in the tunnels and couldn't find out the exit to you!"));
    }
  }

  public Result bypass_barrier(String generatedSlug)
  {
    String golden_shovel = Form.form().bindFromRequest().get("goldenShovel");
    String username = Form.form().bindFromRequest().get("username");

    if(!golden_shovel.isEmpty() && !username.isEmpty()) {
      String pass = URL.access_granted(username, generatedSlug);
      if(pass != null && golden_shovel.equals(pass)){
        String real_link = URL.real_link(username, generatedSlug);
        return redirect(real_link);
      }
    }

    return redirect(controllers.routes.Application.forbidden_act("Sorry! Invalid Username OR Pass. Try it again!"));
  }

  public Result access_granted(String username, String shovels)
  {
    String real_link = URL.real_link(username, shovels);
    if(request().getHeader("referer") == null || username.isEmpty() || shovels.isEmpty() || real_link == null)
      return redirect(controllers.routes.Application.forbidden_act("You are not allowed to access this route directly from the browser"));
    return redirect("http://" + real_link);
  }

  public Result logout()
  {
    String username = session("Username");
    if(username != null) {
      if(online.contains(username)) {
        online.remove(username);
      } 
      session().clear();
      return redirect(controllers.routes.Application.test());
    } else {
      flash("login_error","You must be logged in to do that!");
      return redirect(controllers.routes.Users.signin());
    }
  }

  public String get_generated_url(String username, String generatedSlug) {
    String userPath = controllers.routes.Users.redir(username, generatedSlug).toString();
    String generatedUrl = request().host() + userPath;
    return generatedUrl;
  }

}
