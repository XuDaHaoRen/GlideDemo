package activity.xbl.com.glidedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Glid的简单实用
 * btn1:通过放入一个静态图片，ImageView进行加载的简单操作
 * btn2:放置一张占位图，图片没有加载之前显示占位图，并且实现error方法，图片获取失败时放置另一张占位图
 * btn3:指定显示的图片类型（Gif），如果放置一张静态图，会出现错误的占位图
 * btn4:指定图片显示的分别率，默认Glide会将图片自动适应于ImageView的大小
 * 所有的加载都添加了方法没有使用磁盘缓存，默认是使用的
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView main_image;
    private Button download_url_btn;
    private Button download_default_btn;
    private Button download_gif_btn;
    private Button download_size_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        main_image= (ImageView) findViewById(R.id.main_image);
        download_url_btn= (Button) findViewById(R.id.download_url_btn);
        download_url_btn.setOnClickListener(this);
        download_default_btn = (Button) findViewById(R.id.download_default_btn);
        download_default_btn.setOnClickListener(this);
        download_gif_btn= (Button) findViewById(R.id.download_gif_btn);
        download_gif_btn.setOnClickListener(this);
        download_size_btn= (Button) findViewById(R.id.download_size_btn);
        download_size_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //静态图片地址
        String url="http://img.kumi.cn/photo/08/b2/58/08b258f2b7715a59.jpg";
        //动态图片地址
        String gifUrl="http://img.educity.cn/article_image/2013082605/234560051428.jpg";
        switch (v.getId()){
            //获取 网络图片进行下载
            case R.id.download_url_btn:
                //Glide.with 创建一个加载图片的实例，可以接受Activity,Context,Fragment,多种环境下使用
                Glide.with(this).load(url).into(main_image);
                break;
            //使用占位符显示图片
            case R.id.download_default_btn:
                //placeholder  放置占位符，这个在load 和 into方法 中间
                //因为Glide有缓存原理，所以当中把缓存原理去除
                //还可以放置异常占位符，比如手机没有网络，图片地址不对的时候显示的图片
                Glide.with(this).load(url).placeholder(R.drawable.center).
                        error(R.mipmap.ic_launcher).
                        diskCacheStrategy(DiskCacheStrategy.NONE).into(main_image);
                break;
            //将显示的图片指定为一张gif图片
            case R.id.download_gif_btn:
                //如果中的指定了Gif格式却传了一个静态图，那么会显示错误的占位图
                Glide.with(this).load(gifUrl).asGif().placeholder(R.drawable.center).
                        error(R.mipmap.ic_launcher).
                        diskCacheStrategy(DiskCacheStrategy.NONE).into(main_image);
                break;
            //指定图片的大小
            case R.id.download_size_btn:
                //如果一个图片尺寸为1000*1000像素，但是界面上的ImageView只有200*200像素，
                // 如果不对图片进行压缩直接读取到内存中，就属于资源浪费，因为程序根本用不到这么高像素的图片，
                // 但是Glide会自动将图片调节尺寸进行显示，让它适应于ImageView的大小
                //不过当真的有需求时，必须给图片指定一个大小，Glide也是支持的
                Glide.with(this).load(url).placeholder(R.drawable.center).
                        error(R.mipmap.ic_launcher).
                        diskCacheStrategy(DiskCacheStrategy.NONE).
                        //图片将被加载成100*100的尺寸，不管ImageView的大小是多少了（图片会变得模糊）
                        override(100,100).
                        into(main_image);
                break;

        }
    }
}
