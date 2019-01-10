#version 150

in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;

out vec2 pass_textureCoordinates;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCamera;

uniform mat4 viewMatrix;
uniform mat4 location;
uniform mat4 transform;
uniform vec3 lightPos;
uniform vec3 cameraPos;

void main(void)
{
	gl_Position = viewMatrix * vec4(position,1.0);
	pass_textureCoordinates = textureCoordinates;

	surfaceNormal = (transform * vec4(normal,0.0)).xyz;
	toLightVector = lightPos - (location * vec4(position,1.0)).xyz;

	toCamera = cameraPos - (location * vec4(position,1.0)).xyz;
}