//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.larp.dev.impl.event.MoveEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class Step extends Module
{
    private final Setting mode;
    private final Setting height;
    
    public Step() {
        super("Step", "", Category.MOVEMENT);
        this.mode = new Setting("Mode", this, Arrays.asList("Vanilla", "Packet"));
        this.height = new Setting("Height", this, 0, 2, 10);
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.mc.player == null || this.mc.world == null || this.mc.player.isDead) {
            return;
        }
        if (this.mode.getEnumValue().equals("Vanilla")) {
            this.mc.player.stepHeight = 0.5f;
            if (this.mc.player.collidedHorizontally && this.mc.player.onGround) {
                this.mc.player.stepHeight = 0.5f;
            }
            else {
                this.mc.player.stepHeight = 2.0f;
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null || this.mc.player.isDead || !this.mc.player.collidedHorizontally || !this.mc.player.onGround || this.mc.player.isOnLadder() || this.mc.player.isInWater() || this.mc.player.isInLava() || this.mc.player.movementInput.jump || this.mc.player.noClip) {
            return;
        }
        if (this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f) {
            return;
        }
        if (this.mode.getEnumValue().equals("Packet")) {
            final double n = this.get_n_normal();
            if (n < 0.0 || n > 2.0) {
                return;
            }
            if (n == 2.0) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.42, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.78, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.63, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.51, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.9, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.21, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.45, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.43, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 2.0, this.mc.player.posZ);
            }
            if (n == 1.5) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.41999998688698, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.7531999805212, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.00133597911214, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.16610926093821, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.24918707874468, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.1707870772188, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 1.0, this.mc.player.posZ);
            }
            if (n == 1.0) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.41999998688698, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.7531999805212, this.mc.player.posZ, this.mc.player.onGround));
                this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 1.0, this.mc.player.posZ);
            }
        }
    }
    
    public double get_n_normal() {
        this.mc.player.stepHeight = 0.5f;
        double max_y = -1.0;
        final AxisAlignedBB grow = this.mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
        if (!this.mc.world.getCollisionBoxes((Entity)this.mc.player, grow.offset(0.0, 2.0, 0.0)).isEmpty()) {
            return 100.0;
        }
        for (final AxisAlignedBB aabb : this.mc.world.getCollisionBoxes((Entity)this.mc.player, grow)) {
            if (aabb.maxY > max_y) {
                max_y = aabb.maxY;
            }
        }
        return max_y - this.mc.player.posY;
    }
    
    @Override
    public void onDisable() {
        this.mc.player.stepHeight = 0.5f;
    }
}
