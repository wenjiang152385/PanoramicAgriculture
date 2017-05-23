package com.oraro.panoramicagriculture;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.view.CropImageView;
import com.oraro.panoramicagriculture.common.Constants;
import com.oraro.panoramicagriculture.data.dao.DaoMaster;
import com.oraro.panoramicagriculture.data.dao.DaoSession;
import com.oraro.panoramicagriculture.data.entity.UserEntity;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/4.
 */
public class PanoramicAgricultureApplication extends MultiDexApplication {

    private DaoMaster.DevOpenHelper devOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private static PanoramicAgricultureApplication instances;
    public static UserEntity LOCAL_LOGINED_USER = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


        instances = this;
        Log.i("MapApplication", "application=" + instances);
        setDatabase();
        Fresco.initialize(this.getApplicationContext());
        initImagePicker();
    }


    public static PanoramicAgricultureApplication getInstances() {
        return instances;
    }

    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        devOpenHelper = new DaoMaster.DevOpenHelper(this, "map-db", null);
        sqLiteDatabase = devOpenHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(sqLiteDatabase);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return sqLiteDatabase;
    }

    public static boolean isLogined() {
        return LOCAL_LOGINED_USER != null;
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new FrescoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(4);                        //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private class FrescoImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
            if (path.startsWith("/upload")) {
                URL url = null;
//                try {
//                    url = new URL(Constants.SERVER_ADDRESS + path);
//                    imageView.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                Picasso.with(activity)//
                        .load(Constants.SERVER_ADDRESS + path)//
                        .placeholder(R.mipmap.default_image)//
                        .error(R.mipmap.default_image)//
                        .resize(width, height)//
                        .centerInside()//
                        .into(imageView);
            } else {
                Picasso.with(activity)//
                        .load(new File(path))//
                        .placeholder(R.mipmap.default_image)//
                        .error(R.mipmap.default_image)//
                        .resize(width, height)//
                        .centerInside()//
                        .into(imageView);
            }
        }

        @Override
        public void clearMemoryCache() {

        }
    }
}
