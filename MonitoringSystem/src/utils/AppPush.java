package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;

public class AppPush {

	// 采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	private String appId;
	private String appKey;
	private String masterSecret;
	private String host;

	/**
	 * 初始化个推参数
	 * 
	 * @throws IOException
	 */
	public AppPush() throws IOException {
		// 读取配置文件
		Properties properties = new Properties();
		InputStream inStream = AppPush.class
				.getResourceAsStream("getui-config.properties");
		properties.load(inStream);

		// 初始化个推参数
		// 采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
		this.appId = properties.getProperty("AppID");
		this.appKey = properties.getProperty("AppKey");
		this.masterSecret = properties.getProperty("MasterSecret");
		this.host = properties.getProperty("Host");
	}

	/**
	 * 向指定CID用户推送消息
	 * 
	 * @param CID
	 * @param title
	 * @param text
	 */
	public String push(String CID, String title, String text) {
		String result = null; // 推送结果
		IGtPush push = new IGtPush(host, appKey, masterSecret);

		// 设置推送模板
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(text);
		// 配置通知栏图标
		// template.setLogo("icon.png");
		// 配置通知栏网络图标，填写图标URL地址
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 设置打开的网址地址
		// template.setUrl("http://www.baidu.com");

		// 设置消息体
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);

		// 设置送达目标
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(CID);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			result = ret.getResponse().toString();
		} else {
			result = "服务器响应异常";
		}
		return result;
	}

	public String push(String title, String text) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);

		// 定义"点击链接打开通知模板"，并设置标题、内容、链接
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTitle(title);
		template.setText(text);
		template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(1);

		List<String> appIds = new ArrayList<String>();
		appIds.add(appId);

		// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
		AppMessage message = new AppMessage();
		message.setData(template);
		message.setAppIdList(appIds);
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);

		IPushResult ret = push.pushMessageToApp(message);
		return ret.getResponse().toString();
	}

}
