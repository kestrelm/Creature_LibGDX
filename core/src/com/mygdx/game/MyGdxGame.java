package com.mygdx.game;

import MeshBoneUtil.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.graphics.FPSLogger;

public class MyGdxGame extends ApplicationAdapter {
	
	OrthographicCamera camera;
	CreatureManager active_creature_manager;
    CreatureRenderer active_creature_render;
    JsonValue store_json;
    FPSLogger fpsLogger;
	
	@Override
	public void create () {
		fpsLogger = new FPSLogger();
		
		//String jsonFilename = baseResourcePath + "character_raptor_data.json";
		String flatFilename = "character_raptor_data.bin";
		String pngFilename = "character_raptor_img.png";

		camera = new OrthographicCamera( (float)Gdx.graphics.getWidth() * 0.05f,
                (float)Gdx.graphics.getHeight() * 0.05f);
		
		// Load flat data
		CreatureFlatDataJava.rootData flat_data = CreatureModuleUtils.LoadCreatureFlatData(flatFilename);
		
		// Create creature
        //Creature new_creature = new Creature(json_data);
        Creature new_creature = new Creature(flat_data);
        
        // Create animations
        //CreatureAnimation new_animation1 = new CreatureAnimation(json_data, "default");
        CreatureAnimation new_animation1 = new CreatureAnimation(flat_data.dataAnimation(), "default");
        //CreatureAnimation new_animation2 = new CreatureAnimation(flat_data.dataAnimation(), "fast");
        //CreatureAnimation new_animation3 = new CreatureAnimation(flat_data.dataAnimation(), "slow");
        
        // Create manager and add in animations
        CreatureManager new_creature_manager = new CreatureManager(new_creature);
        new_creature_manager.AddAnimation(new_animation1);
        //new_creature_manager.AddAnimation(new_animation2);
        //new_creature_manager.AddAnimation(new_animation3);
        
        new_creature_manager.MakePointCache("default", 2);
        
        new_creature_manager.SetActiveAnimationName("default", false);
        new_creature_manager.SetIsPlaying(true);
        new_creature_manager.SetShouldLoop(true);
        
        active_creature_manager = new_creature_manager;
        
        // load shaders
        final String VERTEX = Gdx.files.internal("MeshBoneShader.vert").readString(); //Gdx.files.internal("MeshBoneShader.vert").readString();
        final String FRAGMENT = Gdx.files.internal("MeshBoneShader.frag").readString(); //Gdx.files.internal("MeshBoneShader.frag").readString();
        ShaderProgram program = new ShaderProgram(VERTEX, FRAGMENT);
        Texture new_texture = new Texture(pngFilename);

        // Create the creature render object
        active_creature_render = new CreatureRenderer(new_creature_manager,
                new_texture,
                program,
                camera);

        // Set a transformation matrix for the creature
        Matrix4 xform = new Matrix4();
        xform.scl(0.7f);
        active_creature_render.SetXform(xform);
        
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// update creature
        camera.update();
        active_creature_manager.Update(Gdx.graphics.getDeltaTime());
        active_creature_render.Flush();
        
        // output the current FPS
        //fpsLogger.log();
	}
}
