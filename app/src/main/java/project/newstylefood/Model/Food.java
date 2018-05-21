package project.newstylefood.Model;

/**
 * Created by PC on 2017/11/5.
 */

public class Food
{
    String Image;
    String Name;
    int Price;
    String store;
    String sortNumber;

    public Food()
    {

    }

    public Food(String Image,String Name,int Price,String store,String sortNumber)
    {
        this.Image = Image;
        this.Name = Name;
        this.Price = Price;
        this.store = store;
        this.sortNumber = sortNumber;
    }

    public String getImage()
    {
        return Image;
    }

    public String getName()
    {
        return Name;
    }

    public int getPrice()
    {
        return Price;
    }

    public String getStore()
    {
        return store;
    }

    public String getSortNumber()
    {
        return sortNumber;
    }
}
