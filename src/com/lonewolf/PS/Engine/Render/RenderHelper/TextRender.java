package com.lonewolf.PS.Engine.Render.RenderHelper;

import com.lonewolf.PS.Engine.Render.Texture;
import com.lonewolf.PS.Reference;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class TextRender
{
    private String text;
    private boolean textChanges;
    private BufferedImage image;

    public TextRender(String text, boolean textChanges)
    {
        this.text = " "+text;
        this.textChanges = textChanges;
        generateBufferedImage();
    }

    public void setText(String text)
    {
        this.text = text;
    }

    private void generateBufferedImage()
    {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();

        Font f = new Font(Reference.configs.getValue("font"), Font.PLAIN, 64);
        g.setFont(f);

        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        Rectangle2D rect = f.getStringBounds(text, frc);

        g.dispose();

        img = new BufferedImage((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()), BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setFont(f);

        FontMetrics fm = g.getFontMetrics();
        int x = 0;
        int y = fm.getAscent();
        g.drawString(text, x, y);

        g.dispose();

        image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        for (int i = 1; i < img.getWidth(); i++)
        {
            for (int j = 1; j < img.getHeight(); j++)
            {
                image.setRGB(i, j , img.getRGB(i, img.getHeight()-j));
            }
        }

    }

    public Texture getTextTexture()
    {
        if (textChanges)
            generateBufferedImage();
        return new Texture(image);
    }
}
