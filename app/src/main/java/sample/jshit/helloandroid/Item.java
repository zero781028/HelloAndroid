package sample.jshit.helloandroid;

import java.util.Date;
import java.util.Locale;

/**
 * Created by zero7 on 2016/2/12.
 */
public class Item implements java.io.Serializable {
    private long id;
    private long datetime;
    private Colors color;
    private String title;
    private String content;
    private String fileName;
    private String recFileName;
    private double latitude;
    private double longitude;
    private long lastModify;
    private boolean selected;

    public Item(){
        title="";
        content="";
        color=Colors.LIGHTGREY;
    }

    public Item(long id,long datetime,Colors color,String title,
                String content,String fileName,String recFileName,
                double latitude,double longtitude,long lastModify){
        this.id=id;
        this.datetime=datetime;
        this.color=color;
        this.title=title;
        this.content=content;
        this.fileName=fileName;
        this.recFileName=recFileName;
        this.latitude=latitude;
        this.longitude=longtitude;
        this.lastModify=lastModify;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public long getDatetime(){
        return datetime;
    }

    public void setDatetime(long datetime){
        this.datetime=datetime;
    }

    //裝置區域的日期時間
    public String getLocaleDateTime(){
        return String.format(Locale.getDefault(),"%tF %<tR",new Date(datetime));
    }

    //裝置區域的日期
    public String getLocaleDate(){
        return String.format(Locale.getDefault(),"%tF",new Date(datetime));
    }

    //裝置區域的時間
    public String getLocaleTime(){
        return String.format(Locale.getDefault(),"%<tR",new Date(datetime));
    }

    public Colors getColor(){
        return color;
    }

    public void setColor(Colors color){
        this.color=color;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content=content;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName=fileName;
    }

    public String getRecFileName(){
        return recFileName;
    }

    public void setRecFileName(String recFileName){
        this.recFileName=recFileName;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude=latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude=longitude;
    }

    public long getLastModify(){
        return lastModify;
    }

    public void setLastModify(long lastModify){
        this.lastModify=lastModify;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected=selected;
    }
}
