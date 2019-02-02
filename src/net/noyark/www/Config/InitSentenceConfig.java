package net.noyark.www.Config;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.SimpleConfig;

public class InitSentenceConfig extends SimpleConfig {
    @Path(value = "bad-sentences")
    public String[] sentences = {};
    public InitSentenceConfig(PluginBase plugin, String file) {
        super(plugin,file+".yml");
    }
}
