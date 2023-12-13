package boraldan.taks1;

import java.io.Serializable;

public class UserData implements Serializable {

    private String name;
    private int age;
    private transient String password;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public UserData(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

}
