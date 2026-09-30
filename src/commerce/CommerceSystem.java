package commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private List<Product> products;

    public CommerceSystem(List<Product> products){
        this.products = products;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            printProducts();
            System.out.print("번호를 입력하세요: ");

            try{
                int input = Integer.parseInt(scanner.nextLine());

                if (input == 0){
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                }

                if(input < 1 || input >products.size()){
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }

                Product selected = products.get(input - 1);
                System.out.printf("%s 상품을 선택했습니다. %n", selected.getName());

            }catch (NumberFormatException e){
                System.out.println("숫자만 입력해주세요.");
            }

        }

        scanner.close();
    }

    private void printProducts(){
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        for(int i = 0; i<products.size(); i++){
            Product p = products.get(i);
            System.out.printf("%d. %-12s | %,10d원 | %s%n", i+1,p.getName(), p.getPrice(), p.getDesciption());
        }
        System.out.println("0. 종료         | 프로그램 종료");
    }


}
