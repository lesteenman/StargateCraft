package net.minecraft.src.sgc;

import java.util.ArrayList;

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
	
	//Image map
	private int[] glyphTextures;
	ArrayList<Integer> selectedGlyphs;
	
	/**
	 * Won't be implemented for now, merely created to save this link:
	 * http://www.youtube.com/watch?v=NnF6TyHjB1o
	 */
	
	public GUIDHD(TileEntityDHD tileEntity) {
		super();
		this.tileEntity = tileEntity;
		selectedGlyphs = new ArrayList<Integer>();
	}
	
	private void loadGlyphTextures() {
		if (mc == null)
			System.out.println("mc was null!");
		if (mc.renderEngine == null)
			System.out.println("renderEngine was null!");
		System.out.println("First texture would be " + mc.renderEngine.getTexture("sgc/images/Glyph_01.png"));
		glyphTextures = new int[]{
				mc.renderEngine.getTexture("sgc/images/Glyph_01.png"), mc.renderEngine.getTexture("sgc/images/Glyph_02.png"), 
				mc.renderEngine.getTexture("sgc/images/Glyph_03.png"), mc.renderEngine.getTexture("sgc/images/Glyph_04.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_05.png"), mc.renderEngine.getTexture("sgc/images/Glyph_06.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_07.png"), mc.renderEngine.getTexture("sgc/images/Glyph_08.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_09.png"), mc.renderEngine.getTexture("sgc/images/Glyph_10.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_11.png"), mc.renderEngine.getTexture("sgc/images/Glyph_12.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_13.png"), mc.renderEngine.getTexture("sgc/images/Glyph_14.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_15.png"), mc.renderEngine.getTexture("sgc/images/Glyph_16.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_17.png"), mc.renderEngine.getTexture("sgc/images/Glyph_18.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_19.png"), mc.renderEngine.getTexture("sgc/images/Glyph_20.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_21.png"), mc.renderEngine.getTexture("sgc/images/Glyph_22.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_23.png"), mc.renderEngine.getTexture("sgc/images/Glyph_24.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_25.png"), mc.renderEngine.getTexture("sgc/images/Glyph_26.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_27.png"), mc.renderEngine.getTexture("sgc/images/Glyph_28.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_29.png"), mc.renderEngine.getTexture("sgc/images/Glyph_30.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_31.png"), mc.renderEngine.getTexture("sgc/images/Glyph_32.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_33.png"), mc.renderEngine.getTexture("sgc/images/Glyph_34.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_35.png"), mc.renderEngine.getTexture("sgc/images/Glyph_36.png"),
				mc.renderEngine.getTexture("sgc/images/Glyph_37.png"), mc.renderEngine.getTexture("sgc/images/Glyph_38.png"),
		};
	}
	
	@Override
	public void initGui()
	{
		page = 1;
		
		dialScreenButton = new GuiButton(1, 15, 10, width / 2 - 30, 20, "Dial");
		worldListButton = new GuiButton(2, width/2 + 15, 10, width / 2 - 30, 20, "List");
		
		controlList.add(dialScreenButton);
		controlList.add(worldListButton);

		this.loadGlyphTextures();
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
        tessellator.addVertexWithUV(x + 0, y + h, zLevel, 0, 1);
        tessellator.addVertexWithUV(x + w, y + h, zLevel, 1, 1);
        tessellator.addVertexWithUV(x + w, y + 0, zLevel, 1, 0);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
    }
    
    private static int buttonsPerCircle = 18;
    private static double buttonGrowthPerc = 1.08;
    private static double outerGlyphScale = 0.8;
    private static double innerGlyphScale = 0.5;
    
    /**
     * Draws the background (i is always 0 as of 1.2.2)
     */
    public void drawDialGui() {
    	dhdCX = width/2;
    	dhdCY = height/2 + 20;
    	
    	//Full background circle
    	dhdRadius = Math.min(width - 20, height - 50) / 2;
    	drawFullCircle(dhdCX, dhdCY, dhdRadius * 1.02, 185/255f, 185/255f, 185/255f, 1f);
    	drawFullCircle(dhdCX, dhdCY, dhdRadius, 135/255f, 135/255f, 135/255f, 0.8f);
    	
    	//Center button circle
    	double centerButtonRadius = dhdRadius * (dhdCenterRadPerc);
    	drawFullCircle(dhdCX, dhdCY, centerButtonRadius * 1.05, 185/255f, 185/255f, 185/255f, 1);
    	drawFullCircle(dhdCX, dhdCY, centerButtonRadius, 1f, 130/255f, 0f, 1);

    	//Draw outer buttons
    	int imgH = (int) (dhdRadius * buttonGrowthPerc * (dhdSecondOuterRadPerc - dhdSecondInnerRadPerc));
    	int imgW = (int) (imgH * 1.03);
    	int imgX = -imgW / 2;
    	int imgY = -(int) (dhdSecondOuterRadPerc * dhdRadius);
    	
        GL11.glPushMatrix();
        GL11.glColor3d(1.0, 1.0, 1.0);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("sgc/images/DHDOuterButton.png"));
        GL11.glDisable(GL11.GL_LIGHTING);
        
        GL11.glTranslated(dhdCX, dhdCY, 0); // Set the origin to be in the center
        
        GL11.glRotatef(-360/19, 0, 0, 1);
        for (int i = 0; i <= buttonsPerCircle + 1; i++) {
        	GL11.glRotatef(360/19, 0, 0, 1);
        	
        	drawImage(imgX, imgY, imgW, imgH);
        }
        
        //Draw the outer glyphs
    	imgH = (int) (dhdRadius * outerGlyphScale * (dhdSecondOuterRadPerc - dhdSecondInnerRadPerc));
    	imgW = (int) (imgH * 1.03);
    	imgX = -imgW / 2;
    	imgY = -(int) (dhdSecondOuterRadPerc * dhdRadius);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        GL11.glRotatef(360/19, 0, 0, 1);
        for (int i = 0; i <= buttonsPerCircle + 1; i++) {
        	GL11.glRotatef(360/19, 0, 0, 1);
        	
        	if (selectedGlyphs.contains(19 + i))
                GL11.glColor4f(1.0F, 0.0F, 1.0F, 1.0F);
        	else
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, glyphTextures[17 + i]);
        	drawImage(imgX, imgY, imgW, imgH);
        }
        
        GL11.glPopMatrix();

        //Draw inner buttons
    	imgH = (int) (dhdRadius * buttonGrowthPerc * (dhdFirstOuterRadPerc - dhdFirstInnerRadPerc));
    	imgW = (int) (imgH * 0.612); //Real image width/height
    	imgX = -imgW / 2;
    	imgY = -(int) (dhdFirstOuterRadPerc * dhdRadius);
    	
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("sgc/images/DHDInnerButton.png"));
        GL11.glDisable(GL11.GL_LIGHTING);
        
        GL11.glTranslated(dhdCX, dhdCY, 0); // Set the origin to be in the center
        
        GL11.glRotatef(-360/19, 0, 0, 1);
        for (int i = 0; i <= buttonsPerCircle + 1; i++) {
        	GL11.glRotatef(360/19, 0, 0, 1);
        	drawImage(imgX, imgY, imgW, imgH);
        }
        
        //Draw the inner glyphs
    	imgH = (int) (dhdRadius * innerGlyphScale * (dhdFirstOuterRadPerc - dhdFirstInnerRadPerc));
    	imgW = (int) (imgH * 1.03);
    	imgX = -imgW / 2;
    	imgY = (int) (-(dhdFirstOuterRadPerc * dhdRadius) + (dhdRadius * (dhdFirstOuterRadPerc - dhdFirstInnerRadPerc)) / 2 - imgH / 2);
        GL11.glRotatef(360/19, 0, 0, 1);
        for (int i = 0; i <= buttonsPerCircle + 1; i++) {
        	GL11.glRotatef(360/19, 0, 0, 1);

        	if (selectedGlyphs.contains(i + 1))
                GL11.glColor4f(1.0F, 0.0F, 1.0F, 1.0F);
        	else
        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, glyphTextures[i]);
        	drawImage(imgX, imgY, imgW, imgH);
        }
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
    		selectedGlyphs.clear();
    	}
    }
    
    public void pressButton(int button) {
    	if (tileEntity.buttonPressed(button)) {
    		System.out.println("Button could be pressed, highlighting");
    	System.out.println("Pressed button " + button);
    		selectedGlyphs.add(button);
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
