package net.noyark.www.threadBase;

import cn.nukkit.utils.Config;
import net.noyark.www.basic.MFunCore;

import static net.noyark.www.Config.InitDynamicConfig.iniBroadCastConfig;

/**
 * 广播类
 * 定期广播
 */
public class BroadCast implements Runnable{
    MFunCore core = MFunCore.getMFunCore();
    public void run(){
        if(core.getCfg().bcOpen == true){
            sendBroadCast();
        }
    }

    /**
     * 该方法用于定义公告数量和公告信息
     *USING XML
     */
    public void sendBroadCast(){
        /**
         * 生成配置文件
         */
        Config c = iniBroadCastConfig("broad.yml");
        /**
         * 播放
         */
        int i = 0;
        while(true){
            String s = c.getString("broadcast"+i);
            if(i== core.getCfg().broadcastNumber){
                i = 0;
            }
            core.getServer().broadcastMessage("[MFun-BroadCast]"+s);
            try {
                Thread.sleep(core.getCfg().broadcastSecond);
            }catch(Exception e){

            }
        }
    }
    public void writeMessage(Config c){
        for(int i = 0; i< core.getCfg().broadcastNumber; i++) {
            c.set("broadcast" + i, "");
            c.save();
        }
    }


}
