package commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private final List<Category> categories;

    public CommerceSystem(List<Category> categories){
        this.categories = categories;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            printCategory();
            System.out.print("번호를 입력하세요: ");

            try{
                int input = Integer.parseInt(scanner.nextLine());

                if (input == 0){
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                }

                if(input < 1 || input >categories.size()){
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }

                printProducts(input);
                Category selected = categories.get(input - 1);
                System.out.printf("%s 상품을 선택했습니다. %n", selected.getName());

            }catch (NumberFormatException e){
                System.out.println("숫자만 입력해주세요.");
            }

        }

        scanner.close();
    }

    private void printCategory(){
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for(int j =0; j<categories.size();j++){
            Category p = categories.get(j);
            System.out.printf("%d. %s %n",j+1,p.getName());
        }
    }

    private void printProducts(int input){
        Category category = categories.get(input-1);
        List<Product> products = category.getProducts();

        for (int k = 0; k<products.size();k++){
            Product p = products.get(k);
            System.out.printf("%d. %-12s | %,10d원 | %s%n",k+1 ,p.getName(), p.getPrice(), p.getDesciption());
        }


        System.out.println("0. 종료         | 프로그램 종료");
    }


}
