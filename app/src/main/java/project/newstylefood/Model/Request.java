package project.newstylefood.Model;

import java.util.List;

/**
 * Created by PC on 2017/11/7.
 */

public class Request
{
    private String phone;
    private String name;
    private String time;
    private String uid;

    public Request()
    {

    }

    public Request(String name, String phone, String time, String uid)
    {
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
