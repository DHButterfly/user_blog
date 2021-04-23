package com.dl.blog.util;

import com.dl.blog.common.Const;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserAvatarUtil {
    public static String getUserAvatar(){
        List<String> avatarAddress=new ArrayList<>();
        for(int i=0;i<11;i++){
            String avatarName= Const.UserAvatar.avatarPath+(i+1)+".png";
            avatarAddress.add(i,avatarName);
        }
        //不用System.currentTimeMillis的原因是：当多线程调用时，由于CPU速率很快，因此currentTimeMillis很可能相等，使得随机数结果也会相等
        //返回最准确的可用系统计时器的当前值,以毫微秒为单位。此方法只能用于测量已过的时间,与系统或钟表时间的其他任何时间概念无关。
        long seed1 = System.nanoTime();
        Random random=new Random(seed1);
        int avatarIndex=random.nextInt(11);
        return avatarAddress.get(avatarIndex);
    }
}
