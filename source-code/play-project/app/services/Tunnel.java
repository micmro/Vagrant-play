package services;

import models.URL;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Tunnel {

  //Number of possible characters to create the new link. Right now, using a-z, A-Z and 0-9
  private int shovels = 62;
  //Time in ms that a previously generated URL will be guaranteed to redirect to its first reference. 600000 is 10 minutes
  private long minutes10 = 600000;
  //New URL will be generated using 6 shovels
  private static int small_url_size = 6;
  private HashMap<String, Link> hash = new HashMap<String, Link>();

  public String dig(String original_link, String user)
  {
    String generated;
    String user_urls;

    original_link = trim_link(original_link);
    generated = generate_shortened_url(original_link);
    /*If its a member...*/
    if(!user.isEmpty())
      while(URL.already_generated(user, generated)) {
        generated = generate_shortened_url(generated);
        System.out.println(generated);
      }
    /*Otherwise*/
    else generated = insert_link(generated, original_link);

    return generated;
  }

  public boolean have(String generated)
  {
    return hash.containsKey(generated);
  }

  public String end_point(String generated)
  {
    return (hash.get(generated)).get_original_link();
  }

  private String trim_link(String original) {
    if(original.contains("://")) original = original.split("://")[1];
    if(original.contains("www.")) original = original.split("www\\.")[1];
    return original;
  }

  private String insert_link(String generated, String original_link)
  {
    Date now = new Date();
    if(!hash.containsKey(generated) || (hash.containsKey(generated) && (now.getTime() - ((hash.get(generated)).get_creation_date()).getTime() >= minutes10)))
      hash.put(generated, new Link(original_link));
    else {
      /*Must have passed 10 minutes to use a key previously generated, otherwise a new key is created*/
      while(hash.containsKey(generated) && (now.getTime() - ((hash.get(generated)).get_creation_date()).getTime() < minutes10)) {
        /*Modifier must be > 1*/
        int modifier = to_number(generated) + 2;

        generated = "";
        for(int index = 0; index < small_url_size; index++)
          /*we don't want to multiplicate by zero*/
          generated += replace_by_value(((index + 1) * modifier) % 62);
      }
      hash.put(generated, new Link(original_link));
    }
    return generated;
  }

  private String generate_shortened_url(String original)
  {
    String generated = "";
    Random random = new Random(System.currentTimeMillis());

    for(int index = 0, new_size = small_url_size, original_size = original.length(); new_size > 0; new_size--) {
      int value = 0, actual_char;
      int characters = (int)Math.ceil(((double)original_size) / new_size);
      for(actual_char = 0; actual_char < characters; actual_char++) {
        value += original.charAt(index++);
      }
      original_size -= actual_char;
      generated += replace_by_value((value + random.nextInt(62)) % 62);
    }

    return generated;
  }

  private int to_number(String generated)
  {
    int value = 0;
    for(int index = 0; index < generated.length(); index++)
      value += get_value(generated.charAt(index));
    return value;
  }

  private String replace_by_value(int value)
  {
    String str = "";
    switch(value) {
      case 0: str = "a"; break;
      case 1: str = "b"; break;
      case 2: str = "c"; break;
      case 3: str = "d"; break;
      case 4: str = "e"; break;
      case 5: str = "f"; break;
      case 6: str = "g"; break;
      case 7: str = "h"; break;
      case 8: str = "i"; break;
      case 9: str = "j"; break;
      case 10: str = "k"; break;
      case 11: str = "l"; break;
      case 12: str = "m"; break;
      case 13: str = "n"; break;
      case 14: str = "o"; break;
      case 15: str = "p"; break;
      case 16: str = "q"; break;
      case 17: str = "r"; break;
      case 18: str = "s"; break;
      case 19: str = "t"; break;
      case 20: str = "u"; break;
      case 21: str = "v"; break;
      case 22: str = "w"; break;
      case 23: str = "x"; break;
      case 24: str = "y"; break;
      case 25: str = "z"; break;
      case 26: str = "A"; break;
      case 27: str = "B"; break;
      case 28: str = "C"; break;
      case 29: str = "D"; break;
      case 30: str = "E"; break;
      case 31: str = "F"; break;
      case 32: str = "G"; break;
      case 33: str = "H"; break;
      case 34: str = "I"; break;
      case 35: str = "J"; break;
      case 36: str = "K"; break;
      case 37: str = "L"; break;
      case 38: str = "M"; break;
      case 39: str = "N"; break;
      case 40: str = "O"; break;
      case 41: str = "P"; break;
      case 42: str = "Q"; break;
      case 43: str = "R"; break;
      case 44: str = "S"; break;
      case 45: str = "T"; break;
      case 46: str = "U"; break;
      case 47: str = "V"; break;
      case 48: str = "W"; break;
      case 49: str = "X"; break;
      case 50: str = "Y"; break;
      case 51: str = "Z"; break;
      case 52: str = "0"; break;
      case 53: str = "1"; break;
      case 54: str = "2"; break;
      case 55: str = "3"; break;
      case 56: str = "4"; break;
      case 57: str = "5"; break;
      case 58: str = "6"; break;
      case 59: str = "7"; break;
      case 60: str = "8"; break;
      case 61: str = "9"; break;
    }
    return str;
  }

  private int get_value(char ch)
  {
    int value = 0;
    switch(ch) {
      case 'a': value = 0; break;
      case 'b': value = 1; break;
      case 'c': value = 2; break;
      case 'd': value = 3; break;
      case 'e': value = 4; break;
      case 'f': value = 5; break;
      case 'g': value = 6; break;
      case 'h': value = 7; break;
      case 'i': value = 8; break;
      case 'j': value = 9; break;
      case 'k': value = 10; break;
      case 'l': value = 11; break;
      case 'm': value = 12; break;
      case 'n': value = 13; break;
      case 'o': value = 14; break;
      case 'p': value = 15; break;
      case 'q': value = 16; break;
      case 'r': value = 17; break;
      case 's': value = 18; break;
      case 't': value = 19; break;
      case 'u': value = 20; break;
      case 'v': value = 21; break;
      case 'w': value = 22; break;
      case 'x': value = 23; break;
      case 'y': value = 24; break;
      case 'z': value = 25; break;
      case 'A': value = 26; break;
      case 'B': value = 27; break;
      case 'C': value = 28; break;
      case 'D': value = 29; break;
      case 'E': value = 30; break;
      case 'F': value = 31; break;
      case 'G': value = 32; break;
      case 'H': value = 33; break;
      case 'I': value = 34; break;
      case 'J': value = 35; break;
      case 'K': value = 36; break;
      case 'L': value = 37; break;
      case 'M': value = 38; break;
      case 'N': value = 39; break;
      case 'O': value = 40; break;
      case 'P': value = 41; break;
      case 'Q': value = 42; break;
      case 'R': value = 43; break;
      case 'S': value = 44; break;
      case 'T': value = 45; break;
      case 'U': value = 46; break;
      case 'V': value = 47; break;
      case 'W': value = 48; break;
      case 'X': value = 49; break;
      case 'Y': value = 50; break;
      case 'Z': value = 51; break;
      case '0': value = 52; break;
      case '1': value = 53; break;
      case '2': value = 54; break;
      case '3': value = 55; break;
      case '4': value = 56; break;
      case '5': value = 57; break;
      case '6': value = 58; break;
      case '7': value = 59; break;
      case '8': value = 60; break;
      case '9': value = 61; break;
    }
    return value;
  }

  public class Link
  {
    private Date creation_date;
  	private String original_link;

    public Link(String original_link)
  	{
  		this.creation_date = new Date();
  		this.original_link = original_link;
  	}

    public String get_original_link()
    {
      return this.original_link;
    }

    public Date get_creation_date()
    {
      return this.creation_date;
    }
  }
}
