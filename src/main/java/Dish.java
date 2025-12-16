import lombok.*;

import java.util.List;
@Data
@EqualsAndHashCode
@ToString

public class Dish {
    private int id;
    private String name;
    private DishTypeEnum dishType;
    private List<Ingredient> ingredients;


    public Double getDishPrice() {
       // throw new UnsupportedOperationException("Not implemented.");

        return ingredients.stream()
                .map(Ingredient::getPrice)
                .reduce(0.0, Double::sum);

        /*double totalPrice = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            totalPrice += ingredients.get(i).getPrice();
        }
        return totalPrice;*/

    };


}
