package com.rr.pm.http;

import com.xianglin.xlnodecore.common.service.facade.base.Header;
import com.xianglin.xlnodecore.common.service.facade.enums.DeviceEnum;

/**
 * @author lary.huang on 16/9/2.
 */
public class HttpSyncApi {
    public static Header makeHeader() {
        Header reqHeader = new Header();

        String macId = android.os.Build.SERIAL;
        if (macId == "unknown" || macId == null) {
            macId = "X60090020900";
        }

        reqHeader.setHardId(macId);
        reqHeader.setInterfaceVersion(AppInfo.getInstance().getProductVersionCode());
        reqHeader.setClientDevice(DeviceEnum.POS.getMsg());

        reqHeader.setNodeId(0L);

        reqHeader.setNodePartyId(0L);

        reqHeader.setNodeManagerPartyId(0L);
        return reqHeader;
    }
}
