package blueappsoftware.wordpressinandroid;

/**
 * Created by Jaink on 14-09-2017.
 */

public class Model {

    public static final int IMAGE_TYPE =1;
    public String title, subtitle, Image;
    public int type;


    public Model ( int mtype, String mtitle, String msubtitle, String image  ){

        this.title = mtitle;
        this.subtitle = msubtitle;
        this.type = mtype;
        this.Image = image;
    }
}

