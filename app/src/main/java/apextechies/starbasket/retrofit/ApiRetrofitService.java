package apextechies.starbasket.retrofit;


import apextechies.starbasket.model.AddressModel;
import apextechies.starbasket.model.CartModel;
import apextechies.starbasket.model.CategoryModel;
import apextechies.starbasket.model.CommonModel;
import apextechies.starbasket.model.HomeBannerModel;
import apextechies.starbasket.model.LoginModel;
import apextechies.starbasket.model.ProductModel;
import apextechies.starbasket.model.StateModel;
import apextechies.starbasket.model.SubCategoryModel;
import apextechies.starbasket.model.SubSubCategory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Shankar on 1/27/2018.
 */

public interface ApiRetrofitService {
   /* @POST(ApiUrl.NOTIFICATION)
    @FormUrlEncoded
    Call<NotificationModel> otpLogin(@Field("school_id") String school_id);*/

    @GET(ApiUrl.CATEGORY)
    Call<CategoryModel> getCategory();

    @GET(ApiUrl.HOMEBANNER)
    Call<HomeBannerModel> getBanner();

    @POST(ApiUrl.HOMESUBCATEGORY)
    @FormUrlEncoded
    Call<SubCategoryModel> getSubCategory(@Field("cat_id") String cat_id);

    @POST(ApiUrl.HOMESUBSUBCATEGORY)
    @FormUrlEncoded
    Call<SubSubCategory> getSubSubCategory(@Field("sub_cat_id") String sub_cat_id);

    @POST(ApiUrl.PRODUCTLIST)
    @FormUrlEncoded
    Call<ProductModel> getProduct(@Field("sub_cat_id") String sub_cat_id, @Field("sub_sub_cat_id") String sub_sub_cat_id);

    @POST(ApiUrl.GETCARTITEM)
    @FormUrlEncoded
    Call<CartModel> getCartItem(@Field("user_id") String user_id);

    @POST(ApiUrl.ADDUPDATECART)
    @FormUrlEncoded
    Call<CartModel> addUpdateCart(@Field("user_id") String user_id, @Field("product_id") String product_id, @Field("quantity") String quantity, @Field("name") String name, @Field("price") String price, @Field("image") String image, @Field("unit") String unit );

    @POST(ApiUrl.DELETECARTITEM)
    @FormUrlEncoded
    Call<CommonModel> deleteCart(@Field("cart_id") String user_id);

    @GET(ApiUrl.GETSTATE)
    Call<StateModel> stateList();

    @POST(ApiUrl.GETADDRESS)
    @FormUrlEncoded
    Call<AddressModel> allAddress(@Field("user_id") String user_id);

    @POST(ApiUrl.ADDUPDATEADDRESS)
    @FormUrlEncoded
    Call<AddressModel> addUpdateAddress(@Field("user_id") String user_id, @Field("address_id") String address_id, @Field("state_id") String state_id, @Field("pincode") String pincode, @Field("address1") String address1, @Field("address2") String address2, @Field("name") String name, @Field("city") String city, @Field("landmark") String landmark);

    @POST(ApiUrl.DELETEADDRESS)
    @FormUrlEncoded
    Call<AddressModel> deleteAddress(@Field("address_id") String address_id);

    @POST(ApiUrl.USERLOGIN)
    @FormUrlEncoded
    Call<LoginModel> userLogin(@Field("email") String email, @Field("mobile") String mobile, @Field("password") String password);

    @POST(ApiUrl.USER_SIGNUP)
    @FormUrlEncoded
    Call<LoginModel> userSignup(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("mobile") String mobile, @Field("address") String address, @Field("device_token") String device_token);

}
