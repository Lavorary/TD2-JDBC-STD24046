import lombok.*;

import java.util.List;
@Data
@EqualsAndHashCode
@Builder


public class Dish {
    int id;
    String name;
    DishTypeEnum dishType;
    List<Ingredient> ingredients;


    Double getDishPrice() {
        return ingredients.getLast().price;
    };
}
