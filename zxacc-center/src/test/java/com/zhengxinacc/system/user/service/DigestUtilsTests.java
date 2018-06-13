/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.service;

import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午4:02:50
 * @version 1.0
 */
public class DigestUtilsTests {

	@Test
	public void testBase64(){
		String salt = String.valueOf(Calendar.getInstance().getTimeInMillis());
		System.out.println(salt);
		String encode = Base64.encode(salt.getBytes());
		System.out.println(encode);
		
		byte[] decode = Base64.decode(encode);
		System.out.println(new String(decode));
	}
	
	@Test
	public void testMd5Encode(){
		String md5Hex = DigestUtils.md5Hex("888888");
		System.out.println(md5Hex);
	}
	
	@Test
	public void testTof(){
		System.out.println(Boolean.parseBoolean("true"));
		System.out.println(Boolean.valueOf("0"));
	}
}
