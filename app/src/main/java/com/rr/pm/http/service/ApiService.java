package com.rr.pm.http.service;

import com.rr.pm.data.bean.Response;
import com.xianglin.xlnodecore.common.service.facade.resp.PosBasicInfoResp;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public interface ApiService {
    /**
     * 查询站点信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("mgw.htm")
    Observable<Response<PosBasicInfoResp>> getNodeMessage(@FieldMap Map<String, String> map);
}
