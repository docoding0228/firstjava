package my2_team2;

import java.util.*;
import static my2_team2.Student.pushID;

public class Subject {
    private static final List<String> REQUIRED_SUBJECTS = Arrays.asList("Java", "객체지향", "Spring", "JPA", "MySQL");
    private static final List<String> ELECTIVE_SUBJECTS = Arrays.asList("디자인_패턴", "Spring_Security", "Redis", "MongoDB");

    private static Map<String, List<String>> studentSubjects = new HashMap<>();

    private static Scanner sc = new Scanner(System.in);

    public static List<String> getAllSubjects() {
        List<String> allSubjects = new ArrayList<>(REQUIRED_SUBJECTS);
        allSubjects.addAll(ELECTIVE_SUBJECTS);
        return allSubjects;
    }

    public static boolean deleteStudentSubjects(String studentId) {
        if (studentSubjects.containsKey(studentId)) {
            studentSubjects.remove(studentId);
            return true;
        } else {
            return false;
        }
    }

    public static void manageSubjects() throws InterruptedException {
        String studentId = pushID();

        if (!Student.isRegistered(studentId)) {
            System.out.println("등록되지 않은 ID 입니다.");
            return;
        }

        if (!studentSubjects.containsKey(studentId)) {
            studentSubjects.put(studentId, new ArrayList<>());
        }

        List<String> subjectlist = studentSubjects.get(studentId);

        System.out.print("필수 과목 목록: ");
        for (int i = 0; i < ELECTIVE_SUBJECTS.size(); i++) {
            System.out.print("[" + (i + 1) + ". " + REQUIRED_SUBJECTS.get(i) + "], ");
        }

        System.out.println("[" + (5) + ". " + REQUIRED_SUBJECTS.get(4) + "]");

        int subjectSize = 0;
        while (true) {
            if(subjectlist.size() >= 5) {
                break;
            } else if (subjectlist.size() >= 3) {
                System.out.print("필수 과목을 더 추가하시나요? (YES/NO): ");
                String answer = sc.next();
                if ("NO".equalsIgnoreCase(answer)) {
                    break;
                }
            }

            System.out.print("희망하는하는 필수 과목의 번호를 입력하세요: ");
            int requiredSubjectsIndex = sc.nextInt();

            if (requiredSubjectsIndex >= 1 && requiredSubjectsIndex <= REQUIRED_SUBJECTS.size()) {
                String requiredSubject = REQUIRED_SUBJECTS.get(requiredSubjectsIndex - 1);
                if (!subjectlist.contains(requiredSubject)) {
                    subjectlist.add(requiredSubject);
                    subjectSize++;
                } else {
                    System.out.println("이미 선택한 과목입니다.");
                }
            }
            else {
                System.out.println("올바른 번호를 입력하세요.");
            }
        }

        System.out.print("선택 과목 목록: ");
        for (int i = 0; i < ELECTIVE_SUBJECTS.size() - 1; i++) {
            System.out.print("[" + (i + 1) + ". " + ELECTIVE_SUBJECTS.get(i) + "], ");
        }
        System.out.println("[4. " + ELECTIVE_SUBJECTS.get(3) + "]");

        while (true) {
            if(subjectlist.size() >= subjectSize + 4){
                break;
            }

            if (subjectlist.stream().filter(ELECTIVE_SUBJECTS::contains).count() >= 2) {
                System.out.print("선택 과목을 더 추가하시나요? (YES/NO): ");
                String answer = sc.next();
                if (answer.equalsIgnoreCase("no")) {
                    break;
                }
            }

            System.out.print("희망하는 선택 과목의 번호를 입력하세요: ");
            int electiveSubjectsIndex = sc.nextInt();

            if (electiveSubjectsIndex >= 1 && electiveSubjectsIndex <= ELECTIVE_SUBJECTS.size()) {
                String electiveSubject = ELECTIVE_SUBJECTS.get(electiveSubjectsIndex - 1);
                if (!subjectlist.contains(electiveSubject)) {
                    subjectlist.add(electiveSubject);
                } else {
                    System.out.println("이미 선택한 과목입니다.");
                }
            } else {
                System.out.println("올바른 번호를 입력하세요.");
            }
        }

        System.out.println("수강생 ID: " + studentId);
        System.out.println("필수 과목: " + subjectlist.stream().filter(REQUIRED_SUBJECTS::contains).toList());
        System.out.println("선택 과목: " + subjectlist.stream().filter(ELECTIVE_SUBJECTS::contains).toList());

    }

    public static List<String> getStudentSubjects(String studentId) {
        if (studentSubjects.containsKey(studentId)) {
            return new ArrayList<>(studentSubjects.get(studentId));
        } else {
            return Collections.emptyList();
        }
    }

    public static List<String> getRequiredSubjects() {
        return REQUIRED_SUBJECTS;
    }

    public static List<String> getElectiveSubjects() {
        return ELECTIVE_SUBJECTS;
    }

    public static void subjectCheck() {
        System.out.println("수강생 ID를 입력해주세요:");
        String studentId = sc.next();

        if (!Student.isRegistered(studentId)) {
            System.out.println("등록되지 않은 학생입니다.");
        } else {
            System.out.println("수강생 ID: " + studentId);
            List<String> subjectlist = studentSubjects.get(studentId);
            if (subjectlist != null) {
                System.out.println("수강 과목: " + subjectlist);
            } else {
                System.out.println("등록된 과목이 없습니다.");
            }
        }
    }

    public static void subjectEdit() {
        System.out.println("수정할 수강생 ID를 입력해주세요.");
        String pushId = sc.next();

        if (!Student.isRegistered(pushId)) {
            System.out.println("등록되지 않은 학생입니다.");
            return;
        }
        while (true) {
            List<String> subjectlist = studentSubjects.get(pushId);
            if (subjectlist == null) {
                System.out.println("수정할 과목이 없습니다.");
                break;
            } else {
                System.out.println("수정할 과목: " + subjectlist);
                System.out.println("1. 추가");
                System.out.println("2. 삭제");
                int editChoice = sc.nextInt();

                if (editChoice == 1) {
                    System.out.println("추가할 과목의 종류를 선택하세요 (필수과목:1, 선택과목:2): ");
                    int category = sc.nextInt();
                    List<String> subjectCategory = null;
                    if (category == 1) {
                        subjectCategory = REQUIRED_SUBJECTS;
                    }else if (category == 2) {
                        subjectCategory = ELECTIVE_SUBJECTS;
                    }

                    switch (category) {
                        case 1:
                            System.out.println(REQUIRED_SUBJECTS);
                            break;
                        case 2:
                            System.out.println(ELECTIVE_SUBJECTS);
                            break;
                        default:
                            System.out.println("잘못된 입력입니다.");
                            break;
                    }

                    System.out.println("추가할 과목의 번호를 입력하세요: ");
                    int newSubjectIndex = sc.nextInt();
                    if (newSubjectIndex >= 1 && newSubjectIndex <= subjectCategory.size()) {
                        String newSubject = subjectCategory.get(newSubjectIndex - 1);
                        if (!subjectlist.contains(newSubject)) {
                            subjectlist.add(newSubject);
                            System.out.println("과목이 추가되었습니다.");
                            break;
                        } else {
                            System.out.println("이미 선택한 과목입니다.");
                        }
                    } else {
                        System.out.println("잘못된 과목 번호입니다.");
                    }
                } else if (editChoice == 2) {
                    System.out.println("삭제할 과목 번호를 입력하세요: ");
                    int subjectIndex = sc.nextInt();

                    if (subjectIndex >= 1 && subjectIndex <= subjectlist.size()) {
                        subjectlist.remove(subjectIndex - 1);
                        System.out.println("과목이 삭제되었습니다.");
                        break;
                    } else {
                        System.out.println("잘못된 선택입니다.");
                    }
                } else {
                    System.out.println("잘못된 과목 번호입니다.");
                }
            }
        }
    }
}