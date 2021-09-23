package by.NB6361.quizer;


import java.util.ArrayList;

public class Quiz {
    public Quiz(Task.Generator generator, int taskCount) {
        tasks = new ArrayList<>();
        for (int i = 0; i < taskCount; ++i) {
            tasks.add(generator.generate());
        }
        tasksQuantity = taskCount;
    }

    public Quiz(int tasks) {
        tasksQuantity = tasks;
    }

    public Task nextTask() {
        return tasks.get(currentTask);
    }

    public Result provideAnswer(String answer) {
        Result result = tasks.get(currentTask).validate(answer);
        currentTask++;
        if (result == Result.INCORRECT_INPUT) {
            currentTask--;
            incorrectAnswers++;
        } else if (result == Result.OK) {
            correctAnswers++;
        }
        return result;
    }

    public boolean isFinished() {
        return currentTask == tasksQuantity;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswers;
    }

    public int getWrongAnswerNumber() {
        return tasksQuantity - correctAnswers;
    }

    public int getIncorrectInputNumber() {
        return incorrectAnswers;
    }

    public double getMark() {
        if (currentTask != tasksQuantity) {
            throw new IllegalArgumentException();
        }
        return (double) (correctAnswers) / (double) (tasksQuantity);
    }

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int currentTask = 0;
    private int tasksQuantity = 0;
    private ArrayList<Task> tasks;
}
