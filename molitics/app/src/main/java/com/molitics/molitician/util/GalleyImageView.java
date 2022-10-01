package com.molitics.molitician.util;

public class GalleyImageView {


    public static int getImageCol(int currentPosition, int totalItem) {
        switch (totalItem) {
            case 1:
                return 6;

            case 2:
                return 3;

            case 3:
                if (currentPosition == 0) {
                    return 6;
                } else
                    return 3;
            case 4:
                    return 3;
            case 5:
                if (currentPosition == 0 || currentPosition == 1) {
                    return 3;
                } else
                    return 2;

            default:
                return 1;

        }
    }
}
