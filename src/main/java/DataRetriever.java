import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.getInt;

public class DataRetriever {
    Dish findDishById(Integer id){
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("""
        select dish.id, dish.name, dish_type
        from dish
        where dish.id = ?
""");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Dish();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    List<Ingredient> findIngredients(int page, int size){
        DBConnection dbConnection = new DBConnection();
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = """
                SELECT i.id, i.name, i.price, i.category, i.id_dish, d.name as dish_name
                FROM Ingredient i
                LEFT JOIN Dish d ON i.id_dish = d.id
                ORDER BY i.id
                LIMIT ? OFFSET ?
                """;

        int offset = (page - 1) * size;

        try(Connection conn = dbConnection.getDBConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, size);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
              Ingredient ingredient = new Ingredient();

              ingredient.setId(resultSet.getInt("id"));
              ingredient.setName(resultSet.getString("name"));
              ingredient.setPrice(resultSet.getDouble("price"));
              ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("category")));

              if (resultSet.getObject("id_dish") != null) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id_dish"));
                dish.setName(resultSet.getString("name"));
                ingredient.setDish(dish);
                }

              ingredients.add(ingredient);
            }


        } catch (SQLException e) {
            throw new RuntimeException("Cannot find ingredients",e);
        }
        return ingredients;
    }

    List<Ingredient> createIngredients(List<Ingredient> newIngredients){
        DBConnection dbConnection = new DBConnection();

        try{
            String checkSql = """
                    SELECT COUNT(*) FROM Ingredient WHERE name = ?
                    """;
            PreparedStatement checkStatement = dbConnection.getDBConnection().prepareStatement(checkSql);
            for (Ingredient ingredient : newIngredients) {
                checkStatement.setString(1, ingredient.getName());
                ResultSet resultSet = checkStatement.executeQuery();

                if(resultSet.next() && resultSet.getInt(1) > 0){
                    dbConnection.getDBConnection().rollback();
                    throw new RuntimeException("Ingredient " + ingredient.getName() + "already exists");
                }
                checkStatement.clearParameters();
            }

            String sql = """
                INSERT INTO Ingredient (name, price, category) VALUES (?, ?, ?)
        """;
            PreparedStatement insertStmt = dbConnection.getConnection().prepareStatement(sql);
            List<Ingredient> createdIngredients = new ArrayList<>();

            for(Ingredient ingredient : newIngredients){
                insertStmt.setString(1, ingredient.getName());
                insertStmt.setDouble(2, ingredient.getPrice());
                insertStmt.setString(3, ingredient.getCategory().name());

                ResultSet resultSet = insertStmt.executeQuery();
                if(resultSet.next()){
                    ingredient.setId(resultSet.getInt("id"));
                    createdIngredients.add(ingredient);
                }
                insertStmt.clearParameters();
            }
            dbConnection.getDBConnection().commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return newIngredients;
    }

    public Dish saveDish(Dish dishToSave) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            Integer id = dishToSave.getId();
            if(id == null || id == 0){
                String sql = """
                        INSERT INTO Dish (name, dish_type) VALUES (?, ?)
                        
                        """;

                PreparedStatement statement = dbConnection.getConnection().prepareStatement(sql);
                statement.setString(1, dishToSave.getName());
                statement.setString(2, dishToSave.getDishType().name());

                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    id = resultSet.getInt("id");
                    dishToSave.setId(id);
                }
                statement.close();

            }

            else {
                String checkSql = """
                        SELECT COUNT(*) FROM Dish WHERE id = ?;
                        """;
                PreparedStatement checkStatement = dbConnection.getDBConnection().prepareStatement(checkSql);
                checkStatement.setInt(1, id);
                ResultSet resultSet = checkStatement.executeQuery();

                if(resultSet.next() && resultSet.getInt(1) > 0){
                    String updateSql = """
                            UPDATE Dish SET name = ?, dish_type = ? WHERE id = ?;
                    """;
                    PreparedStatement updateStmt = dbConnection.getDBConnection().prepareStatement(updateSql);
                    updateStmt.setString(1, dishToSave.getName());
                    updateStmt.setString(2, dishToSave.getDishType().name());
                    updateStmt.setInt(3, id);
                    updateStmt.executeUpdate();
                    updateStmt.close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Save failed",e);
        }

        return dishToSave;
    }
}
