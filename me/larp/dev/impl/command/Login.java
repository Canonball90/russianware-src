//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.command;

import me.larp.dev.api.util.LoggerUtil;
import me.larp.dev.api.util.SessionUtil;
import me.larp.dev.api.command.Command;

public class Login extends Command
{
    public Login(final String name, final String[] alias, final String usage) {
        super(name, alias, usage);
    }
    
    @Override
    public void onTrigger(final String arguments) {
        final String[] split = arguments.split(" ");
        try {
            if (split[0].equals("") || split[1].equals("")) {
                this.printUsage();
                return;
            }
        }
        catch (Exception var4) {
            this.printUsage();
            return;
        }
        if (SessionUtil.login(split[0], split[1])) {
            LoggerUtil.sendMessage("Logged in to " + SessionUtil.getSession().getUsername());
        }
        else {
            LoggerUtil.sendMessage("Failed to log in");
        }
    }
}
