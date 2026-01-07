import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString

public class Ingredient {
    private int id;
    private String name;
    private Double price;
    private Double RequiredQuantity;
    private CategoryEnum category;
    private Dish dish;



    public String getDishName() {
      if (dish != null){
          return dish.getName();
      }
      return null;
    }



}
