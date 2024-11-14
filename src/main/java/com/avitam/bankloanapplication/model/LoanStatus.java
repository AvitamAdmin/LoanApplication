package com.avitam.bankloanapplication.model;

public enum LoanStatus {
    ACTIVE, INACTIVE;
    public static LoanStatus get(int index){
        return LoanStatus.values()[index];
    }
}
