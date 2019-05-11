package de.emm_ess.commons.communication;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import java.util.ArrayList;
import java.util.Collection;

public abstract class OSCHelper {
    public static OSCMessage toMessage(final String route, final boolean data) {
        final Collection<Object> list = new ArrayList<>();
        list.add(data);
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final boolean[] data) {
        final Collection<Object> list = new ArrayList<>();
        for (boolean value : data) {
            list.add(value);
        }
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final int data) {
        final Collection<Object> list = new ArrayList<>();
        list.add(data);
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final int[] data) {
        final Collection<Object> list = new ArrayList<>();
        for (int value : data) {
            list.add(value);
        }
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final float data) {
        final Collection<Object> list = new ArrayList<>();
        list.add(data);
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final float[] data) {
        final Collection<Object> list = new ArrayList<>();
        for (float value : data) {
            list.add(value);
        }
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final double data) {
        final Collection<Object> list = new ArrayList<>();
        list.add((float) data);
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final double[] data) {
        final Collection<Object> list = new ArrayList<>();
        for (double value : data) {
            list.add((float) value);
        }
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final Vector3f data) {
        final Collection<Object> list = new ArrayList<>();
        list.add(data.x);
        list.add(data.y);
        list.add(data.z);
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final Vector3d data) {
        final Collection<Object> list = new ArrayList<>();
        list.add((float) data.x);
        list.add((float) data.y);
        list.add((float) data.z);
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final Vector4f data) {
        final Collection<Object> list = new ArrayList<>();
        list.add(data.w);
        list.add(data.x);
        list.add(data.y);
        list.add(data.z);
        return new OSCMessage(route, list);
    }

    public static OSCMessage toMessage(final String route, final Collection<Object> data) {
        return new OSCMessage(route, data);
    }


    //TODO
    // better check input data? in terms of efficiency
    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final boolean data) {
        oscMsgs.add(toMessage(route, data));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final boolean[] data) {
        oscMsgs.add(toMessage(route, data));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final int data) {
        oscMsgs.add(toMessage(route, data));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final int[] data) {
        oscMsgs.add(toMessage(route, data));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final float data) {
        final Collection<Object> list = new ArrayList<>();
        if (Float.isNaN(data)) {
            return;
        } else {
            list.add(data);
        }
        oscMsgs.add(new OSCMessage(route, list));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final float[] data) {
        final Collection<Object> list = new ArrayList<>();

        for (float value : data) {
            if (Float.isNaN(value)) {
                return;
            } else {
                list.add(value);
            }
        }
        oscMsgs.add(new OSCMessage(route, list));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final double data) {
        final Collection<Object> list = new ArrayList<>();
        if (Double.isNaN(data)) {
            return;
        } else {
            list.add((float) data);
        }
        oscMsgs.add(new OSCMessage(route, list));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final double[] data) {
        final Collection<Object> list = new ArrayList<>();

        for (double value : data) {
            if (Double.isNaN(value)) {
                return;
            } else {
                list.add((float) value);
            }
        }
        oscMsgs.add(new OSCMessage(route, list));
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final Vector3f data) {
        addMessage(oscMsgs, route, new float[]{data.x, data.y, data.z});
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final Vector3d data) {
        addMessage(oscMsgs, route, new float[]{(float) data.x, (float) data.y, (float) data.z});
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final Vector4f data) {
        addMessage(oscMsgs, route, new float[]{data.w, data.x, data.y, data.z});
    }

    public static void addMessage(final Collection<OSCPacket> oscMsgs, final String route, final Collection<Object> data) {
        //TODO
        // also check
        oscMsgs.add(new OSCMessage(route, data));
    }
}
