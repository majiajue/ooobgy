package com.ooobgy.income.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ooobgy.comm.util.ValidationUtility;
import com.ooobgy.income.exception.IllTaxConfException;

/**
 * 表示当前配置的实体pojo
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-10-16
 */
public class SalaryTaxConf {
    private BigDecimal salaryBase;
    private Map<BigDecimal, BigDecimal> taxLevlRates;//分级税率
    private Map<BigDecimal, BigDecimal> taxLevlQuicks;//分级速算扣除数
    
    private BigDecimal avgSalary;
    private BigDecimal floorSS;
    private BigDecimal topSS;
    
    private BigDecimal comPension;
    private BigDecimal comMedicare;
    private BigDecimal comUnEmp;
    private BigDecimal comInjure;
    private BigDecimal comMaternity;
    private BigDecimal comAccFund;
    private BigDecimal indvPension;
    private BigDecimal indvMedicare;
    private BigDecimal indvUnEmp;
    private BigDecimal indvAccFund;
    

    
    public void setTaxLevlRates(Map<BigDecimal, BigDecimal> taxLevlRates) throws IllTaxConfException{
        //LinkedHashMap实现有序
        this.taxLevlRates = taxLevlRates;
        this.taxLevlQuicks = new LinkedHashMap<BigDecimal, BigDecimal>();
        try {
            BigDecimal preLv = new BigDecimal("-1");
            BigDecimal preRate = new BigDecimal("0.0");
            BigDecimal quick = new BigDecimal("0");
            for (BigDecimal nowLv : this.taxLevlRates.keySet()) {
                //quick = quick + nowLv * (nowRate - preRate)
                ValidationUtility.checkGreaterThan(nowLv, preLv, false, IllTaxConfException.class, "税率等级配置错误,排列不可乱序");
                BigDecimal nowRate = this.taxLevlRates.get(nowLv);
                quick = quick.add(nowLv.multiply(nowRate.subtract(preRate)));
                this.taxLevlQuicks.put(nowLv, quick);
                //更新pre
                preRate = nowRate;
                preLv = nowLv;
            }
        } catch (NullPointerException e) {
            throw new IllTaxConfException("个税征收等级配置标号错误", e);
        } catch (IndexOutOfBoundsException e){
            throw new IllTaxConfException("个税征收等级配置格式错误", e);
        } catch (NumberFormatException e) {
            throw new IllTaxConfException("个税征收等级配置格式错误", e);
        }
    }
    
    public Map<BigDecimal, BigDecimal> getTaxLevlQuicks() {
        return taxLevlQuicks;
    }

    public BigDecimal getSalaryBase() {
        return salaryBase;
    }
    public void setSalaryBase(BigDecimal salaryBase) {
        this.salaryBase = salaryBase;
    }
    public BigDecimal getAvgSalary() {
        return avgSalary;
    }
    public void setAvgSalary(BigDecimal avgSalary) {
        this.avgSalary = avgSalary;
        this.floorSS = avgSalary.multiply(new BigDecimal("0.6"));//同时初始化社保基值和封顶
        this.topSS = avgSalary.multiply(new BigDecimal("3.0"));
    }
    public BigDecimal getComPension() {
        return comPension;
    }
    public void setComPension(BigDecimal comPension) {
        this.comPension = comPension;
    }
    public BigDecimal getComMedicare() {
        return comMedicare;
    }
    public void setComMedicare(BigDecimal comMedicare) {
        this.comMedicare = comMedicare;
    }
    public BigDecimal getComUnEmp() {
        return comUnEmp;
    }
    public void setComUnEmp(BigDecimal comUnEmp) {
        this.comUnEmp = comUnEmp;
    }
    public BigDecimal getComInjure() {
        return comInjure;
    }
    public void setComInjure(BigDecimal comInjure) {
        this.comInjure = comInjure;
    }
    public BigDecimal getComMaternity() {
        return comMaternity;
    }
    public void setComMaternity(BigDecimal comMaternity) {
        this.comMaternity = comMaternity;
    }
    public BigDecimal getComAccFund() {
        return comAccFund;
    }
    public void setComAccFund(BigDecimal comAccFund) {
        this.comAccFund = comAccFund;
    }
    public BigDecimal getIndvPension() {
        return indvPension;
    }
    public void setIndvPension(BigDecimal indvPension) {
        this.indvPension = indvPension;
    }
    public BigDecimal getIndvMedicare() {
        return indvMedicare;
    }
    public void setIndvMedicare(BigDecimal indvMedicare) {
        this.indvMedicare = indvMedicare;
    }
    public BigDecimal getIndvUnEmp() {
        return indvUnEmp;
    }
    public void setIndvUnEmp(BigDecimal indvUnEmp) {
        this.indvUnEmp = indvUnEmp;
    }
    public BigDecimal getIndvAccFund() {
        return indvAccFund;
    }
    public void setIndvAccFund(BigDecimal indvAccFund) {
        this.indvAccFund = indvAccFund;
    }
    public Map<BigDecimal, BigDecimal> getTaxLevlRates() {
        return taxLevlRates;
    }
    public BigDecimal getFloorSS() {
        return floorSS;
    }
    public BigDecimal getTopSS() {
        return topSS;
    }
}
