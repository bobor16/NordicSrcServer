/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicLayer;

/**
 *
 * @author mehgn
 */
public class Offer {

    private int offerID;
    private int orderID;
    private Boolean status;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    private int amount;
    private double priceper;
    private double pricetotal;
    private String completionDate;
    private String deliveryDate;
    private String briefDescription;
    private byte[] psBytes;
    private String psname;

    public String getPsname() {
        return psname;
    }

    public void setPsname(String psname) {
        this.psname = psname;
    }
    private String manfemail;

    public String getManfemail() {
        return manfemail;
    }

    public void setManfemail(String manfemail) {
        this.manfemail = manfemail;
    }

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPriceper() {
        return priceper;
    }

    public void setPriceper(double priceper) {
        this.priceper = priceper;
    }

    public double getPricetotal() {
        return pricetotal;
    }

    public void setPricetotal(double pricetotal) {
        this.pricetotal = pricetotal;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public byte[] getPsBytes() {
        return psBytes;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPsBytes(byte[] psBytes) {
        this.psBytes = psBytes;
    }

    public Offer(int offerID, int orderID, int amount, double priceper, double pricetotal, String completionDate, String deliveryDate, String briefDescription, String psname ,Boolean stauts) {
        this.offerID = offerID;
        this.orderID = orderID;
        this.amount = amount;
        this.priceper = priceper;
        this.pricetotal = pricetotal;
        this.completionDate = completionDate;
        this.deliveryDate = deliveryDate;
        this.briefDescription = briefDescription;
        this.psname = psname;
        this.status = status;
    }

    

}
