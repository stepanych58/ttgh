package com.stbegradleapp.fixer.model.params.user;

public enum UserRole {
    CLIENT, /*WHO MAKE ORDER*/
    ENGINEER, /*WHO SERVE ORDER*/
    ROLE_ADMIN, /*WHO FIX APP*/
    CUSTOMER_ADMIN, /*WHO ASSIGN ORDER TO ENGEENER*/
    MANAGER, /*WHO COORDINATE CUSTOMER_ADMIN*/
    ANONYMOUS /*JUST IN CASE*/;
}
