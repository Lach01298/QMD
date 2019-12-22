package lach_01298.qmd.particle;

public enum ColourCharge
{
	WHITE("white"),
	RED("red"),
	GREEN("green"),
	BLUE("blue"),
	ANTIRED("antired"),
	ANTIGREEN("antigreen"),
	ANTIBLUE("antiblue"),
	UNSPECIFIED("unspecified");
	
	private String name;
	
	private ColourCharge(String name)
	{
		this.name = name;
	
	}
	
	public ColourCharge getAntiColour()
	{
		switch (this)
		{
		case RED:
			return ANTIRED;
		case GREEN:
			return ANTIGREEN;
		case BLUE:
			return ANTIBLUE;
		case ANTIRED:
			return RED;
		case ANTIGREEN:
			return GREEN;
		case ANTIBLUE:
			return BLUE;
		default:
			return WHITE;
		}
	}
	
	public boolean isColourNeutral()
	{
		if(this == WHITE)
		{
			return true;
		}
		return false;
	}
	
	
	public static boolean isColourNeutral(ColourCharge a, ColourCharge b)
	{
		if(a==b.getAntiColour())
		{
			return true;
		}
		return false;
	}
	
	public static boolean isColourNeutral(ColourCharge a, ColourCharge b, ColourCharge c)
	{
		if((a == RED && b == GREEN && c == BLUE)||
				(a == BLUE && b == RED && c == GREEN)||
				(a == GREEN && b == BLUE && c == RED)||
				(a == ANTIRED && b == ANTIGREEN && c == ANTIBLUE||
				(a == ANTIBLUE && b == ANTIRED && c == ANTIGREEN)||
				(a == ANTIGREEN && b == ANTIBLUE && c == ANTIRED)))
		{
			return true;
		}
		return false;
	}
	
	
	
}
