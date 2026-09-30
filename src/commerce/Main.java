package commerce;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Commerce!");

        List<Product> products = new ArrayList<>();
        products.add(new Product("MacBook Air13",1500000,"M4, 2025년 모델",10));
        products.add(new Product("Pebble Mouse 2",20000,"블루투스 마우스", 5));
        products.add(new Product("Ipad Air4", 700000, "2021년 모델", 2));


        CommerceSystem commerceSystem = new CommerceSystem(products);
        commerceSystem.start();




    }
}
