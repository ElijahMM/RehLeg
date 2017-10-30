package assist.com.rehleg.ui.animators;

import android.view.View;

/**
 * Created by Sergiu on 26.10.2017.
 */

public class Utils {

    public static float sin(double degree) {
        return (float) Math.sin(Math.toRadians(degree));
    }

    public static float cos(double degree) {
        return (float) Math.cos(Math.toRadians(degree));
    }

    public static float asin(double value) {
        return (float) Math.toDegrees(Math.asin(value));
    }

    public static float acos(double value) {
        return (float) Math.toDegrees(Math.acos(value));
    }

    public static float centerX(View view) {
        return view.getX() + view.getWidth() / 2;
    }

    public static float centerY(View view) {
        return view.getY() + view.getHeight() / 2;
    }

}
