package new10.example.com.myapplication.Model;

public class Ingredient {
    private float quantity;
    private String measure;
    // at database is "ingredient"
    private String name;

    public Ingredient(float quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }
}
