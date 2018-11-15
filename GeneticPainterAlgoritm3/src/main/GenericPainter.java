package main;

import windows.Window;

public class GenericPainter {

	public static void main(String args[])
	{
		Window bs = new Window(1280, 720, 100, 100, "Generic");
		bs.initializeData();
		while(bs.isVisible())
		{
			bs.refreshFrame();
		}
		bs.clearUp();
	}
}
