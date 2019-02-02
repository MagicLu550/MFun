package net.noyark.www.basic;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.SimpleConfig;
import cn.nukkit.utils.TextFormat;
import net.noyark.www.Command.CommandBase;
import net.noyark.www.Config.*;
import net.noyark.www.Listener.BlockListener;
import net.noyark.www.Listener.PlayerListener;
import net.noyark.www.Listener.ServerListener;
import net.noyark.www.threadBase.BroadCast;
import net.noyark.www.threadBase.CheckTPS;
import net.noyark.www.threadBase.QQ;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.noyark.www.Config.InitDynamicConfig.initQQConfig;

/**GulesBerry Technology Co. Ltd. (c) Java Lib.
 * <hr>
 * <br>该类实现了最主要的功能
 * <p>这个类是插件的<strong>主运行类</strong>，继承自PluginBase</p>
 * {@code onEnable}
 * @author magiclu550
 * @since JDK1.8.0_201
 * @since NukkitxAPI_1.0.7
 * @see cn.nukkit.command.CommandExecutor
 * @see cn.nukkit.plugin.Plugin
 * @see cn.nukkit.plugin.PluginBase
 *
 */
public class MFunCore extends PluginBase {

    private QQ qq;
    private Config QQConfig;
    private InitSetConfig cfg;
    private ExecutorService pool = Executors.newFixedThreadPool(100);
    private String fs[] = {};
    private List<Listener> listeners;
    private List<SimpleConfig> configs;
    private CommandBase commandBase;
    private static MFunCore MFunCore;
    private Info info;
    private InfoThings things;
    @Override
    public void onLoad(){
        getLogger().info(TextFormat.BLUE+"MFun 集成小插件 By MagicLu GulesBerry Tech");
        loadWorld();
    }
    @Override
    public void onEnable(){
        setting();
        loadInfo();
    }
    @Override
    public void onDisable(){
        getLogger().info("插件关闭成功,MFun QQ 843983728 Web:magic.noyark.net");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        return commandBase.onCommand(sender,command,label, args);
    }

    /**
     * 设置所有的初始化信息
     */
    public void setting(){
        initVar();
        loadCfg();
        registerListener();
        addThread();
    }

    /**
     * 创建参考文档
     */
    public void loadInfo(){
        info.initFile();
        things.info();
    }

    /**
     * 初始化和实例化
     */
    public void initVar(){
        MFunCore = this;
        listeners = new ArrayList<>();
        configs = new ArrayList<>();
        qq=new QQ();
        QQConfig =initQQConfig();
        commandBase = new CommandBase();
        info = new Info();
        things = new InfoThings();
    }

    /**
     * 加载配置文件
     */
    public void loadCfg(){
        /* 设置Set.yml */
        this.cfg = new InitSetConfig(this,"Set");
        //读取数值
        this.cfg.load();
        //保存值到文件
        this.cfg.save();
        if(this.getCfg().reset) {
            //1.死亡信息公告的配置文件
            //2.屏蔽关键字创建文字
            //3.屏蔽关键字所在的句子集合
            //4.监听器注册
            configs.add(new
                    InitDieConfig(this, "die"));
            configs.add(new InitSentenceConfig(this, "badSentence"));
            configs.add(new InitWordsConfig(this, "badWord"));
            configs.add(new InitListenerConfig(this,"Listener"));
            for(SimpleConfig config:configs){
                config.save();
            }
        }
        this.getCfg().reset = false;//关闭第二次创建
        this.getCfg().save();
    }

    /**
     * 注册监听器
     */
    public void registerListener(){
        /*

         * 注册监听器
         */
        Config listenerConfig = InitDynamicConfig.initListenerConfig();
        listenerConfig.save();
        List<String> listenersClasses = listenerConfig.getList("Listeners");
        try {
            for (int i = 0; i < listenersClasses.size(); i++) {
                Class clz = Class.forName(listenersClasses.get(i));
                Listener l = (Listener)clz.newInstance();
                listeners.add(l);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        for(Listener listener:listeners){
            this.getServer().getPluginManager().registerEvents(listener,this);
        }
    }

    /**
     * 向线程池里添加任务
     */
    public void addThread(){
        /*
         * 检查TPS
         */
        CheckTPS check = new CheckTPS();
        BroadCast broadCast = new BroadCast();
        pool.execute(check);
        pool.execute(broadCast);
    }

    /**
     * 加载世界
     */
    public void loadWorld(){
        File f = new File("worlds");
        File[] files = f.listFiles();
        fs = new String[files.length];
        for(int i = 0;i<fs.length;i++){
            fs[i] = files[i].toString();
            StringBuilder builder = new StringBuilder(fs[i]);
            fs[i] = builder.substring(fs[i].indexOf("/")+1);
        }
        getLogger().info("您的所有世界为："+ Arrays.toString(fs));
    }
    public Config getQQConfig(){
        return QQConfig;
    }
    public QQ getQq(){
        return qq;
    }
    public static MFunCore getMFunCore(){
        return MFunCore;
    }
    public InitSetConfig getCfg(){
        return cfg;
    }
}
