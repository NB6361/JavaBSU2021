package by.NB6361.quizer;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMathTask implements MathTask {
    static abstract class Generator implements MathTask.Generator {
        public Generator(double minNumber, double maxNumber, EnumSet<Operations> operations, int precision) {
            this.maxNumber = maxNumber;
            this.minNumber = minNumber;
            this.precision = precision;
            this.operations = operations;

            if (precision == 0) {
                a = (ThreadLocalRandom.current().nextInt((int) (maxNumber - minNumber + 1)) + (int) minNumber);
                b = (ThreadLocalRandom.current().nextInt((int) (maxNumber - minNumber + 1)) + (int) minNumber);
            } else {
                a = ThreadLocalRandom.current().nextDouble(maxNumber - minNumber + 1) + minNumber;
                b = ThreadLocalRandom.current().nextDouble(maxNumber - minNumber + 1) + minNumber;
            }
            Optional<Operations> rawOperation = operations
                    .stream()
                    .skip(ThreadLocalRandom.current().nextInt(operations.size()))
                    .findFirst();
            if (rawOperation.isEmpty()) {
                throw new IllegalArgumentException();
            }
            operation = rawOperation.get();
        }

        public double getMinNumber() {
            return minNumber;
        }

        public double getMaxNumber() {
            return maxNumber;
        }

        protected EnumSet<Operations> operations;
        protected double minNumber;
        protected double maxNumber;
        protected int precision;
        protected Number a;
        protected Number b;
        protected Operations operation;
    }

    AbstractMathTask(String test, String answer) {
        this.test = test;
        this.answer = answer;
    }

    public String getText() {
        return test;
    }

    public Result validate(String answer) {
        if (!this.isCorrect(answer)) {
            return Result.INCORRECT_INPUT;
        } else if (this.answer.equals("any")) {
            return Result.OK;
        } else if (this.answer.equals("any_0")) {
            double d = Double.parseDouble(answer);
            if (d == 0) {
                return Result.WRONG;
            } else {
                return Result.OK;
            }
        } else if (Objects.equals(this.answer, answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    public boolean isCorrect(String answer) {
        try  {
            double d = Double.parseDouble(answer);
        }  catch(NumberFormatException nfe)  {
            return false;
        }
        return true;
    }

    protected String test;
    protected String answer;
}
