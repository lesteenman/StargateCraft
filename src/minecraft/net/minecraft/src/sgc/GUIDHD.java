package net.minecraft.src.sgc;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.GuiButton;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.mod_SGC;

public class GUIDHD extends net.minecraft.src.GuiScreen{

	private TileEntityDHD tileEntity;
	
	private GuiButton dialScreenButton;
	private GuiButton worldListButton;
	private int page;
	
	private int dhdRadius;
	private int dhdCX;
	private int dhdCY;
	
	//The amount of degrees one button is on the circle;
	private static double dhdButtonDegrees = 18.9;
	private static double dhdCenterRadPerc = 0.26;
	private static double dhdFirstInnerRadPerc = 0.36;
	private static double dhdFirstOuterRadPerc = 0.64;
	private static double dhdSecondInnerRadPerc = 0.66;
	private static double dhdSecondOuterRadPerc = 0.92;
	
	/**
	 * Won't be implemented for now, merely created to save this link:
	 * http://www.youtube.com/watch?v=NnF6TyHjB1o
	 */
	
	public GUIDHD(TileEntityDHD tileEntity) {
		super();
		this.tileEntity = tileEntity;
	}
	
	@Override
	public void initGui()
	{
		page = 1;
		
		dialScreenButton = new GuiButton(1, 15, 10, width / 2 - 30, 20, "Dial");
		worldListButton = new GuiButton(2, width/2 + 15, 10, width / 2 - 30, 20, "List");
		
		controlList.add(dialScreenButton);
		controlList.add(worldListButton);
	}
	
	public static void drawFullCircle(int cx, int cy, double radius, float r, float g, float b, float a) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(r, g, b, a);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (int i = 0; i <= 360; i++) {
            double x = Math.sin((i * 3.141526D / 180)) * radius;
            double y = Math.cos((i * 3.141526D / 180)) * radius;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(3553 /* GL_TEXTURE_2D */);
        GL11.glDisable(3042 /* GL_BLEND */);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, width, height
     */
    public void drawImage(int x, int y, int w, int h)
    {
        float f = 1;//0.0390625F;
        float f1 = 1;//0.0390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + h, zLevel, 0, h);
        tessellator.addVertexWithUV(x + w, y + h, zLevel, w, h);
        tessellator.addVertexWithUV(x + w, y + 0, zLevel, w, 0);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = 1;//0.0390625F;
        float f1 = 1;//0.0390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (float)(par3 + 0) * f, (float)(par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (float)(par3 + par5) * f, (float)(par4 + par6) * f1);
        tessellator.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (float)(par3 + par5) * f, (float)(par4 + 0) * f1);
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (float)(par3 + 0) * f, (float)(par4 + 0) * f1);
        tessellator.draw();
    }
    
    /**
     * Draws the background (i is always 0 as of 1.2.2)
     */
    public void drawDialGui() {
    	dhdCX = width/2;
    	dhdCY = height/2 + 20;
    	
    	//Full background circle
    	dhdRadius = Math.min(width - 20, height - 50) / 2;
    	drawFullCircle(dhdCX, dhdCY, dhdRadius, 121/255f, 193/255f, 251/255f, 0.8f);
    	
    	//Center button circle
    	double centerButtonRadius = dhdRadius * (dhdCenterRadPerc);
    	drawFullCircle(dhdCX, dhdCY, centerButtonRadius, 1, 62/255f, 62/255f, 1);

    	GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("sgc/images/DHDOuterButton.png"));
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glScaled(0.3, 0.3, 0.3);
        GL11.glTranslated(150, 150, 0);
        //drawTexturedModalRect(50, 50, 0, 0, 73, 73);
        drawImage(50, 50, 75, 73);
        GL11.glPopMatrix();
    }
    
    public void drawListGui() {
    	drawRect(10, 40, width - 20, height - 50, 0x80000000);
    }
	
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
    	if (page == 1)
    		drawDialGui();
    	else
    		drawListGui();
        super.drawScreen(par1, par2, par3);
    }
    
    /**
     * @require 0 < x&y < 1
     * @param x point on the circle
     * @param y point on the circle 
     * @return integer for the button: 
     * 		   counting from the top button, counterwise, inner circle first.
     * @return 0 if it is the center button
     * @return -1 if it was not a button
     */
    private int buttonForCoordinates(double x, double y) {
    	double xfm = Math.abs(0.5 - x);//x as seen from mid, in percentage
    	double yfm = Math.abs(0.5 - y);//y as seen from mid, in percentage
    	double dfm = Math.sqrt(xfm * xfm + yfm * yfm);//distance from mid

		double deg = ( Math.atan2(y-0.5,x-0.5) * 180/Math.PI + 360 ) % 360 + 90 + dhdButtonDegrees / 2; //Degrees on the circle it clicked on
		if (deg > 360)
			deg -= 360;
		
    	if (dfm < dhdCenterRadPerc / 2) {
    		return 0;
    	} else if (dfm < dhdFirstOuterRadPerc / 2 && dfm > dhdFirstInnerRadPerc / 2) {
    		int button = (int) (deg/dhdButtonDegrees);
    		return 1 + button;
    	} else if (dfm < dhdSecondOuterRadPerc / 2 && dfm > dhdSecondInnerRadPerc / 2) {
    		int button = (int) (deg/dhdButtonDegrees);
    		return 20 + button;
    	}
    	return -1;
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
    	page = guibutton.id;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int x, int y, int par3)
    {
    	super.mouseClicked(x, y, par3);
    	if (page == 1) 
    	{
    		if (par3 == 0)
            {
    			double corX = ((double)x - ((double)dhdCX - dhdRadius)) / (dhdRadius * 2.0);
    			double corY = ((double)y - ((double)dhdCY - dhdRadius)) / (dhdRadius * 2.0);
    	    	double xfm = Math.abs(0.5 - corX);//x as seen from mid, in percentage
    	    	double yfm = Math.abs(0.5 - corY);//y as seen from mid, in percentage
    			double dfm = Math.sqrt(xfm * xfm + yfm * yfm);
    			
            	if (dfm < 0.5) {
            		//Clicked inside the circle, tested
            		int button = buttonForCoordinates(corX , corY);
           		
            		if (button == 0)
            			this.dial();
            		else if (button >= 1)
            			this.pressButton(button);
            	}
            }
    	}
    }
    
    /**
     * Called when the dial button is selected. Should check if a valid address is dialed
     * and if so, tell the entity to dial.
     */
    public void dial() {
    	if (tileEntity.dial()) {
    		System.out.println("Dialed");
    	}
    }
    
    public void pressButton(int button) {
    	if (tileEntity.buttonPressed(button)) {
    		System.out.println("Button could be pressed, highlighting");
    	} else {
    		System.out.println("Button could not be pressed, not highlighting");
    	}
    }

    /**
     * Called when the mouse is moved or a mouse button is released.  Signature: (mouseX, mouseY, which) which==-1 is
     * mouseMove, which==0 or which==1 is mouseUp
     */
   /* protected void mouseMovedOrUp(int mouseX, int mouseY, int status)
    {
    	if (status == -1) {
    		//Mouse moved
    		
    	} else if (status > -1) {
    		//Mouse released 
    		
    	}
    }*/
    
    public boolean doesGuiPauseGame() 
    {
    	return false;
    }
	
}
