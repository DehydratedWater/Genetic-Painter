package elemets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Random;

import windows.Window;

public class Triangle 
{
	public int x1, y1, x2, y2, x3, y3;
	public int R, G, B, A;
	
	public Triangle(Random r, int x, int y, int size)
	{
		x1 = r.nextInt(x);
		y1 = r.nextInt(y);
		x2 = x1+r.nextInt(size);
		x3 = x1+r.nextInt(size);
		y2 = y1+r.nextInt(size);
		y3 = y1+r.nextInt(size);
		R = r.nextInt(256);
		G = r.nextInt(256);
		B = r.nextInt(256);
		A = r.nextInt(256);
	}
	
	public Triangle(Triangle t, Random r, int mutationSize, int mutationChance)
	{
		if(chance(r, mutationChance))
			x1 = mutateX(r, t.x1, mutationSize);
		else
			x1 = t.x1;
		if(chance(r, mutationChance))
			x2 = mutateX(r, t.x2, mutationSize);
		else
			x2 = t.x2;
		if(chance(r, mutationChance))
			x3 = mutateX(r, t.x3, mutationSize);
		else
			x3 = t.x3;
		if(chance(r, mutationChance))
			y1 = mutateY(r, t.y1, mutationSize);
		else
			y1 = t.y1;
		if(chance(r, mutationChance))
			y2 = mutateY(r, t.y2, mutationSize);
		else
			y2 = t.y2;
		if(chance(r, mutationChance))
			y3 = mutateY(r, t.y3, mutationSize);
		else
			y3 = t.y3;
		if(chance(r, mutationChance))
			R = mutateRGBA(r, t.R, mutationSize);
		else
			R = t.R;
		if(chance(r, mutationChance))
			G = mutateRGBA(r, t.G, mutationSize);
		else
			G = t.G;
		if(chance(r, mutationChance))
			B = mutateRGBA(r, t.B, mutationSize);
		else
			B = t.B;
		if(chance(r, mutationChance))
			A = mutateRGBA(r, t.A, mutationSize);
		else
			A = t.A;
	}
	
	public Triangle(Triangle t)
	{
		x1 = t.x1;
		x2 = t.x2;
		x3 = t.x3;
		y1 = t.y1;
		y2 = t.y2;
		y3 = t.y3;
		R = t.R;
		G = t.G;
		B = t.B;
		A = t.A;
	}
	
	
	
	public void drawTriangle(Graphics2D g)
	{
		Path2D p = new Path2D.Float();
		p.moveTo(x1, y1);
		p.lineTo(x1, y1);
		p.lineTo(x2, y2);
		p.lineTo(x3, y3);
		p.lineTo(x1, y1);
		g.setPaint(new Color(R, G, B, A));
		g.fill(p);
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
	public int mutateX(Random r, int value, int mutationSize)
	{
		value = value + r.nextInt(mutationSize) - mutationSize/2;
		if(value<0)
			value = 0;
		if(value>Window.weight)
			value = Window.weight;
		return value;
	}
	public int mutateY(Random r, int value, int mutationSize)
	{
		value = value + r.nextInt(mutationSize) - mutationSize/2;
		if(value<0)
			value = 0;
		if(value>Window.height)
			value = Window.height;
		return value;
	}
	public int mutateXY(Random r, int value, int mutationSize, boolean isX)
	{
		value = value + r.nextInt(mutationSize) - mutationSize/2;
		if(value<0)
			value = 0;
		if(isX)
		{
			if(value>Window.weight)
				value = Window.weight;
		}
		else
		{
			if(value>Window.height)
				value = Window.height;
		}
		//System.out.println(value);
		return value;
	}
	
	public int mutateRGBA(Random r, int value, int mutationSize)
	{
		value = value + r.nextInt(mutationSize) - mutationSize/2;
		if(value>255)
			value = 255;
		if(value<0)
			value = 0;
		return value;
	}
}
