package net.noyark.www.threadBase;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.noyark.www.basic.MFunCore;

public class QQ {
    MFunCore core = MFunCore.getMFunCore();
    public void getQQ(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.getName()!=null) {
            player.sendMessage("请输入您的qq:/qq qq号码");
        }else{
            player.sendMessage("你要修改您的qq吗：/qq false取消 /qq true 确认");
        }

    }

    /**
     * 单个玩家设置qq
     * @param sender
     * @param args
     */
    public void setQQ(CommandSender sender,String[] args,Config c){

       try{
            String name = sender.getName();
            core.getLogger().info(c.getString(name));
            if (c.getString(name).equals("")) {
                setThis(sender, args,c);
                sender.sendMessage("设置成功");
            }
            if (args[0].equals("true")) {
                setThis(sender, args,c);
                sender.sendMessage("修改成功");
            } else if (args[0].equals("false")) {
                sender.sendMessage("确认不修改成功");
            }
        }catch(Exception e){
            sender.sendMessage(TextFormat.RED+"非法指令");
        }
    }
    public void setThis(CommandSender sender,String[] args,Config c){
        c.set(sender.getName(),args[0]);
        c.save();
    }
}
