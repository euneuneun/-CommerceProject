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

                Category selected = categories.get(input - 1);
                System.out.printf("%s 카테고리를 선택했습니다. %n", selected.getName());
                selectProduct(scanner, selected);

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
        System.out.println("0. 종료         | 프로그램 종료");
    }

    private void selectProduct(Scanner scanner, Category category){
        List<Product> products = category.getProducts();
        printProducts(products);
        System.out.print("번호를 입력하세요: ");

        try{
            int input = Integer.parseInt(scanner.nextLine());

            if (input == 0){
                return;
            }

            if(input < 1 || input > products.size()){
                System.out.println("올바른 번호를 입력해주세요.");
                return;
            }

            Product p = products.get(input - 1);
            System.out.printf("선택한 상품: %s | %,d원 | %s | 재고: %d개%n",
                    p.getName(), p.getPrice(), p.getDescription(), p.getStock());

        }catch (NumberFormatException e){
            System.out.println("숫자만 입력해주세요.");
        }
    }

    private void printProducts(List<Product> products){
        for (int k = 0; k<products.size();k++){
            Product p = products.get(k);
            System.out.printf("%d. %-12s | %,10d원 | %s%n",k+1 ,p.getName(), p.getPrice(), p.getDescription());
        }

        System.out.println("0. 뒤로가기     | 메인으로 돌아가기");
    }


}
