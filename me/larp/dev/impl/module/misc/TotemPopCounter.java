// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import java.util.Iterator;
import me.larp.dev.api.util.LoggerUtil;
import me.larp.dev.Client;
import me.larp.dev.api.module.Category;
import me.larp.dev.impl.module.combat.utilz.stuff;
import me.larp.dev.api.module.Module;

public class TotemPopCounter extends Module implements stuff
{
    public TotemPopCounter() {
        super("TotemPopCounter", "", Category.MISC);
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (!Client.popManager.toAnnouce.isEmpty()) {
            try {
                for (final String string : Client.popManager.toAnnouce) {
                    LoggerUtil.sendOverwriteClientMessage(string);
                }
                Client.popManager.toAnnouce.clear();
            }
            catch (Exception ex) {}
        }
    }
}
