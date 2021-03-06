package sample.jshit.helloandroid;

import android.graphics.Color;

/**
 * Created by zero7 on 2016/2/12.
 */
public enum Colors {
    LIGHTGREY("#D3D3D3"),BLUE("#33B5E5"),PURPLE("#800080"),GREEN("#99CC00"),
    ORANGE("#FFBB33"),RED("#FF4444"),BLACK("#000000");

    private String code;

    private Colors(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }

    public int parseColor(){
        return Color.parseColor(code);
    }
}
