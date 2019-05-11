package de.emm_ess.commons.math;

public abstract class MathHelper {
    public static int clamp(final int value, final int min, final int max){
        return Math.min(Math.max(value, min), max);
    }

    public static float clamp(final float value, final float min, final float max){
        return Math.min(Math.max(value, min), max);
    }

    public static double clamp(final double value, final double min, final double max){
        return Math.min(Math.max(value, min), max);
    }
}
