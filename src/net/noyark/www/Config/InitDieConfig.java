package net.noyark.www.Config;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.SimpleConfig;

public class InitDieConfig extends SimpleConfig {
    //是否开启
    @Path (value = "broadcast.state")
    public boolean state =true;
    public InitDieConfig(PluginBase plugin, String file) {
        super(plugin,file+".yml");
    }
}
