//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import java.util.ArrayList;
import me.larp.dev.api.module.Category;
import java.util.List;
import me.larp.dev.api.module.Module;

public class FakePlayer extends Module
{
    private final List<Integer> fakePlayerIdList;
    
    public FakePlayer() {
        super("FakePlayer", "", Category.MISC);
        this.fakePlayerIdList = new ArrayList<Integer>();
    }
    
    @Override
    public void onEnable() {
        if (this.mc.world == null || this.mc.player == null) {
            this.disable();
        }
        final GameProfile profile = new GameProfile(UUID.fromString("181b8238-5656-4ff3-ba0f-6c8065caccc2"), "Joe Biden");
        final EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)this.mc.world, profile);
        fakePlayer.copyLocationAndAnglesFrom((Entity)this.mc.player);
        fakePlayer.setHealth(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount());
        this.mc.world.addEntityToWorld(-69, (Entity)fakePlayer);
        this.fakePlayerIdList.add(-69);
    }
    
    @Override
    public void onDisable() {
        for (final int id : this.fakePlayerIdList) {
            this.mc.world.removeEntityFromWorld(id);
        }
    }
}
