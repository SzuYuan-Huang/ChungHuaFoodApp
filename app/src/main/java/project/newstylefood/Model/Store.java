package project.newstylefood.Model;

/**
 * Created by PC on 2017/5/24.
 */

public class Store
{
    String titleId;
    String titleName;
    String titleImage;
    String titleMenuImage;
    String titleIntroduction;
    String titleAddress;
    String titlephone;
    double latitude;
    double longitude;

    public Store()
    {

    }

    public Store(String titleId, String titleName)
    {
        this.titleId = titleId;
        this.titleName = titleName;
    }

    public String getTitleId()
    {
        return titleId;
    }

    public String getTitleName()
    {
        return titleName;
    }

    public String getTitleImage()
    {
        return titleImage;
    }

    public String getTitleMenuImage()
    {
        return titleMenuImage;
    }

    public String getTitleIntroduction()
    {
        return titleIntroduction;
    }

    public String getTitleAddress()
    {
        return titleAddress;
    }

    public String getTitlephone() {
        return titlephone;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
