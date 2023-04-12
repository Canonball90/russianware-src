// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import org.apache.commons.lang3.StringEscapeUtils;
import java.util.Arrays;
import net.minecraftforge.client.event.ClientChatEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class ChatSuffix extends Module
{
    public ChatSuffix() {
        super("ChatSuffix", Category.MISC);
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatEvent event) {
        for (final String s : Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\")) {
            if (event.getMessage().startsWith(s)) {
                return;
            }
        }
        final String watermark = StringEscapeUtils.unescapeJava(" \\u23d0 \\u0280\\u1d1c\\ua731\\ua731\\u026a\\u1d00\\u0274\\u1d21\\u1d00\\u0280\\u1d07");
        event.setMessage(event.getMessage() + watermark);
    }
}
