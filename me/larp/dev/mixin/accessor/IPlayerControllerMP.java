// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.mixin.accessor;

import net.minecraft.util.math.BlockPos;

public interface IPlayerControllerMP
{
    void setIsHittingBlock(final boolean p0);
    
    BlockPos getCurrentBlock();
    
    void callSyncCurrentPlayItem();
    
    boolean isHittingBlock();
}
