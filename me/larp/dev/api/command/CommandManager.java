// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.command;

import java.util.Iterator;
import me.larp.dev.api.util.LoggerUtil;
import me.larp.dev.impl.command.Login;
import me.larp.dev.impl.command.Prefix;
import me.larp.dev.impl.command.Help;
import java.util.ArrayList;

public class CommandManager
{
    private final ArrayList commands;
    private String prefix;
    
    public CommandManager() {
        this.commands = new ArrayList();
        this.prefix = ".";
        this.commands.add(new Help("Help", new String[] { "h", "help" }, "help"));
        this.commands.add(new Prefix("Prefix", new String[] { "prefix" }, "prefix <char>"));
        this.commands.add(new Login("Login", new String[] { "login" }, "login <email> <password>"));
    }
    
    public void runCommand(final String args) {
        boolean found = false;
        final String[] split = args.split(" ");
        final String startCommand = split[0];
        final String arguments = args.substring(startCommand.length()).trim();
        for (final Command command : this.getCommands()) {
            for (final String alias : command.getAlias()) {
                if (startCommand.equals(this.getPrefix() + alias)) {
                    command.onTrigger(arguments);
                    found = true;
                }
            }
        }
        if (!found) {
            LoggerUtil.sendMessage("Unknown command");
        }
    }
    
    public ArrayList getCommands() {
        return this.commands;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
}
