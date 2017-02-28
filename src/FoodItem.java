import java.util.Date;

/**
 * Created by BHarris on 2/27/17.
 */
public class FoodItem {
    int id;
    String name;
    Date date = new Date();


    public FoodItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

}

