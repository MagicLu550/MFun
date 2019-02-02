package net.noyark.www.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.noyark.www.Config.InitDynamicConfig;
import net.noyark.www.basic.MFunCore;

import java.util.*;

/**
 * 关于玩家的监听器
 */
public class PlayerListener implements Listener {

    static MFunCore core = MFunCore.getMFunCore();
    private boolean isFlying;
    private int playerOnline;
    private static final double X = core.getCfg().x;
    private static final double Y = core.getCfg().y;
    private static final double Z = core.getCfg().z;
    public static final int MAX = core.getServer().getMaxPlayers();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event){
        sendMessage(event);
        core.getQq().getQQ(event);
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerFly(PlayerToggleFlightEvent event){
        check(event);
    }
    public void check(PlayerToggleFlightEvent event){
        /*
        判断飞行，将其拽回出生点
         */
        boolean isOp = false;
        isFlying = true;
        while(core.getCfg().isFly&&isFlying&&!isOp) {
            isFlying = event.isFlying();
            isOp = event.getPlayer().isOp();
            Player player = event.getPlayer();
            core.getLogger().info(Thread.currentThread().toString());
            core.getLogger().info("正在："+isFlying);
            core.getLogger().warning(player.getName() + "可疑飞行,被拖回");
            event.getPlayer().move(X,Y,Z);
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerExit(PlayerQuitEvent event){
        subPeople();

    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerChat(PlayerChatEvent event){
        event.setCancelled();
        String name = event.getPlayer().getName();
        String ip = event.getPlayer().getAddress();
        String oldMessage = event.getMessage();
        //屏蔽脏话
        Config c = new Config(core.getDataFolder()+"/badWord.yml");
        c.save();
        Config c1 = new Config(core.getDataFolder()+"/badSentence.yml");
        c1.save();

        List<String> badWords = c.getList("bad-words");
        core.getLogger().warning("badwords"+badWords);
        boolean isBad = false;
        String message = oldMessage;
        if(core.getCfg().badwordCheck) {
            if (!badWords.isEmpty()) {
                for (String s : badWords) {
                    core.getLogger().info(s);
                    if (!(oldMessage.indexOf(s) == -1) && !s.equals("")) {
                        String newMessage = oldMessage.replaceAll(s, "***");
                        message = newMessage;
                        isBad = true;
                        Date date = new Date();
                        c1.set("bad-sentences", "[" + date + "]" + name + ":" + oldMessage);//记录脏话
                        c1.save();
                        break;
                    }
                }
            }
        }
        Set<Map.Entry<UUID,Player>> set = core.getServer().getOnlinePlayers().entrySet();
        //将进入服务器信息发送给每个玩家
        String title = InitDynamicConfig.initTitleConfig().getString(name,"Player");
        for(Map.Entry<UUID,Player> e:set){
            e.getValue().sendMessage("["+title+"]"+"["+ip+"]"+name+":"+message);
        }
    }
    /**
     * 自定义死亡
     * @param event
     * 未开始
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerAction(PlayerDeathEvent event){
    }
    public void sendMessage(PlayerJoinEvent event){
        String name = event.getPlayer().getName();
        addPeople();
        //玩家进入欢迎提示
        event.getPlayer().sendMessage(core.getCfg().welcomeMessage);
        //玩家大标题提示
        event.getPlayer().sendTitle(core.getCfg().title);
        //玩家小标题提示
        event.getPlayer().sendTip(core.getCfg().tip);
        //获取服务端最大人数，和当前在线人数，向服务器个人反馈，向全服反馈进入者
        core.getServer().broadcastMessage(TextFormat.RED+name+ core.getCfg().welcomeMessage+"["+playerOnline+"/"+MAX+"]");
        Set<Map.Entry<UUID,Player>> set = core.getServer().getOnlinePlayers().entrySet();
        //将进入服务器信息发送给每个玩家
        for(Map.Entry<UUID,Player> e:set){
            e.getValue().sendTip(TextFormat.RED+name+TextFormat.BLUE+ core.getCfg().peopleTip);
        }
    }
    public void addPeople(){
        //人数增多
        playerOnline++;
    }
    public void subPeople(){
        playerOnline--;
    }
}
