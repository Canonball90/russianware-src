// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.manager;

import java.util.Iterator;
import me.larp.dev.api.util.FriendUtil;
import java.util.ArrayList;

public class FriendManager
{
    private final ArrayList<FriendUtil> friends;
    
    public FriendManager() {
        this.friends = new ArrayList<FriendUtil>();
    }
    
    public ArrayList<FriendUtil> getFriends() {
        return this.friends;
    }
    
    public void addFriend(final String name) {
        this.friends.add(new FriendUtil(name));
    }
    
    public void delFriend(final String name) {
        this.friends.remove(this.getFriendByName(name));
    }
    
    public FriendUtil getFriendByName(final String name) {
        for (final FriendUtil friend : this.getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                return friend;
            }
        }
        return null;
    }
    
    public boolean isFriend(final String name) {
        for (final FriendUtil friend : this.getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<String> getFriendsForConfig() {
        final ArrayList<String> arr = new ArrayList<String>();
        for (final FriendUtil friend : this.getFriends()) {
            arr.add(friend.getName());
        }
        return arr;
    }
}
