// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.command;

import java.util.Iterator;
import me.larp.dev.Client;
import me.larp.dev.api.util.LoggerUtil;
import me.larp.dev.api.command.Command;

public class Help extends Command
{
    public Help(final String name, final String[] alias, final String usage) {
        super(name, alias, usage);
    }
    
    @Override
    public void onTrigger(final String arguments) {
        LoggerUtil.sendMessage("Project-Larp, made by legends");
        for (final Command command : Client.commandManager.getCommands()) {
            LoggerUtil.sendMessage(command.getName() + " - " + command.getUsage());
        }
    }
}
