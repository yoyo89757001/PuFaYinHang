package com.example.huiyiqiandaotv.tuisong_jg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.huiyiqiandaotv.MyApplication;
import com.example.huiyiqiandaotv.beans.BaoCunBean;
import com.example.huiyiqiandaotv.beans.BaoCunBeanDao;
import com.example.huiyiqiandaotv.beans.PiLiangBean;
import com.example.huiyiqiandaotv.beans.RenYuanInFo;
import com.example.huiyiqiandaotv.beans.RenYuanInFoDao;
import com.example.huiyiqiandaotv.beans.SheBeiInFoBean;
import com.example.huiyiqiandaotv.beans.SheBeiInFoBeanDao;
import com.example.huiyiqiandaotv.beans.TuiSongBean;
import com.example.huiyiqiandaotv.beans.ZhuJiBeanH;
import com.example.huiyiqiandaotv.beans.ZhuJiBeanHDao;
import com.example.huiyiqiandaotv.cookies.CookiesManager;
import com.example.huiyiqiandaotv.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.huiyiqiandaotv.MyApplication.TIMEOUT;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";
	public  OkHttpClient okHttpClient=null;
	private BaoCunBeanDao baoCunBeanDao=null;
	private BaoCunBean baoCunBean=null;
	private ZhuJiBeanH zhuJiBeanH=null;
	private ZhuJiBeanHDao zhuJiBeanHDao=null;
	private SheBeiInFoBeanDao sheBeiInFoBeanDao=null;
	private SheBeiInFoBean sheBeiInFoBean=null;
	private RenYuanInFoDao renYuanInFoDao=null;
	private boolean isA=true;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			baoCunBeanDao = MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
			baoCunBean = baoCunBeanDao.load(123456L);
			zhuJiBeanHDao = MyApplication.myApplication.getDaoSession().getZhuJiBeanHDao();
			sheBeiInFoBeanDao = MyApplication.myApplication.getDaoSession().getSheBeiInFoBeanDao();
			renYuanInFoDao = MyApplication.myApplication.getDaoSession().getRenYuanInFoDao();

			Bundle bundle = intent.getExtras();
		//	Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

				Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

				JsonObject jsonObject= GsonUtil.parse(bundle.getString(JPushInterface.EXTRA_MESSAGE)).getAsJsonObject();
				Gson gson=new Gson();
				TuiSongBean renShu=gson.fromJson(jsonObject,TuiSongBean.class);
				//1 新增 2修改//3是删除
				switch (renShu.getTitle()){
					case "主机管理":
						//先从老黄哪里拿主机数据。
						link_getHouTaiZhuJi(renShu.getContent().getId(),context,renShu.getContent().getStatus());
						break;
					case "设备管理":
						//先从老黄哪里拿门禁数据。
						link_getHouTaiMenJin(renShu.getContent().getId(),context,renShu.getContent().getStatus());
						break;
					case "人员管理":  //单个人员
						//先从老黄哪里拿人员数据。
						link_getHouTaiDanRen(renShu.getContent().getId(),context,renShu.getContent().getStatus());
						break;
					case "人员列表管理":
						//先从老黄哪里拿批量人员数据。
						baoCunBean.setHuiyiId(renShu.getContent().getId()+"");
						baoCunBeanDao.update(baoCunBean);
						link_getHouTaiPiLiang(renShu.getContent().getId(),context,renShu.getContent().getStatus());

						Intent intent2=new Intent("gxshipingdizhi");
						context.sendBroadcast(intent2);
						break;

				}
			//	processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);


			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

				//打开自定义的Activity
//				Intent i = new Intent(context, TestActivity.class);
//				i.putExtras(bundle);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

//	// 打印所有的 intent extra 数据
//	private static String printBundle(Bundle bundle) {
//		StringBuilder sb = new StringBuilder();
//		for (String key : bundle.keySet()) {
//			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
//				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
//				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
//					Logger.i(TAG, "This message has no Extra data");
//					continue;
//				}
//
//				try {
//					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//					Iterator<String> it =  json.keys();
//
//					while (it.hasNext()) {
//						String myKey = it.next();
//						sb.append("\nkey:" + key + ", value: [" +
//								myKey + " - " +json.optString(myKey) + "]");
//					}
//				} catch (JSONException e) {
//					Logger.e(TAG, "Get message extra JSON error!");
//				}
//
//			} else {
//				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//			}
//		}
//		return sb.toString();
//	}

	//从老黄后台拿主机信息
	private void link_getHouTaiZhuJi(int id, final Context context, final int status){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient=  new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();

		RequestBody body = new FormBody.Builder()
				.add("id",id+"")
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/getEntity.do");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "获取主机数据"+ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					ZhuJiBeanH zhaoPianBean=gson.fromJson(jsonObject,ZhuJiBeanH.class);
					//ws://192.168.2.58:9000/video
					String s1=zhaoPianBean.getHostUrl();
					if (s1.contains("//")){
						String s=s1.split("//")[1];
						baoCunBean.setTouxiangzhuji(zhaoPianBean.getHostUrl()+"/");
						baoCunBean.setZhujiDiZhi("ws://"+s+":9000/video");
						baoCunBeanDao.update(baoCunBean);
					}

					zhuJiBeanHDao.deleteAll();
					zhuJiBeanHDao.insert(zhaoPianBean);
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}
			}
		});
	}

	//从老黄后台拿门禁信息
	private void link_getHouTaiMenJin(int id, final Context context, final int status){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient=  new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();

		RequestBody body = new FormBody.Builder()
				.add("id",id+"")
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/getEquipment.do");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "获取设备信息"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					SheBeiInFoBean zhaoPianBean=gson.fromJson(jsonObject,SheBeiInFoBean.class);
					baoCunBean.setShipingIP(zhaoPianBean.getCamera_address());
					baoCunBean.setShiPingWeiZhi(zhaoPianBean.getCamera_position());
					baoCunBeanDao.update(baoCunBean);

					//保存到本地
					if (sheBeiInFoBeanDao.load(zhaoPianBean.getId())==null){
						//新增
						sheBeiInFoBeanDao.insert(zhaoPianBean);
					}
					//先登陆
					getOkHttpClient(context,status,zhaoPianBean, null);


				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}
			}
		});
	}

	//从老黄后台拿单人信息
	private void link_getHouTaiDanRen(int id, final Context context, final int status){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		OkHttpClient okHttpClient=  new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();

		RequestBody body = new FormBody.Builder()
				.add("id",id+"")
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(baoCunBean.getHoutaiDiZhi()+"/getSubject.do");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "获取单人信息"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					RenYuanInFo renYuanInFo=gson.fromJson(jsonObject,RenYuanInFo.class);


					//保存到本地
//					if (sheBeiInFoBeanDao.load(zhaoPianBean.getId())==null){
//						//新增
//						sheBeiInFoBeanDao.insert(zhaoPianBean);
//					}
					//先登陆
					getOkHttpClient(context,status,null,renYuanInFo);
					Log.d("MyReceiver", "登陆");

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}
			}
		});
	}

		//首先登录-->获取所有主机-->创建或者删除或者更新门禁
		private void getOkHttpClient(final Context context, final int status, final SheBeiInFoBean zhaoPianBean, final RenYuanInFo renYuanInFo){
		 zhuJiBeanH=zhuJiBeanHDao.loadAll().get(0);
			okHttpClient = new OkHttpClient.Builder()
					.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
					.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
					.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
					.cookieJar(new CookiesManager())
					.retryOnConnectionFailure(true)
					.build();

//			JSONObject json = new JSONObject();
//			try {
//				json.put("username", "test@megvii.com");
//				json.put("password", "123456");
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}


			//创建一个RequestBody(参数1：数据类型 参数2传递的json串)
		//	RequestBody requestBody = RequestBody.create(JSON, json.toString());

			RequestBody body = new FormBody.Builder()
					.add("username", zhuJiBeanH.getUsername())
					.add("password", zhuJiBeanH.getPwd())
					.build();

			Request.Builder requestBuilder = new Request.Builder();
			requestBuilder.header("User-Agent", "Koala Admin");
			requestBuilder.header("Content-Type","application/json");
			requestBuilder.post(body);
			requestBuilder.url(zhuJiBeanH.getHostUrl()+"/auth/login");
			final Request request = requestBuilder.build();

			Call mcall= okHttpClient.newCall(request);
			mcall.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {

					Log.d(TAG, "登陆失败"+e.getMessage());
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					String s=response.body().string();
					Log.d(TAG, "123   "+s);
					JsonObject jsonObject= GsonUtil.parse(s).getAsJsonObject();
					int i=1;
					i=jsonObject.get("code").getAsInt();
					if (i==0){
						//登录成功,后续的连接操作因为cookies 原因,要用 MyApplication.okHttpClient
						MyApplication.okHttpClient=okHttpClient;
						if (zhaoPianBean!=null){
							//对设备的操作 增删该等等
							link_getALLzhuji(MyApplication.okHttpClient,context,status,zhuJiBeanH,zhaoPianBean);
						}
						if (renYuanInFo!=null){
							//对人员的操作 增删该等等
							switch (status){
								case 1:
									//先传图片，再新增单个人员
									Bitmap bitmap1=null;
									try {
										  bitmap1 = Glide.with(context)
                                                .load(baoCunBean.getHoutaiDiZhi()+"/upload/compare/"+renYuanInFo.getPhoto_ids())
                                                .asBitmap()
                                                // .sizeMultiplier(0.5f)
                                                .fitCenter()
                                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                .get();
									} catch (InterruptedException | ExecutionException e) {
										e.printStackTrace();
									}
									if (bitmap1!=null){
										link_P1(zhuJiBeanH,compressImage(bitmap1),renYuanInFo,context);
									}else {
										link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
									}

									break;
								case 2:
							//修改人员
									Bitmap bitmap=null;
									try {
										  bitmap = Glide.with(context)
												.load(baoCunBean.getHoutaiDiZhi()+"/upload/compare/"+renYuanInFo.getPhoto_ids())
												.asBitmap()
												// .sizeMultiplier(0.5f)
												.fitCenter()
												.into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
												.get();
									} catch (InterruptedException | ExecutionException e) {
										e.printStackTrace();
									}
									if (bitmap!=null){
										link_P2(zhuJiBeanH,compressImage(bitmap),renYuanInFo,context);
									}else {

										link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
									}
									break;
								case 3:
									//删除人员
									link_ShanChuRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo);
									break;
							}

						}

					}else {
						Intent intent=new Intent("gxshipingdizhi");
						intent.putExtra("date","登录失败");
						context.sendBroadcast(intent);
					}

				}
			});


	}

	/**
	 * 压缩图片（质量压缩）
	 * @param bitmap
	 */
	public static File compressImage(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			//long length = baos.toByteArray().length;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String filename = format.format(date);
		File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			try {
				fos.write(baos.toByteArray());
				fos.flush();
				fos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	//	recycleBitmap(bitmap);
		return file;
	}
//	public static void recycleBitmap(Bitmap... bitmaps) {
//		if (bitmaps==null) {
//			return;
//		}
//		for (Bitmap bm : bitmaps) {
//			if (null != bm && !bm.isRecycled()) {
//				bm.recycle();
//			}
//		}
//	}

	private void link_XinZengMenJing(OkHttpClient okHttpClient, ZhuJiBeanH zhuji, final Context context, String box_token, final SheBeiInFoBean zhaoPianBean){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");


		RequestBody body = new FormBody.Builder()
				.add("box_id",box_token)
				.add("network_switcher","")
				.add("description","")
				.add("camera_address",zhaoPianBean.getCamera_address())
				.add("camera_position",zhaoPianBean.getCamera_position())
				.build();

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url(zhuji.getHostUrl()+"/system/screen");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "新增门禁"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					zhaoPianBean.setBid(jo.get("id").getAsInt());
					 sheBeiInFoBeanDao.update(zhaoPianBean);



				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}


	private void link_ShanChuMenJing(final OkHttpClient okHttpClient, ZhuJiBeanH zhuji, final Context context, final String box_token, final SheBeiInFoBean zhaoPianBean, final int i){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		SheBeiInFoBean sheBeiInFoBean=sheBeiInFoBeanDao.load(zhaoPianBean.getId());

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.delete()
				.url(zhuji.getHostUrl()+"/system/screen/"+sheBeiInFoBean.getBid());

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "删除门禁"+ss);
					if (i==1){
						link_XinZengMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean);
					}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	//先要获取局域网内所有可用主机
	private void link_getALLzhuji(final OkHttpClient okHttpClient, final Context context, final int status, final ZhuJiBeanH zhuJiBeanH, final SheBeiInFoBean zhaoPianBean){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.get()
				.url(zhuJiBeanH.getHostUrl()+"/system/boxes");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "获取主机数据"+ss);

					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					JsonArray jsonArray=jsonObject.get("data").getAsJsonArray();
				    JsonObject  joo=jsonArray.get(0).getAsJsonObject();
				    String box_token=joo.get("id").getAsString();
					if (box_token!=null){
						switch (status){
							case 1:
								//新增
								link_XinZengMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean);
								break;
							case 2:
								//修改
								link_ShanChuMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean,1);
								break;
							case 3:
								//删除
								link_ShanChuMenJing(okHttpClient,zhuJiBeanH,context,box_token,zhaoPianBean,2);
								break;

						}

					}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	//创建人员
	private void link_addRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo, int i){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		JSONObject json = new JSONObject();
			try {
				JSONArray jsonArray= new JSONArray();
				jsonArray.put(i);
				json.put("subject_type","0");
				json.put("name",renYuanInFo.getName());
				json.put("remark",renYuanInFo.getRemark());
				json.put("photo_ids",jsonArray);
				json.put("phone",renYuanInFo.getPhone());
				json.put("department",renYuanInFo.getDepartment());
				json.put("title",renYuanInFo.getTitle());
				json.put("job_number", renYuanInFo.getId());
				json.put("gender", renYuanInFo.getGender());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		Log.d(TAG, json.toString());
			RequestBody requestBody = RequestBody.create(JSON, json.toString());

//		RequestBody body = new FormBody.Builder()
//				.add("subject_type","0")
//				.add("name",renYuanInFo.getName())
//				.add("remark",renYuanInFo.getRemark())
//				.add("photo_ids",list.toString())
//				.add("phone",renYuanInFo.getPhone())
//				.add("department",renYuanInFo.getDepartment())
//				.add("title",renYuanInFo.getTitle())
//				.build();

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.post(requestBody)
				.url(zhuJiBeanH.getHostUrl()+"/subject");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "创建人员"+ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					if (jsonObject.get("code").getAsInt()==0){
						JsonObject jo=jsonObject.get("data").getAsJsonObject();
						renYuanInFo.setSid(jo.get("id").getAsInt());
						renYuanInFoDao.insert(renYuanInFo);
					}
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	//修改人员
	private void link_XiuGaiRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo, int i){
		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		JSONObject json = new JSONObject();
		try {
			JSONArray jsonArray= new JSONArray();
			jsonArray.put(i);
			json.put("subject_type","0");
			json.put("name",renYuanInFo.getName());
			json.put("remark",renYuanInFo.getRemark());
			json.put("photo_ids",jsonArray);
			json.put("phone",renYuanInFo.getPhone());
			json.put("department",renYuanInFo.getDepartment());
			json.put("title",renYuanInFo.getTitle());
			json.put("job_number", renYuanInFo.getId());
			json.put("gender", renYuanInFo.getGender());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.d(TAG, json.toString());
		RequestBody requestBody = RequestBody.create(JSON, json.toString());

//		RequestBody body = new FormBody.Builder()
//				.add("subject_type","0")
//				.add("name",renYuanInFo.getName())
//				.add("remark",renYuanInFo.getRemark())
//				.add("photo_ids",list.toString())
//				.add("phone",renYuanInFo.getPhone())
//				.add("department",renYuanInFo.getDepartment())
//				.add("title",renYuanInFo.getTitle())
//				.build();
		RenYuanInFo renYuanInFo1= renYuanInFoDao.load(renYuanInFo.getId());

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.put(requestBody)
				.url(zhuJiBeanH.getHostUrl()+"/subject/"+(renYuanInFo1==null?"":renYuanInFo1.getSid()));

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "修该人员"+ss);
//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					if (jsonObject.get("code").getAsInt()==0){
//						JsonObject jo=jsonObject.get("data").getAsJsonObject();
//						renYuanInFo.setSid(jo.get("id").getAsInt());
//						renYuanInFoDao.insert(renYuanInFo);
//					}
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	//删除人员
	private void link_ShanChuRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo){

		RenYuanInFo renYuanInFo1= renYuanInFoDao.load(renYuanInFo.getId());

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.delete()
				.url(zhuJiBeanH.getHostUrl()+"/subject/"+(renYuanInFo1==null?"":renYuanInFo1.getSid()));

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					Log.d("AllConnects", "删除人员"+ss);
//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					if (jsonObject.get("code").getAsInt()==0){
//						JsonObject jo=jsonObject.get("data").getAsJsonObject();
//						renYuanInFo.setSid(jo.get("id").getAsInt());
//						renYuanInFoDao.insert(renYuanInFo);
//					}
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}

	private void link_P1(final ZhuJiBeanH zhuJiBeanH, final File file, final RenYuanInFo renYuanInFo, final Context context) {

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
		;
		MultipartBody mBody;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

		RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"),file);

		builder.addFormDataPart("photo",file.getName(), fileBody1);
		//builder.addFormDataPart("subject_id","228");
		mBody = builder.build();

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(mBody)
				.url(zhuJiBeanH.getHostUrl()+ "/subject/photo");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
				Log.d("AllConnects", "请求识别失败" + e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				file.delete();
				Log.d("AllConnects", "请求识别成功" + call.request().toString());
				//获得返回体
				try {
					ResponseBody body = response.body();
					String ss = body.string();

					Log.d("AllConnects", "传照片" + ss);
					int ii=0;
					JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					ii=jo.get("id").getAsInt();
					if (ii!=0){
						link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
					}else {
						link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					}
				} catch (Exception e) {
					link_addRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});

	}
	public static final int TIMEOUT = 1000 * 150;
	private void link_P2(final ZhuJiBeanH zhuJiBeanH, final File file, final RenYuanInFo renYuanInFo, final Context context) {

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
		;
		MultipartBody mBody;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

		RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"),file);

		builder.addFormDataPart("photo",file.getName(), fileBody1);
		//builder.addFormDataPart("subject_id","228");
		mBody = builder.build();

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(mBody)
				.url(zhuJiBeanH.getHostUrl()+ "/subject/photo");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
				Log.d("AllConnects", "请求识别失败" + e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				file.delete();
				Log.d("AllConnects", "请求识别成功" + call.request().toString());
				//获得返回体
				try {
					ResponseBody body = response.body();
					String ss = body.string();

					Log.d("AllConnects", "传照片" + ss);
					int ii=0;
					JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					ii=jo.get("id").getAsInt();
					if (ii!=0){
						link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
					}else {
						link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					}
				} catch (Exception e) {
					link_XiuGaiRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});
	}


	//	<----------------------------------------------------------------------------------------------------------------------->
	//批量人员操作

	//从老黄后台拿批量信息
	private void link_getHouTaiPiLiang(int id, final Context context, final int status){
		if (status==3){
			//删除
			getOkHttpClient2(context,3);

		}else {
			//新增
			final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
			OkHttpClient okHttpClient = new OkHttpClient.Builder()
					.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
					.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
					.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//				.cookieJar(new CookiesManager())
					.retryOnConnectionFailure(true)
					.build();

			RequestBody body = new FormBody.Builder()
					.add("accountId", baoCunBean.getZhanghuId())
					.add("cardNumber", id + "")
					.build();
			Request.Builder requestBuilder = new Request.Builder()
					.header("Content-Type", "application/json")
					.post(body)
					.url(baoCunBean.getHoutaiDiZhi() + "/queryMeetingSubject.do");

			// step 3：创建 Call 对象
			Call call = okHttpClient.newCall(requestBuilder.build());

			//step 4: 开始异步请求
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					Log.d("AllConnects", "请求失败" + e.getMessage());
				}

				@Override
				public void onResponse(Call call, Response response) throws IOException {
					Log.d("AllConnects", "请求成功" + call.request().toString());
					//获得返回体
					try {

						ResponseBody body = response.body();
						String ss = body.string().trim();
						Log.d("AllConnects", "获取批量人员信息" + ss);

						JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
						Gson gson = new Gson();
						PiLiangBean zhaoPianBean = gson.fromJson(jsonObject, PiLiangBean.class);
						int size = zhaoPianBean.getObjects().size();

						for (int i = 0; i < size; i++) {
							PiLiangBean.ObjectsBean bbb = zhaoPianBean.getObjects().get(i);
							RenYuanInFo inFo = new RenYuanInFo();
							inFo.setAccountId(bbb.getAccountId());
							inFo.setCardNumber(bbb.getCardNumber());
							inFo.setDepartment(bbb.getDepartment());
							inFo.setGender(bbb.getGender());
							inFo.setId((long) bbb.getId());
							inFo.setName(bbb.getName());
							inFo.setPhone(bbb.getPhone());
							inFo.setRemark(bbb.getRemark());
							inFo.setPhoto_ids(bbb.getPhoto_ids());
							inFo.setTitle(bbb.getTitle());
							inFo.setJobStatus(bbb.getJobStatus());
							try {//保存到本地
								renYuanInFoDao.insert(inFo);
							} catch (Exception e) {
								Log.d("MyReceiver", "插入批量到本地异常" + e.getMessage());
							}
						}

						//先登陆
						getOkHttpClient2(context, 1);


					} catch (Exception e) {
						Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
					}
				}
			});
		}
	}

	//首先登录-->获取所有主机-->创建或者删除或者更新门禁
	private void getOkHttpClient2(final Context context, final int status){
		zhuJiBeanH=zhuJiBeanHDao.loadAll().get(0);
		okHttpClient = new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();

		RequestBody body = new FormBody.Builder()
				.add("username", zhuJiBeanH.getUsername())
				.add("password", zhuJiBeanH.getPwd())
				.build();

		Request.Builder requestBuilder = new Request.Builder();
		requestBuilder.header("User-Agent", "Koala Admin");
		requestBuilder.header("Content-Type","application/json");
		requestBuilder.post(body);
		requestBuilder.url(zhuJiBeanH.getHostUrl()+"/auth/login");
		final Request request = requestBuilder.build();

		Call mcall= okHttpClient.newCall(request);
		mcall.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Intent intent=new Intent("gxshipingdizhi");
				intent.putExtra("date","登录失败");
				context.sendBroadcast(intent);
				Log.d(TAG, "登陆失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

				String s=response.body().string();
				//Log.d(TAG, "123   "+s);
				JsonObject jsonObject= GsonUtil.parse(s).getAsJsonObject();
				int n=1;
				n=jsonObject.get("code").getAsInt();
				if (n==0){
					//登录成功,后续的连接操作因为cookies 原因,要用 MyApplication.okHttpClient
					MyApplication.okHttpClient=okHttpClient;
					List<RenYuanInFo> renYuanInFoList=renYuanInFoDao.loadAll();
					int size=renYuanInFoList.size();
				//	Log.d("MyReceiver", "size:" + size);
					if (status==3){
						//删除
						for (int i=0;i<size;i++){
							SystemClock.sleep(500);
							link_ShanChuRenYuan2(okHttpClient,context,renYuanInFoList.get(i));
						}

					}else {
					//	Log.d("MyReceiver", "图片-1");
						for (int i=0;i<size;i++){
							isA=true;
							XiaZaiTuPian(context,renYuanInFoList.get(i));
							while (isA){
								Log.d(TAG, "D");

							}
						}

					}


				}else {
					Intent intent=new Intent("gxshipingdizhi");
					intent.putExtra("date","登录失败");
					context.sendBroadcast(intent);
				}

			}
		});

	}

	//删除批量人员
	private void link_ShanChuRenYuan2(final OkHttpClient okHttpClient, final Context contex, final RenYuanInFo renYuanInFo){

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.delete()
				.url(zhuJiBeanH.getHostUrl()+"/subject/"+renYuanInFo.getSid());

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{
					ResponseBody body = response.body();
					String ss=body.string().trim();
					renYuanInFoDao.delete(renYuanInFo);
					//Log.d("AllConnects", "批量删除人员"+ss);

//					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					if (jsonObject.get("code").getAsInt()==0){
//						JsonObject jo=jsonObject.get("data").getAsJsonObject();
//						renYuanInFo.setSid(jo.get("id").getAsInt());
//						renYuanInFoDao.insert(renYuanInFo);
//					}
				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage()+"gggg");
				}

			}
		});
	}


	private void link_piliang_P2(final ZhuJiBeanH zhuJiBeanH, final File file, final RenYuanInFo renYuanInFo, final Context context) {

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.cookieJar(new CookiesManager())
				.retryOnConnectionFailure(true)
				.build();
		;
		MultipartBody mBody;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

		RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"),file);

		builder.addFormDataPart("photo",file.getName(), fileBody1);
		//builder.addFormDataPart("subject_id","228");
		mBody = builder.build();

		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(mBody)
				.url(zhuJiBeanH.getHostUrl()+ "/subject/photo");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
				//Log.d("AllConnects", "请求识别失败" + e.getMessage());
				//Log.d("MyReceiver", "图片3");
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				file.delete();
				//Log.d("AllConnects", "请求识别成功" + call.request().toString());
				//获得返回体
				try {
					ResponseBody body = response.body();
					String ss = body.string();

					//Log.d("AllConnects", "传照片" + ss);
					int ii=0;
					JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
					JsonObject jo=jsonObject.get("data").getAsJsonObject();
					ii=jo.get("id").getAsInt();
					if (ii!=0){
						link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,ii);
						//Log.d("MyReceiver", "图片4");
					}else {
						link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
						//Log.d("MyReceiver", "图片5");
					}
				} catch (Exception e) {
					//Log.d("MyReceiver", "图片6");
					link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
					//Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});
	}

	private void XiaZaiTuPian(Context context,RenYuanInFo renYuanInFo){
		//Log.d("MyReceiver", "图片0");
		Bitmap bitmap=null;
		try {
			  bitmap = Glide.with(context)
					.load(baoCunBean.getHoutaiDiZhi()+"/upload/compare/"+renYuanInFo.getPhoto_ids())
					.asBitmap()
					// .sizeMultiplier(0.5f)
					.fitCenter()
					.into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
					.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		if (bitmap!=null){
			link_piliang_P2(zhuJiBeanH,compressImage(bitmap),renYuanInFo,context);

		}else {
			link_addPiLiangRenYuan(MyApplication.okHttpClient,context,zhuJiBeanH,renYuanInFo,0);
		}
	}


	//创建批量人员
	private void link_addPiLiangRenYuan(final OkHttpClient okHttpClient, final Context contex, final ZhuJiBeanH zhuJiBeanH, final RenYuanInFo renYuanInFo, int i){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		JSONObject json = new JSONObject();
		try {
			JSONArray jsonArray= new JSONArray();
			jsonArray.put(i);
			json.put("subject_type","0");
			json.put("name",renYuanInFo.getName());
			json.put("remark",renYuanInFo.getRemark());
			json.put("photo_ids",jsonArray);
			json.put("phone",renYuanInFo.getPhone());
			json.put("department",renYuanInFo.getDepartment());
			json.put("title",renYuanInFo.getTitle());
			json.put("job_number", renYuanInFo.getId());
			json.put("gender", renYuanInFo.getGender());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//Log.d(TAG, json.toString());
		RequestBody requestBody = RequestBody.create(JSON, json.toString());

//		RequestBody body = new FormBody.Builder()
//				.add("subject_type","0")
//				.add("name",renYuanInFo.getName())
//				.add("remark",renYuanInFo.getRemark())
//				.add("photo_ids",list.toString())
//				.add("phone",renYuanInFo.getPhone())
//				.add("department",renYuanInFo.getDepartment())
//				.add("title",renYuanInFo.getTitle())
//				.build();

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.post(requestBody)
				.url(zhuJiBeanH.getHostUrl()+"/subject");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
					isA=false;
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//Log.d("MyReceiver", "请求成功"+call.request().toString());
				//获得返回体
				try{

					ResponseBody body = response.body();
					String ss=body.string().trim();
				//	Log.d("MyReceiver", "批量创建人员"+ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					if (jsonObject.get("code").getAsInt()==0){
						JsonObject jo=jsonObject.get("data").getAsJsonObject();
						renYuanInFo.setSid(jo.get("id").getAsInt());
						renYuanInFoDao.update(renYuanInFo);
					}
				}catch (Exception e){
					Log.d("MyReceiver", e.getMessage()+"gggg");
				}
				isA=false;

			}
		});
	}


}
