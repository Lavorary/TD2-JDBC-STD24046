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


    public Double getDishCost() {

        Double totalPrice = 0.0;
       for (Ingredient ingredient : ingredients) {
           Double Price = ingredient.getPrice();
            Double quantity = ingredient.getRequiredQuantity();

            if(quantity != 0){
                totalPrice += Price * quantity;
            }
            else{
                throw new RuntimeException("quantity required null");
            }


       };

       return totalPrice;


    };


}
