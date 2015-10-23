//combined projection and view matrix
uniform mat4 u_projView;
uniform mat4 u_xform;

//"in" attributes from our SpriteBatch
attribute vec3 Position;
attribute vec2 TexCoord;
attribute vec4 Color;

//"out" varyings to our fragment shader
varying vec4 vColor;
varying vec2 vTexCoord;
 
void main() {
	vColor = Color;
	vTexCoord = TexCoord;
	gl_Position = u_projView * u_xform * vec4(Position, 1.0);
}