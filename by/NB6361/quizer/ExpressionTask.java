package by.NB6361.quizer;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ExpressionTask extends AbstractMathTask {
    ExpressionTask(String test, String answer) {
        super(test, answer);
    }

    static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber,
                         EnumSet<Operations> operations, int precision) {
            super(minNumber, maxNumber, operations, precision);
            if (operations.isEmpty()) {
                throw new IllegalArgumentException();
            }
        }

        private ExpressionTask toReturn(double a, double b, double ans, Operations operation) {
            a = BigDecimal.valueOf(a).setScale(precision, RoundingMode.HALF_UP).doubleValue();
            b = BigDecimal.valueOf(b).setScale(precision, RoundingMode.HALF_UP).doubleValue();
            ans = BigDecimal.valueOf(ans).setScale(precision, RoundingMode.HALF_UP).doubleValue();
            return new ExpressionTask(a + operation.getCharacter() + b + "=?", String.valueOf(ans));
        }

        private ExpressionTask  generateReal() {
            double a = ThreadLocalRandom.current().nextDouble(maxNumber - minNumber + 1) + minNumber;
            double b = ThreadLocalRandom.current().nextDouble(maxNumber - minNumber + 1) + minNumber;
            List<Operations> operationsList = operations.stream().collect(Collectors.toList());
            Operations operation = operationsList.get(ThreadLocalRandom.current().nextInt(operationsList.size()));
            if (operation == Operations.SUM) {
                toReturn(a, b, a + b, Operations.SUM);
            } else if (operation == Operations.DIFFERENCE) {
                return toReturn(a, b, a - b, Operations.DIFFERENCE);
            } else if (operation == Operations.MULTIPLICATION) {
                return toReturn(a, b, a * b, Operations.MULTIPLICATION);
            } else {
                if (minNumber == 0 && maxNumber == 0) {
                    if (operations.size() == 1) {
                        throw new IllegalArgumentException("Zero division");
                    } else {
                        operations.remove(Operations.DIVISION);
                        return generateReal();
                    }
                }
                while (b == 0) {
                    b = ThreadLocalRandom.current().nextDouble(maxNumber - minNumber + 1) + minNumber;
                }
                return toReturn(a, b, a / b, Operations.DIVISION);
            }
            return null;
        }

        private ExpressionTask generateInteger() {
            int a = (ThreadLocalRandom.current().nextInt((int) (maxNumber - minNumber + 1)) + (int) minNumber);;
            int b = (ThreadLocalRandom.current().nextInt((int) (maxNumber - minNumber + 1)) + (int) minNumber);
            List<Operations> operationsList = operations.stream().collect(Collectors.toList());
            Operations operation = operationsList.get(ThreadLocalRandom.current().nextInt(operationsList.size()));
            if (operation == Operations.SUM) {
                return new ExpressionTask(a + "+" + b + "=?", String.valueOf(b + a));
            } else if (operation == Operations.DIFFERENCE) {
                return new ExpressionTask(a + "-" + b + "=?", String.valueOf(a - b));
            } else if (operation == Operations.MULTIPLICATION) {
                return new ExpressionTask(a + "*" + b + "=?", String.valueOf(a * b));
            } else {
                if (minNumber == 0 && maxNumber == 0) {
                    if (operations.size() != 1) {
                        operations.remove(Operations.DIVISION);
                        return generateInteger();
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                while (a == 0 && b != 0) {
                    a = ThreadLocalRandom.current().nextInt((int) (maxNumber - minNumber + 1)) + (int) minNumber;
                }
                ArrayList<Integer> divisors = new ArrayList<Integer>();
                int divisor = 1;
                while (divisor <= Math.sqrt(Math.abs(a)) + 1) {
                    if (a % divisor == 0) {
                        if (divisor >= (int) minNumber) {
                            divisors.add(divisor);
                        }
                        if (a / divisor >= (int) minNumber) {
                            divisors.add(a / divisor);
                        }
                    }
                    divisor++;
                }
                b = divisors.get(ThreadLocalRandom.current().nextInt(divisors.size()));
                return new ExpressionTask(String.valueOf(a) + "/" + b + "=?",
                        String.valueOf(a / b));
            }
        }

        public ExpressionTask generate() {
            if (precision == 0) {
                return generateInteger();
            } else {
                return generateReal();
            }
        }
    }
}
