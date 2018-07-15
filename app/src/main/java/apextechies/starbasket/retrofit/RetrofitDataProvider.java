package apextechies.starbasket.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import apextechies.starbasket.R;
import apextechies.starbasket.model.CartModel;
import apextechies.starbasket.model.CategoryModel;
import apextechies.starbasket.model.CommonModel;
import apextechies.starbasket.model.HomeBannerModel;
import apextechies.starbasket.model.ProductModel;
import apextechies.starbasket.model.SubCategoryModel;
import apextechies.starbasket.model.SubSubCategory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shankar on 1/27/2018.
 */

public class RetrofitDataProvider extends AppCompatActivity implements ServiceMethods {
    Context context;

    private ApiRetrofitService createRetrofitService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiRetrofitService.class);
    }

    public RetrofitDataProvider(Context context) {
        this.context = context;
    }

    @Override
    public void category(final DownlodableCallback<CategoryModel> callback) {
        createRetrofitService().getCategory().enqueue(
                new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CategoryModel> call, @NonNull final Response<CategoryModel> response) {
                        if (response.isSuccessful()) {
                            CategoryModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CategoryModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void homeBanner(final DownlodableCallback<HomeBannerModel> callback) {
        createRetrofitService().getBanner().enqueue(
                new Callback<HomeBannerModel>() {
                    @Override
                    public void onResponse(@NonNull Call<HomeBannerModel> call, @NonNull final Response<HomeBannerModel> response) {
                        if (response.isSuccessful()) {
                            HomeBannerModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<HomeBannerModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void subCategory(String cat_id, final DownlodableCallback<SubCategoryModel> callback) {
        createRetrofitService().getSubCategory(cat_id).enqueue(
                new Callback<SubCategoryModel>() {
                    @Override
                    public void onResponse(@NonNull Call<SubCategoryModel> call, @NonNull final Response<SubCategoryModel> response) {
                        if (response.isSuccessful()) {
                            SubCategoryModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SubCategoryModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void subSubCategory(String sub_cat_id, final DownlodableCallback<SubSubCategory> callback) {
        createRetrofitService().getSubSubCategory(sub_cat_id).enqueue(
                new Callback<SubSubCategory>() {
                    @Override
                    public void onResponse(@NonNull Call<SubSubCategory> call, @NonNull final Response<SubSubCategory> response) {
                        if (response.isSuccessful()) {
                            SubSubCategory mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SubSubCategory> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }

    @Override
    public void productList(String sub_cat_id, String sub_sub_cat_id, final DownlodableCallback<ProductModel> callback) {
        createRetrofitService().getProduct(sub_cat_id,sub_sub_cat_id).enqueue(
                new Callback<ProductModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ProductModel> call, @NonNull final Response<ProductModel> response) {

                        if (response.isSuccessful()) {
                            ProductModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProductModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    @Override
    public void cartItem(String user_id, final DownlodableCallback<CartModel> callback) {
        createRetrofitService().getCartItem(user_id).enqueue(
                new Callback<CartModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CartModel> call, @NonNull final Response<CartModel> response) {

                        if (response.isSuccessful()) {
                            CartModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CartModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    @Override
    public void addUpdaDteCart(String user_id, String product_id, String quantity, String name, String price, String image, String unit, final DownlodableCallback<CartModel> callback) {
        createRetrofitService().addUpdateCart(user_id,product_id, quantity, name, price,image, unit).enqueue(
                new Callback<CartModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CartModel> call, @NonNull final Response<CartModel> response) {

                        if (response.isSuccessful()) {
                            CartModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CartModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }

    @Override
    public void deleteCart(String cart_id, final DownlodableCallback<CommonModel> callback) {
        createRetrofitService().deleteCart(cart_id).enqueue(
                new Callback<CommonModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonModel> call, @NonNull final Response<CommonModel> response) {

                        if (response.isSuccessful()) {
                            CommonModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                //Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());

                    }
                }
        );
    }


   /* @Override
    public void notification(String school_id, final DownlodableCallback<NotificationModel> callback) {
        createRetrofitService().otpLogin(school_id).enqueue(
                new Callback<NotificationModel>() {
                    @Override
                    public void onResponse(@NonNull Call<NotificationModel> call, @NonNull final Response<NotificationModel> response) {
                        if (response.isSuccessful()) {
                            NotificationModel mobileRegisterPojo = response.body();
                            callback.onSuccess(mobileRegisterPojo);

                        } else {
                            if (response.code() == 401) {
                                callback.onUnauthorized(response.code());
                            } else {
                                Utilz.closeDialog();
                                Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong_error_message), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                        Log.d("Result", "t" + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                }
        );
    }*/

}