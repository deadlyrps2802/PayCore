package com.paycore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payslips")
public class Payslip {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "pay_month", nullable = false)
    private Integer month;
    @Column(name = "pay_year", nullable = false)
    private Integer year;
    @Column(name = "basic_salary", nullable = false)
    private BigDecimal basicSalary;
    @Column(name = "hra", nullable = false)
    private BigDecimal hra;
    @Column(name = "allowances", nullable = false)
    private BigDecimal allowances;
    @Column(name = "gross_salary", nullable = false)
    private BigDecimal grossSalary;
    @Column(name = "pf_deduction", nullable = false)
    private BigDecimal pfDeduction;
    @Column(name = "tax_deduction", nullable = false)
    private BigDecimal taxDeduction;
    @Column(name = "unpaid_leave_days", nullable = false)
    private Integer unpaidLeaveDays;
    @Column(name = "unpaid_leave_deduction", nullable = false)
    private BigDecimal unpaidLeaveDeduction;
    @Column(name = "total_deductions", nullable = false)
    private BigDecimal totalDeductions;
    @Column(name = "net_pay", nullable = false)
    private BigDecimal netPay;
    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    public Payslip() { this.generatedAt = LocalDateTime.now(); }

    public Payslip(Employee employee, Integer month, Integer year, BigDecimal basicSalary, BigDecimal hra,
                   BigDecimal allowances, BigDecimal grossSalary, BigDecimal pfDeduction, BigDecimal taxDeduction,
                   Integer unpaidLeaveDays, BigDecimal unpaidLeaveDeduction, BigDecimal totalDeductions, BigDecimal netPay) {
        this.employee = employee; this.month = month; this.year = year; this.basicSalary = basicSalary;
        this.hra = hra; this.allowances = allowances; this.grossSalary = grossSalary; this.pfDeduction = pfDeduction;
        this.taxDeduction = taxDeduction; this.unpaidLeaveDays = unpaidLeaveDays;
        this.unpaidLeaveDeduction = unpaidLeaveDeduction; this.totalDeductions = totalDeductions;
        this.netPay = netPay; this.generatedAt = LocalDateTime.now();
    }

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Employee getEmployee(){return employee;} public void setEmployee(Employee v){employee=v;}
    public Integer getMonth(){return month;} public void setMonth(Integer v){month=v;}
    public Integer getYear(){return year;} public void setYear(Integer v){year=v;}
    public BigDecimal getBasicSalary(){return basicSalary;} public void setBasicSalary(BigDecimal v){basicSalary=v;}
    public BigDecimal getHra(){return hra;} public void setHra(BigDecimal v){hra=v;}
    public BigDecimal getAllowances(){return allowances;} public void setAllowances(BigDecimal v){allowances=v;}
    public BigDecimal getGrossSalary(){return grossSalary;} public void setGrossSalary(BigDecimal v){grossSalary=v;}
    public BigDecimal getPfDeduction(){return pfDeduction;} public void setPfDeduction(BigDecimal v){pfDeduction=v;}
    public BigDecimal getTaxDeduction(){return taxDeduction;} public void setTaxDeduction(BigDecimal v){taxDeduction=v;}
    public Integer getUnpaidLeaveDays(){return unpaidLeaveDays;} public void setUnpaidLeaveDays(Integer v){unpaidLeaveDays=v;}
    public BigDecimal getUnpaidLeaveDeduction(){return unpaidLeaveDeduction;} public void setUnpaidLeaveDeduction(BigDecimal v){unpaidLeaveDeduction=v;}
    public BigDecimal getTotalDeductions(){return totalDeductions;} public void setTotalDeductions(BigDecimal v){totalDeductions=v;}
    public BigDecimal getNetPay(){return netPay;} public void setNetPay(BigDecimal v){netPay=v;}
    public LocalDateTime getGeneratedAt(){return generatedAt;} public void setGeneratedAt(LocalDateTime v){generatedAt=v;}
}
