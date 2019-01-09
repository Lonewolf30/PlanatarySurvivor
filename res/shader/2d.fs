#version 150

in vec2 textCoords;

uniform sampler2D img;

out vec4 out_Color;

void main(void)
{
    out_Color = texture(img, textCoords);
}