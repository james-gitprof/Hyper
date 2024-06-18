package com.example.carcab.Defaults;

import java.util.HashMap;
import java.util.Map;

public class UserState {
    public final Map<String, Object> USER_SEARCH_ACTIVE_STATE = new HashMap<>();
    public final Map<String, Object> USER_SEARCH_INACTIVE_STATE = new HashMap<>();
    private static UserState mUserState;
    private UserState()
    {
        USER_SEARCH_ACTIVE_STATE.put("searchActive", "true");
        USER_SEARCH_INACTIVE_STATE.put("searchActive", "false");
    }

    public static UserState getInstanceDefaults()
    {
        if (mUserState == null)
        {
            mUserState = new UserState();
        }
        return mUserState;
    }
}
