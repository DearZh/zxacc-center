/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午7:45:39
 * @version 1.0
 */
public class EncryptUtils {

	/**
	 * 对密码进行加密
	 * @author eko.zhan at 2017年12月23日 下午7:48:47
	 * @param password 明文
	 * @param salt 明文
	 * @return
	 */
	public static String encode(String password, String salt){
		String origin = password + "&" + salt;
		String md5Hex = DigestUtils.md5Hex(origin); //获得密文
		return md5Hex;
	}
	/**
	 * 验证用户密码准确性
	 * @author eko.zhan at 2017年12月23日 下午7:55:47
	 * @param input 用户输入的密码，明文传入
	 * @param target 数据库中存储的密码，密文，不可逆
	 * @param salt 数据库中存储的盐值，密文，可逆
	 * @return
	 * @throws Base64DecodingException 
	 */
	public static Boolean verify(String input, String target, String salt){
		salt = new String(Base64.decodeBase64(salt));
		String password = encode(input, salt);
		if (target.equals(password)){
			return true;
		}
		return false;
	}
}

