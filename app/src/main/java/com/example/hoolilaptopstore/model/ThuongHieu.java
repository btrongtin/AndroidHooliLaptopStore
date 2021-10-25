package com.example.hoolilaptopstore.model;

import java.io.Serializable;

public class ThuongHieu implements Serializable {
    private int id;
    private String ten;
    private String hinhAnh;

    public ThuongHieu(int id, String ten, String hinhAnh) {
        this.id = id;
        this.ten = ten;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
