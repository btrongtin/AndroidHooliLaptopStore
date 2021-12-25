package com.example.hoolilaptopstore.model;

public class GioHang {
    private int idSP;
    private String tenSP;
    private long giaSP;
    private String hinhAnhSP;
    private int soLuongSP; // số lượng sp của chi tiết sp. vd laptop A, mua 2 cái =? soluongsp = 2
    private int soLuongTon; //số lượng sp đó còn trong kho

    public GioHang(int idSP, String tenSP, long giaSP, String hinhAnhSP, int soLuongSP, int soLuongTon) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhAnhSP = hinhAnhSP;
        this.soLuongSP = soLuongSP;
        this.soLuongTon = soLuongTon;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public long getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

    public int getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }
}
