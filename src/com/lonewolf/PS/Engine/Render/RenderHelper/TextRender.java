package com.lonewolf.PS.Engine.Render.RenderHelper;

import com.lonewolf.PS.Engine.Render.Texture;

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
        BufferedImage image = new BufferedImage(text.length()*8,8,1);

        for(char c : text.toCharArray())
        {

        }
    }

    public Texture getTextTexture()
    {
        if (textChanges)
            generateBufferedImage();
        return new Texture(image);
    }
}
