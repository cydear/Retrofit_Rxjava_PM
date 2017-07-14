package com.rr.pm.biz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.rr.pm.R;
import com.rr.pm.base.ToolbarActivity;
import com.rr.pm.constants.EVConstants;
import com.rr.pm.designpattern.builder.Member;
import com.rr.pm.data.bean.Response;
import com.rr.pm.designpattern.clone.Course;
import com.rr.pm.designpattern.clone.Person;
import com.rr.pm.designpattern.observer.Observable;
import com.rr.pm.designpattern.observer.Observer;
import com.rr.pm.designpattern.observer.Weather;
import com.rr.pm.designpattern.proxy_dynamic.ProxySubject;
import com.rr.pm.designpattern.proxy_dynamic.RealSubject;
import com.rr.pm.designpattern.proxy_dynamic.Subject;
import com.rr.pm.designpattern.strategy.AirTravel;
import com.rr.pm.designpattern.strategy.TrainTravel;
import com.rr.pm.designpattern.strategy.Travel;
import com.rr.pm.designpattern.strategy.TravelPolicy;
import com.rr.pm.designpattern.strategy.WalkTravel;
import com.rr.pm.http.AbstarctSubscriber;
import com.rr.pm.http.HttpRequestWrapper;
import com.rr.pm.http.HttpSyncApi;
import com.rr.pm.http.RetrofitClient;
import com.rr.pm.http.RxUtil;
import com.rr.pm.http.service.ApiService;
import com.rr.pm.util.LogUtils;
import com.rr.pm.util.ToastUtils;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.xianglin.xlnodecore.common.service.facade.base.BaseReq;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

/**
 * 原型模式
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class MainActivity extends ToolbarActivity implements View.OnClickListener {
    private static final int PRE_REQ_CODE = 0x100;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        findViewById(R.id.btn_request).setOnClickListener(this);
        findViewById(R.id.btn_builder).setOnClickListener(this);
        findViewById(R.id.btn_observer).setOnClickListener(this);
        findViewById(R.id.btn_clone).setOnClickListener(this);
        findViewById(R.id.btn_policy).setOnClickListener(this);
        findViewById(R.id.btn_dynamic_proxy).setOnClickListener(this);
        findViewById(R.id.btn_icon).setOnClickListener(this);
        findViewById(R.id.btn_clock).setOnClickListener(this);
        findViewById(R.id.btn_load_patch).setOnClickListener(this);
        findViewById(R.id.btn_load_test).setOnClickListener(this);
        setToolBarTitle("首页");
        showBack(false);
        showRightTV(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_request) {
            request();
        } else if (id == R.id.btn_builder) {
            builder();
        } else if (id == R.id.btn_observer) {
            observer();
        } else if (id == R.id.btn_clone) {
            try {
                TestClone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.btn_policy) {
            policy();
        } else if (id == R.id.btn_dynamic_proxy) {
            dynamic_proxy();
        } else if (id == R.id.btn_icon) {
            testIconMarker();
        } else if (id == R.id.btn_clock) {
            testClock();
        } else if (id == R.id.btn_load_patch) {
            textLoadPatch();
        } else if (id == R.id.btn_load_test) {
            ToastUtils.show(this, "我是测试load patch");
        }
    }


    private void textLoadPatch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (flag != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRE_REQ_CODE);
            } else {
                loadPatch();
            }
        }
    }

    private void loadPatch() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed.apk";
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), filePath);
        } else {
            ToastUtils.show(this, "补丁文件不存在!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PRE_REQ_CODE) {
            if (grantResults != null && grantResults.length > 0) {
                loadPatch();
            } else {
                ToastUtils.show(this, "获取读取存储权限失败!");
            }
        }
    }

    private void testIconMarker() {
        startActivity(new Intent(this, TestIconActivity.class));
    }

    private void dynamic_proxy() {
        Subject subject = new RealSubject();
        ProxySubject proxy = new ProxySubject(subject);
        Subject sub = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), proxy);
        int sum = sub.operate(10, 25);
        ToastUtils.show(this, "sum=" + sum);
    }

    private void request() {
        BaseReq req = new BaseReq();
        req.setHeader(HttpSyncApi.makeHeader());
        RetrofitClient.getInstance()
                .getService(ApiService.class)
                .getNodeMessage(HttpRequestWrapper.getParamWrapper(EVConstants.GET_NODE_MESSAGE, req))
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(new AbstarctSubscriber() {
                    @Override
                    public void onFail(Response resp) {

                    }

                    @Override
                    public void onSuccess(Response resp) {

                    }
                });
    }

    /**
     * builder模式
     */
    private void builder() {
        Member member = new Member.Builder()
                .setName("tom")
                .setAge(20)
                .setSex("male")
                .setCertNo("1541257236327636726736")
                .build();
        LogUtils.d(member.toString());
    }

    private void observer() {
        Observable<Weather> observable = new Observable<>();

        Observer<Weather> observer1 = new Observer<Weather>() {
            @Override
            public void onUpdate(Observable<Weather> observable, Weather data) {
                LogUtils.d("observer1=" + data.getDescription());
            }
        };

        Observer<Weather> observer2 = new Observer<Weather>() {
            @Override
            public void onUpdate(Observable<Weather> observable, Weather data) {
                LogUtils.d("observer2=" + data.getDescription());
            }
        };

        observable.register(observer1);
        observable.register(observer2);

        Weather w1 = new Weather("多云转晴");
        Weather w2 = new Weather("阴天转小雨");
        Weather w3 = new Weather("大雨到暴雨");
        observable.notifyObservers(w1);
        observable.notifyObservers(w2);
        observable.notifyObservers(w3);
    }

    private void TestClone() throws CloneNotSupportedException {
        ArrayList<Course> arr = new ArrayList<>();
        Course c1 = new Course("70");
        arr.add(c1);
        Person p1 = new Person("tom", "male", 18);
        p1.setCourses(arr);
        Person p2 = (Person) p1.clone();

        Course c2 = new Course("100");
        arr.add(c2);
        p1.setCourses(arr);
        Person p3 = (Person) p1.clone();

        LogUtils.d("p1=" + p1.toString());
        LogUtils.d("p2=" + p2.toString());
        LogUtils.d("p3=" + p3.toString());

        Course c3 = new Course("99");
        p3.getCourses().add(c3);

        LogUtils.d("p3=" + p3.toString());
        LogUtils.d("p1=" + p1.toString());
    }

    private void policy() {
        TravelPolicy tp = TravelPolicy.newInstance();

        Travel walk = new WalkTravel();
        tp.setTravel(walk);
        tp.travel();

        Travel train = new TrainTravel();
        tp.setTravel(train);
        tp.travel();

        Travel air = new AirTravel();
        tp.setTravel(air);
        tp.travel();
    }

    public void testClock() {
        startActivity(new Intent(this, TestClockActivity.class));
    }
}
