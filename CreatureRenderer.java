/******************************************************************************
 * Creature Runtimes License
 *
 * Copyright (c) 2015, Kestrel Moon Studios
 * All rights reserved.
 *
 * Preamble: This Agreement governs the relationship between Licensee and Kestrel Moon Studios(Hereinafter: Licensor).
 * This Agreement sets the terms, rights, restrictions and obligations on using [Creature Runtimes] (hereinafter: The Software) created and owned by Licensor,
 * as detailed herein:
 * License Grant: Licensor hereby grants Licensee a Sublicensable, Non-assignable & non-transferable, Commercial, Royalty free,
 * Including the rights to create but not distribute derivative works, Non-exclusive license, all with accordance with the terms set forth and
 * other legal restrictions set forth in 3rd party software used while running Software.
 * Limited: Licensee may use Software for the purpose of:
 * Running Software on Licensee’s Website[s] and Server[s];
 * Allowing 3rd Parties to run Software on Licensee’s Website[s] and Server[s];
 * Publishing Software’s output to Licensee and 3rd Parties;
 * Distribute verbatim copies of Software’s output (including compiled binaries);
 * Modify Software to suit Licensee’s needs and specifications.
 * Binary Restricted: Licensee may sublicense Software as a part of a larger work containing more than Software,
 * distributed solely in Object or Binary form under a personal, non-sublicensable, limited license. Such redistribution shall be limited to unlimited codebases.
 * Non Assignable & Non-Transferable: Licensee may not assign or transfer his rights and duties under this license.
 * Commercial, Royalty Free: Licensee may use Software for any purpose, including paid-services, without any royalties
 * Including the Right to Create Derivative Works: Licensee may create derivative works based on Software,
 * including amending Software’s source code, modifying it, integrating it into a larger work or removing portions of Software,
 * as long as no distribution of the derivative works is made
 *
 * THE RUNTIMES IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE RUNTIMES OR THE USE OR OTHER DEALINGS IN THE
 * RUNTIMES.
 *****************************************************************************/

package MeshBoneUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.Vector;


/**
 * Created by jychong on 9/20/14.
 */
public class CreatureRenderer {
    private OrthographicCamera camera;
    private Texture creature_texture;
    private CreatureManager creature_manager;
    private ShaderProgram shader_program;
    private Mesh render_mesh;
    private ShapeRenderer sr;
    private float[] vertices;
    private Matrix4 xform;

    public CreatureRenderer(CreatureManager manager_in,
                            Texture texture_in,
                            ShaderProgram shader_program_in,
                            OrthographicCamera camera_in)
    {
        creature_manager = manager_in;
        creature_texture = texture_in;
        shader_program = shader_program_in;
        camera = camera_in;
        sr = new ShapeRenderer();
        xform = new Matrix4();

        InitRender();
    }

    public void InitRender()
    {
        render_mesh = new Mesh(false,
                               creature_manager.target_creature.total_num_pts,
                               creature_manager.target_creature.total_num_indices,
                               new VertexAttribute(VertexAttributes.Usage.Position, 3, "Position"),
                               new VertexAttribute(VertexAttributes.Usage.Color, 4, "Color"),
                               new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "TexCoord")
                );

        Creature target_creature = creature_manager.target_creature;
        short[] set_indices = new short[creature_manager.target_creature.total_num_indices];
        for(int i = 0; i < set_indices.length; i++)
        {
            set_indices[i] = target_creature.global_indices.get(i).shortValue();
        }

        render_mesh.setIndices(set_indices);

        vertices = new float[target_creature.total_num_pts * (3 + 4 + 2)];
    }

    // Sets the transformation matrix for this creature
    public void SetXform(Matrix4 mat_in)
    {
        xform = mat_in;
    }

    private void UpdateData()
    {
        int vert_offset = 3 + 4 + 2;
        int pt_index = 0, uv_index = 0, color_index = 0;
        Creature target_creature = creature_manager.target_creature;
        Vector<Float> read_pts = target_creature.render_pts;
        Vector<Float> read_uvs = target_creature.global_uvs;
        Vector<Float> read_colours = target_creature.render_colours;


        for(int i = 0; i < vertices.length; i += vert_offset)
        {
            vertices[i + 0] = read_pts.get(pt_index + 0);
            vertices[i + 1] = read_pts.get(pt_index + 1);
            vertices[i + 2] = read_pts.get(pt_index + 2);

            vertices[i + 3] = read_colours.get(color_index + 0);
            vertices[i + 4] = read_colours.get(color_index + 1);
            vertices[i + 5] = read_colours.get(color_index + 2);
            vertices[i + 6] = read_colours.get(color_index + 3);

            vertices[i + 7] = read_uvs.get(uv_index + 0);
            vertices[i + 8] = read_uvs.get(uv_index + 1);

            pt_index += 3;
            uv_index += 2;
            color_index += 4;
        }

        render_mesh.setVertices(vertices);
    }

    public void Flush()
    {
        UpdateData();

        creature_texture.bind();
        //no need for depth...
        Gdx.gl.glDepthMask(false);

        //enable blending, for alpha
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shader_program.begin();

        shader_program.setUniformMatrix("u_projView", camera.combined);
        shader_program.setUniformMatrix("u_xform", xform);
        shader_program.setUniformi("u_texture", 0);

        render_mesh.render(shader_program, GL20.GL_TRIANGLES);

        shader_program.end();

        //re-enable depth to reset states to their default
        Gdx.gl.glDepthMask(true);
    }

    public void debugDraw()
    {
        // For debugging
        sr.setColor(Color.WHITE);
        sr.setProjectionMatrix(camera.combined);

        sr.begin(ShapeRenderer.ShapeType.Line);
        debugDrawBone(creature_manager.target_creature.render_composition.getRootBone());
        sr.end();
    }

    public void debugDrawBone(MeshBone bone_in)
    {
        sr.line(bone_in.getWorldStartPt().x, bone_in.getWorldStartPt().y, bone_in.getWorldEndPt().x, bone_in.getWorldEndPt().y);
        for(MeshBone cur_child : bone_in.getChildren())
        {
            debugDrawBone(cur_child);
        }
    }

}
