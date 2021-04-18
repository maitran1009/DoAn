/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pizza.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author xonv
 */
public class VNPAYConfig {
	public static String vnp_Version = "2.0.0";
	public static String vnp_Command = "pay";
	public static String vnp_PayUrl = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
	public static String vnp_Returnurl = "http://localhost:9090/mySuFood/";
	public static String vnp_TmnCode = "C1CYOX5V";
	public static String order_type = "180000";
	public static String vnp_IpAddr = "127.0.0.1";
	public static String vnp_CurrCode = "VND";
	public static String vnp_Merchant = "MySu Food";
	public static String vnp_OrderInfo = "Thanh toán đơn hàng tại MySu Food";
	public static String vnp_HashSecret = "JHBHNXTUXDTIPPLAJMYDRSVDTFOIOTRG";
	public static String vnp_apiUrl = "http://sandbox.vnpayment.vn/merchant_webapi/merchant.html";

	public static String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes("UTF-8"));

            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            digest = sb.toString();

        } catch (UnsupportedEncodingException ex) {
            digest = "";
            // Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
            // null, ex);
        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
            // null, ex);
            digest = "";
        }
        return digest;
    }

	public static String getRandomNumber(int len) {
		Random rnd = new Random();
		String chars = "0123456789";
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
}
