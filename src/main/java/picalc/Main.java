package picalc;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify number of digits");
            return;
        }
        long numDecimalDigits = Long.parseLong(args[0]);
        System.out.println(String.format("Calculate pi to %d digits", numDecimalDigits));

        BigRational term1 = approximateMachinSeries(1, 5, 4, numDecimalDigits);
        BigRational term2 = approximateMachinSeries(1, 239, 1, numDecimalDigits);
        BigRational sum = term1.subtract(term2);
        BigRational result = sum.multiply(4);

        System.out.println(result.formatAsDecimalApproximation(numDecimalDigits));
    }

    public static BigRational approximateMachinSeries(int an, int bn, int cn, long numDecimalDigits) {
        final BigInteger two = BigInteger.valueOf(2);
        // Add an extra digit. Seems to work without this but I haven't carefully gone through all the math and rounding.
        final long numTerms = calcTermExpansionRequired(an, bn, numDecimalDigits + 1);
        final int ansquared = an*an;
        final BigInteger ansquaredbi = BigInteger.valueOf(ansquared);
        final int bnsquared = bn*bn;
        final BigInteger bnsquaredbi = BigInteger.valueOf(bnsquared);

        BigRational accumulator = BigRational.ZERO;
        BigInteger anexp = BigInteger.valueOf(an);
        BigInteger bnexp = BigInteger.valueOf(bn);
        BigInteger twoiplusone = BigInteger.ONE;
        int signum = 1;

        for (long i = 0; i < numTerms; i++) {
            BigRational term = new BigRational(anexp, twoiplusone.multiply(bnexp));
            if (signum == 1) {
                accumulator = accumulator.add(term);
            } else {
                accumulator = accumulator.subtract(term);
            }

            signum *= -1;
            anexp = anexp.multiply(ansquaredbi);
            bnexp = bnexp.multiply(bnsquaredbi);
            twoiplusone = twoiplusone.add(two);

            //System.out.println(String.format("Iteration %d of %d,%d. Term=%s. Acc=%s", i, an, bn, term.formatAsDecimalApproximation(6), accumulator.formatAsDecimalApproximation(6)));
        }

        BigRational result = accumulator.multiply(cn);
        System.out.println(String.format("After %d terms of %d,%d. acc=%s. result=%s", numTerms, an, bn, accumulator.formatAsDecimalApproximation(6), result.formatAsDecimalApproximation(6)));
        return result;
    }

    public static long calcTermExpansionRequired(int an, int bn, long numDecimalDigits) {
        double terms = numDecimalDigits / (Math.log10(bn) - Math.log10(an)) / 2;
        long intTerms = Math.round(Math.ceil(terms));
        System.out.println(String.format("Calculating %d/%d Machin series to %d digits will require %.3f or %d terms", an, bn, numDecimalDigits, terms, intTerms));
        return intTerms;
    }
}
