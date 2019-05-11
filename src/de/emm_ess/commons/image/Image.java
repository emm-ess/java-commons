package de.emm_ess.commons.image;

import processing.core.PImage;

import javax.management.InvalidAttributeValueException;

public class Image {
    public final static int BLUR_RADIUS = 3;
    public final static float MAX_CONTRAST = 0.75f;
    public final static float SATURATION = 1.7f;

    protected final int[] histogram;

    public final int[] pixels;
    public final int width;
    public final int height;
    public final int pixelCount;


    public Image(final PImage pImage) {
        pixels = pImage.pixels;
        width = pImage.width;
        height = pImage.height;
        pixelCount = width * height;

        histogram = ImageHelper.histogram(pixels);
    }

    public Image(final int[] pixels, final int width, final int height) throws InvalidAttributeValueException {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.pixelCount = width * height;

        if (pixels.length != pixelCount) {
            throw new InvalidAttributeValueException();
        }

        histogram = ImageHelper.histogram(pixels);
    }

    public PImage toPimage(){
        PImage pim = new PImage(width, height);
        pim.pixels = pixels;
        return pim;
    }

    public int getMinimum() {
        return ImageHelper.getMinimum(histogram);
    }

    public int getMaximum() {
        return ImageHelper.getMaximum(histogram);
    }

    public int getMedian() {
        return ImageHelper.getMedian(pixels);
    }

    protected float getAverage() {
        return ImageHelper.getAverage(histogram);
    }

    public int getPixel(final int[] position) {
        return getPixel(position[1] * width + position[0]);
    }

    public int getPixel(final int position) {
        return pixels[position];
    }

    public int[] getPixels() {
        return pixels;
    }
}
