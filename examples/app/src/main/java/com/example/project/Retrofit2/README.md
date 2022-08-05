# Retrofit2原理详解

## 使用

```java
public interface Api {


    @POST("/api/user/verificationPassword")
    Observable<Response<User>> login(@Body LoginParams loginParams);

}


public class RetrofitClient {
    private static Retrofit retrofit;

    private static RetrofitClient instance;

    private static OkHttpClient okHttpClient;


    public static RetrofitClient getInstance() {
        if(instance == null) {
            synchronized (RetrofitClient.class) {
                if(instance == null) {
                    initOkHttpClient();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(ENV.baseUrl)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }


    private static void initOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RequestHeadInterceptor())
                .build();
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}

```

在Activity中使用

```java
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Rxjava";

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        binding.login.setOnClickListener(view -> {
            RetrofitClient.getInstance().getApi().login(new LoginParams(false, "9189186758f7d7ff", "", "88971028", "aYRnq14w4UHnjY9zy7JKSYk/QBK9iXtQOjaNn4djkEXhp8KcxPonX7+OXp3Y94mJobWIuvbiNoBP4K4a9R8T37ONV7AG49wUKHhvK7flxPqrgnwvMj6ioGKSwhl42gl0raAHUJst6vOily3rhGwNLshkxQGkyWIUMRdlCFbQBs1czbvWMpPuhX8I4C4vfcscPo/8kvfPmtJFRCQdFhPMnUbPWKdWrAaAK0XU+SC/F/dib6sJotsn3WofdX3mniOyxoR5z9+R7aRc43FCDxM4rWVp44Q0dscssYWLb42jtr7i0AVRs2RQ/1S2f9hVSksGMAgaBEkRydXRlRgSMVekgg=="))
                    .map((result -> {
                        Log.d(TAG, "response: " + result.getCode());
                        Log.d(TAG, "1-> currentThreadName: " + Thread.currentThread().getName());
                        return result;
                    }))
                    .subscribeOn(Schedulers.io())
                    .map((Response<User> response) -> {
                        Log.d(TAG, "2-> currentThreadName: " + Thread.currentThread().getName());
                        return response.data;
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<User>() {
                        @Override
                        public void onNext(User value) {
                            Log.d(TAG, "onNext: " + value.getMobile());
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            e.printStackTrace();
                        }
                    });
        });

    }
}
```


## 原理



### retrofit.create



