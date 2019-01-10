#version 150

in vec2 pos;

uniform vec2 scale;
uniform vec2 offset;

out vec2 textCoords;

void main(void)
{
    gl_Position = vec4(offset + (pos *scale), 0 ,1.0);
    textCoords = pos;
}