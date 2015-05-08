package ru.egslava.flag.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import ru.egslava.flag.R;


/**
 * Created by egslava on 09/04/15.
 */
public class GenderView extends CheckBox {

    public GenderView(Context context) { super(context); }
    public GenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setButtonDrawable(R.mipmap.nil);
        setGender( Gender.MALE );
    }

    void setGender(Gender gender){
        setChecked( gender == Gender.FEMALE );
    }
    Gender getGender() { return isChecked()? Gender.FEMALE: Gender.MALE; }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        setText( checked ? "♀" : "♂" );
    }
}
