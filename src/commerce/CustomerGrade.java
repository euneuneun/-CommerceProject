package commerce;

// 고객 등급과 등급별 할인율을 관리한다
public enum CustomerGrade {
    BRONZE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15);

    private final int discountRate; // 할인율(%)

    CustomerGrade(int discountRate){
        this.discountRate = discountRate;
    }

    public int getDiscountRate(){
        return discountRate;
    }

    // 금액에 대한 할인 금액을 계산한다 (곱셈 중 int 범위를 넘지 않도록 long으로 계산)
    public int calculateDiscount(int price){
        return (int) ((long) price * discountRate / 100);
    }
}
