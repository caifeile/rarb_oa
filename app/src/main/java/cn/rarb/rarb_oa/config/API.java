package cn.rarb.rarb_oa.config;

import cn.rarb.rarb_oa.utils.Utils;

/**
 * Created by cfl on 2016/2/29.
 */
public class API {
    public static final String BASIC_URL = "http://192.168.8.183:8080/rarb_oa";
    public static final String imgPath = Utils.getSDPath() + "/MyPictures";
    //图片搜索接口
    public static final String baseUrl = "http://pic.sogou.com";
    //笑话api
    public static final String LAIFU_JOY_IMAGE = "http://api.laifudao.com/open/tupian.json";
    public class status{
        public static final int success = 200;
        public static final int error = -1;
    }
}
