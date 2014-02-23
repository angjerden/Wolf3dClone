#version 330

const int MAX_POINT_LIGHTS = 4; //number of lights affecting a single pixel

in vec2 texCoord0;
in vec3 normal0; //up direction of a vertex
in vec3 worldPos0; //the position of the pixel

out vec4 fragColor;

struct BaseLight //basic components of light
{
    vec3 color;
    float intensity;
};

struct DirectionalLight
{
    BaseLight base;
    vec3 direction;
};

struct Attenuation //how quickly the point light fades out
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    BaseLight base;
    Attenuation atten;
    vec3 position;
};

uniform vec3 baseColor; //a basic color which is subject to change from lighting
uniform vec3 eyePos; //where our camera is
uniform vec3 ambientLight; //a constant light amount applied to everything
uniform sampler2D sampler; //where to read texture data from

uniform float specularIntensity;
uniform float specularPower;

uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];

//normal - the up direction
vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
    float diffuseFactor = dot(normal, -direction);
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    vec4 specularColor = vec4(0, 0, 0, 0);

    if(diffuseFactor > 0) //is it affecting the surface at all
    {
        //BRDF * intensity * attenuation
        diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;

        //Calculations for specular reflection
        vec3 directionToEye = normalize(eyePos - worldPos0);
        //the reflect-function calculates the reflection direction
        //based on an incoming direction and the up direction of the vertex
        vec3 reflectDirection = normalize(reflect(direction, normal));

        float specularFactor = dot(directionToEye, reflectDirection);
        specularFactor = pow(specularFactor, specularPower);

        if(specularFactor > 0)
        {
            specularColor = vec4(base.color, 1.0) * specularIntensity * specularFactor;
        }
    }

    return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal)
{
    return calcLight(directionalLight.base, -directionalLight.direction, normal);
}

vec4 calcPointLight(PointLight pointLight, vec3 normal)
{
    vec3 lightDirection = worldPos0 - pointLight.position; //which direction the light is in
    float distanceToPoint = length(lightDirection);
    lightDirection = normalize(lightDirection);

    vec4 color = calcLight(pointLight.base, lightDirection, normal);

    float attenuation = pointLight.atten.constant +
                        pointLight.atten.linear * distanceToPoint +
                        pointLight.atten.exponent * distanceToPoint * distanceToPoint +
                        0.0001;    //adding a small number to avoid division by zero

    return color / attenuation;
}

void main()
{
    vec4 totalLight = vec4(ambientLight, 1);
    vec4 color = vec4(baseColor, 1);
    vec4 textureColor = texture(sampler, texCoord0.xy);

    if(textureColor != vec4(0, 0, 0, 0))
    {
        color *= textureColor;
    }

    vec3 normal = normalize(normal0);

    totalLight += calcDirectionalLight(directionalLight, normal);

    for(int i = 0; i < MAX_POINT_LIGHTS; i++)
    {
        totalLight += calcPointLight(pointLights[i], normal);
    }

    fragColor = color * totalLight;

}
