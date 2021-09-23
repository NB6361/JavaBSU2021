package by.NB6361.quizer;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {
    EquationTask(String test, String answer) {
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

        private EquationTask toReturn(double a, double b, double ans, Operations operation) {
            a = BigDecimal.valueOf(a).setScale(precision, RoundingMode.HALF_UP).doubleValue();
            b = BigDecimal.valueOf(b).setScale(precision, RoundingMode.HALF_UP).doubleValue();
            ans = BigDecimal.valueOf(ans).setScale(precision, RoundingMode.HALF_UP).doubleValue();
            return new EquationTask(a + operation.getCharacter() + "x=" + b, String.valueOf(ans));
        }

        private EquationTask generateReal() {
            double a = this.a.doubleValue();
            double b = this.b.doubleValue();
            if (operation == Operations.SUM) {
                return toReturn(a, b, b - a, Operations.SUM);
            } else if (operation == Operations.DIFFERENCE) {
                return toReturn(a, b, a - b, Operations.DIFFERENCE);
            } else {
                if (minNumber == 0 && maxNumber == 0) {
                    if (operation == Operations.MULTIPLICATION) {
                        return new EquationTask(toReturn(0, 0, 0, operation).getText(), "any");
                    } else {
                        return new EquationTask(toReturn(0, 0, 0, operation).getText(), "any_0");
                    }
                }
                while (a == 0 && b != 0) {
                    a = ThreadLocalRandom.current().nextDouble(maxNumber - minNumber + 1) + minNumber;
                }
                if (operation == Operations.DIVISION) {
                    return toReturn(a, b, a / b, Operations.DIVISION);
                } else {
                    return toReturn(b, a, a / b, Operations.MULTIPLICATION);
                }
            }
        }

        private EquationTask generateInteger() {
            int a = this.a.intValue();
            int b = this.b.intValue();
            if (operation == Operations.SUM) {
                return new EquationTask(a + "+x=" + b, String.valueOf(b - a));
            } else if (operation == Operations.DIFFERENCE) {
                return new EquationTask(a + "-x=" + b, String.valueOf(a - b));
            } else {
                if (minNumber == 0 && maxNumber == 0) {
                    if (operation == Operations.MULTIPLICATION) {
                        return new EquationTask(0 + "*x=" + 0, "any");
                    } else {
                        return new EquationTask(0 + "/x=" + 0, "any_0");
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
                if (operation == Operations.DIVISION) {
                    return new EquationTask(a + "/x=" + b,
                            String.valueOf(a / b));
                } else {
                    return new EquationTask(b + "*x=" + a,
                            String.valueOf(a / b));
                }
            }
        }

        public EquationTask generate() {
            if (precision == 0) {
                return generateInteger();
            } else {
                return generateReal();
            }
        }
    }
}
