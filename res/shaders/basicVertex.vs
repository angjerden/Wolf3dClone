#version 330

//retrieving vertex data from vertexattribarray 0 (location = 0)
//in = input
//datatype vec3 (vector3)
//position is the variable name, will contain every individual vertex
layout (location = 0) in vec3 position;

layout (location = 1) in vec2 texCoord;

out vec2 texCoord0;

uniform mat4 transform; //should contain all our translation, rotation and scale

void main() 
{
    //clamp - constrain a value to lie between two further values

    //color = vec4(clamp(position, 0.0, 1.0), 1.0);
	gl_Position = transform * vec4(position, 1.0); //setting the fourth component to 1.0
    texCoord0 = texCoord;
}
