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

void main(void)
{

    vec3 normSurfaceNormal = normalize(surfaceNormal);
    vec3 normToCamera = normalize(toCamera);

    vec3 totalDiffuse = vec3(0);
    vec3 totalSpecular = vec3(0);

        float nDot1 = dot(normSurfaceNormal, normalize(toLightVector));
        float brightness = max(nDot1, 0);
        vec3 reflectedLight = reflect(-normalize(toLightVector), normalize(surfaceNormal));
        float specularFactor = dot(reflectedLight, normToCamera);
        float specular = pow(max(specularFactor, 0.0), shineDamp);

        vec3 finalSpec = specular * lightColor * refelct;
        vec3 diffuse = brightness * lightColor * intencity;

        totalDiffuse += diffuse;
        totalSpecular += finalSpec;

    totalDiffuse = max(totalDiffuse, minbrightness);

	out_Color = vec4(totalDiffuse,1.0) * texture(modelTexture,pass_textureCoordinates) + vec4(totalSpecular,1.0);

}