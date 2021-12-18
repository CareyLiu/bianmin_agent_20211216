package com.shendeng_bianmin.agent.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.StringUtils;
import com.shendeng_bianmin.agent.bean.Notice;
import com.shendeng_bianmin.agent.util.RxBus;
import com.tencent.bugly.Bugly;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

public class App extends Application {
    private static App instance;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bugly.init(getApplicationContext(), "4949afa776", false);
        initRongYun();
    }

    public void connectRongYun(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallbackEx() {
            /**
             * 数据库回调.
             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
             */
            @Override
            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                Log.i("rongYun", "数据库打开失败");
            }

            /**
             * token 无效
             */
            @Override
            public void onTokenIncorrect() {
                Log.i("rongYun", "token 无效");
            }

            /**
             * 成功回调
             * @param userId 当前用户 ID
             */
            @Override
            public void onSuccess(String userId) {
                //UIHelper.ToastMessage(mContext, "融云连接成功");
                Log.i("rongYun", "融云连接成功");
                PreferenceHelper.getInstance(getApplicationContext()).putString(AppConfig.RONGYUN_TOKEN, token);

//                String content = "再来一次";
//
//                Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
//                String targetId = "jcz_sub_230";
//
//                TextMessage messageContent = TextMessage.obtain(content);
//                Message message = Message.obtain(targetId, conversationType, messageContent);
//                RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback() {
//                    /**
//                     * 消息发送前回调, 回调时消息已存储数据库
//                     * @param message 已存库的消息体
//                     */
//                    @Override
//                    public void onAttached(Message message) {
//
//                    }
//
//                    /**
//                     * 消息发送成功。
//                     * @param message 发送成功后的消息体
//                     */
//                    @Override
//                    public void onSuccess(Message message) {
//
//                    }
//
//                    /**
//                     * 消息发送失败
//                     * @param message   发送失败的消息体
//                     * @param errorCode 具体的错误
//                     */
//                    @Override
//                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//
//                    }
//                });
            }

            /**
             * 错误回调
             * @param errorCode 错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("rongYun", "融云连接失败");
            }
        });

    }


    private void initRongYun() {
// 初始化. 建议在 Application 中进行初始化.
        String appKey = "cpj2xarlct6en";
        RongIM.init(instance, appKey);
        String rongYunToken = PreferenceHelper.getInstance(getApplicationContext()).getString("token_rong", "");
        if (!StringUtils.isEmpty(rongYunToken)) {
            connectRongYun(rongYunToken);

            RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
                /**
                 * 接收实时或者离线消息。
                 * 注意:
                 * 1. 针对接收离线消息时，服务端会将 200 条消息打成一个包发到客户端，客户端对这包数据进行解析。
                 * 2. hasPackage 标识是否还有剩余的消息包，left 标识这包消息解析完逐条抛送给 App 层后，剩余多少条。
                 * 如何判断离线消息收完：
                 * 1. hasPackage 和 left 都为 0；
                 * 2. hasPackage 为 0 标识当前正在接收最后一包（200条）消息，left 为 0 标识最后一包的最后一条消息也已接收完毕。
                 *
                 * @param message    接收到的消息对象
                 * @param left       每个数据包数据逐条上抛后，还剩余的条数
                 * @param hasPackage 是否在服务端还存在未下发的消息包
                 * @param offline    消息是否离线消息
                 * @return 是否处理消息。 如果 App 处理了此消息，返回 true; 否则返回 false 由 SDK 处理。
                 */
                @Override
                public boolean onReceived(final Message message, final int left, boolean hasPackage, boolean offline) {
                    Notice notice = new Notice();
                    notice.type = ConstanceValue.MSG_RONGYUN_REVICE;
                    // notice.content = status.
                    //* 用户被开发者后台封禁
                    notice.content = message;
                    RxBus.getDefault().sendRx(notice);
                    return false;
                }
            });


            RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
                /**
                 * 接收实时或者离线消息。
                 * 注意:
                 * 1. 针对接收离线消息时，服务端会将 200 条消息打成一个包发到客户端，客户端对这包数据进行解析。
                 * 2. hasPackage 标识是否还有剩余的消息包，left 标识这包消息解析完逐条抛送给 App 层后，剩余多少条。
                 * 如何判断离线消息收完：
                 * 1. hasPackage 和 left 都为 0；
                 * 2. hasPackage 为 0 标识当前正在接收最后一包（200条）消息，left 为 0 标识最后一包的最后一条消息也已接收完毕。
                 *
                 * @param message    接收到的消息对象
                 * @param left       每个数据包数据逐条上抛后，还剩余的条数
                 * @param hasPackage 是否在服务端还存在未下发的消息包
                 * @param offline    消息是否离线消息
                 * @return 是否处理消息。 如果 App 处理了此消息，返回 true; 否则返回 false 由 SDK 处理。
                 */
                @Override
                public boolean onReceived(final Message message, final int left, boolean hasPackage, boolean offline) {
                    return false;
                }
            });

            RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                /**
                 * 连接状态返回回调
                 * @param status 状态值
                 * CONN_USER_BLOCKED
                 * 用户被开发者后台封禁
                 * CONNECTED
                 * 连接成功。
                 * CONNECTING
                 * 连接中。
                 * DISCONNECTED
                 * 断开连接。
                 * KICKED_OFFLINE_BY_OTHER_CLIENT
                 * 用户账户在其他设备登录，本机会被踢掉线。
                 * NETWORK_UNAVAILABLE
                 * 网络不可用。
                 * SERVER_INVALID
                 * 服务器异常或无法连接。
                 * TOKEN_INCORRECT
                 * Token 不正确。
                 */
                @Override
                public void onChanged(ConnectionStatus status) {

                    Log.i("rongyun", status.getMessage());
                    Notice notice = new Notice();
                    notice.type = ConstanceValue.MSG_RONGYUN_STATE;
                    // notice.content = status.
                    //* 用户被开发者后台封禁
                    notice.content = status;
                    RxBus.getDefault().sendRx(notice);

                }
            });
        }

    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        RongIM.getInstance().logout();
        super.onTerminate();
    }
}
