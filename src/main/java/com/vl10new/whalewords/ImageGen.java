package com.vl10new.whalewords;

import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import com.kennycason.kumo.*;

import javax.imageio.ImageIO;
import java.io.*;

public class ImageGen
{
	public static enum Figure
	{
		RECTANGLE {
			@Override
			CollisionMode asCollision() {
				return CollisionMode.RECTANGLE;
			}
		},
		CIRCLE {
			@Override
			CollisionMode asCollision() {
				return CollisionMode.PIXEL_PERFECT;
			}
		};

		abstract CollisionMode asCollision();
	}

	private List<WordFrequency> wordsList;
	private ColorPalette palette;
	private BufferedImage outputImage;
	public ImageGen(SortedSet<Map.Entry<String, Long>> wordsList, ColorPalette palette)
	{
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		this.wordsList = frequencyAnalyzer.load();
		this.palette = palette;

		//new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF))
	}

	public void imageFromMask(String imagePath, Dimension dim)
	{
		this.genImage(CollisionMode.PIXEL_PERFECT, imagePath, dim);
	}

	public void imageFromFigure(Figure fig, Dimension dim)
	{
		this.genImage(fig.asCollision(), "", dim);
	}

	private void genImage(CollisionMode collision, String imagePath, Dimension dim)
	{
		final Dimension dimension = dim;
		final WordCloud wordCloud = new WordCloud(dimension, collision);

		wordCloud.setPadding(2);

		//set image background if thereis
		try
		{
			if (imagePath.isBlank() || !(new File(imagePath).exists()))
				wordCloud.setBackground(new CircleBackground(300));
			else
				wordCloud.setBackground(new PixelBoundryBackground(imagePath));
		}
		catch(IOException ignored)
		{
			return;
		}


		wordCloud.setColorPalette(this.palette);
		wordCloud.setFontScalar(new SqrtFontScalar(10, 40));

		wordCloud.build(this.wordsList);

		this.outputImage = wordCloud.getBufferedImage();

	}

	public void saveToFile(String filename, String format)
	{
		try
		{
			File outputfile = new File(filename);
			ImageIO.write(this.outputImage, format, outputfile);
		}
		catch(Exception ignored)
		{}
	}

	public String imageToB64()
	{
		try(ByteArrayOutputStream os = new ByteArrayOutputStream())
		{
			ImageIO.write(this.outputImage, "png", os);
			return Base64.getEncoder().encodeToString(os.toByteArray());
		}
		catch(IOException ignored)
		{ }
		return null;
	}
}
