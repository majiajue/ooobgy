package com.ooobgy.income;

import java.math.BigDecimal;

import org.junit.Test;

import com.ooobgy.income.conf.ConfReader;
import com.ooobgy.income.pojo.SalaryResult;
import com.ooobgy.income.pojo.SalaryTaxConf;

import junit.framework.TestCase;

public class SalaryTest extends TestCase{
    
    @Test
    public void testCommCal() throws Exception {
        SalaryTaxConf conf = ConfReader.makeSalaryTaxConf("conf/tax-conf.properties");
        BigDecimal salary = new BigDecimal("6000");
        SalaryResult res = SalaryTaxCalculator.calculateAll(conf, salary);
        System.out.println(res.getTax());
        System.out.println(res.getIndvSSTotal());
        System.out.println(res.getIndvSSPension());
    }
}
