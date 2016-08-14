package com.kpi.magazines.commands;

import com.kpi.magazines.beans.Category;
import com.kpi.magazines.beans.Edition;
import com.kpi.magazines.config.Page;
import com.kpi.magazines.dao.basic.DaoManager;
import com.kpi.magazines.dao.basic.interfaces.CategoryDao;
import com.kpi.magazines.dao.basic.interfaces.EditionDao;
import com.kpi.magazines.utils.drive.DriveUtils;
import com.kpi.magazines.utils.validation.validators.Validator;
import com.kpi.magazines.utils.validation.validators.ValidatorProvider;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by Konstantin Minkov on 27.07.2016.
 */

@Log4j2
public class AdminEditionModificationCommand extends AbstractCommand {

    public AdminEditionModificationCommand() {
        super();
    }

    public AdminEditionModificationCommand(boolean isProtected) {
        super(isProtected);
    }

    @Override
    public Page executeGet(HttpServletRequest request, HttpServletResponse response) {
        final int id;
        final Edition edition;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            edition = DaoManager.getEditionDao().findById(id);
            if (edition == null) {
                return Page.MAIN.redirect();
            }
            request.setAttribute("edition", edition);
            request.setAttribute("category", DaoManager.getCategoryDao().findById(edition.getCategoryId()));
        } catch (Exception e) {
            log.catching(e);
        }
        request.setAttribute("categories", DaoManager.getCategoryDao().findAll());
        return Page.MODIFY_EDITION;
    }

    @Override
    public Page executePost(HttpServletRequest request, HttpServletResponse response) {
        final EditionDao editionDao = DaoManager.getEditionDao();
        final Validator validator =
                ValidatorProvider.getEditionModificationValidator((String) request.getAttribute("locale"));
        String driveId = null;
        final Edition edition = setEdition(request);
        final Category category = getCategory(request);
        request.setAttribute("edition", edition);
        request.setAttribute("category", category);
        if (category != null) {
            edition.setCategoryId(category.getId());
        }
        if ( !validator.isValid(request)) {
            request.setAttribute("errors", validator.getErrors());
            return Page.MODIFY_EDITION;
        }
        try {
            driveId = DriveUtils.createFile(request.getPart("image").getSubmittedFileName(),
                    initStream(request));
        } catch (IOException | ServletException e) {
            log.catching(e);
        }
        if (driveId == null) {
//            request.setAttribute("error", "Drive is unavailable.");
            return Page.MODIFY_EDITION;
        }
        edition.setDriveId(driveId);
        if (editionDao.findByName(edition.getName()) == null) {
            editionDao.create(edition);
            edition.setId(editionDao.findByName(edition.getName()).getId());
        } else {
            edition.setId(editionDao.findByName(edition.getName()).getId());
            editionDao.update(edition);
        }
        return Page.ADMIN_EDITION;
    }

    @Override
    public Page executePut(HttpServletRequest request, HttpServletResponse response) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Page executeDelete(HttpServletRequest request, HttpServletResponse response) {
        final EditionDao editionDao = DaoManager.getEditionDao();
        final Edition edition = new Edition();
        try {
            edition.setId(Integer.parseInt(request.getParameter("id")));
            editionDao.delete(edition);
            DriveUtils.deleteFile(edition.getDriveId());
        } catch (Exception e) {
            log.catching(e);
            return Page.MAIN.redirect();
        }
        return Page.ADMIN_PANEL.redirect();
    }

    private Edition setEdition(HttpServletRequest request) {
        final Edition edition = new Edition();
        edition.setName(request.getParameter("name"));
        edition.setDescription(request.getParameter("description"));
        try {
            edition.setPrice(Float.parseFloat(request.getParameter("price")));
        } catch (Exception ignored) {}
        try {
            edition.setEditionsPerMonth(Integer.parseInt(request.getParameter("editionsPerMonth")));
        } catch (Exception ignored) {}
        return edition;
    }

    private Category getCategory(HttpServletRequest request) {
        final String categoryNameNew = request.getParameter("categoryNew");
        final String categoryNameSelect = request.getParameter("categorySelect");
        final CategoryDao categoryDao = DaoManager.getCategoryDao();
        Category category;
        if (categoryNameNew != null && categoryNameNew.length() > 0) {
            category = categoryDao.findByName(categoryNameNew);
            if (category != null) {
                return category;
            }
            category = new Category();
            category.setName(categoryNameNew);
            categoryDao.create(category);
            return categoryDao.findByName(categoryNameNew);
        }
        if (categoryNameSelect != null && categoryNameSelect.length() > 0) {
            return categoryDao.findByName(categoryNameSelect);
        }
        return null;
    }

    private InputStream initStream(HttpServletRequest request) {
        final Part filePart;
        final InputStream inputStream;
        final BufferedInputStream buffer;
        int c;
        try {
            filePart = request.getPart("image");
            inputStream = filePart.getInputStream();
            return new BufferedInputStream(inputStream);
        } catch (IOException | ServletException e) {
            log.catching(e);
        }
        return null;
    }

    @Deprecated
    private String saveImage(InputStream stream, int id, HttpServletRequest request) {
        final ServletContext context = request.getServletContext();
        final String realContextPath = context.getRealPath(request.getContextPath());
        final String url = realContextPath + "images" + File.separator + "editions" + File.separator + "edition_" + id + ".jpg";
        final File file = new File(url);
        int b;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            while ((b = stream.read()) != -1) {
                outputStream.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
