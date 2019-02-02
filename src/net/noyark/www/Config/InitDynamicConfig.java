package net.noyark.www.Config;

import cn.nukkit.utils.Config;
import net.noyark.www.basic.MFunCore;
import net.noyark.www.threadBase.BroadCast;

public class InitDynamicConfig {
    static MFunCore core;
    static BroadCast cast;
    static {
        core = MFunCore.getMFunCore();
        cast = new BroadCast();
    }
    public static Config initQQConfig(){
        Config c =new Config(core.getDataFolder()+"/"+"qq.yml");
        return c;
    }
    /**
     * 存储玩家称号信息
     */
    public static Config initTitleConfig(){
        Config c = new Config(core.getDataFolder()+"/title.yml",Config.YAML);
        return c;
    }
    public static Config iniBroadCastConfig(String file){
        Config c = new Config(core.getDataFolder()+"/"+file,Config.YAML);
        cast.writeMessage(c);
        return c;
    }
    public static Config initListenerConfig(){
        Config listenerConfig = new Config(core.getDataFolder()+"/Listener.yml",Config.YAML);
        return listenerConfig;
    }
}
