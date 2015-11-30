package unit_tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class TunnelTest() {

  //trim_links
  //Two cases : 
  //  - original.contains("://")
  //  - original.contains("www.")
  // @test

  //replace_by_value
  //Three diffente cases only

  //get_value
  //Three diffente cases only

  //to_number
  //Just one case

  //generate_shortened_url
  //Try to test this method, but It could be hard because it have a random generator number

  //insert_link
  //Two cases : 
  //  - generated is already on hash (if)
  //  - generated is already on hash and time not over (if)
  //  - generated is not on hash and a new key is created
  // Verify in all the cases if the link is present on the hash

  //end_point
  //Just one case
  
  //have
  //Just one case

  //dig
  //Two cases:
  //  - Has user
  //  - Dont have user
}