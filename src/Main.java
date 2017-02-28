import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap<String, User> users = new HashMap<>();
    static int counter = 0;
    public static void main(String[] args) {
        Spark.init();

        Spark.get("/", (request, response)->{
            Session session = request.session();
            HashMap m = new HashMap();
            String name = session.attribute("userName");
            User user = users.get(name);

            if(user == null){
                return new ModelAndView(m, "login.html");
            }else {
                return new ModelAndView(user, "home.html");
            }

        }, new MustacheTemplateEngine());

        Spark.post("/login", (request, response)->{
            String name = request.queryParams("loginName");
            Session session = request.session();

            users.putIfAbsent(name, new User(name));
            session.attribute("userName", name);
            response.redirect("/");
            return "";
        });

        Spark.post("/addFood", (request, response) -> {
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);
            if (user == null) {
                throw new Exception("User is not logged in");
            }

            String foodItem = request.queryParams("foodItem");
            FoodItem item = new FoodItem(users.get(name).food.size(), foodItem);
            users.get(name).food.add(item);
            response.redirect("/");
            return "";
        });

        Spark.post("/logout", (request, response) -> {
            Session session = request.session();
            session.invalidate();
            response.redirect("/");
            return "";
        });

        Spark.post("/delete", (request, response) -> {
            String toDelete = request.queryParams("id");
            int delete = Integer.parseInt(toDelete);
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);
            for (FoodItem i : user.food) {
                if(i.id == delete) {
                    user.food.remove(i);
                    response.redirect("/");
                    return "";
                }
            }
            return "";
        });

        Spark.get("/edit-text", (request, response)->{
            Session session = request.session();
            int id = Integer.parseInt(request.queryParams("id"));
            session.attribute("idNum", id);
            HashMap m = new HashMap();
            String name = session.attribute("userName");
            User user = users.get(name);
            return new ModelAndView(user, "edit.html");

        }, new MustacheTemplateEngine());

        Spark.post("/edit", (request, response) ->{
            Session session = request.session();
            String newText = request.queryParams("editText");
            int id = session.attribute("idNum");
            System.out.println(id);
            String name = session.attribute("userName");
            User user = users.get(name);
            FoodItem food = new FoodItem(users.get(name).food.size(), newText);
            user.food.set(id, food);
            response.redirect("/");
            return "";
        });
    }
}
