package week03.w03_7_Inclusion;

public class w03_7_Handle {

    String company; // 핸들 회사
    String type; // 핸들 타입

    public w03_7_Handle(String company, String type) {
        this.company = company;
        this.type = type;
    }

    public void turnHandle(String direction) {
        System.out.println(direction + " 방향으로 핸들을 돌립니다.");
    }
}

