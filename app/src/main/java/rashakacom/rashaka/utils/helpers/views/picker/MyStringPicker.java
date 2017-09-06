package rashakacom.rashaka.utils.helpers.views.picker;

/**
 * Created by User on 30.08.2017.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class MyStringPicker extends NumberPicker {

    public MyStringPicker(Context context) {
        super(context);
        changeDividerColor(this, Color.parseColor("#00ffffff"));
    }

    public MyStringPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
        changeDividerColor(this, Color.parseColor("#00ffffff"));
    }

    public MyStringPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
        changeDividerColor(this, Color.parseColor("#00ffffff"));
    }
    private void processAttributeSet(AttributeSet attrs) {
        //This method reads the parameters given in the xml file and sets the properties according to it
        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 0));
        this.setValue(attrs.getAttributeIntValue(null, "def", 0));
    }

    private void changeDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}