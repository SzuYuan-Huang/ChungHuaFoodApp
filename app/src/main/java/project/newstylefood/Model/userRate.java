package project.newstylefood.Model;

/**
 * Created by PC on 2017/12/5.
 */

public class userRate
{
    private String comment;
    private int star;
    private String email;

    public userRate(String comment, int star, String email)
    {
        this.comment = comment;
        this.star = star;
        this.email = email;
    }

    public userRate()
    {

    }

    public String getComment()
    {
        return comment;
    }

    public int getStar()
    {
        return star;
    }

    public String getEmail()
    {
        return email;
    }
}
