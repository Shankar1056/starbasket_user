package apextechies.starbasket.listener;


import apextechies.starbasket.model.CartDataModel;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 Aug 2017 at 7:13 PM
 */

public interface OnCartListener {
	void onCartUpdate(CartDataModel item);
}
