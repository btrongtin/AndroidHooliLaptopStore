package com.example.hoolilaptopstore.util;

public class Server {
    public static String localHost = "192.168.1.234:8080";
    public static String duongDanThuongHieu = "http://" + localHost + "/server/getthuonghieu.php";
    public static String duongDanSPMoiNhat = "http://" + localHost + "/server/getsanphammoinhat.php";
    public static String duongDanLaptopTheoThuongHieu = "http://" + localHost + "/server/getsanpham.php?page=";
    public static String duongDanLogin = "http://" + localHost + "/server/login.php";
    public static String duongDanRegister = "http://" + localHost + "/server/register.php";
}
