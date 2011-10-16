package com.ooobgy.income.pojo;

import java.math.BigDecimal;

/**
 * 月薪个税计算的结果集pojo
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-10-16
 */
public class SalaryResult {
    /** 税前月收入 */
    private BigDecimal preTaxSalary;
    /** 税后月收入 */
    private BigDecimal postTaxSalary;
    /** 缴纳个税 */
    private BigDecimal tax;
    /** 应纳税工资部分 */
    private BigDecimal inTaxSalary;
    
    /** 个人缴费合计 */
    private BigDecimal indvSSTotal;
    /** 个人缴养老保险 */
    private BigDecimal indvSSPension;
    /** 个人缴医疗保险 */
    private BigDecimal indvSSMedicare;
    /** 个人缴失业保险 */
    private BigDecimal indvSSUnEmp;
    /** 个人缴公积金 */
    private BigDecimal indvSSAccFund;
    
    /** 单位支出总计 */
    private BigDecimal comTotalCost;
    /** 单位缴费合计 */
    private BigDecimal comSSTotal;
    /** 单位缴养老保险 */
    private BigDecimal comSSPension;
    /** 单位缴医疗保险 */
    private BigDecimal comSSMedicare;
    /** 单位缴失业保险 */
    private BigDecimal comSSUnEmp;
    /** 单位缴工伤保险 */
    private BigDecimal comSSInjure;
    /** 单位缴生育保险 */
    private BigDecimal comSSMaternity;
    /** 单位缴公积金  */
    private BigDecimal comSSAccFund;
    
    public BigDecimal getPreTaxSalary() {
        return preTaxSalary;
    }
    public void setPreTaxSalary(BigDecimal preTaxSalary) {
        this.preTaxSalary = preTaxSalary;
    }
    public BigDecimal getPostTaxSalary() {
        return postTaxSalary;
    }
    public void setPostTaxSalary(BigDecimal postTaxSalary) {
        this.postTaxSalary = postTaxSalary;
    }
    public BigDecimal getTax() {
        return tax;
    }
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    public BigDecimal getInTaxSalary() {
        return inTaxSalary;
    }
    public void setInTaxSalary(BigDecimal inTaxSalary) {
        this.inTaxSalary = inTaxSalary;
    }
    public BigDecimal getIndvSSTotal() {
        return indvSSTotal;
    }
    public void sumIndvSSTotal() {
        this.indvSSTotal = this.indvSSPension.add(this.indvSSMedicare)
                .add(this.indvSSUnEmp).add(this.indvSSAccFund);
    }
    public BigDecimal getIndvSSPension() {
        return indvSSPension;
    }
    public void setIndvSSPension(BigDecimal indvSSPension) {
        this.indvSSPension = indvSSPension;
    }
    public BigDecimal getIndvSSMedicare() {
        return indvSSMedicare;
    }
    public void setIndvSSMedicare(BigDecimal indvSSMedicare) {
        this.indvSSMedicare = indvSSMedicare;
    }
    public BigDecimal getIndvSSUnEmp() {
        return indvSSUnEmp;
    }
    public void setIndvSSUnEmp(BigDecimal indvSSUnEmp) {
        this.indvSSUnEmp = indvSSUnEmp;
    }
    public BigDecimal getIndvSSAccFund() {
        return indvSSAccFund;
    }
    public void setIndvSSAccFund(BigDecimal indvSSAccFund) {
        this.indvSSAccFund = indvSSAccFund;
    }
    public BigDecimal getComTotalCost() {
        return comTotalCost;
    }
    public void sumComTotalCost() {
        sumComSSTotal();
        this.comTotalCost = this.preTaxSalary.add(this.comSSTotal);
    }
    public BigDecimal getComSSTotal() {
        return comSSTotal;
    }
    private void sumComSSTotal() {
        this.comSSTotal = this.comSSPension.add(this.comSSMedicare)
                .add(this.comSSUnEmp).add(this.comSSAccFund)
                .add(this.comSSInjure).add(this.comSSMaternity);
    }
    public BigDecimal getComSSPension() {
        return comSSPension;
    }
    public void setComSSPension(BigDecimal comSSPension) {
        this.comSSPension = comSSPension;
    }
    public BigDecimal getComSSMedicare() {
        return comSSMedicare;
    }
    public void setComSSMedicare(BigDecimal comSSMedicare) {
        this.comSSMedicare = comSSMedicare;
    }
    public BigDecimal getComSSUnEmp() {
        return comSSUnEmp;
    }
    public void setComSSUnEmp(BigDecimal comSSUnEmp) {
        this.comSSUnEmp = comSSUnEmp;
    }
    public BigDecimal getComSSInjure() {
        return comSSInjure;
    }
    public void setComSSInjure(BigDecimal comSSInjure) {
        this.comSSInjure = comSSInjure;
    }
    public BigDecimal getComSSMaternity() {
        return comSSMaternity;
    }
    public void setComSSMaternity(BigDecimal comSSMaternity) {
        this.comSSMaternity = comSSMaternity;
    }
    public BigDecimal getComSSAccFund() {
        return comSSAccFund;
    }
    public void setComSSAccFund(BigDecimal comSSAccFund) {
        this.comSSAccFund = comSSAccFund;
    }
}
