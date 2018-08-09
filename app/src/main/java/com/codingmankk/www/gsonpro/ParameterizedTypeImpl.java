package com.codingmankk.www.gsonpro;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ================================================
 * 创 建 人：
 * 版    本：
 * 创建日期：
 * 描    述：
 * 修订历史：
 * ================================================
 * @author Administrator
 */
public class ParameterizedTypeImpl implements ParameterizedType{

    private final Class raw;
    private final Type[] args;

    public ParameterizedTypeImpl(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null? args:new Type[0];
    }

    /**
     * // 返回Map<String,User>里的String和User，所以这里返回[String.class,User.clas]
     * @return
     */
    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    /**
     * // Map<String,User>里的Map,所以返回值是Map.class
     * @return
     */
    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
