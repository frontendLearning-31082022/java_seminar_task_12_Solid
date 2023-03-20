package task_1.refactoring;

import java.util.Scanner;

    // Сделать рефакторинг кода
public class TestDrive {

    // Запаковал POJO классы в один в качестве приватных
    // Инкапсулирована логика работы RobotMap
    // Можно поменять печать в консоль на текстовое возвращаемое значение в рамках полной инкапсуляции
    // Дополнительно можно создать интерфейс для CommmandManager для расширения списка команд
    // , принятие осущесвлять в конструкторе или методе или в методе принимать интерфес и кидать в лист команд, итд.

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Приветствуем пользователя и объясняем, куда он попал
        System.out.println("Введите размеры карты:");
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();

        RobotMap map = new RobotMap(n, m);

        Robot robotTestDrive= map.createRobot(1,1);
        String id=robotTestDrive.getID();
        String point=robotTestDrive.getPoint();
        String direction=robotTestDrive.getDirection();

        while (true) {
            map.help();

            String command = sc.nextLine();
            map.inputComand(command);
        }
    }
}
