package com.dl.blog.common;

public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final String USERNAME = "username";
    public interface Role {
        int ROLE_USER = 100; //访客
        int ROLE_MANAGER = 200;//管理者
    }
    public interface UserAvatar{
        String avatarPath="/blog/images/pic";
    }
    //可以封装枚举方法或者静态方法在这里
    /**
     * @Author: Helon
     * @Description: JWT使用常量值
     * @Data: Created in 2018/7/27 14:37
     * @Modified By:
     */
    public interface SecretConstant {

        //签名秘钥 自定义
        public static final String BASE64SECRET = "ding7786df7fc3a34e26a61c034d5ec8245dli";

        //超时毫秒数（默认30分钟）
        public static final int EXPIRESSECOND = 1800000;

        //用于JWT加密的密匙 自定义
        public static final String DATAKEY = "************";

    }

}

