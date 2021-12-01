package com.tom.cpl.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class Image {
	public final BufferedImage internalImage;

	public Image(int w, int h) {
		internalImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
	}

	public Image(Image cpyFrom) {
		internalImage = cpyFrom.internalImage;
	}

	public Image(BufferedImage image) {
		internalImage = image;
	}

	public Image(int[] data, int w) {
		internalImage = new BufferedImage(w, 1, BufferedImage.TYPE_4BYTE_ABGR);
	}

	public void setRGB(int x, int y, int rgb) {
		internalImage.setRGB(x, y, rgb);
	}

	public int getRGB(int x, int y) {
		return internalImage.getRGB(x, y);
	}

	public int[] getData() {
		return new int[0];
	}

	public int getWidth() {
		return internalImage.getWidth();
	}

	public int getHeight() {
		return internalImage.getHeight();
	}

	public static Image loadFrom(File f) throws IOException {
		return new Image(ImageIO.read(f));
	}

	public static Image loadFrom(InputStream f) throws IOException {
		return new Image(ImageIO.read(f));
	}

	public void storeTo(File f) throws IOException {
		ImageIO.write(internalImage, "PNG", f);
	}

	public void storeTo(OutputStream f) throws IOException {
		ImageIO.write(internalImage, "PNG", f);
	}

	public void draw(Image i) {
		int width = getWidth();
		int height = getHeight();
		int secondWidth = i.getWidth();
		int secondHeight = i.getHeight();

		for(int x = 0;x<width && x<secondWidth;x++) {
			for(int y = 0;y<height && y<secondHeight;y++) {
				setRGB(x, y, i.getRGB(x, y));
			}
		}
	}

	public void draw(Image i, int xs, int ys) {
		int w = getWidth();
		int h = getHeight();
		int iw = i.getWidth();
		int ih = i.getHeight();

		for(int x = 0;x + xs < w && x < iw;x++) {
			for(int y = 0;y + ys < h && y < ih;y++) {
				int p = (y + ys) * w + x + xs;
				if(p < 0)continue;
				setRGB(x + xs, y + ys, i.getRGB(x, y));
			}
		}
	}

	public void draw(Image i, int xs, int ys, int w, int h) {
		for(int y = ys;y<h;y++) {
			for(int x = xs;x<w;x++) {
				float fx = x / (float) w;
				float fy = y / (float) h;
				setRGB(x, y, i.getRGB(Math.min((int) (fx * i.getWidth()), i.getWidth()-1), Math.min((int) (fy * i.getHeight()), i.getHeight()-1)));
			}
		}
	}

	public static CompletableFuture<Image> download(String url) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				URL input = new URL(url);
				InputStream istream = null;
				try {
					istream = input.openStream();
				} catch (IOException e) {
					throw new IOException("Can't get input stream from URL!", e);
				}
				try {
					throw new IOException();
				} finally {
					istream.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void fill(int color) {
		int w = getWidth();
		int h = getHeight();

		for(int y = 0;y<h;y++) {
			for(int x = 0;x<w;x++) {
				setRGB(x, y, color);
			}
		}
	}

	public void fill(int xs, int ys, int w, int h, int color) {
		int thisw = getWidth();
		int thish = getHeight();

		for(int x = 0;x + xs < thisw && x < w;x++) {
			for(int y = 0;y + ys < thish && y < h;y++) {
				setRGB(x + xs, y + ys, color);
			}
		}
	}
}
