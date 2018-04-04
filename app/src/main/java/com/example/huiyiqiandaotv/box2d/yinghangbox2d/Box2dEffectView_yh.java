package com.example.huiyiqiandaotv.box2d.yinghangbox2d;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.huiyiqiandaotv.beans.TanChuangBean;
import com.example.huiyiqiandaotv.box2d.Beans.BallInfo;
import com.example.huiyiqiandaotv.box2d.Beans.Box2dConstant;
import com.example.huiyiqiandaotv.box2d.Beans.YHBodyBean;
import com.example.huiyiqiandaotv.box2d.Box2dSenserLogic;
import com.example.huiyiqiandaotv.box2d.Tools.Transform;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;


/**
 * @author AleXQ
 * @Date 15/6/19
 * @Description: 物体撞击绘制实体类
 */
public class Box2dEffectView_yh implements ApplicationListener {

    private static final String TAG = "Box2dEffectView";
    private static final float PXTM = 30;
  //  private OrthographicCamera camera;
    //private Box2DDebugRenderer m_debugRenderer;
   // private World world;
    private Box2dSenserLogic m_box2dSenserLogic;
    private Context m_context;
    private List<Body> m_ballBodys = new ArrayList<>();

    private SpriteBatch m_spriteBatch;
    private boolean m_isDebugRenderer;
	private boolean m_candraw = true;

    private StringBuilder  WENZI=new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ\nabcdefghijklmnopqrstuvwxyz1234567890 \\\"!`?'.,;:()[]{}<>|/@\\\\^$-%+=#_&~*\"") ;
    // 舞台
    private Stage stage;
    private FreeTypeFontGenerator generator;

    /**
     * World掌管Box2d创建所有物理实体，动态模拟，异步查询。也包含有效的内存管理工具
     */
    World world;
    /**
     * Box2d只是模拟世界，并不会像屏幕上（Stage之类上）添加任何显示对象，但是Libgdx提供了Box2DDebugRenderer
     * 供我们模拟，里面包含了ShapeRenderer，进行模拟调试
     */
    private Box2DDebugRenderer box2DDebugRenderer;
    private Body body;
    private OrthographicCamera camera;

    // 行走动画
    private Animation<TextureRegion> walkAnimation;

    // 状态时间, 渲染时间步 delta 的累加值
    private float stateTime;
    private Texture walkSheetTexture;
    private float ww;
    private float hh;
    private Image image_yg,image_ld;
    private BitmapFont font;
    private  Vector<YHBodyBean> yhBodyBeans=new Vector<>();
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private static final float SCENE_WIDTH = 41.8f; // 13 metres wide
    private static final float SCENE_HEIGHT = 36.2f; // 7 metres high
    ParticleEffect particleEffect;
    private ParticleEffectPool pool;
    private Array<ParticleEffectPool.PooledEffect> effects;
    private float cameraWidth;
    private float cameraHeight;

    // 演员的包围矩形
    private final Rectangle badlogicRect = new Rectangle();
    private final Rectangle logoRect = new Rectangle();


	Box2dEffectView_yh(Context context) {
        m_context = context;

    }

	public void release()
    {
        //m_box2dSenserLogic.release();
    }

    @Override
    public void create() {

        ww = Gdx.graphics.getWidth();
        hh= Gdx.graphics.getHeight();
        cameraWidth = ww / PXTM;
        cameraHeight = hh / PXTM;

        particleEffect = new ParticleEffect();
        // 加载粒子文件
        particleEffect.load(Gdx.files.internal("box2d/dd.pfx"), Gdx.files.internal("particles"));
       // particleEffect.setPosition(100,100);
        particleEffect.start();


        //pool = new ParticleEffectPool(particleEffect, 0, 70);
       // effects = new Array<>();

//        Gdx.input.setInputProcessor(new InputAdapter() {
//
//            @Override
//            public boolean touchDragged(int screenX, int screenY, int pointer) {
//                ParticleEffectPool.PooledEffect effect = pool.obtain();
//                effect.setPosition(screenX, -screenY + Gdx.graphics.getHeight());
//                effects.add(effect);
//                return true;
//            }
//
//        });



        fontParameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/xk.TTF"));

        //m_debugRenderer = new Box2DDebugRenderer();
        m_spriteBatch = new SpriteBatch();

        //舞台伸缩类型
        Viewport stretchViewport=new ScalingViewport(Scaling.stretch,ww,hh);
        stage = new Stage(stretchViewport);

        // 在X轴方向上受力为0， 在Y轴方向上受到向下的重力 9.8
        world = new World(new Vector2(0f, 0f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(cameraWidth, cameraHeight);


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
              //  Log.d(TAG, "开始");
//                for (int i=0;i<contact.getWorldManifold().getPoints().length;i++){
//                    Log.d(TAG, contact.getWorldManifold().getPoints()[i].toString());
//                }

                Vector2 cc= contact.getFixtureA().getBody().getPosition();
                Vector2 dd=  contact.getFixtureB().getBody().getPosition();


            }

            @Override
            public void endContact(Contact contact) {
              //  Log.d(TAG, "结束");


            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
//                Log.d(TAG, oldManifold.getLocalPoint().toString()+"qq");
//                for (int i=0;i<oldManifold.getPoints().length;i++){
//                    Log.d(TAG, oldManifold.getPoints()[i].localPoint.x+"  "+ oldManifold.getPoints()[i].localPoint.x);
//                }
//                Log.d(TAG, "oldManifold.getLocalNormal():" + oldManifold.getLocalNormal().toString());


            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
//                Log.d(TAG, "impulse.getCount():" + impulse.getCount()+"ee");
//                for (int i=0;i<impulse.getTangentImpulses().length;i++){
//                    Log.d(TAG, "impulse.getTangentImpulses().length:" +impulse.getTangentImpulses()[i] );
//                }



            }
        });


        addleftwall();
        addrighttwall();
        addground();
        createGround3();

//        m_box2dSenserLogic = new Box2dSenserLogic(world, m_context);
//        int frameRows = 5;  // 小人单元格的行数
//        int frameCols = 6;  // 小人单元格的列数
//
//        int perCellWidth = walkSheetTexture.getWidth() / frameCols;     // 计算每一个小人单元格的宽度
//        int perCellHeight = walkSheetTexture.getHeight() / frameRows;   // 计算每一个小人单元格的高度
//
//        // 按照指定的宽高作为一个单元格分割大图纹理, 分割后的结果为一个 5 * 6 的纹理区域二维数组, 数组中的元素是分割出来的小人单元格
//        TextureRegion[][] cellRegions = TextureRegion.split(walkSheetTexture, perCellWidth, perCellHeight);
//
//        // 把二维数组变为一维数组, 因为 Animation 只能接收一维数组作为关键帧序列, 数组中的一个元素（小人单元格的纹理区域）表示一个关键帧
//        TextureRegion[] walkFrames = new TextureRegion[frameRows * frameCols];
//        int index = 0;
//        for (int row = 0; row < frameRows; row++) {
//            for (int col = 0; col < frameCols; col++) {
//                walkFrames[index++] = cellRegions[row][col];
//            }
//        }

        // 使用关键帧（纹理区域）数组 walkFrames 创建一个动画实例, 每一帧（一个小人单元格/纹理区域）播放 0.05 秒
       // walkAnimation = new Animation<>(0.05f, walkFrames);

        /*
         * 设置播放模式:
         *
         * Animation.PlayMode.NORMAL: 正常播放一次（默认）
         * Animation.PlayMode.REVERSED: 倒序播放一次
         *
         * Animation.PlayMode.LOOP: 正常循环播放
         * Animation.PlayMode.LOOP_REVERSED: 倒序循环播放
         *
         * Animation.PlayMode.LOOP_RANDOM: 随机循环播放
         * Animation.PlayMode.LOOP_PINGPONG: 开关式（先正序再倒序）循环播放
         */
       // walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
      //  loadImageFromNet();

    }

    /**
     * 把Bitmap转Byte
     */
    public  byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    /**
     * 从网络中加载一张图片
     */
    private void loadImageFromNet() {
        /*
         * 图片链接, 最好先把链接复制到浏览器地址栏, 先用浏览器访问看看链接还是否有效, 如有意外, 建议多试几个链接。
         */
        // String url = "https://libgdx.badlogicgames.com/img/logo.png";
        String url = "http://h.hiphotos.baidu.com/image/w%3D310/sign=8eaa413779ec54e741ec1c1" +
                "f89399bfd/9d82d158ccbf6c812f9fe0e1be3eb13533fa400b.jpg";

        // 1. 创建请求构建器
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

        // 2. 构建请求对象
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(url).build();

        // 3. 发送请求, 监听结果回调
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                // 获取响应状态
                HttpStatus httpStatus = httpResponse.getStatus();

                if (httpStatus.getStatusCode() == 200) {
                    // 请求成功
                    Gdx.app.log(TAG, "请求成功");

                    // 以字节数组的方式获取响应内容
                    final byte[] result = httpResponse.getResult();

                    // 还可以以流或字符串的方式获取
                    // httpResponse.getResultAsStream();
                    // httpResponse.getResultAsString();

                    /*
                     * 在响应回调中属于其他线程, 获取到响应结果后需要
                     * 提交到 渲染线程（create 和 render 方法执行所在线程） 处理。
                     */
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            // 把字节数组加载为 Pixmap
                            Pixmap pixmap = new Pixmap(result, 0, result.length);

                            // 把 pixmap 加载为纹理
//                            texture = new Texture(pixmap);

                            // pixmap 不再需要使用到, 释放内存占用
                            pixmap.dispose();

                            // 使用纹理创建演员

                           // actor = new Image(texture);

                           // actor.setPosition(100,100);

                            // 添加演员到舞台
                            //stage.addActor(actor);
                        }
                    });

                } else {
                    Gdx.app.error(TAG, "请求失败, 状态码: " + httpStatus.getStatusCode());
                }
            }

            @Override
            public void failed(Throwable throwable) {
                Gdx.app.error(TAG, "请求失败", throwable);
            }

            @Override
            public void cancelled() {
                Gdx.app.log(TAG, "请求被取消");
            }

        });
    }


    @Override
    public void dispose() {
// 当应用退出时释放资源
        Log.d(TAG, "销毁");
        if (generator!=null)
        generator.dispose();
        if (stage != null) {
            stage.dispose();
        }
        yhBodyBeans.clear();
        if (box2DDebugRenderer!=null)
            box2DDebugRenderer.dispose();
        if (generator!=null)
            generator.dispose();
        if (m_spriteBatch!=null)
            m_spriteBatch.dispose();
        if (world!=null)
            world.dispose();

    }

	public void setCanDraw(boolean candraw) {


	//	m_candraw = candraw;
        _destoryAll();
//		if (!m_candraw) {
//
//		}
	}
    private int cc=0;
    // 缩放平滑因子
    private float zoomSmothFactor = 0.2f;
    Vector2 oldPositionVector = new Vector2();
    Vector2 newZoomVector = new Vector2(0, 700f);
	private float rot;
    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        float deltatm = Gdx.app.getGraphics().getDeltaTime();
        world.step(deltatm, 6, 2);
        box2DDebugRenderer.render(world, camera.combined);


        // 更新舞台逻辑
        stage.act();
        // 绘制舞台
        stage.draw();
//        // 累加时间步（stateTime 也可表示游戏的运行时间）
//        stateTime += Gdx.graphics.getDeltaTime();

        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧
//        currentFrame = walkAnimation.getKeyFrame(stateTime);

//        if (particleEffect.isComplete()) {
//            particleEffect.reset();
//        }

        synchronized (Box2dEffectView_yh.class) {
            m_spriteBatch.begin();


//            if (texture!=null){
//                m_spriteBatch.draw(texture,100,100,100,100);
//            }
//            if (actor!=null)
//            actor.draw(m_spriteBatch,1.0f);
//            if (world.getBodyCount()>0 && body!=null)
//                body.applyForceToCenter(new Vector2(1000f, 0), true);

            //  Log.d(TAG, "body.getAngle():" + body.getAngle());

            for (int i=0; i<yhBodyBeans.size(); i++) {
                YHBodyBean bd = yhBodyBeans.get(i);
               // YHBodyBean ballInfo = (YHBodyBean)bd.getUserData();
                //判断是否已经到了生命尽头
                float curruntm = bd.getRunTime();
                if (curruntm >= Box2dConstant.MaxBallLifer){
                    destoryBody(i);
                    i--;
                    continue;
                }
                //更新时间
                bd.setRunTime(curruntm + deltatm);
              //  if (i+1<yhBodyBeans.size())
                //checkCollision(bd.getSprite(),yhBodyBeans.get(i+1).getSprite());



            //    Vector2 vv = Transform.mtp(m_ballBodys.get(i).getPosition().x, m_ballBodys.get(i).getPosition().y, new Vector2(ww / 30f / 2f, hh/30/8), PXTM);

//                Sprite sprite=bd.getSprite();
//                Image image=bd.getImage();
//                if (image==null){
//                    m_spriteBatch.end();
//                    return;
//                }
              //  sprite.setSize(ww,hh/4);
              //  sprite.setSize(bd.getWdith(),bd.getHight());

             //   sprite.setPosition(bd.getBg_X(),bd.getBg_Y());

             //   image.setPosition(bd.getBg_X()+100,bd.getBg_Y()+bd.getHight()/2-image.getHeight()/2);
               // image.moveBy(bd.getBg_X()+100,bd.getBg_Y()+bd.getHight()/2-image.getHeight()/2);
              //  sprite.draw(m_spriteBatch);
               // image.draw(m_spriteBatch,1.0f);
                if (bd.getImage()!=null)
                font.draw(m_spriteBatch,bd.getName(),bd.getImage().getX()+bd.getImage().getImageWidth(),bd.getImage().getY()+bd.getHight()/2);


                       particleEffect.draw(m_spriteBatch, Gdx.graphics.getDeltaTime());




      //  bd.applyForceToCenter(0,1000,true);
//        bd.setPosition(200,oldPositionVector.lerp(newZoomVector,zoomSmothFactor * Math.min(2f,Gdx.graphics.getDeltaTime())).y);
//     //   Log.d(TAG, "oldPositionVector.lerp(newZoomVector,zoomSmothFactor * Math.min(0.05f,Gdx.graphics.getDeltaTime())).y:" + oldPositionVector.lerp(newZoomVector, zoomSmothFactor * Math.min(0.05f, Gdx.graphics.getDeltaTime())).y);
//        if (oldPositionVector.lerp(newZoomVector,zoomSmothFactor * Math.min(0.05f,Gdx.graphics.getDeltaTime())).y>=680)
//        oldPositionVector.setZero();


            }


            //if (sprite2!=null)
//            sprite2.draw(m_spriteBatch);

//            font.draw( m_spriteBatch, "今天是个好日子，\n大家心情都很好\nVery Good! 20140521!!", 120, 100*(3) );

            m_spriteBatch.end();


        }
    }

    @Override
    public void resize(int width, int height) {
	    Configuration mConfiguration = m_context.getResources().getConfiguration(); //获取设置的配置信息
	    int ori = mConfiguration.orientation ; //获取屏幕方向

	    setIsPortrait(ori == Configuration.ORIENTATION_PORTRAIT);
    }


    @Override
    public void pause() {
        Log.d(TAG, "销毁");

    }

    @Override
    public void resume() {


    }

	private void setIsPortrait(boolean isPortrait){
		if (m_box2dSenserLogic != null)
			m_box2dSenserLogic.setIsPortrait(isPortrait);
	}

    void addStar(final TanChuangBean bean, final int type, final Bitmap bitmap) {
	    int size=yhBodyBeans.size();
	    for (int i=0;i<size;i++){

	        yhBodyBeans.get(i).getSprite().addAction(Actions.moveBy(0,  yhBodyBeans.get(i).getHight(), 1.5f, Interpolation.smoother));
	        yhBodyBeans.get(i).getImage().addAction(Actions.moveBy(0,  yhBodyBeans.get(i).getHight(), 1.5f, Interpolation.smoother));

        }

           byte[] bb = Bitmap2Bytes(bitmap);
        final Pixmap pixmap = new Pixmap(bb, 0, bb.length);

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    fontParameter.characters = bean.getName();
                    fontParameter.size = 42;
                    font = generator.generateFont(fontParameter);
                    font.setColor(Color.WHITE);
                    final YHBodyBean yhBodyBean=new YHBodyBean();
                    yhBodyBean.setType(bean.getBumen());
                    yhBodyBean.setName(bean.getName());
                    if (bean.getBumen().equals("0")){

                        image_yg=new Image(new Texture(Gdx.files.internal("box2d/pufa_yg.png")));
                        image_yg.setOrigin(0,0);
                        image_yg.setZIndex(0);
                        image_yg.setSize(ww-ww/20,hh/4);
                        yhBodyBean.setWdith(image_yg.getWidth());
                        yhBodyBean.setHight(image_yg.getHeight());
                        yhBodyBean.setSprite(image_yg);

                        particleEffect.setPosition(ww/2,hh*3/8);

                    }else {
                        if (particleEffect!=null)
                        particleEffect.setPosition(yhBodyBean.getImage().getX()+yhBodyBean.getImage().getWidth(),yhBodyBean.getImage().getY()+yhBodyBean.getHight());
                        image_ld=new Image(new Texture(Gdx.files.internal("box2d/pufa_ld.png")));
                        image_ld.setOrigin(0,0);
                        image_ld.setZIndex(0);
                        image_ld.setSize(ww-ww/20,hh/4);
                        yhBodyBean.setWdith(image_ld.getWidth());
                        yhBodyBean.setHight(image_ld.getHeight());
                        yhBodyBean.setSprite(image_ld);

                    }
                    //背景
                    yhBodyBean.setBg_X(ww/40);
                    yhBodyBean.setBg_Y(hh/4);
                    yhBodyBean.getSprite().setPosition(ww/40, 0);
                    yhBodyBean.getSprite().addAction(Actions.moveBy(0,  yhBodyBean.getSprite().getHeight(), 1.5f, Interpolation.smoother));

                    Texture texture = new Texture(pixmap);
                    pixmap.dispose();
                    //头像
                    final Image image = new Image(texture);
                    image.setOrigin(0, 0);
                    image.setZIndex(1);
                    image.setPosition(ww/20, yhBodyBean.getHight()/2  - image.getHeight() / 2);
                    image.addAction(Actions.moveBy(0, yhBodyBean.getSprite().getHeight(), 1.5f, Interpolation.smoother));
                    yhBodyBean.setImage(image);

                    stage.addActor(yhBodyBean.getSprite());
                    stage.addActor(image);
                    yhBodyBeans.add(yhBodyBean);

                  //  if (particleEffect!=null);
                  //  particleEffect.start();

//                /** BodyDef 定义创建刚体所需要的全部数据。可以被重复使用创建不同刚体，BodyDef之后需要绑定Shape **/
//                BodyDef bodyDef = new BodyDef();
//                /** 动态刚体，受力之后运动会发生改变。 默认创建的是静态BodyType.StaticBody **/
//                bodyDef.type = BodyDef.BodyType.DynamicBody;
//                bodyDef.position.set(new Vector2(-cameraWidth/2,-cameraHeight/2+cameraHeight/4));
//               // bodyDef.linearVelocity.set(1.0f, 1.0f);//速度
//                // bodyDef.angularVelocity=Math.round(6); //角速度
//                bodyDef.fixedRotation=true; //阻止旋转
//                bodyDef.linearDamping = 0.0f;
//                bodyDef.angularDamping = 0.01f;
//                body = world.createBody(bodyDef);
//                body.setUserData(yhBodyBean);
//                // 刚体没有任何显示，Shape主要用来显示和做碰撞检测。
//                PolygonShape shape = new PolygonShape(); // 多边
//
//                shape.setAsBox(ww/30f/2f-0.01f,hh/30/8);  // 半个宽度和半个高度作为参数，这样盒子就是一米宽，一米高
//                // Fixture(夹具)，将形状绑定到物体上，并添加密度(density)、摩擦(friction)、恢复(restitution)等材料特性
//                // 还将形状放入到碰撞检测系统中(Broad Phase)，以使之能与其它形状相碰撞
//                // 一个物体和另一个物体碰撞，碰撞后速度和碰撞前速度的比值会保持不变，这比值就叫恢复系数
//                FixtureDef fixtureDef = new FixtureDef();
//                /** 当你使用 fixture 向 body 添加 shape 的时候， shape 的坐标对于 body 来说就变成本地的了。因此
//                 当 body 移动的时候， shape 也一起移动。 fixture 的世界变换继承自它的父 body。 fixture 没有独立
//                 于 body 的变换。所以我们不需要移动 body 上的 shape。不支持移动或修改 body 上的 shape。原因很简单：
//                 形状发生改变的物体不是刚体，而 Box2D 只是个刚体引擎。 Box2D 所做的很多假设都是基于刚体模型的。
//                 如果这一条被改变的话，很多事情都会出错。
//                 **/
//                fixtureDef.shape = shape;
//                fixtureDef.friction=0f;
//                fixtureDef.density = 0.1f;
//                fixtureDef.restitution = 0.6f;  // 恢复系数，物理受到反作用力的运动情况，值越大反向运动速度越快
//                body.createFixture(fixtureDef);
//                shape.dispose();
//                m_ballBodys.add(body);
                }
            });


    }



          // loadImageFromNet();

//
//	    if (!m_candraw)
//		    return;
//
//        _totalLimitsLogic();
//
//        synchronized (Box2dEffectView.class) {
//
//            BodyDef BallBodydef = new BodyDef();
//          //  Log.d(TAG, "BallBodydef:" + BallBodydef);
//            BallBodydef.type = BodyDef.BodyType.DynamicBody;
//            BallBodydef.gravityScale=0.1f;
//            float thrownXRandom = (float) Math.random() * 2.0f;
//            float thrownYRandom =  -camera.viewportHeight/2f+2.0f;
//	        float yRandomStart = -0.08f;//(float) Math.random() * 8f;
//            BallBodydef.linearVelocity.set(-thrownXRandom, thrownYRandom);
//            BallBodydef.position.set(new Vector2((float) Math.random()*(Math.random()>0.5?1:-1) * camera.viewportWidth/2.0f, camera.viewportHeight/2.0f - yRandomStart) );
//
//            //            if (isLeft) {
////               // BallBodydef.linearVelocity.set(thrownXRandom, thrownYRandom);
////               // BallBodydef.position.set(new Vector2(-camera.viewportWidth/2 + 2f, camera.viewportHeight/2 - yRandomStart) );
////                BallBodydef.linearVelocity.set(thrownXRandom, thrownYRandom);
////                    BallBodydef.position.set(new Vector2(-((float) Math.random() * camera.viewportWidth/2 ), camera.viewportHeight/2 - yRandomStart) );
////
////            } else {
////                BallBodydef.linearVelocity.set(-thrownXRandom, thrownYRandom);
////                BallBodydef.position.set(new Vector2((float) Math.random() * camera.viewportWidth/2, camera.viewportHeight/2 - yRandomStart) );
////            }
//
//	        BallInfo ballinfo = new BallInfo();
//	        ballinfo.setBallIndex(isSelf?1002:1001);
//            Body BallBody = world.createBody(BallBodydef);
//            BallBody.setUserData(ballinfo);
//            BallBody.setFixedRotation(false);
//            CircleShape shape = new CircleShape();
//            shape.setRadius(2.0f);
//            FixtureDef BallFixtureDef = new FixtureDef();
//            BallFixtureDef.shape = shape;
//            BallFixtureDef.density = 1.5f;
//            BallFixtureDef.friction = 0.3f;
//            BallFixtureDef.restitution = 0.0f; // Make it bounce a little bit
//            BallBody.createFixture(BallFixtureDef);
//            shape.dispose();
//
//            m_ballBodys.add(BallBody);
//
//            if (m_ballBodys.size() == 1)
//                m_box2dSenserLogic.startListener();
//        }



    private boolean m_randomGiftLeft = false;//礼物在屏幕左侧
	public void addGift(int index) {

//		if (!m_candraw)
//			return;
//
//		_totalLimitsLogic();
//
//		synchronized (Box2dEffectView.class) {
//
//			BodyDef BallBodydef = new BodyDef();
//			BallBodydef.type = BodyDef.BodyType.DynamicBody;
//
//			float posXRandom =  (float) Math.random() * (camera.viewportWidth/2 * 5/6);
//			posXRandom = m_randomGiftLeft? posXRandom : -posXRandom;
//			m_randomGiftLeft = !m_randomGiftLeft;
//
//			float thrownXRandomTemp = (int)((float) Math.random() * 10.0f)%2 ==0 ? -1: 1;
//			float thrownXRandom = thrownXRandomTemp*( (float) Math.random() * 5.0f );
//			float thrownYRandom = -( (float) Math.random() * 40.0f );
//			BallBodydef.linearVelocity.set(thrownXRandom, thrownYRandom);
//			BallBodydef.position.set(new Vector2(posXRandom, camera.viewportHeight/2 + 2.5f) );
//
//			float randomScale = (float) Math.random()*0.5f+1f;
//			BallInfo ballinfo = new BallInfo();
//			ballinfo.setBallIndex(index);
//			ballinfo.setRandomScale(randomScale);
//			Body BallBody = world.createBody(BallBodydef);
//			BallBody.setUserData(ballinfo);
//			BallBody.setFixedRotation(false);
//			CircleShape shape = new CircleShape();
//			shape.setRadius(1.1f*randomScale);
//			FixtureDef BallFixtureDef = new FixtureDef();
//			BallFixtureDef.shape = shape;
//			BallFixtureDef.density = 0.5f;
//			BallFixtureDef.friction = 0.3f;
//			BallFixtureDef.restitution = 0.5f; // Make it bounce a little bit
//			BallBody.createFixture(BallFixtureDef);
//			shape.dispose();
//            m_ballBodys.add(BallBody);
//
//			if (m_ballBodys.size() == 1)
//				m_box2dSenserLogic.startListener();
//		}
	}


    private void createGround3() {
        //上面墙
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(new Vector2(0,camera.viewportHeight/2 ));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox( camera.viewportWidth,0.1f / PXTM);
        groundBody.createFixture(groundBox, 0.5f);
        groundBox.dispose();
    }


	private void addground(){
	    //下面墙
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(new Vector2(0, -camera.viewportHeight/2-0.2f));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, 0.1f / PXTM);
        groundBody.createFixture(groundBox, 0.5f);
        groundBox.dispose();
    }

    private void addleftwall(){
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(new Vector2(-camera.viewportWidth/2-0.1f, 0f));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(0.1f / PXTM, camera.viewportHeight);// 调节墙位置
        groundBody.createFixture(groundBox, 0.5f);
        groundBox.dispose();
    }


    private void addrighttwall(){
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(new Vector2(camera.viewportWidth/2+0.1f, 0f));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(0.1f / PXTM, camera.viewportHeight);
        groundBody.createFixture(groundBox, 0.5f);
        groundBox.dispose();
    }

    private void _totalLimitsLogic(){

        synchronized (Box2dEffectView_yh.class){
            if (m_ballBodys.size() > Box2dConstant.MaxBallCounter){
                //destoryBody(0);
            }
        }

    }

	private void _destoryAll(){


//			for (int i=0; i<yhBodyBeans.size();i++){
//				destoryBody(i);
//				i--;
//			}


	}

    /**
     * 碰撞检测
     * @param image
     * @param sprite
     */
    private void checkCollision(Image image, Image sprite) {
        /*
         * 获取演员的包围矩形
         *
         * 注意: 如果对演员进行了缩放旋转等变换, 需要获取的是变换后视觉上的包围矩形,
         * 后续的引擎封装中将详细介绍。
         */
        badlogicRect.set(
                image.getX(),
                image.getY(),
                image.getWidth(),
                image.getHeight()
        );
        logoRect.set(
                sprite.getX(),
                sprite.getY(),
                sprite.getWidth(),
                sprite.getHeight()
        );

        // 判断两个演员是否碰撞, 如果碰撞, 则将 badlogicActor 设置为半透明
        if (badlogicRect.overlaps(logoRect)) {
            Log.d(TAG, "ddd");
            image.addAction(Actions.moveBy(0, 10, 0));
        }
    }


    private float hidesmallBody( int indexofbodys){
        BallInfo ballInfo = (BallInfo)m_ballBodys.get(indexofbodys).getUserData();
        float als = ballInfo.getAplhascale();
        als = als - 0.02f;
        if (als <= 0)
            return 0;
        ballInfo.setAplhascale(als);
        return  als;
    }

    private void destoryBody(int indexofbodys){
     //   Body body = m_ballBodys.remove(indexofbodys);
       // world.destroyBody(body);
        yhBodyBeans.get(indexofbodys).getImage().remove();
        yhBodyBeans.get(indexofbodys).getSprite().remove();
        yhBodyBeans.remove(indexofbodys);

//        if (m_ballBodys.size() == 0){
//            m_box2dSenserLogic.stopListener();
//        }
    }

    public void openDebugRenderer(boolean debugRenderer) {
        m_isDebugRenderer = debugRenderer;
    }

	private class MyContactListener implements ContactListener{

		@Override
		public void beginContact(Contact contact) {

		//	BallInfo ballInfoA = (BallInfo)contact.getFixtureA().getBody().getUserData();
		//	BallInfo ballInfoB = (BallInfo)contact.getFixtureB().getBody().getUserData();
//			if (ballInfoA != null && ballInfoB != null){
//				Log.d(TAG, "beginContact");
//			}
		}

		@Override
		public void endContact(Contact contact) {

		}

		@Override
		public void preSolve(Contact contact, Manifold manifold) {

		}

		@Override
		public void postSolve(Contact contact, ContactImpulse contactImpulse) {

		}
	}
}