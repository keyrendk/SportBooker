package com.example.sportbooker;

public class configuration {
    public static final String URL_BASE = "http://192.168.95.75/Android/sportbooker/";
    public static final String URL_LOGIN = URL_BASE + "login.php";
    public static final String URL_ADD_USER = URL_BASE + "addUser.php";
    public static final String URL_GET_ALL_USER = URL_BASE + "showUser.php";
    public static final String URL_GET_USER = URL_BASE + "showDetailUser.php?user_id=";
    public static final String URL_GET_USERNAME_USER = URL_BASE + "showUserUsername.php?user_id=";
    public static final String URL_UPDATE_USER = URL_BASE + "updateUser.php";
    public static final String URL_DELETE_USER = URL_BASE + "deleteUser.php?user_id=";

    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_FIRST_NAME = "first_name";
    public static final String KEY_USER_LAST_NAME = "last_name";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_PHONE_NUMBER = "phone_number";

    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_USER_ID = "id";
    public static final String TAG_USER_USERNAME = "username";
    public static final String TAG_USER_FIRST_NAME = "first_name";
    public static final String TAG_USER_LAST_NAME = "last_name";
    public static final String TAG_USER_EMAIL = "email";
    public static final String TAG_USER_PASSWORD = "password";
    public static final String TAG_USER_PHONE_NUMBER = "phone_number";

    public static final String USER_ID = "user_id";
}
