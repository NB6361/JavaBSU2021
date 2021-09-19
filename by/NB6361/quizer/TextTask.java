package by.NB6361.quizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class TextTask implements Task {
    public TextTask() throws IOException {
        FileReader fr_1 = new FileReader("JavaBSU2021/TextTasks.txt");
        BufferedReader tests = new BufferedReader(fr_1);
        FileReader fr_2 = new FileReader("JavaBSU2021/TextTasksAnswers.txt");
        BufferedReader answers = new BufferedReader(fr_2);

        int line_ind = ThreadLocalRandom.current().nextInt(max_quantity_of_lines);
        int curr_line = 0;
        while (curr_line != line_ind) {
            answer = answers.readLine();
            test = tests.readLine();
            curr_line++;
        }
        answer = answers.readLine();
        test = tests.readLine();

        tests.close();
        answers.close();
    }

    @Override
    public String getText() {
        return test;
    }

    @Override
    public Result validate(String answer) {
        if (this.answer.equals(answer)) {
            return Result.OK;
        }
        return Result.WRONG;
    }

    private String test;
    private String answer;
    private static final int max_quantity_of_lines = 5;
}
