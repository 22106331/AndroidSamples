package com.gw.drawable.tool;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.StateSet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by GongWen on 17/9/5.
 * 需要设置
 * android:clickable="true"
 *
 * ColorDrawable,GradientDrawable
 *
 * BitmapDrawable
 */

public class StateDrawableUtil {
    public final static int[] pressedState = new int[]{android.R.attr.state_pressed};
    public final static int[] normalState = StateSet.WILD_CARD;

    //////////////////////颜色状态Drawable////////////////////////////////////////////////
    public static StateListDrawable getStateListDrawable(int normalColor, int pressedColor) {
        StateListDrawable mStateListDrawable = new StateListDrawable();
        ColorDrawable normalColorDrawable = new ColorDrawable();
        ColorDrawable pressedColorDrawable = new ColorDrawable();
        normalColorDrawable.setColor(normalColor);
        pressedColorDrawable.setColor(pressedColor);
        //顺序不可颠倒
        mStateListDrawable.addState(pressedState, pressedColorDrawable);
        mStateListDrawable.addState(normalState, normalColorDrawable);
        return mStateListDrawable;
    }

    public static StateListDrawable getStateListDrawable(GradientDrawable normalDrawable, GradientDrawable pressedDrawable) {
        StateListDrawable mStateListDrawable = new StateListDrawable();
        mStateListDrawable.addState(pressedState, pressedDrawable);
        mStateListDrawable.addState(normalState, normalDrawable);
        return mStateListDrawable;
    }

    public static StateListDrawable getStateListDrawable(Drawable normalDrawable, Drawable pressedDrawable) {
        StateListDrawable mStateListDrawable = new StateListDrawable();
        mStateListDrawable.addState(pressedState, pressedDrawable);
        mStateListDrawable.addState(normalState, normalDrawable);
        return mStateListDrawable;
    }

    public static StateListDrawable getStateListDrawable(StateListDrawable mStateListDrawable) {
        return mStateListDrawable;
    }

    //////////////////////图片状态Drawable////////////////////////////////////////////////
    public static Drawable getTintListDrawable(Context mContext, @DrawableRes int resId, int normalColor, int pressColor) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        int[][] states = new int[][]{pressedState, normalState};
        int[] colors = new int[]{pressColor, normalColor};
        return getTintListDrawable(drawable, new ColorStateList(states, colors));
    }

    public static Drawable getTintListDrawable(@NonNull Drawable drawable, @Nullable ColorStateList tint) {
        return getTintListDrawable(drawable, tint, null);
    }

    public static Drawable getTintListDrawable(@NonNull Drawable drawable, @Nullable ColorStateList tint, @Nullable PorterDuff.Mode tintMode) {
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            Drawable d = null;
            try {
                Class<?> dclClazz = Class.forName("android.support.v4.graphics.drawable.DrawableCompatLollipop");
                Method method = dclClazz.getMethod("wrapForTinting", Drawable.class);
                d = (Drawable) method.invoke(null, drawable);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (d != null) {
                drawable = d;
            }
        } else {
            drawable = DrawableCompat.wrap(drawable);
        }
        if (tintMode != null) {
            DrawableCompat.setTintMode(drawable, tintMode);
        }
        DrawableCompat.setTintList(drawable, tint);
        return drawable;
    }

    private StateDrawableUtil() {
    }
}
