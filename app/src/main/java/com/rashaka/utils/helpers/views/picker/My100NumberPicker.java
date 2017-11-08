package com.rashaka.utils.helpers.views.picker;

/**
 * Created by User on 30.08.2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class My100NumberPicker extends NumberPicker {

    public String[] numbers = {"000", "100", "200","300", "400", "500","600", "700", "800","900"};

    public My100NumberPicker(Context context) {
        super(context);
    }

    public My100NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    public My100NumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
    }
    private void processAttributeSet(AttributeSet attrs) {
        //This method reads the parameters given in the xml file and sets the properties according to it
        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 9));
        this.setDisplayedValues(numbers);
    }

}