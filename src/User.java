import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by BHarris on 2/27/17.
 */
public class User {
    String name;
    ArrayList<FoodItem> food = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
