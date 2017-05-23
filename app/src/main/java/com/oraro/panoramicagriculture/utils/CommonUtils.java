package com.oraro.panoramicagriculture.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;


import com.oraro.panoramicagriculture.PanoramicAgricultureApplication;
import com.oraro.panoramicagriculture.common.Constants;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {
    public static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat mDotDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    public static SimpleDateFormat mLineDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static ConnectivityManager mCnnManager;

    public enum CHART_TYPE{PIE,LINE}
    public enum ABILITY_TYPE{FARM,SALE}
    public enum NUM_TYPE{EXCEPT,ACTUAL,BOTH};

    /**
     * Toast消息
     */
    public static void Toast(Context ctx, int stringId) {
        Toast.makeText(ctx, ctx.getResources().getString(stringId), Toast.LENGTH_SHORT).show();
    }

    public static void Toast(Context ctx, String string) {
        Toast.makeText(ctx, string, Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        String versionName = "";
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取软件code 类似于18这种
     */
    public static int getAppCode(Context context) {
        int code = -1;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            code = pi.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Compress image by pixel, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param resId  image resource id
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap decodeBitmap(Context context, int resId, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        if (bitmap != null) {
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 想要缩放的目标尺寸
            float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
            float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0) be = 1;
            newOpts.inSampleSize = be;//设置缩放比例
            // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
            bitmap = BitmapFactory.decodeResource(context.getResources(), resId, newOpts);
            // 压缩好比例大小后再进行质量压缩
            //return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        }

        return bitmap;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;

    }

    /**
     * 判断本程序是否拥有某权限
     *
     * @param context
     * @return
     */
    public static boolean hasExternalStoragePermission(Context context, String permission) {
        int perm = context.checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean selfPermissionGranted(Context context, String permission) {
        boolean result = false;
        try {
            PackageInfo mPackageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int mTargetSdkVersion = mPackageInfo.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mTargetSdkVersion >= Build.VERSION_CODES.M) {
                    result = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                } else {
                    result = PermissionChecker.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
                }
            } else {
                return true;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ConnectivityManager getCnnManager(Context context) {
        if (mCnnManager == null)
            mCnnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mCnnManager;
    }

    /**
     * 检测是否有网络
     *
     * @return
     */
    public static boolean hasInternet(Context context) {
        return getCnnManager(context).getActiveNetworkInfo() != null && getCnnManager(context).getActiveNetworkInfo().isAvailable();
    }

    /**
     * 网络类型
     *
     * @return
     */
    public static int getInternetType(Context context) {
        return getCnnManager(context).getActiveNetworkInfo().getType();
    }

    public static int getBetweenDays(String t1, String t2) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
// 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c1 = c2;
            c2.setTime(d1);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    public static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("UTF-8").length > 1;
    }


    /**
     * 按字节截取字符串
     *
     * @param orignal 原始字符串
     * @param count   截取位数
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static String substring(String orignal, int count)
            throws UnsupportedEncodingException {
        // 原始字符不为null，也不是空字符串
        if (orignal != null && !"".equals(orignal)) {
            // 将原始字符串转换为GBK编码格式
            orignal = new String(orignal.getBytes(), "UTF-8");//
            // 要截取的字节数大于0，且小于原始字符串的字节数
            if (count > 0 && count < orignal.getBytes("UTF-8").length) {
                StringBuffer buff = new StringBuffer();
                char c;
                for (int i = 0; i < count; i++) {
                    c = orignal.charAt(i);
                    buff.append(c);
                    if (isChineseChar(c)) {
                        // 遇到中文汉字，截取字节总数减1
                        --count;
                    }
                }
                return (new String(buff.toString().getBytes(), "UTF-8")) + "...";
            }
        }
        return orignal;
    }
    public static String substring_chinese(String orignal, int count)
            throws UnsupportedEncodingException {
        // 原始字符不为null，也不是空字符串
        if (orignal != null && !"".equals(orignal)) {
            // 将原始字符串转换为GBK编码格式
            orignal = new String(orignal.getBytes(), "UTF-8");//
            // 要截取的字节数大于0，且小于原始字符串的字节数
            if (count > 0) {
                StringBuffer buff = new StringBuffer();
                char c;
                final int length = orignal.getBytes("UTF-8").length;
                for (int i = 0; i < length; i++) {
                    c = orignal.charAt(i);
                    buff.append(c);
                    if (isChineseChar(c)) {
                        // 遇到中文汉字，截取字节总数减1
                        i++;
                        if (i < length) {
                            c = orignal.charAt(i);
                            buff.append(c);
                        }
                    }
                    --count;
                    if (count == 0){
                        break;
                    }

                }
                return (new String(buff.toString().getBytes(), "UTF-8")) + "...";
            }
        }
        return orignal;
    }

    public static boolean CheckYouSelf(Long uid){
        if (!PanoramicAgricultureApplication.isLogined())
            return false;
        if (PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserRole() == Constants.ADMIN
                || uid == PanoramicAgricultureApplication.LOCAL_LOGINED_USER.getUserId())
            return true;
        return false;
    }

    public static  double numberFormat(double number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        double num = bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        return num;
    }
}
