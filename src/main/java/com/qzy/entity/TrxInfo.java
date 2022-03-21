package com.qzy.entity;

/**
 * @author: Charlie Qzy
 * @date: 2022/3/21
 * @description:
 * @version: 1.0.0
 */

public class TrxInfo {
    private String id;
    private String city;
    private Double trxAmt;
    private Integer trxCnt;
    private String trxDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTrxAmt() {
        return trxAmt;
    }

    public void setTrxAmt(Double trxAmt) {
        this.trxAmt = trxAmt;
    }

    public Integer getTrxCnt() {
        return trxCnt;
    }

    public void setTrxCnt(Integer trxCnt) {
        this.trxCnt = trxCnt;
    }

    public String getTrxDesc() {
        return trxDesc;
    }

    public void setTrxDesc(String trxDesc) {
        this.trxDesc = trxDesc;
    }
}
