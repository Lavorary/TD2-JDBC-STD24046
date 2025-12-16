public class Ingredient {
    int id;
    String name;
    Double price;
    CategoryEnum category;
    Dish dish;



    String getDishName() {
        return this.name;
    }

}
