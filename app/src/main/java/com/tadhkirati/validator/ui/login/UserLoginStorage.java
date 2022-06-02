package com.tadhkirati.validator.ui.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class UserLoginStorage {

    private static final String PROFILE_PATH = "/profile/";

    public static String storeProfileImage(Context context, Bitmap profileImage) throws IOException {
        File storage = getProfileStorage(context);
        String randomId = UUID.randomUUID().toString();

        File storedProfileImage = new File(storage.getPath() + "/" + randomId);
        if (storedProfileImage.createNewFile()) {
            FileOutputStream out = new FileOutputStream(storedProfileImage);
            profileImage.compress(Bitmap.CompressFormat.PNG, 100, out);
            return randomId;

        } else {
            throw new IOException("could not store the image");
        }
    }

    public static Bitmap getStoredImageProfile(Context context, String imagePath) {
        File appStorage = getProfileStorage(context);
        File imageFile = new File(appStorage + "/" + PROFILE_PATH + "/" + imagePath);
        if (imageFile.exists()) {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }
        return null;
    }

    private static File getProfileStorage(Context context) {
        File storage = context.getFilesDir();
        File profileStorage = new File(storage.getPath() + PROFILE_PATH);
        if (profileStorage.exists()) {
            return profileStorage;
        }
        if (profileStorage.mkdir())
            return profileStorage;
        return null;
    }

    private static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }


}
