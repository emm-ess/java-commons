package de.emm_ess.commons;

import java.util.Collection;

public abstract class Helper {
    public static String toDebugString(final float[] arr) {
        StringBuilder sb = new StringBuilder();
        for (float ele : arr) {
            sb.append(String.format("%.3f", ele));
            sb.append("\t");
        }
        return sb.toString();
    }

    public static String toDebugString(final int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int ele : arr) {
            sb.append(ele);
            sb.append("\t");
        }
        return sb.toString();
    }

    public static String toDebugString(final Collection<Object> aL) {
        StringBuilder sb = new StringBuilder();
        for (Object o : aL) {
            sb.append(o);
            sb.append(", ");
        }
        return sb.toString();
    }
}
