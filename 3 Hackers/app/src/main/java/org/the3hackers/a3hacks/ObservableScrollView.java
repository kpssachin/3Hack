package org.the3hackers.a3hacks;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {
    private Callback callback;

    public interface Callback {
        void onScrollChanged(int i, int i2, int i3, int i4);
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        super.onScrollChanged(left, top, oldLeft, oldTop);
        if (this.callback != null) {
            this.callback.onScrollChanged(left, top, oldLeft, oldTop);
        }
    }
}
