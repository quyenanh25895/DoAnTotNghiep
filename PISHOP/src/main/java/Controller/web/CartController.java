package Controller.web;

import Model.*;
import Service.IService.*;
import Utils.FormUtil;
import Utils.SessionUtil;
import paging.IPageble;
import paging.PageRequest;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Inject
    private IProductService productService;

    @Inject
    private IColorService colorService;

    @Inject
    private ICapacityService capacityService;

    @Inject
    private ICartService cartService;

    @Inject
    private ICartProductService cartProductService;

    @Inject
    private IImageService imageService;

    @Inject
    private IProductImageService productImageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = (UserModel) SessionUtil.getInstance().getValue(req, "USERMODEL");
        ColorModel colorModel = new ColorModel();
        colorModel.setListResult(colorService.findAll());
        CapacityModel capacityModel = new CapacityModel();
        capacityModel.setListResult(capacityService.findAll());
        IPageble pageable = new PageRequest();
        CartModel cartModel= cartService.findByUserID(user.getId());
        ProductModel productModel = FormUtil.toModel(ProductModel.class, req);
        productModel.setListResult(productService.findAll(pageable));

        CartProductModel cartProductModel = FormUtil.toModel(CartProductModel.class, req);
        ImageModel imageModels = FormUtil.toModel(ImageModel.class, req);

        cartProductModel.setListResult(cartProductService.findByCartID(cartModel.getId()));
        imageModels.setListResult(imageService.findAll());

        req.setAttribute("cartProducts", cartProductModel);
        req.setAttribute("products", productModel);
        req.setAttribute("colors", colorModel);
        req.setAttribute("capacities", capacityModel);
        req.setAttribute("images", imageModels);
        req.getRequestDispatcher("/views/web/Cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
