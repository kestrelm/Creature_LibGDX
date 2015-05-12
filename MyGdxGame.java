package com.mygdx.game;

import MeshBoneUtil.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.HashMap;


public class MyGdxGame extends ApplicationAdapter {
    OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
    CreatureManager active_creature_manager, active_creature_manager_2;
    CreatureRenderer active_creature_render, active_creature_render_2;
	
	@Override
	public void create () {
        camera = new OrthographicCamera( (float)Gdx.graphics.getWidth() * 0.05f,
                                            (float)Gdx.graphics.getHeight() * 0.05f);
        batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        System.out.println("Loading json...");
        // Load json
        JsonValue json_data = CreatureModuleUtils.LoadCreatureJSONData("mageTest.json");

        System.out.println("Loading creature...");
        // Create creature
        Creature new_creature = new Creature(json_data);

        System.out.println("Loading animations...");
        // Create animations
        CreatureAnimation new_animation1 = new CreatureAnimation(json_data, "default");
        CreatureAnimation new_animation2 = new CreatureAnimation(json_data, "standing");

        System.out.println("Loading creature manager...");
        // Create manager and add in animations
        CreatureManager new_creature_manager = new CreatureManager(new_creature);
        new_creature_manager.AddAnimation(new_animation1);
        new_creature_manager.AddAnimation(new_animation2);

        // If you want to use a custom time range
        /*
        new_creature_manager.SetUseCustomTimeRane(true);
        new_creature_manager.SetCustomTimeRange(10, 20);
        */

        new_creature_manager.SetActiveAnimationName("default", false);
        new_creature_manager.SetIsPlaying(true);
        new_creature_manager.SetShouldLoop(true);

        active_creature_manager = new_creature_manager;

        // load shaders
        final String VERTEX = Gdx.files.internal("MeshBoneShader.vert").readString();
        final String FRAGMENT = Gdx.files.internal("MeshBoneShader.frag").readString();
        ShaderProgram program = new ShaderProgram(VERTEX, FRAGMENT);

        Texture new_texture = new Texture(Gdx.files.internal("character-mage.png"));

        // Create the creature render object
        active_creature_render = new CreatureRenderer(new_creature_manager,
                new_texture,
                program,
                camera);

        // Set a transformation matrix for the creature
        Matrix4 xform = new Matrix4();
        xform.scl(.5f);
        active_creature_render.SetXform(xform);





        // Instancing 2nd creature example
        Creature new_creature_2 = new Creature(json_data);
        active_creature_manager_2 = new CreatureManager(new_creature_2);
        active_creature_manager_2.AddAnimation(new_animation1);
        active_creature_manager_2.AddAnimation(new_animation2);
        active_creature_manager_2.SetActiveAnimationName("standing", false);
        active_creature_manager_2.SetIsPlaying(true);
        active_creature_manager_2.SetShouldLoop(true);
        active_creature_manager_2.SetUseCustomTimeRane(true);
        active_creature_manager_2.SetCustomTimeRange(50, 120);

        // This is an example of how to set a custom override callback function
        // to modify the bones during playback. In this example, we are displacing
        // the bones in the character by a fixed amount in y
        active_creature_manager_2.SetBonesOverrideCallback(
                new CreatureManager.BonesCallback() {
                    public void run(HashMap<String, MeshBone> bones_map)
                    {
                        for (HashMap.Entry<String, MeshBone> entry : bones_map.entrySet()) {
                            MeshBone cur_bone = entry.getValue();
                            Vector3 cur_start_pt = cur_bone.getWorldStartPt();
                            Vector3 cur_end_pt = cur_bone.getWorldEndPt();

                            cur_start_pt.y += 17.5;
                            cur_end_pt.y += 17.5;

                            cur_bone.setWorldStartPt(cur_start_pt);
                            cur_bone.setWorldEndPt(cur_end_pt);
                        }
                    }
                }
        );

        active_creature_render_2 = new CreatureRenderer(active_creature_manager_2,
                new_texture,
                program,
                camera);

        // Set a transformation matrix for the creature
        Matrix4 xform2 = new Matrix4();
        xform2.scl(-1, 1, 1);
        xform2.translate(-5, -15, 0);
        active_creature_render_2.SetXform(xform2);


    }

	@Override
	public void render () {
        //Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

        camera.update();

        active_creature_manager.Update(Gdx.graphics.getDeltaTime());
        active_creature_render.Flush();

        active_creature_manager_2.Update(Gdx.graphics.getDeltaTime());
        active_creature_render_2.Flush();
    }

    public void resize (int width, int height) {
        //camera.setToOrtho(false);
    }
}
