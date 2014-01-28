#version 330

//retrieving vertex data from vertexattribarray 0
//in = input
//datatype vec3 (vector3)
//position is the variable name, will contain every individual vertex
layout (location = 0) in vec3 position;

out vec4 color; //outputting

uniform mat4 transform;

void main() 
{
    //clamp - constrain a value to lie between two further values

    color = vec4(clamp(position, 0.0, 1.0), 1.0);
	gl_Position = transform * vec4(position, 1.0); //setting the fourth component to 1.0
}
