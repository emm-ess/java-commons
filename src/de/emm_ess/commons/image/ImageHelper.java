package de.emm_ess.commons.image;

public abstract class ImageHelper {
    public static int colorToLuma(final int pixel){
        return (int) (0.299f * ((pixel & 0xff0000) >> 16)
                + 0.587f * ((pixel & 0x00ff00) >> 8)
                + 0.114f * (pixel & 0x0000ff));
    }

    public static void convertToLuma(final int[] pixels){
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = colorToLuma(pixels[i]);
        }
    }

    public static int[] intToColorArray(final int color) {
        final int[] arr = new int[3];
        arr[0] = (color & 0xff0000) >> 16;
        arr[1] = (color & 0x00ff00) >> 8;
        arr[2] = (color & 0x0000ff);
        return arr;
    }

    public static int[] histogram(final int[] pixels){
        final int[] histogram = new int[256];

        for (int i = 0, luminanz; i < pixels.length; i++) {
            luminanz = colorToLuma(pixels[i]);
            histogram[luminanz] += 1;
        }

        return histogram;
    }

    public static int getMinimum(final int[] histogram) {
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                return i;
            }
        }
        return histogram.length;
    }

    public static int getMaximum(final int[] histogram) {
        for (int i = histogram.length - 1; i >= 0; i--) {
            if (histogram[i] > 0) {
                return i;
            }
        }
        return 0;
    }

    public static float getAverage(final int[] histogram) {
        float average = 0;
        int pixelCount = 0;
        for (int i = 0; i < 256; i++) {
            pixelCount += histogram[i];
            average += histogram[i] * i;
        }
        return average / pixelCount;
    }

    public static int getMedian(final int[] pixels){
        final int pixelCount = pixels.length;
        final int[] sortedLuminanz = new int[pixelCount];

        for (int i = 0; i < pixelCount; i++) {
            sortedLuminanz[i] = colorToLuma(pixels[i]);
        }

        java.util.Arrays.sort(sortedLuminanz);
        return sortedLuminanz[pixelCount >> 1];
    }

    // ==================================================
    // Super Fast Blur v1.1
    // by Mario Klingemann
    // <http://incubator.quasimondo.com>
    // got even a tiny bit faster
    // ==================================================
    public static void fastblur(final int[] pixels, final int width, final int height, final int blurRadius) {
        final int pixelCount = pixels.length;
        int wm = width - 1;
        int hm = height - 1;
        int div = blurRadius + blurRadius + 1;
        int[] r = new int[pixelCount];
        int[] g = new int[pixelCount];
        int[] b = new int[pixelCount];
        int rsum, gsum, bsum, x, y, i, p, p1, p2, yp, yi, yw;
        int[] vmin = new int[Math.max(width, height)];
        int[] vmax = new int[Math.max(width, height)];
        int l = 256 * div;
        int[] dv = new int[l];
        for (i = 0; i < l; i++) {
            dv[i] = (i / div);
        }

        yw = yi = 0;

        for (y = 0; y < height; y++) {
            rsum = gsum = bsum = 0;
            for (i = -blurRadius; i <= blurRadius; i++) {
                p = pixels[yi + Math.min(wm, Math.max(i, 0))];
                rsum += (p & 0xff0000) >> 16;
                gsum += (p & 0x00ff00) >> 8;
                bsum += p & 0x0000ff;
            }
            for (x = 0; x < width; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                if (y == 0) {
                    vmin[x] = Math.min(x + blurRadius + 1, wm);
                    vmax[x] = Math.max(x - blurRadius, 0);
                }
                p1 = pixels[yw + vmin[x]];
                p2 = pixels[yw + vmax[x]];

                rsum += ((p1 & 0xff0000) - (p2 & 0xff0000)) >> 16;
                gsum += ((p1 & 0x00ff00) - (p2 & 0x00ff00)) >> 8;
                bsum += (p1 & 0x0000ff) - (p2 & 0x0000ff);
                yi++;
            }
            yw += width;
        }

        for (x = 0; x < width; x++) {
            rsum = gsum = bsum = 0;
            yp = -blurRadius * width;
            for (i = -blurRadius; i <= blurRadius; i++) {
                yi = Math.max(0, yp) + x;
                rsum += r[yi];
                gsum += g[yi];
                bsum += b[yi];
                yp += width;
            }
            yi = x;
            for (y = 0; y < height; y++) {
                pixels[yi] = 0xff000000 | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
                if (x == 0) {
                    vmin[y] = Math.min(y + blurRadius + 1, hm) * width;
                    vmax[y] = Math.max(y - blurRadius, 0) * width;
                }
                p1 = x + vmin[y];
                p2 = x + vmax[y];

                rsum += r[p1] - r[p2];
                gsum += g[p1] - g[p2];
                bsum += b[p1] - b[p2];

                yi += width;
            }
        }
    }

    public static boolean isEqual(final Image a, final Image b, final int threshold){
        if(a.pixels == b.pixels){
            return true;
        }
        else if(a.pixelCount != b.pixelCount){
            return false;
        }
        else {
            final int[] aPixelsLuma = a.pixels.clone();
            final int[] bPixelsLuma = b.pixels.clone();

            convertToLuma(aPixelsLuma);
            convertToLuma(bPixelsLuma);

            int difference = 0;

            for(int i = 0; i < a.pixelCount; i++){
                difference += Math.abs(aPixelsLuma[i] - bPixelsLuma[i]);

                if(difference > threshold){
                    return false;
                }
            }

            return true;
        }
    }
}
