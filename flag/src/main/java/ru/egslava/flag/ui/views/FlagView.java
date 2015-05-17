package ru.egslava.flag.ui.views;

import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;

import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.InstanceState;

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
//        setBackgroundResource(R.drawable.flag_bg);
    }

    @Override public void setChecked(boolean checked) {
        this.checked = checked;
        setSelected(checked);
        final boolean selected = isSelected();
    }

    @Override public void setSelected(boolean selected) {
        super.setSelected(selected);
        setAlpha(selected ? 0.3f : 1.0f);
    }

    @Override public boolean isChecked() { return checked; }

    @Override public void toggle() { setChecked(!isChecked()); }

    @Override public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.resId = resId;
    }

    @Override public void onClick(View v) { toggle();   }

    @Override protected Parcelable onSaveInstanceState() {
        final Parcelable parcelable = super.onSaveInstanceState();
        final FlagParcellable state = new FlagParcellable(parcelable);
        state.isChecked = isChecked();
        return state;
    }
    @Override protected void onRestoreInstanceState(Parcelable state) {
        if ( ! ( state instanceof FlagParcellable )) {
            super.onRestoreInstanceState(state);
            return;
        }


        FlagParcellable flagParcellable = (FlagParcellable)state;
        super.onRestoreInstanceState(flagParcellable.getSuperState());

        setChecked( flagParcellable.isChecked );
    }


    class FlagParcellable extends BaseSavedState {
        public boolean isChecked;
        public FlagParcellable(Parcel source) {
            super(source);
            isChecked = source.readInt() == 1;
        }
        public FlagParcellable(Parcelable superState) { super(superState); }

        @Override public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt( isChecked ? 1 : 0 );
        }

        public final Parcelable.Creator<FlagParcellable> CREATOR =
            new Parcelable.Creator<FlagParcellable>() {
                public FlagParcellable createFromParcel(Parcel in) {
                    return new FlagParcellable(in);
                }
                public FlagParcellable[] newArray(int size) {
                    return new FlagParcellable[size];
                }
            };
    }

}

