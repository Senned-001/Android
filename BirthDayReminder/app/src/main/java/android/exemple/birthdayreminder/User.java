package android.exemple.birthdayreminder;

public class User {
    private long id;
    private String name;
    private int day;
    private int month;
    private int age;

    public User(long id, String name, int day, int month, int age) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.month = month;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getAge() {
        return age;
    }
}
