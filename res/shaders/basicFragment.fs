#version 330

in vec4 color;

out vec4 fragColor;

void main() 
{
	fragColor = color; //vec4(0.0, 1.0, 1.0, 1.0); //cyan
}
