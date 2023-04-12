//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import me.larp.dev.api.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import me.larp.dev.api.module.Module;

public class KillAura extends Module
{
    boolean isAttacking;
    public static EntityPlayer target;
    boolean rotating;
    private final Setting range;
    private final Setting rotation;
    private final Setting swordOnly;
    private final Setting mode;
    
    public KillAura() {
        super("KillAura", "", Category.COMBAT);
        this.isAttacking = false;
        this.range = new Setting("Range", this, 4, 0, 10);
        this.rotation = new Setting("Rotation", this, true);
        this.swordOnly = new Setting("Sword Only", this, false);
        this.mode = new Setting("Mode", this, Arrays.asList("Default", "Leg"));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player != null || this.mc.world != null) {
            for (final EntityPlayer player : this.mc.world.playerEntities) {
                if (player != this.mc.player) {
                    if (this.mc.player.getDistance((Entity)player) < this.range.getIntegerValue()) {
                        if (player.isDead || player.getHealth() > 0.0f) {
                            if (this.rotating && this.rotation.getBooleanValue()) {
                                final float[] angle = MathUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), player.getPositionVector());
                                this.mc.player.rotationYaw = angle[0];
                                if (this.mode.getEnumValue().equals("Leg")) {
                                    this.mc.player.rotationPitch = angle[1];
                                }
                            }
                            this.attackPlayer(player);
                        }
                        KillAura.target = player;
                    }
                    else {
                        this.rotating = false;
                    }
                }
            }
        }
    }
    
    public void attackPlayer(final EntityPlayer player) {
        if (player != null) {
            if (player != this.mc.player && this.mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
                this.rotating = true;
                this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)player);
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
        else {
            this.rotating = false;
        }
    }
}
