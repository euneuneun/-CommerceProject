package commerce;

import java.util.List;

public class Category {

    private final String name;
    private final List<Product> products;

    public Category(String name, List<Product> products){
        this.name = name;
        this.products = products;
    }

    public String getName(){
        return name;
    }

    public List<Product> getProducts(){
        return products;
    }
}
