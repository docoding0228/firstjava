package week03.w03_9_super1;

public class w03_9_SportCar extends w03_9_Car {

    String engine;

    String model = "Ferrari"; // 자동차 모델
    String color = "Red"; // 자동차 색상
    double price = 300000000; // 자동차 가격

    public w03_9_SportCar(String engine) {
        this.engine = engine;
    }

    public void booster() {
        System.out.println("엔진 " + engine + " 부앙~\n");
    }

    public void setCarInfo(String model, String color, double price) {
        super.model = model; // model은 부모 필드에 set
        super.color = color; // color는 부모 필드에 set
        this.price = price; // price는 자식 필드에 set
    }

    @Override
    public double brakePedal() {
        speed = 100;
        System.out.println("스포츠카에 브레이크란 없다");
        return speed;
    }

    @Override
    public void horn() {
        booster();
    }
}
