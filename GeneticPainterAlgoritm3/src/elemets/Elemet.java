package elemets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Bitmap.BitmapTool;
import managers.textureManager;

public class Elemet 
{
	public Triangle[] triangleTab;
	public float value;
	private boolean isBlack = true;
	
	public Elemet(Random r, int numOfElemets, int x, int y, int sizeOfTriangles)
	{
		triangleTab = new Triangle[numOfElemets];
		for(int i = 0; i < numOfElemets; i++)
		{
			triangleTab[i] = new Triangle(r, x, y, sizeOfTriangles);
		}
	}
	
	public Elemet(Random r, Elemet e1, Elemet e2, int mutateChance, int mutationSize)
	{
		triangleTab = new Triangle[e1.triangleTab.length];
		for(int i = 0; i < e1.triangleTab.length; i++)
		{
			if(chance(r))
			{
				triangleTab[i] = new Triangle(e1.triangleTab[i], r, mutationSize, mutateChance);
			}
			else
			{
				triangleTab[i] = new Triangle(e2.triangleTab[i], r, mutationSize, mutateChance);
			}
		}
	}
	
	public void checkValue(BufferedImage mainImage)
	{
		BufferedImage img = new BufferedImage(mainImage.getWidth(), mainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.createGraphics();
		//System.out.println("Rysowanie mapy trójk¹tów");
		if(isBlack )
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, img.getWidth(), img.getHeight());
		}
		for(int i = 0; i < triangleTab.length; i++)
		{
			triangleTab[i].drawTriangle(g);
		}
		value = BitmapTool.compareBitmaps(mainImage, img);	
	}
	
	public void checkValueAndSetBitmap(BufferedImage mainImage, textureManager tm, int indexActualImage)
	{
		BufferedImage img = new BufferedImage(mainImage.getWidth(), mainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.createGraphics();
		//System.out.println("Rysowanie mapy trójk¹tów");
		if(isBlack )
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, img.getWidth(), img.getHeight());
		}
		for(int i = 0; i < triangleTab.length; i++)
		{
			triangleTab[i].drawTriangle(g);
		}
		value = BitmapTool.compareBitmaps(mainImage, img);
		tm.getTexture(indexActualImage).setBitmap(img);
		
	}
	public void setBitmap(BufferedImage mainImage, textureManager tm, int indexEditedImage)
	{
		BufferedImage img = new BufferedImage(mainImage.getWidth(), mainImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.createGraphics();
		//System.out.println("Rysowanie mapy trójk¹tów");
		if(isBlack )
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, img.getWidth(), img.getHeight());
		}
		for(int i = 0; i < triangleTab.length; i++)
		{
			triangleTab[i].drawTriangle(g);
		}
		value = BitmapTool.compareBitmaps(mainImage, img);
		tm.getTexture(indexEditedImage).setBitmap(img);
		
	}
	public boolean chance(Random r)
	{
		if(r.nextInt(100)<50)
			return true;
		else 
			return false;
	}
	
	public boolean chance(Random r, int m)
	{
		if(r.nextInt(100)<m)
			return true;
		else 
			return false;
	}
}
