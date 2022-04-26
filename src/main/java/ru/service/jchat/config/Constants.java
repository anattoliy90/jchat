package ru.service.jchat.config;

public class Constants {
    public final static int API_VERSION_CURRENT = 1;
    public final static String API = "/api/v" + API_VERSION_CURRENT;
    public final static String AUTH = API + "/auth";
    public final static String LOGIN = AUTH + "/login";
    public final static String NEW_ACCESS_TOKEN = API + "/newAccessToken";
    public final static String USERS = API + "/users";
    public final static String SING_UP = "/add";
    public final static String ACTIVATION = "/activation";
    public final static String CHATS = API + "/chats";
    public final static String MESSAGES = API + "/messages";
}
