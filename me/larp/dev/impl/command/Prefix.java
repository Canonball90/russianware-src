// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.command;

import me.larp.dev.api.util.LoggerUtil;
import me.larp.dev.Client;
import me.larp.dev.api.command.Command;

public class Prefix extends Command
{
    public Prefix(final String name, final String[] alias, final String usage) {
        super(name, alias, usage);
    }
    
    @Override
    public void onTrigger(final String arguments) {
        if (arguments.equals("")) {
            this.printUsage();
        }
        else {
            Client.commandManager.setPrefix(arguments);
            LoggerUtil.sendMessage("Prefix set to " + arguments);
        }
    }
}
