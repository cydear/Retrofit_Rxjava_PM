package com.rr.pm.designpattern.observer;

import java.io.Serializable;

/**
 * 天气实体
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class Weather implements Serializable {
    private static final long serialVersionUID = 5083070325795055037L;

    private String description;

    public Weather(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
