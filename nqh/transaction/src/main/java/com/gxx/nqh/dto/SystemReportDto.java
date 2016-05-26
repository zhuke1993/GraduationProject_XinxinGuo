package com.gxx.nqh.dto;

/**
 * Created by GXX on 2016/4/10.
 */
public class SystemReportDto {
    private int totoal;
    private int inRaising;
    private int completed;
    private int inRepayment;
    private int repaymentCompleted;


    public SystemReportDto(int totoal, int inRaising, int completed, int inRepayment, int repaymentCompleted) {
        this.totoal = totoal;
        this.inRaising = inRaising;
        this.completed = completed;
        this.inRepayment = inRepayment;
        this.repaymentCompleted = repaymentCompleted;
    }

    public int getRepaymentCompleted() {
        return repaymentCompleted;
    }

    public void setRepaymentCompleted(int repaymentCompleted) {
        this.repaymentCompleted = repaymentCompleted;
    }

    public int getTotoal() {
        return totoal;
    }

    public void setTotoal(int totoal) {
        this.totoal = totoal;
    }

    public int getInRaising() {
        return inRaising;
    }

    public void setInRaising(int inRaising) {
        this.inRaising = inRaising;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getInRepayment() {
        return inRepayment;
    }

    public void setInRepayment(int inRepayment) {
        this.inRepayment = inRepayment;
    }
}
