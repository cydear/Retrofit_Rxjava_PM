package com.rr.pm.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * gson操作类
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class GsonUtils {
    private static Gson gson = null;

    static {
        if (gson == null) {
            //排除@Expose修饰符为指定类型的字段
//            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
//                    .create();
            gson = new Gson();
        }
    }

    private GsonUtils() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 序列化包含Date的对象
     *
     * @param bean
     * @return
     */
    public static String DateBean2Json(Object bean) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new MyDateSerializer())
                .setDateFormat(DateFormat.LONG).create();
        return gson.toJson(bean);
    }

    /**
     * 反序列化包含Date的json字符串
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T DateJson2Bean(String json, Type type) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new MyDateDeserializer())
                .setDateFormat(DateFormat.LONG).create();
        return gson.fromJson(json, type);
    }

    /**
     * 反序列化包含Date的json字符串
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T DateJson2Bean(String json, Class<T> cls) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new MyDateDeserializer())
                .setDateFormat(DateFormat.LONG).create();
        return gson.fromJson(json, cls);
    }
}
