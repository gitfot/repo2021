package org.fun.test.contractType.base;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wanwan 2021/10/15
 */
@Data
public class ResultMap {

	private Map<String, Object> resultMap;

	public static ResultMap creatResultMap(int initialCapacity){
		return new ResultMap(initialCapacity);
	}

	public static ResultMap creatResultMap(){
		return new ResultMap();
	}

	public ResultMap(int initialCapacity){
		resultMap = Maps.newHashMapWithExpectedSize(initialCapacity);
	}

	public ResultMap(){
		resultMap = Maps.newHashMapWithExpectedSize(2);
	}

	@Deprecated
	public ResultMap errorBuilder(String errorDesc){
		this.resultMap.put("returnCode","-1");
		this.resultMap.put("returnDesc",errorDesc);
		return this;
	}

	private ResultMap builder(String returnCode, String errorDesc){
		this.resultMap.put("returnCode",returnCode);
		this.resultMap.put("returnDesc",errorDesc);
		return this;
	}

	public static ResultMap builder(ResultEnum resultEnum, int initialCapacity){
		return ResultMap.creatResultMap(initialCapacity).builder(resultEnum.getCode(),resultEnum.getDesc());
	}

	public static ResultMap builder(ResultEnum resultEnum){
		return ResultMap.builder(resultEnum,2);
	}

	public ResultMap successBuilder(){
		return builder(ResultEnum.SUCCESS);
	}

	public Map build(){
		return this.resultMap;
	}

	/**
	 * 封装result层级
	 * @return
	 */
	public Map buildWithResult(){
		Map returnMap = Maps.newHashMapWithExpectedSize(1);
		returnMap.put("result", this.resultMap);
		return returnMap;
	}

	/**
	 * 加入键值对
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap putAndReturn(String key, Object value){
		this.resultMap.put(key, value);
		return this;
	}

	/**
	 * 对指定的key对应的值，以on为间隔符拼接value
	 * 如果指定的key对应的值不是String类型，不进行任何操作，直接返回
	 * @param key
	 * @param on
	 * @param value
	 * @return
	 */
	public ResultMap putStringAndJoinOn(String key, String on, String value){
		Object temp = this.resultMap.get(key);
		if (temp instanceof String) {
			String join = Joiner.on(on).skipNulls().join(temp, value);
			this.resultMap.put(key, join);
		}
		return this;
	}

	/**
	 * 给returnDesc添加详细描述
	 * @param detail
	 * @return
	 */
	public ResultMap addDescDetail(String detail){
		return putStringAndJoinOn("returnDesc"," : ",detail);
	}

	/**
	 * 加入入参map中所有键值对
	 * @param map
	 * @return
	 */
	public ResultMap putAllAndReturn(Map map){
		this.resultMap.putAll(map);
		return this;
	}

	/**
	 * 加入值为null的节点
	 * @param nullObj null的key值
	 * @return
	 */
	public ResultMap putNullObjAndReturn(String... nullObj){
		for (String obj : nullObj) {
			this.resultMap.put(obj,null);
		}
		return this;
	}

	/**
	 * 加入值为空列表的节点
	 * @param nullList 空列表的key值
	 * @return
	 */
	public ResultMap putNullListAndReturn(String... nullList){
		for (String list : nullList) {
			List temp = new ArrayList(0);
			this.resultMap.put(list,temp);
		}
		return this;
	}
}
