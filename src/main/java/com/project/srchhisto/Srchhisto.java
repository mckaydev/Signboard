package com.project.srchhisto;

public class Srchhisto {
    private String memberId;
    private String imageFileName;
    private String gpsAddress;
    private String storeName;
    private String storeMenu;
    private String storePhone;
    private int rate;
    private String aLineReview;
    private int isBookmarked;

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getGpsAddress() {
        return gpsAddress;
    }

    public void setGpsAddress(String gpsAddress) {
        this.gpsAddress = gpsAddress;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getIsBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(int isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreMenu() {
        return storeMenu;
    }

    public void setStoreMenu(String storeMenu) {
        this.storeMenu = storeMenu;
    }

    public int getRate() { return rate; }

    public void setRate(int rate) { this.rate = rate; }

    public String getaLineReview() {
        return aLineReview;
    }

    public void setaLineReview(String aLineReview) {
        this.aLineReview = aLineReview;
    }
}
