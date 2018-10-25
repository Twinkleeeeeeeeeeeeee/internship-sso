package com.internship.mvc.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class SingleResult {
    private static final ObjectMapper MAPPER = new ObjectMapper();    // 定义jackson对象
    private Integer status;  //响应状态
    private String msg; //响应内容
    private Object data; //相应数据

    public SingleResult() {
    }

    public SingleResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public SingleResult(Object data) {
        this.status = 200;
        this.msg = "ok";
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static SingleResult ok(Object data) {
        return new SingleResult(data);
    }

    public static SingleResult ok() {
        return new SingleResult(null);
    }

    public static SingleResult build(Integer status, String msg, Object data) {
        return new SingleResult(status, msg, data);
    }

    public static SingleResult build(Integer status, String msg) {
        return new SingleResult(status, msg, null);
    }

    /**
     * 将json结果集转化为SingleResult对象
     *
     * @param jsonData json数据
     * @param clazz    SingleResult中的object类型
     * @return
     */
    public static SingleResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, SingleResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static SingleResult format(String json) {
        try {
            return MAPPER.readValue(json, SingleResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static SingleResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
