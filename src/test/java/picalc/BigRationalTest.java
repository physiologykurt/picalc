package picalc;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BigRationalTest {
    @Test
    public void testFormatting() {
        assertThat((new BigRational(10, 1)).formatAsDecimalApproximation(0), is("10"));
        assertThat((new BigRational(100, 1)).formatAsDecimalApproximation(0), is("100"));
        assertThat((new BigRational(1000, 1)).formatAsDecimalApproximation(0), is("1000"));
        assertThat((new BigRational(9, 4)).formatAsDecimalApproximation(0), is("2"));
        assertThat((new BigRational(9, 4)).formatAsDecimalApproximation(1), is("2.2"));
        assertThat((new BigRational(9, 4)).formatAsDecimalApproximation(2), is("2.25"));
        assertThat((new BigRational(9, 4)).formatAsDecimalApproximation(3), is("2.250"));
        assertThat((new BigRational(713, 17)).formatAsDecimalApproximation(0), is("41"));
        assertThat((new BigRational(713, 17)).formatAsDecimalApproximation(1), is("41.9"));
        assertThat((new BigRational(713, 17)).formatAsDecimalApproximation(2), is("41.94"));
        assertThat((new BigRational(713, 17)).formatAsDecimalApproximation(3), is("41.941"));
        assertThat((new BigRational(1, 375)).formatAsDecimalApproximation(4), is("0.0026"));
    }

    @Test
    public void testAdd() {
        assertThat(new BigRational(5, 6).add(new BigRational(7, 8)).formatAsDecimalApproximation(4), is("1.7083"));
    }
}
