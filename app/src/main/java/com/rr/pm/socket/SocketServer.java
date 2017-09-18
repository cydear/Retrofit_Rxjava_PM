package com.rr.pm.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/9/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class SocketServer extends Service {
    private boolean isServiceDestroyed = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new RuntimeException("no binder instance");
    }

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    private class TcpServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;

            try {
                //创建ServerSocket 提供监听接口
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                return;
            }
            while (!isServiceDestroyed) {
                try {
                    //接受客户端的请求
                    final Socket client = serverSocket.accept();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        //接受客户端传递的消息
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //向客户端发送消息
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        pw.println("您好，我是服务端");
        while (!isServiceDestroyed) {
            String result = br.readLine();
            Log.d("TAG", "==>收到客户端发来的消息了:" + result);
            if (TextUtils.isEmpty(result)) {
                Log.d("TAG", "==>客户端断开连接");
                break;
            }
            String message = "收到了客户端的信息为:" + result;
            //从客户端收到消息加工后在发送给客户端
            pw.println(message);
        }
        pw.close();
        br.close();
        client.close();
    }


    @Override
    public void onDestroy() {
        isServiceDestroyed = true;
        super.onDestroy();
    }
}
