package com.rr.pm.socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rr.pm.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/9/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class SocketCLientActivity extends Activity implements View.OnClickListener {
    private TextView tvMessage;
    private EditText etReceive;
    private Button btnSend;
    private Socket clientSocket;
    private PrintWriter out;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);

        tvMessage = (TextView) findViewById(R.id.tv_message);
        etReceive = (EditText) findViewById(R.id.et_receive);
        btnSend = (Button) findViewById(R.id.btn_send);

        btnSend.setOnClickListener(this);

        Intent intent = new Intent(this, SocketServer.class);
        startService(intent);

        new Thread() {
            @Override
            public void run() {
                connectSocketServer();
            }
        }.start();
    }

    private void connectSocketServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                clientSocket = socket;
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (IOException e) {
                SystemClock.sleep(1000);
            }
        }

        try {
            //接受服务端消息
            BufferedReader bw = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()) {
                final String result = bw.readLine();
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMessage.setText(tvMessage.getText() + "\n" + "服务端:" + result);
                        }
                    });
                }
            }
            out.close();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();
        if (_id == R.id.btn_send) {
            sendMessage();
        }
    }

    private void sendMessage() {
        String msg = etReceive.getText().toString();
        //向服务端发送消息
        if (!TextUtils.isEmpty(msg) && null != out) {
            out.println(msg);
            tvMessage.setText(tvMessage.getText() + "\n" + "客户端:" + msg);
            etReceive.setText("");
        }
    }
}
