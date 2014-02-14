#version 330

in vec2 texCoord0;

uniform sampler2D sampler; //where to read texture data from

void main() 
{
	gl_FragColor = texture2D(sampler, texCoord0.xy);
}
