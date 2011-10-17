package com.ooobgy.demo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ooobgy.income.consts.IncomeConsts;
import com.ooobgy.income.pojo.OtherTaxConf;
import com.ooobgy.income.pojo.SalaryTaxConf;

public class SinglePanel{
    private JPanel context;
    private SalaryTaxConf stConf;
    private OtherTaxConf otConf;
    private JComboBox calType;
    
    public JComboBox getCalType() {
        return calType;
    }

    public SinglePanel(SalaryTaxConf stConf, OtherTaxConf otConf) {
        super();
        this.stConf = stConf;
        this.otConf = otConf;
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
        JTextField preTax = new JTextField("", 20);
        
        JLabel l3 = new JLabel("税后收入：");
        JTextField postTax = new JTextField("", 20);
        postTax.setEditable(false);
        
        JLabel l4 = new JLabel("缴纳个税：");
        JTextField tax = new JTextField("", 20);
        tax.setEditable(false);
        
        taxPanel.add(l1);
        taxPanel.add(calType);
        taxPanel.add(l2);
        taxPanel.add(preTax);
        taxPanel.add(l3);
        taxPanel.add(postTax);
        taxPanel.add(l4);
        taxPanel.add(tax);

        JPanel ssPanel = new JPanel(new GridLayout(1, 2));
        ssPanel.setBorder(BorderFactory.createTitledBorder("社保缴费"));
        JPanel indvPan = new JPanel(new GridLayout(8, 1));
        indvPan.setBorder(BorderFactory.createTitledBorder("个人"));
        JPanel[] ilines = makeSSPan(indvPan);
        
        ilines[0].add(new JLabel("个人缴费合计:"));
        JTextField indvSSTotal = new JTextField("", 10);
        ilines[0].add(indvSSTotal);
        indvSSTotal.setEditable(false);
        
        ilines[1].add(new JLabel("应缴税额总计:"));
        JTextField inTaxSalary = new JTextField("", 10);
        ilines[1].add(inTaxSalary);
        inTaxSalary.setEditable(false);
        
        ilines[2].add(new JLabel("养老  "));
        JTextField indvPenRate = new JTextField(getPercentRate(stConf.getIndvPension()), 5);
        ilines[2].add(indvPenRate);
        ilines[2].add(new JLabel("%:  "));
        JTextField indvPen = new JTextField("", 8);
        indvPen.setEditable(false);
        ilines[2].add(indvPen);
        
        ilines[3].add(new JLabel("医疗  "));
        JTextField indvMedRate = new JTextField(getPercentRate(stConf.getIndvMedicare()), 5);
        ilines[3].add(indvMedRate);
        ilines[3].add(new JLabel("%:  "));
        JTextField indvMed = new JTextField("", 8);
        indvMed.setEditable(false);
        ilines[3].add(indvMed);
        
        ilines[4].add(new JLabel("失业  "));
        JTextField indvUnempRate = new JTextField(getPercentRate(stConf.getIndvUnEmp()), 5);
        ilines[4].add(indvUnempRate);
        ilines[4].add(new JLabel("%:  "));
        JTextField indvUnemp = new JTextField("", 8);
        indvUnemp.setEditable(false);
        ilines[4].add(indvUnemp);
        
        ilines[7].add(new JLabel("公积金  "));
        JTextField indvAccFundRate = new JTextField(getPercentRate(stConf.getIndvAccFund()), 5);
        ilines[7].add(indvAccFundRate);
        ilines[7].add(new JLabel("%:  "));
        JTextField indvAccFund = new JTextField("", 8);
        indvAccFund.setEditable(false);
        ilines[7].add(indvAccFund);
        
        JPanel comPan = new JPanel(new GridLayout(8, 1));
        JPanel[] clines = makeSSPan(comPan);
        comPan.setBorder(BorderFactory.createTitledBorder("公司"));
        
        clines[0].add(new JLabel("公司缴费合计:"));
        JTextField comSSTotal = new JTextField("", 10);
        clines[0].add(comSSTotal);
        comSSTotal.setEditable(false);

        clines[1].add(new JLabel("公司支出总计:"));
        JTextField comTotalCost = new JTextField("", 10);
        clines[1].add(comTotalCost);
        comTotalCost.setEditable(false);

        clines[2].add(new JLabel("养老  "));
        JTextField comPenRate = new JTextField(getPercentRate(stConf.getComPension()), 5);
        clines[2].add(comPenRate);
        clines[2].add(new JLabel("%:  "));
        JTextField comPen = new JTextField("", 8);
        comPen.setEditable(false);
        clines[2].add(comPen);

        clines[3].add(new JLabel("医疗  "));
        JTextField comMedRate = new JTextField(getPercentRate(stConf.getComMedicare()), 5);
        clines[3].add(comMedRate);
        clines[3].add(new JLabel("%:  "));
        JTextField comMed = new JTextField("", 8);
        comMed.setEditable(false);
        clines[3].add(comMed);

        clines[4].add(new JLabel("失业  "));
        JTextField comUnempRate = new JTextField(getPercentRate(stConf.getComUnEmp()), 5);
        clines[4].add(comUnempRate);
        clines[4].add(new JLabel("%:  "));
        JTextField comUnemp = new JTextField("", 8);
        comUnemp.setEditable(false);
        clines[4].add(comUnemp);

        clines[5].add(new JLabel("工伤  "));
        JTextField comInjRate = new JTextField(getPercentRate(stConf.getComInjure()), 5);
        clines[5].add(comInjRate);
        clines[5].add(new JLabel("%:  "));
        JTextField comInj = new JTextField("", 8);
        comInj.setEditable(false);
        clines[5].add(comInj);

        clines[6].add(new JLabel("生育  "));
        JTextField comMateRate = new JTextField(getPercentRate(stConf.getComMaternity()), 5);
        clines[6].add(comMateRate);
        clines[6].add(new JLabel("%:  "));
        JTextField comMate = new JTextField("", 8);
        comMate.setEditable(false);
        clines[6].add(comMate);

        clines[7].add(new JLabel("公积金  "));
        JTextField comAccFundRate = new JTextField(getPercentRate(stConf.getComAccFund()), 5);
        clines[7].add(comAccFundRate);
        clines[7].add(new JLabel("%:  "));
        JTextField comAccFund = new JTextField("", 8);
        comAccFund.setEditable(false);
        clines[7].add(comAccFund);
        
        ssPanel.add(indvPan);
        ssPanel.add(comPan);
        //TODO: 继续画社保部分
        
        JPanel basePanel = new JPanel(new GridLayout(2, 1));
        JPanel bline1 = new JPanel(new GridLayout(1, 2));
        bline1.add(new JLabel("地区平均月薪(用于计算社保基数)："));
        JTextField avgSalary = new JTextField(stConf.getAvgSalary().toString(), 10);
        bline1.add(avgSalary);
        JPanel bline2 = new JPanel();
        bline2.add(new JLabel("缴费基数：社保"));
        JTextField ssBase = new JTextField("", 6);
        ssBase.setEditable(false);
        bline2.add(ssBase);
        bline2.add(new JLabel("缴费基数：公积金"));
        JTextField ssAcc = new JTextField("", 6);
        ssAcc.setEditable(false);
        bline2.add(ssAcc);
        bline2.add(new JLabel("缴费基数：封顶数"));
        JTextField ssTop = new JTextField(stConf.getTopSS().toString(), 6);
        ssTop.setEditable(false);
        bline2.add(ssTop);
        
        basePanel.add(bline1);
        basePanel.add(bline2);
        //TODO: 继续画基线部分
        
        class SalaryChangeListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
        }
               
        calType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                
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
