package task_1.refactoring;


import java.util.*;
import java.util.function.Consumer;

public class RobotMap {
    private final int n;
    private final int m;
    private final List<RobotMap.RobotConcrete> robots;
    final private CommandManager manager;

    public RobotMap(int n, int m) {
        this.n = n;
        this.m = m;
        this.robots = new ArrayList<>();
        manager= new CommandManager(this);

        System.out.println("Карта успешно создана");
    }

    public Robot createRobot(int x,int y) {
        Point point=new Point(x,y);
        validatePoint(point);
        Robot robot = new RobotConcrete(point);
        robots.add((RobotConcrete) robot);

        return robot;
    }

    private void acceptRobots(Consumer<RobotMap.RobotConcrete> robotAcceptor) {
        for (RobotMap.RobotConcrete robot : robots) {
            robotAcceptor.accept(robot);
        }
    }

    private Optional<RobotMap.RobotConcrete> getById(Long id) {
        for (RobotMap.RobotConcrete robot : robots) {
            if (id.equals(robot.id)) {
                return Optional.of(robot);
            }
        }

        return Optional.empty();
    }

    private void validatePoint(Point point) {
        validatePointIsCorrect(point);
        validatePointIsFree(point);
    }

    private void validatePointIsCorrect(Point point) {
        if (point.x() < 0 || point.x() > n || point.y() < 0 || point.y() > m) {
            throw new IllegalStateException("Некоректное значение точки!");
        }
    }

    private void validatePointIsFree(Point point) {
        for (RobotConcrete robot : robots) {
            Point robotPoint = robot.point;
            if (robotPoint.equals(point)) {
                throw new IllegalStateException("Точка " + point + " занята!");
            }
        }
    }

    public void help() {
        System.out.println("""
                    Доступные действия:
                    1. Для создания робота введите create x y, где x и y - координаты для нового робота
                    2. Для вывода списка всех созданных роботов, введите list
                    3. Для перемещения робота введите move id, где id - идентификатор робота
                    4. Для изменения направления введите changedir id DIRECTION, где id - идентификатор робота, DIRECTION - одно из значений {TOP, RIGHT, BOTTOM, LEFT}
                    5. Для удаления робота введите delete id, где id - идентификатор робота
                    6. Для выхода напишите exit
                    ... список будет пополняться
                    """);
    }

    public void inputComand(String command) {
        manager.acceptCommand(command);
    }

    private class CommandManager {

        private final RobotMap map;
        private final List<CommandManager.CommandHandler> handlers;

        public CommandManager(RobotMap map) {
            this.map = map;
            handlers = new ArrayList<>();
            initHandlers();
        }

        private void initHandlers() {
            initCreateCommandHandler();
            initListCommandHandler();
            initMoveCommandHandler();
        }

        private void initCreateCommandHandler() {
            handlers.add(new CommandManager.CommandHandler() {
                @Override
                public String name() {
                    return "create";
                }

                @Override
                public void runCommand(String[] args) {
                    int x = Integer.parseInt(args[0]);
                    int y = Integer.parseInt(args[1]);
                    Robot robot = map.createRobot(x,y);
                    System.out.println("Робот " + robot + " успешно создан");
                }
            });
        }

        private void initListCommandHandler() {
            handlers.add(new CommandManager.CommandHandler() {
                @Override
                public String name() {
                    return "list";
                }

                @Override
                public void runCommand(String[] args) {
                    map.acceptRobots(System.out::println);
                    //        map.acceptRobots(robot -> System.out.println(robot));
                    //        map.acceptRobots(new Consumer<RobotMap.Robot>() {
                    //            @Override
                    //            public void accept(RobotMap.Robot robot) {
                    //                System.out.println(robot);
                    //            }
                    //        });
                }
            });
        }

        private void initMoveCommandHandler() {
            handlers.add(new CommandManager.CommandHandler() {
                @Override
                public String name() {
                    return "move";
                }

                @Override
                public void runCommand(String[] args) {
                    Long robotId = Long.parseLong(args[0]);
                    Optional<RobotConcrete> robot = map.getById(robotId);
                    robot.ifPresentOrElse(RobotConcrete::move, () ->
                            System.out.println("Робот с идентификатором " + robotId + " не найден"));

//                    if (robot.isPresent()) {
//                        RobotMap.Robot value = robot.get();
//                        value.move();
//                    } else {
//                        System.out.println("Робот с идентификатором " + robotId + " не найден")
//                    }

                    //        robot.ifPresentOrElse(new Consumer<RobotMap.Robot>() {
                    //            @Override
                    //            public void accept(RobotMap.Robot robot) {
                    //                robot.move();
                    //            }
                    //        }, new Runnable() {
                    //            @Override
                    //            public void run() {
                    //                System.out.println("Робот с идентификатором " + robotId + " не найден");
                    //            }
                    //        });

                    //        if (robot != null) {
                    //            robot.move();
                    //        } else {
                    //            System.out.println("Робот с идентификатором " + robotId + " не найден");
                    //        }
                }
            });
        }

        public void acceptCommand(String command) {
            String[] split = command.split(" ");
            String commandName = split[0];
            String[] commandArgs = Arrays.copyOfRange(split, 1, split.length);

            boolean found = false;
            for (CommandManager.CommandHandler handler : handlers) {
                if (commandName.equals(handler.name())) {
                    found = true;
                    try {
                        handler.runCommand(commandArgs);
                    } catch (Exception e) {
                        System.err.println("Во время обработки команды \"" + commandName + "\" произошла ошибка: " + e.getMessage());
                    }
                }
            }

            if (!found) {
                System.out.println("Команда не найдена");
            }
        }

        private interface CommandHandler {
            String name();
            void runCommand(String[] args);
        }
    }

    private class RobotConcrete implements Robot{

        public static final RobotMap.Direction DEFAULT_DIRECTION = RobotMap.Direction.TOP;

        private static Long idCounter = 1L;

        private final Long id;
        private RobotMap.Direction direction;
        private RobotMap.Point point;

        public RobotConcrete(RobotMap.Point point) {
            this.id = idCounter++;
            this.direction = DEFAULT_DIRECTION;
            this.point = point;
        }

        public void changeDirection(RobotMap.Direction direction) {
            this.direction = direction;
        }

        public void move() {
            RobotMap.Point newPoint = switch (direction) {
                case TOP -> new Point(point.x() - 1, point.y());
                case RIGHT -> new Point(point.x(), point.y() + 1);
                case BOTTOM -> new Point(point.x() + 1, point.y());
                case LEFT -> new Point(point.x(), point.y() - 1);
            };
            validatePoint(newPoint);

            System.out.println("Робот переместился с " + point + " на " + newPoint);
            this.point = newPoint;
        }

        @Override
        public String toString() {
            return "[" + id + "] " + point.toString() + " [" + direction.name() + "]";
        }

        @Override
        public String getID() {
            return this.id.toString();
        }

        @Override
        public String getDirection() {
            return this.direction.toString();
        }

        @Override
        public String getPoint() {
            return this.point.toString();
        }
    }

    private class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }


    }

    private enum Direction {

        TOP, RIGHT, BOTTOM, LEFT;

        public static Direction ofString(String str) {
            Direction[] values = values();
            for (Direction value : values) {
                if (str.equals(value.name())) {
                    return value;
                }
            }

            // Осторожно! Может быть null
            return null;
        }

    }
}
