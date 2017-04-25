package com.rr.pm.http.convert;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * String Convert
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class StringConvertFactory extends Converter.Factory {
    public static Converter.Factory create() {
        return new StringConvertFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return StringResponseBodyConverter.INSTANCE;
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    static class StringResponseBodyConverter implements Converter<ResponseBody, String> {

        private static final StringResponseBodyConverter INSTANCE = new StringResponseBodyConverter();

        @Override
        public String convert(ResponseBody responseBody) throws IOException {
            return responseBody.toString();
        }
    }
}
