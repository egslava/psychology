package ru.egslava.flag.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;

import ru.egslava.flag.R;

/**
 * Created by egslava on 18/05/15.
 */
public class FlagView extends ImageView implements Checkable, View.OnClickListener {

    public int resId;
    public boolean checked;

    public FlagView(Context context) { super(context); init(); }
    public FlagView(Context context, AttributeSet attrs) { super(context, attrs); init(); }

    void init() {
        setOnClickListener(this);

        final TypedArray a = getContext().getTheme().obtainStyledAttributes(R.style.AppTheme,
                new int[]{ android.R.attr.selectableItemBackground } );
        final int drawableId = a.getResourceId(0, 0);
        setBackgroundResource(drawableId);
//        setBackgroundResource( android.R.drawable.menuitem_background);
    }

    @Override public void setChecked(boolean checked) {
        this.checked = checked;
        setSelected(checked);
        final boolean selected = isSelected();
    }
    @Override public boolean isChecked() { return checked; }
    @Override public void toggle() { setChecked(!isChecked()); }

    @Override public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.resId = resId;
    }

    @Override public void onClick(View v) { toggle();   }
}

