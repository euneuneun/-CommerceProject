package commerce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Commerce!");

        List<Category> categories = new ArrayList<>();

        categories.add(new Category("전자제품", Arrays.asList(
        new Product("MacBook Air13",1500000,"M4, 2025년 모델",10),
        new Product("Pebble Mouse 2",20000,"블루투스 마우스", 5),
        new Product("Ipad Air4", 700000, "2021년 모델", 2))));


        categories.add(new Category("의류", Arrays.asList(
                new Product("니트",10000,"프리사이즈",5),
                new Product("바지",20000,"여름옷",10),
                new Product("신발", 130000,"나이키 20206년 신상",30)
        )));

        categories.add(new Category("식품",Arrays.asList(
                new Product("떡갈비",12000,"200g",10),
                new Product("아이스크림",600,"와일드바디",3),
                new Product("치즈스틱",12000,"120g",8)
        )));

        CommerceSystem commerceSystem = new CommerceSystem(categories);
        commerceSystem.start();




    }
}
