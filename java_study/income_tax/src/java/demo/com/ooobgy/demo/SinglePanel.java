package com.ooobgy.demo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.PlainDocument;

import com.ooobgy.income.consts.IncomeConsts;
import com.ooobgy.income.pojo.OtherTaxConf;
import com.ooobgy.income.pojo.SalaryTaxConf;

public class SinglePanel{
    private JPanel context;
    private SalaryTaxConf stConf;
    private OtherTaxConf otConf;
    private JComboBox calType;
    private String type;
    
    private JTextField preTax;
    private String preTaxOld = "";
    private JTextField postTax;
    private JTextField tax;
    private JTextField indvSSTotal;
    private JTextField inTaxSalary;
    private JTextField indvPenRate;
    private String indvPenRateOld = "";
    private JTextField indvPen;
    private JTextField indvMedRate;
    private String indvMedRateOld = "";
    private JTextField indvMed;
    private JTextField indvUnempRate;
    private String indvUnempRateOld = "";
    private JTextField indvUnemp;
    private JTextField indvAccFundRate;
    private String indvAccFundRateOld = "";
    private JTextField indvAccFund;
    private JTextField comSSTotal;
    private JTextField comTotalCost;
    private JTextField comPenRate;
    private String comPenRateOld = "";
    private JTextField comPen;
    private JTextField comMedRate;
    private String comMedRateOld = "";
    private JTextField comMed;
    private JTextField comUnempRate;
    private String comUnempRateOld = "";
    private JTextField comUnemp;
    private JTextField comInjRate;
    private String comInjRateOld = "";
    private JTextField comInj;
    private JTextField comMateRate;
    private String comMateRateOld = "";
    private JTextField comMate;
    private JTextField comAccFundRate;
    private String comAccFundRateOld = "";
    private JTextField comAccFund;
    private JTextField avgSalary;
    private String avgSalaryOld = "";
    private JTextField ssBase;
    private JTextField ssAcc;
    private JTextField ssTop;
   public JComboBox getCalType() {
        return calType;
    }

    public SinglePanel(SalaryTaxConf stConf, OtherTaxConf otConf) {
        super();
        this.stConf = stConf;
        this.otConf = otConf;
    }
    
    private static class DoubleDocument extends PlainDocument {
        private static final long serialVersionUID = -8739158860688667543L;
        private String oldStr = "";
        private static final Pattern doublePattern = Pattern.compile("(^[1-9]\\d*(\\.\\d+)?)|(^0(\\.\\d+)?)");
        private static final Pattern halfDoublePattern = Pattern.compile("(^[1-9]\\d*\\.?)|(^0\\.?)");

        @Override
        public void insertString(int offs, String str, AttributeSet a) 
                throws BadLocationException {
            oldStr = this.getText(0, getLength());
            
            if (str == null) { 
                return; 
            } 
            int length = 0; 
            char[] upper = str.toCharArray(); 
            for (int i = 0; i  < upper.length; i++) { 
                if (upper[i] == '.' || (upper[i] >= '0' && upper[i]  <= '9')) { 
                    upper[length++] = upper[i]; 
                } else { 
                    length = 0; 
                    break; 
                } 
            } 
            
            String insertStr = new String(upper, 0, length);

            super.insertString(offs, insertStr, a);
            
            checkAndCal();
        }

        private void checkAndCal() throws BadLocationException {
            String newStr = this.getText(0, getLength());
            if (isLegalDouble(newStr)) {
                oldStr = newStr;
                //TODO: 计算
                
            } else {
                if (!isHalfDouble(newStr)) {
                    super.replace(0, getLength(), oldStr, null);
                }
            }
        }        
        
        @Override
        public void remove(int offs, int len) throws BadLocationException {
            oldStr = this.getText(0, getLength());
            super.remove(offs, len);
            checkAndCal();
        }

        @Override
        public void replace(int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {
//            System.out.println("replace");
//            oldStr = this.getText(0, getLength());
            super.replace(offset, length, text, attrs);
//            checkAndCal();
        }
        
        private boolean isLegalDouble(String num){
            
            boolean valid = doublePattern.matcher(num).matches();
            System.out.println(num + " d: " + valid);
            return valid;
        }
        
        private boolean isHalfDouble(String num){
            if (num.isEmpty()) {
                return true;
            }
            boolean valid = halfDoublePattern.matcher(num).matches();
            System.out.println(num + " hd: " + valid);
            return valid;
        }
    }
    
    public JPanel setUpView(){
        JPanel taxPanel = new JPanel(new GridLayout(4, 2));
        taxPanel.setBorder(BorderFactory.createTitledBorder("收入概况"));
        JLabel l1 = new JLabel("收入类型：");     
        calType = new JComboBox();
        calType.addItem(TPConst.TYPE_SALARY);
        calType.addItem(TPConst.TYPE_INTER);
        calType.addItem(TPConst.TYPE_ACC);
        
        JLabel l2 = new JLabel("税前收入：");
        preTax = new JTextField("", 20);
        preTax.setDocument(new DoubleDocument());
        
        
        JLabel l3 = new JLabel("税后收入：");
        postTax = new JTextField("", 20);
        postTax.setEditable(false);
        
        JLabel l4 = new JLabel("缴纳个税：");
        tax = new JTextField("", 20);
        tax.setEditable(false);
        
        taxPanel.add(l1);
        taxPanel.add(calType);
        taxPanel.add(l2);
        taxPanel.add(preTax);
        taxPanel.add(l3);
        taxPanel.add(postTax);
        taxPanel.add(l4);
        taxPanel.add(tax);

        final JPanel ssPanel = new JPanel(new GridLayout(1, 2));
        ssPanel.setBorder(BorderFactory.createTitledBorder("社保缴费"));
        JPanel indvPan = new JPanel(new GridLayout(8, 1));
        indvPan.setBorder(BorderFactory.createTitledBorder("个人"));
        JPanel[] ilines = makeSSPan(indvPan);
        
        ilines[0].add(new JLabel("个人缴费合计:"));
        indvSSTotal = new JTextField("", 10);
        ilines[0].add(indvSSTotal);
        indvSSTotal.setEditable(false);
        
        ilines[1].add(new JLabel("应缴税额总计:"));
        inTaxSalary = new JTextField("", 10);
        ilines[1].add(inTaxSalary);
        inTaxSalary.setEditable(false);
        
        ilines[2].add(new JLabel("养老  "));
        indvPenRate = new JTextField(getPercentRate(stConf.getIndvPension()), 5);
        ilines[2].add(indvPenRate);
        ilines[2].add(new JLabel("%:  "));
        indvPen = new JTextField("", 8);
        indvPen.setEditable(false);
        ilines[2].add(indvPen);
        
        ilines[3].add(new JLabel("医疗  "));
        indvMedRate = new JTextField(getPercentRate(stConf.getIndvMedicare()), 5);
        ilines[3].add(indvMedRate);
        ilines[3].add(new JLabel("%:  "));
        indvMed = new JTextField("", 8);
        indvMed.setEditable(false);
        ilines[3].add(indvMed);
        
        ilines[4].add(new JLabel("失业  "));
        indvUnempRate = new JTextField(getPercentRate(stConf.getIndvUnEmp()), 5);
        ilines[4].add(indvUnempRate);
        ilines[4].add(new JLabel("%:  "));
        indvUnemp = new JTextField("", 8);
        indvUnemp.setEditable(false);
        ilines[4].add(indvUnemp);
        
        ilines[7].add(new JLabel("公积金  "));
        indvAccFundRate = new JTextField(getPercentRate(stConf.getIndvAccFund()), 5);
        ilines[7].add(indvAccFundRate);
        ilines[7].add(new JLabel("%:  "));
        indvAccFund = new JTextField("", 8);
        indvAccFund.setEditable(false);
        ilines[7].add(indvAccFund);
        
        JPanel comPan = new JPanel(new GridLayout(8, 1));
        JPanel[] clines = makeSSPan(comPan);
        comPan.setBorder(BorderFactory.createTitledBorder("公司"));
        
        clines[0].add(new JLabel("公司缴费合计:"));
        comSSTotal = new JTextField("", 10);
        clines[0].add(comSSTotal);
        comSSTotal.setEditable(false);

        clines[1].add(new JLabel("公司支出总计:"));
        comTotalCost = new JTextField("", 10);
        clines[1].add(comTotalCost);
        comTotalCost.setEditable(false);

        clines[2].add(new JLabel("养老  "));
        comPenRate = new JTextField(getPercentRate(stConf.getComPension()), 5);
        clines[2].add(comPenRate);
        clines[2].add(new JLabel("%:  "));
        comPen = new JTextField("", 8);
        comPen.setEditable(false);
        clines[2].add(comPen);

        clines[3].add(new JLabel("医疗  "));
        comMedRate = new JTextField(getPercentRate(stConf.getComMedicare()), 5);
        clines[3].add(comMedRate);
        clines[3].add(new JLabel("%:  "));
        comMed = new JTextField("", 8);
        comMed.setEditable(false);
        clines[3].add(comMed);

        clines[4].add(new JLabel("失业  "));
        comUnempRate = new JTextField(getPercentRate(stConf.getComUnEmp()), 5);
        clines[4].add(comUnempRate);
        clines[4].add(new JLabel("%:  "));
        comUnemp = new JTextField("", 8);
        comUnemp.setEditable(false);
        clines[4].add(comUnemp);

        clines[5].add(new JLabel("工伤  "));
        comInjRate = new JTextField(getPercentRate(stConf.getComInjure()), 5);
        clines[5].add(comInjRate);
        clines[5].add(new JLabel("%:  "));
        comInj = new JTextField("", 8);
        comInj.setEditable(false);
        clines[5].add(comInj);

        clines[6].add(new JLabel("生育  "));
        comMateRate = new JTextField(getPercentRate(stConf.getComMaternity()), 5);
        clines[6].add(comMateRate);
        clines[6].add(new JLabel("%:  "));
        comMate = new JTextField("", 8);
        comMate.setEditable(false);
        clines[6].add(comMate);

        clines[7].add(new JLabel("公积金  "));
        comAccFundRate = new JTextField(getPercentRate(stConf.getComAccFund()), 5);
        clines[7].add(comAccFundRate);
        clines[7].add(new JLabel("%:  "));
        comAccFund = new JTextField("", 8);
        comAccFund.setEditable(false);
        clines[7].add(comAccFund);
        
        ssPanel.add(indvPan);
        ssPanel.add(comPan);
        //TODO: 继续画社保部分
        
        final JPanel basePanel = new JPanel(new GridLayout(2, 1));
        JPanel bline1 = new JPanel(new GridLayout(1, 2));
        bline1.add(new JLabel("地区平均月薪(用于计算社保基数)："));
        avgSalary = new JTextField(stConf.getAvgSalary().toString(), 10);
        bline1.add(avgSalary);
        JPanel bline2 = new JPanel();
        bline2.add(new JLabel("缴费基数：社保"));
        ssBase = new JTextField("", 6);
        ssBase.setEditable(false);
        bline2.add(ssBase);
        bline2.add(new JLabel("缴费基数：公积金"));
        ssAcc = new JTextField("", 6);
        ssAcc.setEditable(false);
        bline2.add(ssAcc);
        bline2.add(new JLabel("缴费基数：封顶数"));
        ssTop = new JTextField(stConf.getTopSS().toString(), 6);
        ssTop.setEditable(false);
        bline2.add(ssTop);
        
        basePanel.add(bline1);
        basePanel.add(bline2); 
               
        calType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = (String) ((JComboBox) e.getSource()).getSelectedItem();
                //System.out.println(type);
                if (type.equals(TPConst.TYPE_SALARY)) {
                    ssPanel.setVisible(true);
                    basePanel.setVisible(true);
                } else {
                    ssPanel.setVisible(false);
                    basePanel.setVisible(false);
                    preTax.setText("");
                    tax.setText("");
                    postTax.setText("");
                }
            }
        });
        
        this.context = new JPanel();   
//        this.context.setSize(TPConst.SPANEL_WIDTH, TPConst.SPANEL_HEIGHT);
        this.context.add(taxPanel, BorderLayout.NORTH);
        this.context.add(ssPanel);
        this.context.add(basePanel, BorderLayout.SOUTH);
 
        return this.context;
    }

    private String getPercentRate(BigDecimal rate) {
        return rate.multiply(IncomeConsts.HUNDRED).toString();
    }

    private JPanel[] makeSSPan(JPanel ssPan) {
        JPanel[] lines = {new JPanel(new GridLayout(1, 2)),
                new JPanel(new GridLayout(1, 2)),
                new JPanel(new GridLayout(1, 3)),
                new JPanel(new GridLayout(1, 3)),
                new JPanel(new GridLayout(1, 3)),
                new JPanel(new GridLayout(1, 3)),
                new JPanel(new GridLayout(1, 3)),
                new JPanel(new GridLayout(1, 3)),
        };
        
        for (JPanel line : lines) {
            ssPan.add(line);
        }
        
        return lines;
    }
}
