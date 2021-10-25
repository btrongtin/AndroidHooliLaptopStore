package com.example.hoolilaptopstore.model;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private String hoTen;
    private String tenDangNhap;
    private String diaChi;
    private String soDienThoai;

    public Account(int id, String hoTen, String tenDangNhap, String diaChi, String soDienThoai) {
        this.id = id;
        this.hoTen = hoTen;
        this.tenDangNhap = tenDangNhap;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
