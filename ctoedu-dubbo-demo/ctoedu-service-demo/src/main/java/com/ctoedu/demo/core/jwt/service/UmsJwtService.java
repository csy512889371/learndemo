package com.ctoedu.demo.core.jwt.service;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ctoedu.security.rsa.service.FayRsaService;
import com.ctoedu.demo.facade.jwt.domain.Header;
import com.ctoedu.demo.facade.jwt.domain.Payload;

/**
 * JWT接口
 * @author feichongzheng
 *
 */
@Service("umsJwtService")
public class UmsJwtService{
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public String createJwt(String username, boolean remember) throws Exception{
		long sevenDayTime = 7*24*60*60*1000;
		long oneHourTime = 60*60*1000;
		Date currentDate = new Date();
		long dataTime = currentDate.getTime();
		Header header = new Header();
		header.setTyp("JWT");
		String headerJson = JSONObject.toJSONString(header);
		
		String headerJsonBase64 = new String(Base64.encodeBase64(headerJson.getBytes()));
		
		Payload payload = new Payload();
		payload.setName(username);
		payload.setIat(dataTime);
		if(remember){
			payload.setExp(dataTime+sevenDayTime);
		}else{
			payload.setExp(dataTime+oneHourTime);
		}
		String payloadJson = JSONObject.toJSONString(payload);
		
		String payloadJsonBase64 = new String(Base64.encodeBase64(payloadJson.getBytes()));
		
		String encodedString = headerJsonBase64 + "." + payloadJsonBase64;
		
		KeyPair keyPair = FayRsaService.getKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		byte[] encodedText = FayRsaService.encryptByPublicKey(publicKey, encodedString.getBytes());
		String jwt = Base64.encodeBase64String(encodedText);
		//缓存
		ValueOperations<String, Object> vo = redisTemplate.opsForValue();
		if(remember){
			vo.set("uums:jwt:"+jwt, privateKey, sevenDayTime, TimeUnit.MILLISECONDS);
		}else{
			vo.set("uums:jwt:"+jwt, privateKey, oneHourTime, TimeUnit.MILLISECONDS);
		}
		return jwt;
	}
	
	public Payload getPayload(String jwt){
		try {
			ValueOperations<String, Object> vo = redisTemplate.opsForValue();
			Object obj = vo.get("uums:jwt:"+jwt);
			if(obj != null){
				String decodedText = new String(FayRsaService.decryptByPrivateKey((RSAPrivateKey)obj, Base64.decodeBase64(jwt)));
				String[] arr = decodedText.split("\\.");
				String decodedBase64PayloadJson = new String(Base64.decodeBase64(arr[1]));
				Payload payload = JSONObject.parseObject(decodedBase64PayloadJson, Payload.class);
				return payload;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void removeJwt(String username, String jwt) throws Exception{
		if(username == null || jwt == null){
			return;
		}
		Payload payload = getPayload(jwt);
		if(payload != null){
			if(username.equals(payload.getName())){
				redisTemplate.delete("uums:jwt:" + jwt);
			}
		}
	}
}