package Service.IService;

import Model.CartProductModel;
import Model.ProductModel;
import paging.IPageble;

import java.util.List;

public interface ICartProductService {

    List<CartProductModel> findAll(IPageble pageble);

    List<CartProductModel> findJoin(IPageble pageble);

    List<CartProductModel> findByCartId(int cartId);

    List<ProductModel> findProductByCartId(int cartId);

    void saveProductToCart(Integer id, ProductModel productModel);

    List<CartProductModel> findByCartID(Integer id);

    void deleteProductFromCart(Integer[] id);

    void submitProductToCart(Integer[] ids);

    void denyProductFromCart(Integer[] ids);

    void confirmOrder(Integer id);

    void backOrder(Integer id);

    void confirmBackOrder(Integer id);

    Integer countItem();

    void deleteByProductID(Integer[] productID);

    void submitOrder(Integer id);
}
