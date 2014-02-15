#version 330

in vec2 texCoord0;

out vec4 fragColor;

uniform vec3 baseColor; //a basic color which is subject to change from lighting
uniform vec3 ambientLight; //a constant light amount applied to everything
uniform sampler2D sampler; //where to read texture data from

void main()
{
    vec4 totalLight = vec4(ambientLight, 1);
    vec4 color = vec4(baseColor, 1);
    vec4 textureColor = texture(sampler, texCoord0.xy);

    if(textureColor != vec4(0, 0, 0, 0))
    {
        color *= textureColor;
    }

    fragColor = color * totalLight;

}
