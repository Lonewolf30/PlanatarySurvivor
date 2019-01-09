package com.lonewolf.PS.Engine.Render.RenderHelper;

import com.lonewolf.PS.Engine.Render.Texture;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TextRender
{
    private String text;
    private boolean textChanges;
    private BufferedImage image;

    public TextRender(String text, boolean textChanges)
    {
        this.text = text;
        this.textChanges = textChanges;
        generateBufferedImage();
    }

    public void setText(String text)
    {
        this.text = text;
    }

    private void generateBufferedImage()
    {
        //First, we have to calculate the string's width and height

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();

        //Set the font to be used when drawing the string
        Font f = new Font("Tahoma", Font.PLAIN, 48);
        g.setFont(f);

        //Get the string visual bounds
        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        Rectangle2D rect = f.getStringBounds(text, frc);
        //Release resources
        g.dispose();

        //Then, we have to draw the string on the final image

        //Create a new image where to print the character
        img = new BufferedImage((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()), BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setColor(Color.black); //Otherwise the text would be white
        g.setFont(f);

        //Calculate x and y for that string
        FontMetrics fm = g.getFontMetrics();
        int x = 0;
        int y = fm.getAscent(); //getAscent() = baseline
        g.drawString(text, x, y);

        //Release resources
        g.dispose();

        //Return the image
        image = img;
    }

    public Texture getTextTexture()
    {
        if (textChanges)
            generateBufferedImage();
        return new Texture(image);
    }
}
