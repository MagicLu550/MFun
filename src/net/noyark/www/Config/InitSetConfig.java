package net.noyark.www.Config;

import cn.nukkit.utils.SimpleConfig;
import cn.nukkit.plugin.PluginBase;

public class InitSetConfig extends SimpleConfig {
    /*
    服务器
     */
    @Path (value = "server.check-command")
    public boolean isCheck = true;
    @Path (value = "server.tps-reload")
    public int reloadMs = 60;

    /*
    飞行拽回的位置
     */
    @Path (value = "fly-catch-to.x")
    public double x = 0;
    @Path (value = "fly-catch-to.y")
    public double y = 0;
    @Path (value = "fly-catch-to.z")
    public double z = 0;
    /*
    玩家
     */
    @Path (value = "player.fly-check")
    public boolean isFly = false;
    @Path (value = "server.broadcast-ifopen")
    public boolean bcOpen = true;
    @Path (value = "player.com-Message")
    public String welcomeMessage = "欢迎加入服务器";
    @Path (value = "player.com-Title")
    public String title = "欢迎加入服务器";
    @Path (value = "player.com-Tip")
    public String tip = "欢迎加入服务器";
    @Path (value = "server.broadcast-number")
    public int broadcastNumber = 3;
    @Path (value = "server.broadcast-second-one")
    public int broadcastSecond = 10000;
    @Path (value = "server.people-tip")
    public String peopleTip = "";
    @Path (value = "server.updated")
    public boolean updated =false;
    @Path (value = "server.reset")
    public boolean reset = true;
    @Path (value = "server.badword-check")
    public boolean badwordCheck = true;
    public InitSetConfig(PluginBase plugin, String file) {
        super(plugin,file+".yml");
    }

}
