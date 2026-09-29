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


        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ] ");

        for(int i = 0; i<products.size();i++)
        {
            Product p = products.get(i);
            System.out.printf("%d. %s | %,10d원 | %s | 재고 %d개%n", i+1, p.getName(), p.getPrice(), p.getDesciption(),p.getStock());
        }




    }
}
