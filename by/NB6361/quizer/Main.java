package by.NB6361.quizer;


import java.io.IOException;
import java.util.*;

public class Main {
    static Map<String, Quiz> getQuizMap() throws IOException {
        HashMap<String, Quiz> mapOfQuizes = new HashMap<String, Quiz>();
        EnumSet<MathTask.Operations> opers = EnumSet.of(MathTask.Operations.SUM,
                MathTask.Operations.DIFFERENCE, MathTask.Operations.DIVISION, MathTask.Operations.MULTIPLICATION);
        GroupTaskGenerator group = new GroupTaskGenerator(
                (Task.Generator) new ExpressionTask.Generator(0, 100, opers, 1),
                (Task.Generator) new EquationTask.Generator(0, 100, opers, 1));
        mapOfQuizes.putIfAbsent("Math", new Quiz(group, 2));
        PoolTaskGenerator pool = new PoolTaskGenerator(false, new TextTask(), new TextTask(),
                new TextTask(), new TextTask(), new TextTask());
        mapOfQuizes.putIfAbsent("Text", new Quiz((Task.Generator) pool, 5));
        return mapOfQuizes;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Quiz> tests = getQuizMap();
        Scanner in = new Scanner(System.in);
        String name;
        do {
            System.out.println("Enter name of the test");
            name = in.nextLine();
        } while (!tests.containsKey(name));

        Quiz quiz = tests.get(name);
        do {
            System.out.println(quiz.nextTask().getText());
            String answer = in.nextLine();
            switch (quiz.provideAnswer(answer)) {
                case OK -> System.out.println("Right!!!");
                case WRONG -> System.out.println("Wrong(((");
                case INCORRECT_INPUT -> System.out.println("Incorrect input, try again");
            }
        } while (!quiz.isFinished());
        System.out.println("Your mark is " + quiz.getMark());
    }
}
