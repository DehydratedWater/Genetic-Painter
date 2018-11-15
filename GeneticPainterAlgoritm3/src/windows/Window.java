package windows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import Bitmap.BitmapTool;
import elemets.Elemet;
import managers.ControlsManager;
import managers.textureManager;
import objects.StaticObject;

public class Window extends BasicWindow implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener{


	private static final long serialVersionUID = 1L;
	private textureManager texManager;
	private ControlsManager cm;
	private StaticObject mainPhoto, bestVersion, actualVersion;
	private float lastValue = 0;
	private int lastGeneration = 0, actualGeneration = 0;;
	private float lastTestValue;
	
	private int populationSize = 50;
	private int elitSize = 10;
	private int numOfElemets = 300;
	private int mutationChance = 10;
	private int mutateSize = 20;
	private long startTime;
	private BufferedImage mainImage, editImage;
	private Elemet[] population;
	private Elemet[] elit;
	public static int weight, height;
	
	public Window(int resx, int resy, int locx, int locy, String title) {
		super(resx, resy, locx, locy, title);
		BasicWindow.antialiasing = false;
		BasicWindow.clearLastFrame = true;
		BasicWindow.lossyScale = false;
		BasicWindow.showFPS = true;
		BasicWindow.FPSrate = 1000;
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void initializeData()
	{
		cm = new ControlsManager();
		texManager = new textureManager();
		mainPhoto = new StaticObject(0, 50, "mainPhoto", "images.png", texManager);
		bestVersion = new StaticObject(mainPhoto.width+2, 50, texManager.getTexture(0).texture.getWidth(), texManager.getTexture(0).texture.getHeight(),  "bestPhoto", texManager);
		actualVersion = new StaticObject(mainPhoto.width*2+4, 50, texManager.getTexture(0).texture.getWidth(), texManager.getTexture(0).texture.getHeight(),  "actualPhoto", texManager);
		mainImage = texManager.getTexture(0).texture;
		editImage = texManager.getTexture(1).texture;
		System.out.println("Porównanie pocz¹tkowe lekkie "+BitmapTool.compareBitmaps(mainImage, editImage)+"% Porównywanie pocz¹tkowe pe³ne "+BitmapTool.fullCompareBitmaps(mainImage, editImage)+"%");
		weight = mainImage.getWidth();
		height = mainImage.getHeight();
		startPopulation();
		startTime = System.currentTimeMillis();
		
	}
	
	
	public void startPopulation()
	{
		System.out.println("Rozpoczynanie populacji o rozmiarze "+populationSize);
		population = new Elemet[populationSize];
		elit = new Elemet[elitSize];
		Random r = new Random();
		population[0] = new Elemet(r, numOfElemets, mainImage.getWidth(), mainImage.getHeight(), 20);
		for(int i = 1; i < populationSize; i++)
		{
			population[i] = new Elemet(r, population[0], population[0], mutationChance, mutateSize);
		}
	}
	
	public void definePopulation()
	{
		//System.out.println("Ustanawianie podobieñstwa elementów");
		for(int i = 0; i < populationSize-1; i++)
		{
			population[i].checkValue(mainImage);
			lastTestValue = population[i].value;
		}
		population[populationSize-1].checkValueAndSetBitmap(mainImage, texManager, 2);
	}
	
	public void defineElit()
	{
		int[] index = new int[populationSize];
		for(int i = 0; i < populationSize; i++)
			index[i] = i;
		
		for(int i = 0; i < populationSize; i++)
		{
			for(int j = 0; j < populationSize-1; j++)
			{
				if(population[index[j]].value<population[index[j+1]].value)
				{
					int k = index[j];
					index[j] = index[j+1];
					index[j+1] = k;
				}
			}
		}
		actualGeneration++;
//		for(int i = 0; i < populationSize; i++)
//		{
//			System.out.print(population[index[i]].value+" ");
//		}
//		System.out.println();
		//System.out.println("Najlepsza wartoœæ "+population[index[0]].value);
		if(population[index[0]].value>lastValue)
		{
			lastGeneration = actualGeneration;
			//System.out.println("Ustanawianie elity");
		for(int i = 0; i < elitSize; i++)
		{
			elit[i] = population[index[i]];
		}
		
		elit[0].setBitmap(mainImage, texManager, 1);
		lastValue = elit[0].value;
		}
		//System.out.println("OK1");
		population = new Elemet[populationSize];
		Random r = new Random();
		int e1Ind = 0;
		int e2Ind = 1;
		population[0] = new Elemet(r, elit[0], elit[0], mutationChance, mutateSize);
		population[1] = new Elemet(r, elit[0], elit[0], mutationChance, mutateSize);
		population[2] = new Elemet(r, elit[0], elit[0], mutationChance, mutateSize);
		population[3] = new Elemet(r, elit[0], elit[0], mutationChance, mutateSize);
		population[4] = new Elemet(r, elit[0], elit[0], mutationChance, mutateSize);
		for(int i = 5; i < populationSize; i++)
		{
			population[i] = new Elemet(r, elit[e1Ind], elit[e2Ind], mutationChance, mutateSize);
			e1Ind++;
			e2Ind++;
			if(e1Ind>=elitSize)
				e1Ind = 0;
			if(e2Ind>=elitSize)
				e2Ind = 0;
		}
		//System.out.println("Zakoñczono");
	}
	public void refreshFrame()
	{
		super.refresh(texManager);
	}
	
	
	
	@Override
	public void useControls(Graphics2D g, int delta) 
	{
		super.useControls(g, delta);

		definePopulation();
		defineElit();
		cm.reset();
	}
	@Override
	public void drawFrame(Graphics2D g, int delta, textureManager texManager) 
	{
		super.drawFrame(g,delta,texManager);
		mainPhoto.drawObject(g, texManager);
		bestVersion.drawObject(g, texManager);
		actualVersion.drawObject(g, texManager);
		g.setColor(Color.black);
		g.drawString(""+((float)((int)(lastValue*10000))/100)+"% gen: "+lastGeneration, 400, 600);
		g.drawString(""+((float)((int)(lastTestValue*10000))/100)+"% gen: "+actualGeneration, 800, 600);
		g.drawString(""+((float)(System.currentTimeMillis()-startTime)/1000)+"s ", 200, 600);
	}
	
	@Override
	public void checkCollisions(Graphics2D g, int delta) 
	{
		super.checkCollisions(g, delta);
		

	}
	public void clearUp() 
	{
		
		
	}


	public void keyPressed(KeyEvent e) {
		cm.keyPressed(e);
	}
	public void keyReleased(KeyEvent e) {
		cm.keyReleased(e);
	}
	public void keyTyped(KeyEvent e) {
		cm.keyTyped(e);
	}
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		cm.mouseWheelMoved(arg0);
	}
	public void mouseDragged(MouseEvent arg0) {
		cm.mouseDragged(arg0);
	}
	public void mouseMoved(MouseEvent arg0) {
		cm.mouseMoved(arg0);
	}
	public void mouseClicked(MouseEvent arg0) {
		cm.mouseClicked(arg0);
	}
	public void mouseEntered(MouseEvent arg0) {
		cm.mouseEntered(arg0);
	}
	public void mouseExited(MouseEvent arg0) {
		cm.mouseExited(arg0);
	}
	public void mousePressed(MouseEvent arg0) {
		cm.mousePressed(arg0);
	}
	public void mouseReleased(MouseEvent arg0) {
		cm.mouseReleased(arg0);
	}
}
