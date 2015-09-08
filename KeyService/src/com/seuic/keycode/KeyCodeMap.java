package com.seuic.keycode;

public enum KeyCodeMap {
    
    KEY_DELETE("删除",67),
    KEY_ESC("ESC",4),
    KEY_SCAN("扫描",119),
    KEY_ENT("ENT",66),
    KEY_UP("上",19),
    KEY_DOWN("下",20),
    KEY_LEFT("左",21),
    KEY_RIGHT("右",22),
    KEY_HOMEPAGE("主页",140),
    KEY_MAINMENU("菜单",82),
    KEY_QUERY("查询",136),
    KEY_0("0",7),
    KEY_1("1",8),
    KEY_2("2",9),
    KEY_3("3",10),
    KEY_4("4",11),
    KEY_5("5",12),
    KEY_6("6",13),
    KEY_7("7",14),
    KEY_8("8",15),
    KEY_9("9",16),
    KEY_ASTERISK("*",56),
    KEY_TAB("TAB",18),
    KEY_F1("F1",131),
    KEY_F2("F2",132),
    KEY_F3("F3",133),
    KEY_F4("F4",134);
    
    private String desc;
    private int code;
    private KeyCodeMap(String desc,int code) {
        this.desc = desc;
        this.code = code;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public static KeyCodeMap findTypeByCode (int code) {
        
        for(KeyCodeMap key : values()) {
            if(key.code == code) {
                return key;
            }
        }
        
        throw new RuntimeException("invalid key code:" + code);
    }
    
}
