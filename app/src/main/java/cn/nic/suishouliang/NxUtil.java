package cn.nic.suishouliang;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.StrictMode;
import android.view.Display;
import android.view.Window;
import android.widget.EditText;

/**
 * Created by nic on 2018/3/26.
 */

public class NxUtil{

    protected int getStatusBarHeight(Activity activity){
        Display rootRect = activity.getWindow().getWindowManager().getDefaultDisplay();  // sys window
        Rect appRect = new Rect();  // app window
        Rect drawRect = new Rect(); //draw area;
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(appRect);
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(drawRect);

        Point point = new Point();
        rootRect.getSize(point);
        int root_height = point.y;
        int app_top = appRect.top;
//        int h = root_height - app_height;
//        nxprint("root_height/app_top:"+ root_height+"/"+app_top);
        return  app_top;
    }
    protected void connectRemoteDB(){

    }

    protected void db_connect(){

    }
}
