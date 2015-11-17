package enums;

public enum ShortLink {

  SHORTEN("shorten"),
  TEMPORARY("temporary"),
  CUSTOMIZE("customize"),
  SECURE("secure");

  private String type;

  ShortLink(String type) {
    this.type = type;
  }
  
  public String getType() {
    return this.type;
  }

  public static ShortLink valueOfSl(String slType) {
    for(ShortLink sl : ShortLink.values()) {
      if(sl.getType().equals(slType)){
        return sl;
      }
    }
    return null;
  }
} 