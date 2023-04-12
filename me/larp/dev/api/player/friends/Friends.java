// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.player.friends;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Friends
{
    public static List friends;
    
    public Friends() {
        Friends.friends = new ArrayList();
    }
    
    public static List getFriends() {
        return Friends.friends;
    }
    
    public static boolean isFriend(final String name) {
        boolean b = false;
        for (final Friend f : getFriends()) {
            if (f.getName().equalsIgnoreCase(name)) {
                b = true;
            }
        }
        return b;
    }
    
    public Friend getFriendByName(final String name) {
        Friend fr = null;
        for (final Friend f : getFriends()) {
            if (f.getName().equalsIgnoreCase(name)) {
                fr = f;
            }
        }
        return fr;
    }
    
    public void addFriend(final String name) {
        Friends.friends.add(new Friend(name));
    }
    
    public void delFriend(final String name) {
        Friends.friends.remove(this.getFriendByName(name));
    }
}
