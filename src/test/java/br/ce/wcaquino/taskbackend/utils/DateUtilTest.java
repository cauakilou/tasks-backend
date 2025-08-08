package br.ce.wcaquino.taskbackend.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static br.ce.wcaquino.taskbackend.utils.DateUtils.isEqualOrFutureDate;

class DateUtilTest {

    @Test
    void deveRetornarTrueParaFuturas(){
        LocalDate date = LocalDate.of(2030,1,1);
        Assertions.assertTrue(isEqualOrFutureDate(date));
    }
    @Test
    void deveRetornarFalseParaDatasPassadas(){
        LocalDate date = LocalDate.of(2010,1,1);
        Assertions.assertFalse(isEqualOrFutureDate(date));
    }
    @Test
    void deveRetornarTrueParaHoje(){
        LocalDate date = LocalDate.now();
        Assertions.assertTrue(isEqualOrFutureDate(date));
    }


}
