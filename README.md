### MVP+RxJava+Retrofit实现登录功能

整体目录结构：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190409230120394.png)









先封装Application：

```java
public class BaseApp extends Application {
    private static BaseApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApp getApplication() {
        return mInstance;
    }
}
```



定义MVP三个接口：

```java
public interface IModel {
}
...
public interface IPresenter<V extends IView> {
    /**
     * 建立联系
     */
    void attachView(V view);

    /**
     * 取消联系
     */
    void detachView();

    /**
     * 判断View是否销毁
     */
    boolean isViewAttached();
}
...
public interface IView {
    void showLoading();
    void hideLoading();
}
```

然后定义BaseMVPActivity和BasePresenter

```java
public abstract class BaseMVPActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
        initPresenter();
        init();
    }

    private void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }else{
            throw new IllegalStateException("Please call mPresenter in BaseMVPActivity(createPresenter) to create!");
        }
    }

    protected abstract int createView();

    /**
     * 创建P层
     */
    protected abstract P createPresenter();

    protected abstract void init();

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
```

```java
public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }
}
```

然后我们定义Contract类，关联mvp

```java
public interface LoginContract {
    interface View extends IView {
        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败
         */
        void loginFail(String fail);

        /**
         * 登录异常
         */
        void loginError(String error);
    }

    interface Presenter {
        void login(String username, String password);
    }

    interface Model {
        Observable<LoginBean> login(String username, String password);
    }
}
```

接下来我们实现具体的MVP类：

```java
/**
 * M层实现
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public io.reactivex.Observable<LoginBean> login(String username, String password) {
        return HttpService.getInstance().userLogin(username, password);
    }
}
```

```java
/**
 * P层实现
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private LoginContract.Model model;
    private final CompositeDisposable mDisposable;

    public LoginPresenter() {
        this.model = new LoginModel();
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String username, String password) {
        model.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (isViewAttached()) {
                            if (loginBean.errorCode == 0) {
                                mView.loginSuccess();
                            } else {
                                mView.loginFail(loginBean.errorMsg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            mView.loginError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        mDisposable.clear();
    }
}
```

```java
/**
 * V层实现
 * <p>
 * 测试用户名密码：
 * username:testxx
 * password:123123123
 */
public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements LoginContract.View {
    private EditText et_username;
    private EditText et_password;

    @Override
    protected int createView() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void init() {
        setTitle("MVP");
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    public void login(View v) {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
            return;
        }

        mPresenter.login(username, password);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, "登录异常", Toast.LENGTH_SHORT).show();
    }
}
```