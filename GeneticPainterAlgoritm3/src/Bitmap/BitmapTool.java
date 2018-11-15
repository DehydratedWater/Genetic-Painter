package Bitmap;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BitmapTool 
{
	public static final float compareBitmaps(BufferedImage startImg, BufferedImage compImg)
	{
		int width = startImg.getWidth();
		int height = startImg.getHeight();
		
		if(compImg.getWidth()!=width||compImg.getHeight()!=height)
			return 0;
		float maxDiffence = (float) (width*height*Math.sqrt(255*255*3));
		float sum = 0;
		Color c1;
		Color c2;
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				c1 = new Color(startImg.getRGB(i, j));
				c2 = new Color(compImg.getRGB(i, j));
				sum += Math.sqrt(Math.pow((c1.getRed()-c2.getRed()), 2)+Math.pow((c1.getGreen()-c2.getGreen()), 2)+Math.pow((c1.getBlue()-c2.getBlue()), 2));
			}
		}
		return ((maxDiffence-sum))/maxDiffence;
	}
//	private static float compareColors(Color c1, Color c2)
//	{
//		return (float) Math.sqrt(Math.pow((c1.getRed()-c2.getRed()), 2)+Math.pow((c1.getGreen()-c2.getGreen()), 2)+Math.pow((c1.getBlue()-c2.getBlue()), 2));
//	}
	
	public static float fullCompareBitmaps(BufferedImage startImg, BufferedImage compImg)
	{
		int width = startImg.getWidth();
		int height = startImg.getHeight();
		
		if(compImg.getWidth()!=width||compImg.getHeight()!=height)
			return 0;
		
		float result = 0;
		Color startColor;
		Color compColor;
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				startColor = new Color(startImg.getRGB(i, j));
				compColor = new Color(compImg.getRGB(i, j));
				result += fullCompareColors(startColor, compColor);
			}
		}
		result /= (width*height);
		result *= 100;
		return result;
		
	}
	
	public static float fullCompareColors(Color startColor, Color compColor)
	{
		float res = (255-Math.abs(startColor.getRed()-compColor.getRed()))/255;
		res += (255-Math.abs(startColor.getGreen()-compColor.getGreen()))/255;
		res += (255-Math.abs(startColor.getBlue()-compColor.getBlue()))/255;
		res /= 3f;
		return res;
	}
}
