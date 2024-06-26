package my_team;

import my_team.Student;
import java.util.*;

public class Subject {
    // 프로그램 실행 중에 값이 변경되지 않는 상수 리스트로, 필수 과목 목록을 미리 정의
    private static final List<String> REQUIRED_SUBJECTS = Arrays.asList("1.Java", "2.객체지향", "3.Spring", "4.JPA", "5.MySQL");
    private static final List<String> ELECTIVE_SUBJECTS = Arrays.asList("1.디자인_패턴", "2.Spring _ecurity", "3.Redis", "4.MongoDB");

    private static Map<String, List<String>> studentSubjects = new HashMap<>(); // 학생별 과목 목록

    // 수강생 과목 추가
    public static void manageSubjects() throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("수강생 ID를 입력하세요: ");
        String studentId = sc.next();

        // 학생 등록 여부 확인
        // studentId가 Student.isRegistered() 메서드에서 false를 반환하는지 확인
        // 수강생 ID가 등록되지 않았음을 의미
        if (!StudentManagement.isRegistered(studentId)) {
            System.out.println("등록되지 않은 ID 입니다.");
            return;
        }

        // 학생에게 필수과목과 선택과목을 입력받는 과정

        // studentSubjects 맵에서 주어진 studentId가 키로 존재하지 않으면, 해당 키로 빈 ArrayList를 추가
        // studentSubjects.containsKey(studentId)가 false이면, 해당 키로 아직 아무런 값이 추가되지 않았음을 의미
        // 수강생이 아직 과목을 추가하지 않은 경우, 새로운 과목 목록을 생성하고 해당 수강생 ID와 연결하는 데 사용
        if (!studentSubjects.containsKey(studentId)) {
            studentSubjects.put(studentId, new ArrayList<>());
        }

        // studentSubjects 맵에서 studentId에 해당하는 값을 가져와 subjects 리스트에 할당
        // 수강생 ID에 대해 어떤 과목이 등록되어 있는지 확인하거나 추가 작업을 수행할 때 사용
        List<String> subjectlist = studentSubjects.get(studentId);

        // 필수과목 추가
        System.out.println("필수 과목 목록: " + REQUIRED_SUBJECTS);
        System.out.println("필수 과목을 최소 3개 이상 선택하세요.");
        boolean addingRequiredSubjects = true;
        int subjectSize = 0;

        // addingRequiredSubjects 변수가 true인 동안 계속 실행되는 while 루프를 시작
        // addingRequiredSubjects 변수가 false가 되면 루프가 종료

        while (addingRequiredSubjects) {
            System.out.print("희망하는하는 필수 과목의 번호를 입력하세요: ");
            int requiredSubjectsIndex = sc.nextInt();
            // 입력한 번호가 1이상이고 리스트 크기 이하인지 확인
            if (requiredSubjectsIndex >= 1 && requiredSubjectsIndex <= REQUIRED_SUBJECTS.size()) {
                //번호에 따른 과목을 가져옴
                String requiredSubject = REQUIRED_SUBJECTS.get(requiredSubjectsIndex - 1);
                //가져온 과목이 이미 선택한 과목인지 확인 아니라면 목록에 추가
                if (!subjectlist.contains(requiredSubject)) {
                    subjectlist.add(requiredSubject);
                    subjectSize ++;
                } else {
                    System.out.println("이미 선택한 과목입니다.");
                }
            }// 번호가 맞는 번호인지 확인 아니라면 목록에 추가
            else {
                System.out.println("올바른 번호를 입력하세요.");
            }

            // subjectlist 크기가 3 이상인지를 확인하는 조건문
            if (subjectlist.size() >= 3) {
                System.out.print("필수 과목을 더 추가하시나요? (YES/NO): ");
                String answer = sc.next();

                // answer가 "NO"와 같은지, 대소문자를 구분하지 않고 확인
                if ("NO".equalsIgnoreCase(answer)) {
                    // 만약 "NO"와 같다면, addingRequiredSubjects 변수를 false로 설정하여 필수 과목 추가 작업을 중단
                    addingRequiredSubjects = false; // 선택과목으로 넘어가기
                }
            }
        }


        // 선택과목 추가
        System.out.println("선택 과목 목록: " + ELECTIVE_SUBJECTS);
        boolean addingElectiveSubjects = true;

        while (addingElectiveSubjects) {
            System.out.print("희망하는 선택 과목의 번호를 입력하세요: ");
            int electiveSubjectsIndex = sc.nextInt();

            if (electiveSubjectsIndex  >= 1 && electiveSubjectsIndex  <= ELECTIVE_SUBJECTS.size()) {
                String electiveSubject = ELECTIVE_SUBJECTS.get(electiveSubjectsIndex  - 1);
                if (!subjectlist.contains(electiveSubject)) {
                    subjectlist.add(electiveSubject);
                } else {
                    System.out.println("이미 선택한 과목입니다.");
                }
            } else {
                System.out.println("올바른 번호를 입력하세요.");
            }

            if (subjectlist.stream().filter(ELECTIVE_SUBJECTS::contains).count() >= 2) {
                System.out.print("선택 과목을 더 추가하시나요? (YES/NO): ");
                String answer = sc.next();
                if ("NO".equalsIgnoreCase(answer) || subjectlist.size() >= subjectSize + 4 ) {
                    Score.displayScoreView(); //일단 이거를 내일
                    addingElectiveSubjects = false;
                }
            }
        }

        // 최종 선택된 과목 목록 출력
        System.out.println("수강생 ID: " + studentId);
        System.out.println("필수 과목: " + subjectlist.stream().filter(REQUIRED_SUBJECTS::contains).toList());
        System.out.println("선택 과목: " + subjectlist.stream().filter(ELECTIVE_SUBJECTS::contains).toList());

    }
}