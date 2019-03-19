package com.zxfdospy.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxfdospy.tmall.pojo.Product;
import com.zxfdospy.tmall.pojo.ProductImage;
import com.zxfdospy.tmall.service.ProductImageService;
import com.zxfdospy.tmall.service.ProductService;
import com.zxfdospy.tmall.util.ImageUtil;
import com.zxfdospy.tmall.util.Page;
import com.zxfdospy.tmall.util.UploadedImageFile;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
//@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, MultipartFile image) {
        productImageService.add(pi);
        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        if (ProductImageService.type_single.equals(pi.getType())) {
//            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder = "D:\\project\\public\\img\\productSingle";
            imageFolder_small = "D:\\project\\public\\img\\productSingle_small";
            imageFolder_middle = "D:\\project\\public\\img\\productSingle_middle";
        } else {
            imageFolder = "D:\\project\\public\\img\\productDetail";
        }

        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();
        try {
            image.transferTo(f);
            BufferedImage img = ImageUtil.change2jpg(f);
            ImageIO.write(img, "jpg", f);

            if (ProductImageService.type_single.equals(pi.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);

                ImageUtil.resizeImage(f, 56, 56, f_small);
                ImageUtil.resizeImage(f, 217, 190, f_middle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session) {
        ProductImage pi = productImageService.get(id);

        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if (ProductImageService.type_single.equals(pi.getType())) {
            imageFolder = "D:\\project\\public\\img\\productSingle";
            imageFolder_small = "D:\\project\\public\\img\\productSingle_small";
            imageFolder_middle = "D:\\project\\public\\img\\productSingle_middle";
            File imageFile = new File(imageFolder, fileName);
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();

        } else {
            imageFolder = "D:\\project\\public\\img\\productDetail";
            File imageFile = new File(imageFolder, fileName);
            imageFile.delete();
        }

        productImageService.delete(id);

        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product p = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);

        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_update")
    @ResponseBody
    public String update(ProductImage productImage, String edittype) {
        List<ProductImage> pis = productImageService.list(productImage.getPid(), productImage.getType());
        boolean isChange = false;
        if (edittype.equals("down")) {
            if (productImage.getLocation() == pis.size())
                return "success";
            for (ProductImage pi : pis) {
                if (!isChange) {
                    for (ProductImage pi2 : pis) {
                        if (pi2.getLocation().intValue() == productImage.getLocation().intValue()) {
                            pi2.setLocation(productImage.getLocation() - 1);
                            productImageService.update(pi2);
                            isChange = true;
                            break;
                        }
                    }
                }
                if (pi.getId().intValue() == productImage.getId().intValue()) {
                    productImageService.update(productImage);
                    break;
                }
            }
        } else {
            if (productImage.getLocation() < 0) {
                return "success";
            }
            for (ProductImage pi : pis) {
                if (!isChange) {
                    for (ProductImage pi2 : pis) {
                        if (pi2.getLocation().intValue() == productImage.getLocation().intValue()) {
                            pi2.setLocation(productImage.getLocation() + 1);
                            productImageService.update(pi2);
                            isChange = true;
                            break;
                        }
                    }
                }
                if (pi.getId().intValue() == productImage.getId().intValue()) {
                    productImageService.update(productImage);
                    break;
                }
            }
        }
        return "success";
    }
}