package apextechies.starbasket.retrofit;


import apextechies.starbasket.model.CartModel;
import apextechies.starbasket.model.CategoryModel;
import apextechies.starbasket.model.CommonModel;
import apextechies.starbasket.model.HomeBannerModel;
import apextechies.starbasket.model.ProductModel;
import apextechies.starbasket.model.SubCategoryModel;
import apextechies.starbasket.model.SubSubCategory;

/**
 * Created by Shankar on 1/27/2018.
 */

public interface ServiceMethods {
    void category(DownlodableCallback<CategoryModel> callback);
    void homeBanner(DownlodableCallback<HomeBannerModel> callback);
    void subCategory(String cat_id, DownlodableCallback<SubCategoryModel> callback);
    void subSubCategory(String sub_cat_id, DownlodableCallback<SubSubCategory> callback);
    void productList(String sub_cat_id,String sub_sub_cat_id, DownlodableCallback<ProductModel> callback);
    void cartItem(String user_id, DownlodableCallback<CartModel> callback);
    void addUpdaDteCart(String user_id,String product_id,String quantity,String name,String price,String image, String unit, DownlodableCallback<CartModel> callback);
    void deleteCart(String cart_id, DownlodableCallback<CommonModel> callback);

}
