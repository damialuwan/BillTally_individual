package com.example.individual3;

import java.io.Serializable;

public class BillData implements Serializable {
    public String month;
    public int kWhUsed;
    public double totalCharges;
    public int rebatePercentage;
    public double finalCost;

    public BillData() {}

    public BillData(String month, int kWhUsed, double totalCharges, int rebatePercentage, double finalCost) {
        this.month = month;
        this.kWhUsed = kWhUsed;
        this.totalCharges = totalCharges;
        this.rebatePercentage = rebatePercentage;
        this.finalCost = finalCost;
    }
}