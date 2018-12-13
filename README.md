![mvvm_logo](https://user-images.githubusercontent.com/29167110/49944214-c9479680-fe9e-11e8-960f-1d7ec51b22f0.jpg)


# Android MVVM Builder [![](https://jitpack.io/v/Yazan98/Android-MVVM-Builder.svg)](https://jitpack.io/#Yazan98/Android-MVVM-Builder)


Android MVVM Builder the builder library to make your application easier to build based ob Retrofit , RxJava2, Dagger2, ViewModel, LifeCycle and this library will manage your application 

> Note (This Library Need Android X don't support AppCompat)

**Android MVVM Builder** This Library will help you to build your application and Shorten a lot of writing codes 

**Never** waste your time again.

#### Structure

![untitled diagram](https://user-images.githubusercontent.com/29167110/49946309-9784fe80-fea3-11e8-9fd0-b0d70592ec04.jpg)


#### BaseRepository

* The BaseRepository will allowed you to make your api requests using rxJava and manage the Requests
* The BaseMultiRepository will take the all api requests and put them into api stack to solve many problems with Views and be save the Synchronyzed Operations

#### BaseViewModel This Class will manage the ViewLifeCycle and The Api Repository

# Setup 

## 1. Provide the gradle dependency

```
    build.gradle

    allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
    
    dependencies {
	        implementation "com.github.Yazan98:Android-MVVM-Builder:0.0.1"
	}
    
```

```
Maven

<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
	<dependency>
	    <groupId>com.github.Yazan98</groupId>
	    <artifactId>Android-MVVM-Builder</artifactId>
	    <version>0.0.1</version>
	</dependency>

```

# Let's see an Real Example of how This library Works.


#### 2. Add Your Service (That have the method type of the backend Request and the Response Body Like This Example And The Request should be Observable of RxJava)


```
public interface UserApi {

    @GET("users/username")
    Observable<Response<UserModel>> getUser();

}
```

#### 3. Define The Model of Your Request and this Model Should be implements Parcelable

```
public class UserModel implements Parcelable {

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("bio")
    private String bio;

    @SerializedName("created_at")
    private String createdAt;

    //Setters and Getters
    //Parcelable implemetation
}

```

#### 4. Declare Your Repository that will have the all Backend Request's


```
public class UserRepository extends BaseRepository<UserModel, UserApi> {

    private Observable<Response<UserModel>> userObservable;
    private UserApi userApi;

    //Inject the Class Using Dagger
    @Inject
    public UserRepository(String baseUrl) {
        super(baseUrl);
        createService();
    }

    private Observable<Response<UserModel>> getUserObservable(){
        return userObservable = userApi.getUser();
    }

    public void getUserInfo(){
        addRequest(getUserObservable());
    }

    @Override
    protected UserApi createService() {
        return userApi = getRetrofit().create(UserApi.class);
    }

}

```

#### 5. Define an interface that extends BaseView like this
###### this interface is your view for your screen (Activity, Fragment)

```
public interface UserView extends BaseView {

    void showLoading();

    void hideLoading();

    void onSuccess(UserModel userModel);

    void onError(String message);

}
```

#### 6. Define your ViewModel Class to Store and manage your view 


```

public class UserViewModel extends BaseViewModel<UserView, UserModel> {

    @Inject
    UserRepository repository;
    private UserComponent component;
    private MutableLiveData<UserModel> user;

    @Inject
    public UserViewModel() {
        repository = getComponent().getUserRepository();
    }

    @Override
    protected void initialViewModelState(StateType state) {
        if(state == StateType.INITIAL_STATE){
            sendGetUserRequest();
        }
    }

    public void getUserInfo(StateType state) {
        if (state == StateType.CURRENT_STATE) {
            if (getViewStatus()) {
                getView().onSuccess(getUser().getValue());
                return;
            }
        } else if (state == StateType.NEW_STATE) {
            sendGetUserRequest();
        }

    }

    private void sendGetUserRequest(){
        if (getViewStatus()) {
            getView().showLoading();
        }
        repository.getUserInfo();
        repository.setCallback(getCallback());
    }

    @Override
    public ResponseCallback<UserModel> getCallback() {
        return new ResponseCallback<UserModel>() {
            @Override
            public void onSuccess(UserModel content, Integer code) {
                if (getViewStatus()) {
                    if (code != null && (code >= 200 && code <= 250)) {
                        getUser().setValue(content);
                        getView().onSuccess(getUser().getValue());
                    } else {
                        getView().onError("Something Error : " + code);
                    }
                    getView().hideLoading();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (getViewStatus()) {
                    getView().onError(throwable.getMessage());
                    getView().hideLoading();
                }
            }
        };
    }

    public MutableLiveData<UserModel> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }

    public UserComponent getComponent() {
        if (component == null) {
            component = DaggerUserComponent
                    .builder()
                    .userRepoModule(new UserRepoModule())
                    .build();

        }
        return component;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (repository != null) {
            repository.destroyRepo();
        }

    }
}

```

#### 7. finally in your View will inject the ViewModel like this 

```

public class UserActivity extends BaseNetworkActivity<UserView, UserModel, UserViewModel> implements UserView {

    @Inject
    UserViewModel model;

    private ProgressBar loader;
    private ImageView image;
    private TextView name;
    private TextView bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        /**
         * Replace This with Butterknife or DataBinding
         */
        loader = findViewById(R.id.loading);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);

        if (model == null) {
            model = getViewModel();
            model.setView(this);
        }

        model.getUser().observe(this, user -> {

            Toast.makeText(this, "Observing Data...", Toast.LENGTH_SHORT).show();
            if (model.getUser().getValue() == null) {
                model.getUserInfo(StateType.CURRENT_STATE);
            } else {
                name.setText(user.getName());
                bio.setText(user.getBio());
                Picasso.get()
                        .load(user.getAvatarUrl())
                        .into(image);
            }
        });

        if (model.getUser().getValue() == null) {
           model.initialViewModelState(StateType.INITIAL_STATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (model != null) {
            model.onStop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (model != null) {
            model.onResume(reAttachView());
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
        name.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        bio.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
        bio.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(UserModel userModel) {
        name.setText(userModel.getName());
        bio.setText(userModel.getBio());
        Picasso.get()
                .load(userModel.getAvatarUrl())
                .into(image);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public BaseView reAttachView() {
        return this;
    }

    @Override
    protected UserViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void refresh(View view) {
            model.getUserInfo(StateType.NEW_STATE);
    }
}

```




