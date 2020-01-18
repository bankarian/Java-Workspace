package com.netjava.yao.tello;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class FFmpegDecoder implements Runnable {
	private BufferedImage bufferedImage;
	private InputStream inputStream;
	
	public FFmpegDecoder(InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}

	public void run() {
		try {
			FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputStream);
//			frameGrabber.setFrameRate(30);
//			frameGrabber.setFormat("h264");
//			frameGrabber.setVideoBitrate(15);
//			frameGrabber.setVideoOption("preset", "ultrafast");
//			frameGrabber.setNumBuffers(25000000);
			frameGrabber.start();
			Frame frame = frameGrabber.grab();
			Java2DFrameConverter converter = new Java2DFrameConverter();
			while(!Thread.interrupted() && frame != null) {
				BufferedImage bufferedImage = converter.convert(frame);
				setBufferedImage(bufferedImage);
				frame = frameGrabber.grab();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}     
	
	private void setBufferedImage(BufferedImage image) {
		bufferedImage = image;
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
}
