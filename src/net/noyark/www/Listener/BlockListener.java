package net.noyark.www.Listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockUpdateEvent;
import net.noyark.www.basic.MFunCore;

public class BlockListener implements Listener {
    MFunCore core = MFunCore.getMFunCore();

    /**
     * 方块更新事件
     */
    @EventHandler
    public void onCommandUpdated(BlockUpdateEvent event) {
        if (!core.getCfg().updated) {
            int id = event.getBlock().getId();
            if (id == 12 || id == 13) {
                event.setCancelled();
            } else {
                event.setCancelled(false);
            }
        }
    }
}