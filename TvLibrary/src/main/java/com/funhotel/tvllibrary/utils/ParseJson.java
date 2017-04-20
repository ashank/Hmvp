package com.funhotel.tvllibrary.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: ParseJson
 * @Description: TODO 解析json
 * @author Zhiyahan
 * @date 2015年8月24日 上午10:55:13
 */
public class ParseJson {
	private Gson gson;
	public ParseJson() {
		// TODO Auto-generated constructor stub
		gson=new Gson();
	}

	/**
	 * @Title: stringToObject
	 * @Description: TODO  解析json生成对象
	 *  @param json json数据
	 *  @param cls
	 *  @return T
	 */
	public <T> T stringToObject(String json, Class<T> cls) {
		// TODO Auto-generated method stub
		T t=null;
		try {
			t=gson.fromJson(json, cls);
		}catch (Exception e){
			e.printStackTrace();
			t=null;

		}
		return t;
	}
	
	
	/**
	 * @Title: stringToArray
	 * @Description: TODO 解析Json生成数组
	 *  @param json json数据
	 *  @param cls 具体类的数组
	 *  @return List<T>
	 */
	/*public <T> List<T> stringToArray(String json,Class<T[]> cls) {
		// TODO Auto-generated method stub
		T[] atr=gson.fromJson(json, cls);
		return Arrays.asList(atr);
	}*/

	/**
	 * @Title: stringToArray
	 * @Description: TODO 解析Json生成数组
	 *  @param json json数据
	 *  @return List<T>
	 */
	public <T> List<T> stringToArray(String json) {
		// TODO Auto-generated method stub
		Type objectType = new TypeToken<T[]>(){}.getType();
		T[] atr=gson.fromJson(json, objectType);
		return Arrays.asList(atr);
	}

	/**
	 * 转换成Json对象
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public <T> String ObjectToString(Object cls) {
		// TODO Auto-generated method stub
		return gson.toJson(cls);
	}

	
}
