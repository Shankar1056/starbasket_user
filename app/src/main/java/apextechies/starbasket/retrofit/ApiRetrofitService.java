package apextechies.starbasket.retrofit;


import apextechies.starbasket.model.AddressModel;
import apextechies.starbasket.model.CartModel;
import apextechies.starbasket.model.CategoryModel;
import apextechies.starbasket.model.CheckoutModel;
import apextechies.starbasket.model.CommonModel;
import apextechies.starbasket.model.HomeBannerModel;
import apextechies.starbasket.model.LoginModel;
import apextechies.starbasket.model.PinCodeModel;
import apextechies.starbasket.model.PrescriptionModel;
import apextechies.starbasket.model.ProductGradientModel;
import apextechies.starbasket.model.ProductModel;
import apextechies.starbasket.model.StateModel;
import apextechies.starbasket.model.SubCategoryModel;
import apextechies.starbasket.model.SubSubCategory;
import apextechies.starbasket.model.UserOrderListModel;
import apextechies.starbasket.model.WishListMode;
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
    Call<CartModel> addUpdateCart(@Field("user_id") String user_id, @Field("product_id") String product_id, @Field("quantity") String quantity, @Field("name") String name, @Field("price") String price, @Field("image") String image, @Field("unit") String unit, @Field("seller_id") String seller_id);

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
    Call<LoginModel> userSignup(@Field("name") String name, @Field("lastname") String lastname, @Field("email") String email, @Field("password") String password, @Field("mobile") String mobile, @Field("address") String address, @Field("device_token") String device_token);

    @POST(ApiUrl.CHECKOUT)
    Call<CommonModel> doPayment(@Body CheckoutModel checkoutModel);

    @POST(ApiUrl.USERORDERLIST)
    @FormUrlEncoded
    Call<UserOrderListModel> userOrderList(@Field("user_id") String user_id);

    @POST(ApiUrl.INSERTPRESCRIPTION)
    @FormUrlEncoded
    Call<CommonModel> insertPrescription(@Field("user_id") String user_id, @Field("prescription") String prescription);

    @POST(ApiUrl.FORGETPASSWORD)
    @FormUrlEncoded
    Call<CommonModel> forgotPassword(@Field("email_id") String email_id, @Field("otp") String otp, @Field("password") String password, @Field("operation") String operation);

    @POST(ApiUrl.GETPRESCRIPTION)
    @FormUrlEncoded
    Call<PrescriptionModel> getPrescripton(@Field("user_id") String user_id);

    @POST(ApiUrl.CANCELORDER)
    @FormUrlEncoded
    Call<CommonModel> cancelOrder(@Field("transaction_id") String user_id);

    @POST(ApiUrl.SEARCHESPRODUCT)
    @FormUrlEncoded
    Call<ProductModel> getSearchedProduct(@Field("prod_name") String sub_cat_id);

    @POST(ApiUrl.PRODUCTGRADIENT)
    @FormUrlEncoded
    Call<ProductGradientModel> getProductGradient(@Field("prod_id") String prod_id);

    @POST(ApiUrl.CHANGEPASSWORD)
    @FormUrlEncoded
    Call<CommonModel> changePassword(@Field("email") String email, @Field("mobile") String mobile, @Field("password") String password, @Field("newpassword") String newpassword);

    @GET(ApiUrl.GTPINCODE)
    Call<PinCodeModel> getPincode();

    @POST(ApiUrl.ADDDELETEWISHIST)
    @FormUrlEncoded
    Call<WishListMode> addpdateWishList(@Field("user_id") String user_id, @Field("prod_id") String prod_id, @Field("operation") String operation);

    @POST(ApiUrl.GETWISHLIST)
    @FormUrlEncoded
    Call<WishListMode> getWishList(@Field("user_id") String user_id);

}
