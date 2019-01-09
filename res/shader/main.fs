#version 150

in vec3 surfaceNormal;
in vec3 toLightVector;
in vec2 pass_textureCoordinates;
in vec3 toCamera;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform vec3 lightColor;
uniform float intencity;
uniform float minbrightness;
uniform float shineDamp;
uniform float refelct;

void main(void){

    float nDot1 = dot(normalize(surfaceNormal), normalize(toLightVector));
    float brightness = max(nDot1, minbrightness);

    vec3 reflectedLight = reflect(-normalize(toLightVector), normalize(surfaceNormal));
    float specularFactor = dot(reflectedLight, normalize(toCamera));
    float specular = pow(max(specularFactor, 0.0), shineDamp);

    vec3 finalSpec = specular * lightColor * refelct;

    vec3 diffuse = brightness * lightColor * intencity;

	out_Color = vec4(diffuse,1.0) * texture(modelTexture,pass_textureCoordinates) + vec4(finalSpec,1.0);

}