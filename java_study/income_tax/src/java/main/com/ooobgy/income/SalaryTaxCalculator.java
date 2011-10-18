package com.ooobgy.income;

import java.math.BigDecimal;
import java.util.Map;

import com.ooobgy.income.consts.IncomeConsts;
import com.ooobgy.income.exception.IllTaxConfException;
import com.ooobgy.income.pojo.SalaryResult;
import com.ooobgy.income.pojo.SalaryTaxConf;

/**
 * 月薪个税计算工具
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-10-16
 */
public class SalaryTaxCalculator {
    /**
     * 禁用构造，提供static工具api
     */
    private SalaryTaxCalculator() {
    }
    
    /**
     * 计算个人社保
     * @param conf
     * @param salary
     * @param preRes
     * @return
     */
    public static SalaryResult calculatesIndvSS(SalaryTaxConf conf, BigDecimal salary, SalaryResult preRes){
        SalaryResult res = preRes;
        if (res == null) {
            res = new SalaryResult();
        }
        
        
        BigDecimal ssBase = getSSBase(conf, salary);
        BigDecimal accfundBase = getAccFundBase(conf, salary);
        res.setSsBase(ssBase);
        res.setAccBase(accfundBase);
        
        res.setPreTaxSalary(salary);
        res.setIndvSSPension(ssBase.multiply(conf.getIndvPension()));
        res.setIndvSSMedicare(ssBase.multiply(conf.getIndvMedicare()));
        res.setIndvSSUnEmp(ssBase.multiply(conf.getIndvUnEmp()));
        res.setIndvSSAccFund(accfundBase.multiply(conf.getIndvAccFund()));
        res.sumIndvSSTotal();
        BigDecimal inTaxSalary = salary.subtract(res.getIndvSSTotal()).subtract(conf.getSalaryBase());
        if (inTaxSalary.compareTo(IncomeConsts.ZERO) < 1) {
            inTaxSalary = new BigDecimal(IncomeConsts.ZERO.toString());
        }
        res.setInTaxSalary(inTaxSalary);
        
        return res;
    }
    
    /**
     * 计算公司社保
     * @param conf
     * @param salary
     * @param preRes
     * @return
     */
    public static SalaryResult calculatesComSS(SalaryTaxConf conf, BigDecimal salary, SalaryResult preRes){
        SalaryResult res = preRes;
        if (res == null) {
            res = new SalaryResult();
        }
        
        BigDecimal ssBase = getSSBase(conf, salary);
        BigDecimal accfundBase = getAccFundBase(conf, salary);
        res.setSsBase(ssBase);
        res.setAccBase(accfundBase);
        
        res.setPreTaxSalary(salary);
        res.setComSSPension(ssBase.multiply(conf.getComPension()));
        res.setComSSMedicare(ssBase.multiply(conf.getComMedicare()));
        res.setComSSUnEmp(ssBase.multiply(conf.getComUnEmp()));
        res.setComSSInjure(ssBase.multiply(conf.getComInjure()));
        res.setComSSMaternity(ssBase.multiply(conf.getComMaternity()));
        res.setComSSAccFund(accfundBase.multiply(conf.getComAccFund()));
        res.sumComTotalCost();
        
        return res;
    }
    
    /**
     * 默认计算所有内容，可选是否包括公司部分<br>
     * 依赖于{@link #calculatesIndvSS(SalaryTaxConf, BigDecimal)}
     * 可选依赖于{@link #calculatesComSS(SalaryTaxConf, BigDecimal)}
     * @param conf
     * @param salary
     * @param includeCompany
     * @return
     */
    public static SalaryResult calculateAll(SalaryTaxConf conf, BigDecimal salary, boolean includeCompany){
        SalaryResult res = calculatesIndvSS(conf, salary, null);
        
        BigDecimal inTaxSalary = res.getInTaxSalary();
        BigDecimal tax = null;
        if (inTaxSalary.compareTo(IncomeConsts.ZERO) == 0) {
            tax = new BigDecimal(IncomeConsts.ZERO.toString());
        } else {
            Map<BigDecimal, BigDecimal> taxLevlRates = conf.getTaxLevlRates();
            Map<BigDecimal, BigDecimal> taxLevlQuicks = conf.getTaxLevlQuicks();
            BigDecimal level = null;
            for (BigDecimal keyLv : taxLevlRates.keySet()) {
                if (inTaxSalary.compareTo(keyLv) == 1) {
                    level = keyLv;
                } else {
                    break;
                }
            }
            try {
                BigDecimal rate = taxLevlRates.get(level);
                BigDecimal quick = taxLevlQuicks.get(level);
                tax = inTaxSalary.multiply(rate).subtract(quick);
            } catch (NullPointerException e) {
                throw new IllTaxConfException("错误的月薪个税配置！(很可能是第一税级不为0)", e);
            }
        }
        
        res.setTax(tax);
        res.sumPostSalary();
        
        if (includeCompany) {
            res = calculatesComSS(conf, salary, res);
        }
        
        return res;
    }
    
    /**
     * 默认计算所有内容，包括公司部分<br>
     * 依赖于{@link #calculateAll(SalaryTaxConf, BigDecimal, boolean)}
     * 依赖于{@link #calculatesIndvSS(SalaryTaxConf, BigDecimal)}
     * 依赖于{@link #calculatesComSS(SalaryTaxConf, BigDecimal)}
     * @param conf
     * @param salary
     * @return
     */
    public static SalaryResult calculateAll(SalaryTaxConf conf, BigDecimal salary){
        return calculateAll(conf, salary, true);
    }
    
    /**
     * 计算月薪应缴个税值<br>
     * 依赖于{@link #calculateAll(SalaryTaxConf, BigDecimal, boolean)}
     * 依赖于{@link #calculatesIndvSS(SalaryTaxConf, BigDecimal)}
     * @param conf
     * @param salary
     * @return
     */
    public static BigDecimal calculateSalaryTax(SalaryTaxConf conf, BigDecimal salary){
        SalaryResult res = calculateAll(conf, salary, false);
        return res.getTax();
    }
    
    /**
     * 返回用于计算社保的基数
     * @param conf
     * @param salary
     * @return
     */
    private static BigDecimal getSSBase(SalaryTaxConf conf, BigDecimal salary){
        if (salary.compareTo(conf.getFloorSS()) == -1) {
            return conf.getFloorSS();
        } else if (salary.compareTo(conf.getTopSS()) == 1) {
            return conf.getTopSS();
        } else {
            return salary;
        }
    }
    
    /**
     * 返回用于计算公积金的基数
     * @param conf
     * @param salary
     * @return
     */
    private static BigDecimal getAccFundBase(SalaryTaxConf conf, BigDecimal salary){
        if (salary.compareTo(conf.getTopSS()) == 1) {
            return conf.getTopSS();
        } else {
            return salary;
        }
    }
}
