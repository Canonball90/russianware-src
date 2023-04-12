//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class Sprint extends Module
{
    private Setting rage;
    
    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        this.rage = new Setting("Rage", this, false);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.mc.world == null) {
            return;
        }
        if (this.mc.player.movementInput.moveForward == 0.0f && this.mc.player.movementInput.moveStrafe == 0.0f) {
            return;
        }
        if (!this.mc.player.isSprinting()) {
            this.mc.player.setSprinting(true);
        }
    }
}
