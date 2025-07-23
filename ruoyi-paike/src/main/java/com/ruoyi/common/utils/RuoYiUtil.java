package com.ruoyi.common.utils;

import com.ruoyi.common.config.RuoYiConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class RuoYiUtil {
    public static String getAbsoluteFile(String filename) {
        String downloadPath = RuoYiConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    public static String getProfile(String img) {
        if(StringUtils.isBlank(img)){
            return img;
        }
//        if(img.indexOf(RuoYiConfig.getServer()) == 0){
//            return img;
//        }
//        return RuoYiConfig.getServer() + img;
        return null;
    }
}
