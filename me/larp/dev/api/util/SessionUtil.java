//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import javax.naming.AuthenticationException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.util.Session;
import java.net.Proxy;

public class SessionUtil
{
    public static Session createSession(final String username, final String password, final Proxy proxy) throws AuthenticationException, com.mojang.authlib.exceptions.AuthenticationException {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(proxy, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        auth.logIn();
        return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
    }
    
    public static boolean login(final String email, final String password) {
        try {
            final Session session = createSession(email, password, Proxy.NO_PROXY);
            final Field field = Minecraft.class.getDeclaredField("session");
            field.setAccessible(true);
            field.set(Minecraft.getMinecraft(), session);
            return true;
        }
        catch (Exception var4) {
            return false;
        }
    }
    
    public static Session getSession() {
        return Minecraft.getMinecraft().getSession();
    }
}
