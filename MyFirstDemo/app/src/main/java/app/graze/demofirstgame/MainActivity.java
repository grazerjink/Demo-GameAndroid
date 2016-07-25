package app.graze.demofirstgame;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationAtModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.text.TickerText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

public class MainActivity extends BaseGameActivity {

    private static final int CAMERA_WIDTH = 480; //do rong man hinh
    private static final int CAMERA_HEIGHT = 800; // chieu cao

    private Texture mFontTexture;
    private Font mFont;

    //Muc custorm
    private Text text;
    private Texture texture;
    private Font font;

    //Text Change
    private Texture textureChange;
    private Font fontChange;
    int Count = 0;

    //TickerText
    private Texture txtureTicker;
    private Font fonTicker;

    // Load game; kick thuoc camera; khung nhin;
    @Override
    public Engine onLoadEngine() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        //PORTRAIT khởi tạo màn hình xoay ngang
        final EngineOptions engineOptions = new EngineOptions(true,
                EngineOptions.ScreenOrientation.PORTRAIT,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);

        engineOptions.getTouchOptions().setRunOnUpdateThread(true);
        return new Engine(engineOptions);
    }

    //Load cac file nguon nhu am thanh hinh anh
    @Override
    public void onLoadResources() {
        //Texture la cai layout chua text thoi
        mFontTexture = new Texture(256,256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mFont = new Font(mFontTexture, Typeface.create(Typeface.DEFAULT,Typeface.BOLD),32,true, Color.BLUE);
        mEngine.getTextureManager().loadTextures(mFontTexture);
        mEngine.getFontManager().loadFont(mFont);


        //Lam viec voi Custorm Text
        FontFactory.setAssetBasePath("font/"); //chi toi thu muc chua font
        //Khai bao new custorm text
        texture = new Texture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createFromAsset(texture,this,"VHMUSTI.TTF",45,true,Color.YELLOW);
        mEngine.getTextureManager().loadTextures(texture);
        mEngine.getFontManager().loadFont(font);

        //Lam viec voi Text Change
        FontFactory.setAssetBasePath("font/");
        textureChange= new Texture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fontChange = FontFactory.createFromAsset(textureChange,this,"digifaw.ttf",32,true,Color.BLACK);
        mEngine.getTextureManager().loadTextures(textureChange);
        mEngine.getFontManager().loadFont(fontChange);

        //Lam viec voi TickerText
        txtureTicker = new Texture(256, 256, TextureOptions.REPEATING_NEAREST_PREMULTIPLYALPHA);
        fonTicker = new Font(txtureTicker,Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC),28,true,Color.CYAN);
        mEngine.getTextureManager().loadTexture(txtureTicker);
        mEngine.getFontManager().loadFont(fonTicker);

    }

    //Scene phuong thuc hien thi chinh
    @Override
    public Scene onLoadScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());
        final Scene scene = new Scene(2);
        scene.setBackground(new ColorBackground(0.23f, 1, 0));

        //Moi la chuoi String noi dung
        Text mText = new Text(200,200,mFont,"HELLO WORLD!");
        scene.attachChild(mText);
        text = new Text(50,350,font,"The Second Text !");
        scene.attachChild(text);

        final ChangeableText textChange = new ChangeableText(60,400,fontChange,"Count: "+Count+"    ");
        scene.attachChild(textChange);

        CountDownTimer t = new CountDownTimer(10000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                Count=Count+10;
                textChange.setText("Count: "+Count);

            }

            @Override
            public void onFinish() {
            }
        };
        t.start();


        RotationAtModifier rotation = new RotationAtModifier(5,0,360,0.5f,0.5f);

        //TickerText
        TickerText tickerText = new TickerText(100,160,fonTicker,"I LOVE YOU, BABE <3", HorizontalAlign.CENTER,1);
        tickerText.registerEntityModifier(new SequenceEntityModifier(
                new ParallelEntityModifier(new AlphaModifier(10,0f,1f),new ScaleModifier(10,0.5f,2f)),rotation));
//        tickerText.setBlendFunction(Shape.BLENDFUNCTION_SOURCE_DEFAULT,Shape.BLENDFUNCTION_DESTINATION_PREMULTIPLYALPHA_DEFAULT);
//        tickerText.setBlendFunction(GL10.GL_LUMINANCE_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);
        scene.attachChild(tickerText);
        return scene;
    }

    //Chua can quan tam toi
    @Override
    public void onLoadComplete() {

    }
}
