//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import me.larp.dev.api.util.LoggerUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import java.util.Arrays;
import net.minecraftforge.client.event.ClientChatEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class ColorText extends Module
{
    public ColorText() {
        super("ColorText", Category.MISC);
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatEvent event) {
        final Iterator var2 = Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\").iterator();
        String s;
        do {
            if (!var2.hasNext()) {
                if (this.mc.getCurrentServerData() != null && (this.mc.getCurrentServerData() == null || this.mc.getCurrentServerData().serverIP.equals("impurity.me"))) {
                    final String watermark = StringEscapeUtils.unescapeJava("§");
                    event.setMessage(watermark + "d" + event.getMessage());
                    return;
                }
                LoggerUtil.sendMessage("Works only on impurity.me");
            }
            s = var2.next();
        } while (!event.getMessage().startsWith(s));
    }
}
