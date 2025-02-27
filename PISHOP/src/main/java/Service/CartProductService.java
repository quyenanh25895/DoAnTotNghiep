package Service;

import DAO.IDAO.ICartProductDAO;
import DAO.IDAO.IProductDAO;
import Model.CartProductModel;
import Model.ProductModel;
import Service.IService.ICartProductService;
import paging.IPageble;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CartProductService implements ICartProductService {

    @Inject
    private ICartProductDAO cartProductDAO;

    @Inject
    private IProductDAO productDAO;

    @Override
    public List<CartProductModel> findAll(IPageble pageble) {
        return cartProductDAO.findAll(pageble);
    }

    @Override
    public List<CartProductModel> findJoin(IPageble pageble) {
        return cartProductDAO.findJoin(pageble);
    }

    @Override
    public List<CartProductModel> findByCartId(int cartId) {
        return cartProductDAO.findByCartID(cartId);
    }

    @Override
    public List<ProductModel> findProductByCartId(int cartId) {
        List<ProductModel> productModels = new ArrayList<>();
        List<CartProductModel> cartProductModels = cartProductDAO.findByCartID(cartId);
        List<Integer> productIds = new ArrayList<>();

        for (CartProductModel cartProductModel : cartProductModels) {
            productIds.add(cartProductModel.getId());
        }

        for (int i = 0; i < productIds.size(); i++) {
            ProductModel p = productDAO.findOne(cartProductModels.get(i).getProductID());
            Integer[] colorID = new Integer[1];
            colorID[0] = cartProductModels.get(i).getColorID();
            Integer[] capacityID = new Integer[1];
            capacityID[0] = cartProductModels.get(i).getColorID();

            p.setQuantity(cartProductModels.get(i).getQuantity());
            p.setColorIDs(colorID);
            p.setCapacityIDs(capacityID);
            productModels.add(p);
        }

        return productModels;
    }

    @Override
    public void saveProductToCart(Integer id, ProductModel productModel) {
        ProductModel oldProduct = productDAO.findOne(productModel.getId());
        Integer oldQuantity = oldProduct.getQuantity();
        Integer newQuantity = productModel.getQuantity();
        oldProduct.setQuantity(oldQuantity - newQuantity);
        productDAO.update(oldProduct);
        cartProductDAO.saveProductToCart(id, productModel);
    }

    @Override
    public List<CartProductModel> findByCartID(Integer id) {
        return findByCartId(id);
    }

    @Override
    public void deleteProductFromCart(Integer[] ids) {

        for (Integer id : ids) {
            CartProductModel cartProductModel = cartProductDAO.findOne(id);
            ProductModel oldProduct = productDAO.findOne(cartProductModel.getProductID());
            Integer oldQuantity = oldProduct.getQuantity();
            Integer newQuantity = cartProductModel.getQuantity();
            oldProduct.setQuantity(oldQuantity + newQuantity);
            productDAO.update(oldProduct);
            cartProductDAO.delete(id);
        }
    }

    @Override
    public void submitProductToCart(Integer[] ids) {
        for(Integer id : ids) {
            cartProductDAO.submitCartProduct(id);
        }
    }

    @Override
    public void denyProductFromCart(Integer[] ids) {
        for (Integer id : ids) {
            cartProductDAO.denyCartProduct(id);
        }
    }

    @Override
    public void confirmOrder(Integer id) {
        cartProductDAO.confirmOrder(id);
    }

    @Override
    public void backOrder(Integer id) {
        cartProductDAO.backOrder(id);
    }

    @Override
    public void confirmBackOrder(Integer id) {
        CartProductModel cartProductModel = cartProductDAO.findOne(id);
        ProductModel oldProduct = productDAO.findOne(cartProductModel.getProductID());
        Integer oldQuantity = oldProduct.getQuantity();
        Integer newQuantity = cartProductModel.getQuantity();
        oldProduct.setQuantity(oldQuantity + newQuantity);
        productDAO.update(oldProduct);
        cartProductDAO.confirmBackOrder(id);
    }

    @Override
    public Integer countItem() {
        return cartProductDAO.getTotalItem();
    }

    @Override
    public void deleteByProductID(Integer[] productID) {
        for (Integer id : productID) {
            cartProductDAO.deleteByProductID(id);
        }
    }

    @Override
    public void submitOrder(Integer id) {
        cartProductDAO.submitOrder(id);
    }


}
