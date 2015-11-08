import java.util.Date;

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
