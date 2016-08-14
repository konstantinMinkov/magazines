package com.kpi.magazines.utils.drive;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created by Konstantin Minkov on 17.07.2016.
 *
 * Provides a functionality for Google Drive.
 */
@Log4j2
public class DriveUtils {

    /**
     * Google Drive folder identifier. All files will be manipulated inside this folder (by calling static methods of
     * this class).
     */
    private static final String FILES_DIRECTORY_ID = ResourceBundle.getBundle("drive").getString("directoryId");

    /**
     * Creates file
     * @param name - name of the file.
     * @param inputStream - InputStream for this file.
     * @return id of the created file, or null if failed.
     */
    public static String createFile(String name, InputStream inputStream) {
        final File file = new File();
        final Drive drive;
        try {
            drive = DriveServiceFactory.getDriveService();
            file.setName(name);
            file.setParents(Collections.singletonList(FILES_DIRECTORY_ID));
            return drive.files()
                    .create(file, new InputStreamContent(null ,inputStream))
                    .setFields("id")
                    .execute()
                    .getId();
        } catch (IOException e) {
            log.catching(e);
        }
        return null;
    }

    /**
     * Deletes file by its id.
     * @param id - id of the file.
     * @return true if deleted successfully.
     */
    public static boolean deleteFile(String id) {
        try {
            final Drive drive = DriveServiceFactory.getDriveService();
            drive.files()
                    .delete(id)
                    .execute();
        } catch (IOException e) {
            log.catching(e);
            return false;
        }
        return true;
    }

    /**
     * Makes URL for public downloading.
     * @param id - id of the file.
     * @return URL to the file, if successful.
     */
    public static String getFileUrl(String id) {
        final Drive drive;
        try {
            drive = DriveServiceFactory.getDriveService();
            return drive.files()
                    .get(id)
                    .execute()
                    .getWebContentLink();
        } catch (IOException | NullPointerException e) {
            log.catching(e);
        }
        return null;
    }
}
