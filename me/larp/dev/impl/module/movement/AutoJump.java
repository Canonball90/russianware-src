//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class AutoJump extends Module
{
    public AutoJump() {
        super("AutoJump", "", Category.MOVEMENT);
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        if (this.nullCheck()) {
            return;
        }
        event.getMovementInput().jump = true;
    }
}
