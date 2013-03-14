package picalc;

import java.math.BigInteger;

// Simple big rational class built on BigInteger
public class BigRational {
    public static final BigRational ZERO = new BigRational(BigInteger.ZERO, BigInteger.ONE);
    public static final BigRational ONE = new BigRational(BigInteger.ONE, BigInteger.ONE);
    public static final BigRational TEN = new BigRational(BigInteger.TEN, BigInteger.ONE);

    public BigRational(long numerator, long denominator) {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(denominator);
    }

    public BigRational(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public BigRational add(BigRational other) {
        return addGcd(other);
//        return addNaive(other);
    }

    public BigRational addGcd(BigRational other) {
        final BigInteger gcd = denominator.gcd(other.denominator);
        final BigInteger scaleA = other.denominator.divide(gcd);
        final BigInteger scaleB = denominator.divide(gcd);
        final BigInteger nA = numerator.multiply(scaleA);
        final BigInteger nB = other.numerator.multiply(scaleB);
        final BigInteger d = denominator.multiply(scaleA);

        return new BigRational(nA.add(nB), d);
    }

    public BigRational addNaive(BigRational other) {
        if (denominator.equals(other.denominator)) {
            return new BigRational(numerator.add(other.numerator), denominator);
        } else {
            return new BigRational(numerator.multiply(other.denominator).add(other.numerator.multiply(denominator)), denominator.multiply(other.denominator));
        }
    }

    public BigRational subtract(BigRational other) {
        return subtractGcd(other);
    }

    public BigRational subtractGcd(BigRational other) {
        final BigInteger gcd = denominator.gcd(other.denominator);
        final BigInteger scaleA = other.denominator.divide(gcd);
        final BigInteger scaleB = denominator.divide(gcd);
        final BigInteger nA = numerator.multiply(scaleA);
        final BigInteger nB = other.numerator.multiply(scaleB);
        final BigInteger d = denominator.multiply(scaleA);

        return new BigRational(nA.subtract(nB), d);
    }

    public BigRational subtractNaive(BigRational other) {
        if (denominator.equals(other.denominator)) {
            return new BigRational(numerator.subtract(other.numerator), denominator);
        } else {
            return new BigRational(numerator.multiply(other.denominator).subtract(other.numerator.multiply(denominator)), denominator.multiply(other.denominator));
        }
    }

    public BigRational multiply(long scalar) {
        return multiply(BigInteger.valueOf(scalar));
    }

    public BigRational multiply(BigInteger scalar) {
        return new BigRational(numerator.multiply(scalar), denominator);
    }

    public String formatAsDecimalApproximation(long digitsPassedDecimal) {
        final StringBuilder sb = new StringBuilder();
        BigInteger[] divideResult = numerator.divideAndRemainder(denominator);
        sb.append(divideResult[0].toString());

        if (divideResult[1].equals(BigInteger.ZERO) || digitsPassedDecimal < 1) {
            return sb.toString();
        }

        sb.append('.');

        // TODO: Downcasting long to int. Will limit to ~2 billion digits.
        BigInteger passedDecimal = divideResult[1].multiply(BigInteger.TEN.pow((int) digitsPassedDecimal));
        BigInteger quotient = passedDecimal.divide(denominator);
        String quotientString = quotient.toString();
        for (int i = quotientString.length(); i < digitsPassedDecimal; i++) {
            sb.append('0');
        }
        sb.append(quotientString);
        return sb.toString();
    }

    public final BigInteger numerator;
    public final BigInteger denominator;
}
