package com.kpi.magazines;

import com.kpi.magazines.utils.drive.DriveServiceFactory;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

/**
 * Created by Konstantin Minkov on 12.08.2016.
 */
public class Main {

    public static void main(String[] args) {
        final int b = 1 + 2;
        byte c = b + 2;
        try {
            DriveServiceFactory.getDriveService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(DigestUtils.shaHex("admin"));
    }
}

class Super {
    Super() throws Exception {

    }

    Object method() throws Exception {
        return 6;
    }
}

class Child extends Super {
    Child() throws Throwable {

    }

    @Override
    String method() throws RuntimeException {
        return "";
    }
}
