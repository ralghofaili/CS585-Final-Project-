package com;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.PixelGrabber;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {

        File firefoxResult = getImage(BrowserType.FIREFOX);

        File chromeResult = getImage(BrowserType.CHROME);

        System.out.println("The images are equal to :" + CompareScreenshots(firefoxResult, chromeResult));

     System.out.println("If they are false means they are different , True they are equal ");
}

    private static File getImage(BrowserType browserType) throws Exception {
        Program p = new Program(browserType);
        File firefoxResult = p.takeScreenshot("https://www.cpp.edu");
        p.close();
        return firefoxResult;
    }

    private static boolean CheckIfTheyAreEql(final int[] x, final int[] y) {
        int[] min;
        int[] max;

        if (x.length < y.length) {
            min = x;
            max = y;
        } else {
            min = y;
            max = x;
        }

        for (int i = 0; i < min.length; i++) {
            if (min[i] != max[i]) {

                if ((((min[i] >> 24) & 0xff) != 0) || (((max[i] >> 24) & 0xff) != 0)) {
                    return false;
                }
            }
        }
        return true;
    }
    private static BufferedImage getBI(final File f) {
        Image ScreenShot;

        try {
            ScreenShot = Toolkit.getDefaultToolkit().createImage(f.getCanonicalPath());

            new ImageIcon(ScreenShot);
        } catch (final Exception finale) {
            throw new RuntimeException(f.getPath(), finale);
        }

        BufferedImage NBI = new BufferedImage(ScreenShot.getWidth(null),
                ScreenShot.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D Graph2d = NBI.createGraphics();
        Graph2d.drawImage(ScreenShot, 0, 0, null);
        Graph2d.dispose();
        return NBI;
    }

    private static int[] getPicturePixel(BufferedImage Pic, File file) {
        Image pxOfpic;
        int w = Pic.getWidth();
        int h = Pic.getHeight();
        int[]  pxinfo = new int[w * h];


        if (Pic.getColorModel().getColorSpace() == ColorSpace.getInstance(ColorSpace.CS_sRGB)) {
            pxOfpic = Pic;
        } else {
            pxOfpic = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_sRGB), null).filter(Pic, null);
        }

        PixelGrabber pg = new PixelGrabber( pxOfpic, 0, 0, w, h, pxinfo , 0, w);

        try {
           if (!pg.grabPixels()) {
                throw new RuntimeException();
           }
        } catch (final InterruptedException ie) {
            throw new RuntimeException(file.getPath(), ie);
        }

        return pxinfo ;
    }




    private static boolean CompareScreenshots(File first, File second) {
        if (first == second)
            return true;
        return CheckIfTheyAreEql(getPicturePixel(getBI(first), second), getPicturePixel(getBI(second), second));
    }


}
