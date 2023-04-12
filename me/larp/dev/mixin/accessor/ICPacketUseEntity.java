// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.mixin.accessor;

import net.minecraft.network.play.client.CPacketUseEntity;

public interface ICPacketUseEntity
{
    void setEntityId(final int p0);
    
    void setAction(final CPacketUseEntity.Action p0);
}
