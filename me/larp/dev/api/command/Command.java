// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.command;

import me.larp.dev.api.util.LoggerUtil;
import me.larp.dev.Client;

public class Command
{
    private String name;
    private String[] alias;
    private String usage;
    
    public Command(final String name, final String[] alias, final String usage) {
        this.setName(name);
        this.setAlias(alias);
        this.setUsage(usage);
    }
    
    public void onTrigger(final String arguments) {
    }
    
    public void printUsage() {
        LoggerUtil.sendMessage("Usage: " + Client.commandManager.getPrefix() + this.usage);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String[] alias) {
        this.alias = alias;
    }
    
    public String getUsage() {
        return this.usage;
    }
    
    public void setUsage(final String usage) {
        this.usage = usage;
    }
}
