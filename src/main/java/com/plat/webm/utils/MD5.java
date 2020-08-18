package com.plat.webm.utils;

import java.security.MessageDigest;

import org.springframework.util.DigestUtils;

public class MD5 {

	/**
	 * 第一种方法，使用java包，生成MD5加密
	 * @param origin
	 */
	public static String cryptWithSolt(String origin) {
		String resultSting = null;
		try {
			resultSting = new String(Constant.SALT + origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultSting = byteArrayToHexString(md.digest(resultSting.getBytes()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultSting;
	}

	public static String byteArrayToHexString(byte[] bArr) {
		StringBuffer sb = new StringBuffer(bArr.length);
		String sTmp;

		for (int i = 0; i < bArr.length; i++) {
			sTmp = Integer.toHexString(0xFF & bArr[i]);
			if (sTmp.length() < 2)
				sb.append(0);
			sb.append(sTmp.toUpperCase());
		}

		return sb.toString();
	}

	
	/**
	 * 第二种方法，使用spring的包生成md5
	 * @param seckillId
	 * @return
	 */
	public static String getMD5(String origin) {
		String base = Constant.SALT + origin;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes()).toUpperCase();
		return md5;
	}

	/**
	 * 经测试，两种方法的效果是一样的
	 */
	public static void main(String args[]) {
		
		System.out.println(cryptWithSolt("123456"));
		System.out.println(getMD5("123456"));
	}
}
