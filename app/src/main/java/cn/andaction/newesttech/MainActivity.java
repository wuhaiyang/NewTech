package cn.andaction.newesttech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cn.andaction.newesttech.bean.BoardContent;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tv_result;

  /*  @Inject
    ApiService apiService;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = (TextView) findViewById(R.id.tv_request_result);


        startLoadding();
    }

    private void startLoadding() {

      //1、第一种写法
    /*    //RXJAva 用在读取本地联系人成功后 map 中间层处理联系人的headername 封装返回给 事件订阅者
      Observable.create(new Observable.OnSubscribe<BoardContent>() { //创建一个事件
            @Override
            public void call(final Subscriber<? super BoardContent> subscriber) {
//                log("Observable on Thread -> " + Thread.currentThread().getName());
                Log.w(TAG + "1", Thread.currentThread().getName());
                ApiManager.getBoardClient().getBoardContents("22", 1, 10, new Callback<BoardContent>() {
                    @Override
                    public void success(BoardContent boardContent, Response response) {
                        subscriber.onNext(boardContent);
                    }
                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<BoardContent, Integer>() {
                    @Override
                    public Integer call(BoardContent boardContent) {
                        Log.w(TAG + "2", Thread.currentThread().getName());
                        // TODO: 2015/11/9 对网络请求的数据进行一次转换处理
                        if (boardContent.getCode() == 1) {
                            return boardContent.getData().size();
                        } else {
                            return 0;
                        }
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer boardContent) {
                        Log.w(TAG + "3", Thread.currentThread().getName());
                        tv_result.setText(boardContent.intValue() + "个");
                    }
                });*/
//        subscribe.unsubscribe(); //停止整个订阅链 改代码最好放在onDestroy 方法里面


        //第二种写法
        //绑定activity的生命周期
        AppObservable.bindActivity(this, Observable.create(new Observable.OnSubscribe<BoardContent>() { //创建一个事件
            @Override
            public void call(final Subscriber<? super BoardContent> subscriber) {
//                log("Observable on Thread -> " + Thread.currentThread().getName());
                Log.w(TAG + "1", Thread.currentThread().getName());
                ApiManager.getBoardClient().getBoardContents("22", 1, 10, new Callback<BoardContent>() {
                    @Override
                    public void success(BoardContent boardContent, Response response) {
                        subscriber.onNext(boardContent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
            }
        })).map(new Func1<BoardContent, Integer>() {
            @Override
            public Integer call(BoardContent boardContent) {
                return boardContent.getData().size();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer boardContent) {
                        Log.w(TAG + "3", Thread.currentThread().getName());
                        tv_result.setText(boardContent.intValue() + "个");
                    }
                });

        //第三种写法 代码最简洁
//        AppObservable.bindActivity(this,)  使用dagger技术进行依赖注入 （非反射实现、而是预编译实现的技术）
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
