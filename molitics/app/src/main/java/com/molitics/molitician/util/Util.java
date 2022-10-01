package com.molitics.molitician.util;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.db.MoliticsDatabase;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.ui.dashboard.ActivityFragment;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.register.UserModel;
import com.molitics.molitician.ui.dashboard.termCondition.TermConditionActivity;
import com.molitics.molitician.ui.dashboard.userProfile.model.UserProfileModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.molitics.molitician.BuildConfig;
import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;
import com.squareup.picasso.Picasso;

import au.com.bytecode.opencsv.CSVWriter;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.facebook.FacebookSdk.getCacheDir;
import static com.molitics.molitician.constants.PrefConstant.DEVICE_ID_FOR_DEVICE;

/**
 * Created by rahul on 12/16/2016.
 */

public class Util {

    public static ArrayList<Integer> getLeaderDisplayType() {
        ArrayList<Integer> fragment_display_type = new ArrayList<>();
        fragment_display_type.add(1);
        fragment_display_type.add(3);
        fragment_display_type.add(2);
        return fragment_display_type;
    }

    public static ArrayList<Integer> getVoiceDisplayType() {
        ArrayList<Integer> fragment_display_type = new ArrayList<>();
        fragment_display_type.add(1);
        fragment_display_type.add(2);
        fragment_display_type.add(3);
        return fragment_display_type;
    }

    public static ArrayList<String> getVoiceFragmentTitle() {
        ArrayList<String> fragment_title = new ArrayList<>();
        fragment_title.add("Trending");
        fragment_title.add("National");
        fragment_title.add("State");
        return fragment_title;
    }


    public static ArrayList<String> setLeaderProfileTitle(Context mContext) {
        ArrayList<String> mTitle = new ArrayList<>();
        mTitle.add(mContext.getResources().getString(R.string.publics));
        mTitle.add(mContext.getResources().getString(R.string.news));
        mTitle.add(mContext.getResources().getString(R.string.about));
        return mTitle;
    }

    public static void showToastLong(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static void saveUserPreference(DeviceRegistration user) {
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE, user.getState() != null ? user.getState() : 0);
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_DISTRICT_VALUE, user.getDistrict() != null ? user.getDistrict() : 0);
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_MP_VALUE, user.getMp() != null ? user.getMp() : 0);
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE, user.getMla() != null ? user.getMla() : 0);
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_STATE_KEY, user.getState_name() != null ? user.getState_name() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_DISTRICT_KEY, user.getDistrict_name() != null ? user.getDistrict_name() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_MP_KEY, user.getMp_name() != null ? user.getMp_name() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_MLA_KEY, user.getMla_name() != null ? user.getMla_name() : "");
        PrefUtil.putString(Constant.PreferenceKey.USER_IMAGE, user.getPersonPhoto() != null ? user.getPersonPhoto() : "");
    }

    @Deprecated
    public static void saveUserInfo(DeviceRegistration userProfileModel) {

        PrefUtil.putString(Constant.PreferenceKey.USER_NAME, userProfileModel.getUserName());
        PrefUtil.putString(Constant.PreferenceKey.USER_CONTACT, userProfileModel.getUserContact());
        //  PrefUtil.putString(Constant.PreferenceKey.);

    }

    public static void saveNewUserInfo(UserModel userModel) {
        PrefUtil.putString(Constant.PreferenceKey.USER_NAME, userModel.getUsername());
        PrefUtil.putString(Constant.PreferenceKey.AUTH_KEY, userModel.getAuth_key());
        PrefUtil.putString(Constant.PreferenceKey.NEXT_STRING, userModel.getNext());
        PrefUtil.putInt(Constant.PreferenceKey.USER_ID, userModel.getUserId());

        // PrefUtil.putInt(Constant.PreferenceKey.USER_ID, deviceRegistration.getUserId());
        //PrefUtil.putString(Constant.PreferenceKey.API_KEY, deviceRegistration.getApiKey());
        //PrefUtil.putString(Constant.PreferenceKey.USER_CONTACT, userProfileModel.getUserContact());
        //  PrefUtil.putString(Constant.PreferenceKey.);
    }


    @Deprecated
    public static void saveEditUserPreference(UserProfileModel user) {
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE, user.getStateId());
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_DISTRICT_VALUE, user.getDistrict() != null ? user.getDistrictId() : 0);
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_MP_VALUE, user.getMp() != null ? user.getMpId() : 0);
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE, user.getMla() != null ? user.getMlaId() : 0);
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_POLITICAL_PARTY_VALUE, user.getPoliticalOrientation() != null ? user.getPoliticalOrientationId() : 0);
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_STATE_KEY, user.getState() != null ? user.getState() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_DISTRICT_KEY, user.getDistrict() != null ? user.getDistrict() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_MP_KEY, user.getMp() != null ? user.getMp() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_MLA_KEY, user.getMla() != null ? user.getMla() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_MLA_KEY, user.getMla() != null ? user.getMla() : "");
        PrefUtil.putString(Constant.PreferenceKey.DEFAULT_POLITICAL_PARTY_KEY, user.getMla() != null ? user.getPoliticalOrientation() : "");
    }

    @Deprecated
    public static void saveEditUserInfo(UserProfileModel userProfileModel) {
        PrefUtil.putString(Constant.PreferenceKey.USER_NAME, userProfileModel.getName());
        PrefUtil.putString(Constant.PreferenceKey.USER_CONTACT, userProfileModel.getMobile());
        //  PrefUtil.putString(Constant.PreferenceKey.);
    }

    // Validate  error;
    public static void validateError(final Context context, ApiException
            apiException, ViewGroup view, ViewRefreshListener viewRefreshListener, Integer display_item_size) {
        if (apiException.getCode() >= 1000 && apiException.getCode() < 1100) {
            if (apiException.getCode() == 1001) {
                DialogClass.showSessionOut(context, apiException.getMessage());
            } else {
                DialogClass.showAlert(context, apiException.getMessage());
            }
        } else if (apiException.getCode() == 40000) {
            DialogClass.showAlert(context, apiException.getMessage());
        } else if (apiException.getCode() == 2004) {
            if (view != null && context != null) {
                if (display_item_size != null && display_item_size == 0) {

                    view.setVisibility(View.VISIBLE);
                    view.removeAllViews();
                    LayoutInflater view_inflater = (LayoutInflater)
                            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View v = null;
                    if (view_inflater != null) {
                        v = view_inflater.inflate(R.layout.no_result_layout, null);

                        TextView no_result_message = v.findViewById(R.id.no_result_message);
                        no_result_message.setText(R.string.no_data_available);
                        view.addView(v, 0, new
                                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                }
            }
        } else if ((apiException.getCode() >= 400 && apiException.getCode() < 500) || apiException.getCode() == 100003) {
            if (view != null && context != null) {
                if (display_item_size != null) {
                    //   Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
                    if (display_item_size == 0)
                        Util.addNetworkNotFoundView(context, view, viewRefreshListener);
                    else
                        Util.addMiniNetworkFailView(context, view);
                }
            }
        } else {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing()) {
                    return;
                } else {
                    DialogClass.showAlert(context, apiException.getMessage());
                }
            }
        }
    }

    public static void addNetworkNotFoundView(Context context, final ViewGroup viewGroup, final ViewRefreshListener viewRefreshListner) {
        try {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.network_error_layout, null);
                TextView btnRefresh = v.findViewById(R.id.btn_refresh);
                TextView network_error = v.findViewById(R.id.network_error);
                network_error.setText("Had some trouble loading this\r\n page. Please try again");
                btnRefresh.setOnClickListener(view -> {
                    viewGroup.setVisibility(View.GONE);
                    viewRefreshListner.onRefereshClick();
                });
                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addMiniNetworkFailView(Context mContext, final ViewGroup viewGroup) {
        try {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View m_view = inflater.inflate(R.layout.no_network_mini_layout, null);
                viewGroup.addView(m_view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int[] PASTEL_COLORS = {
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80), Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53), Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157), Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(254, 247, 120),
            Color.rgb(106, 167, 134), Color.rgb(53, 194, 209)
    };

    // Get a MemoryInfo object for the device's current memory status.
    public static ActivityManager.MemoryInfo getAvailableMemory(Context mContext) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    public static File saveImage(Context mContext, Bitmap bitmap) {
        FileOutputStream output;

        // Retrieve the image from the res folder
      /*  bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.wallpaper);*/

        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File direct = new File(filepath
                + "/Molitics/");
        if (!direct.exists()) {
            direct.mkdirs();
        }

        // Create a name for the saved image
        File file = new File(direct, "MOL_IMG_" + System.currentTimeMillis() + ".jpg");


        try {

            output = new FileOutputStream(file);

            // Compress into png format image_small_icon from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();

            MediaScannerConnection.scanFile(mContext, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    public static File saveImageAsAuth(Context mContext, Bitmap bitmap, String auth_key) {
        FileOutputStream output;

        File filepath = Environment.getExternalStorageDirectory();

        File direct = new File(filepath
                + "/Molitics/");
        if (!direct.exists()) {
            direct.mkdirs();
        }
        // Create a name for the saved image
        File file = new File(direct, "MOL_IMG_" + auth_key + ".jpg");
        try {
            output = new FileOutputStream(file);

            // Compress into png format image_small_icon from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();

            MediaScannerConnection.scanFile(mContext, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    public static void deleteImageAsAuth(String auth_key) {
        String yourFilePath = Environment.getExternalStorageDirectory() + "/Molitics/" + "MOL_IMG_" + auth_key + ".jpg";
        File file_delete = new File(yourFilePath);
        file_delete.deleteOnExit();
    }

    public static String getImageFromStorage(Context context, String auth_key) {
        return Environment.getExternalStorageDirectory() + "/Molitics/" + "MOL_IMG_" + auth_key + ".jpg";
    }


    public static File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = new File(
                    Environment.getExternalStorageDirectory()
                            + Constant.CAMERA_DIR
                            + "molitics"
            );

            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("CameraSample", "failed to create directory");
                    return null;
                }
            }
        }

        return storageDir;
    }

    public static File getVideoAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = new File(
                    Environment.getExternalStorageDirectory()
                            + Constant.CAMERA_DIR

            );

            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
            File file = new File(storageDir, String.valueOf(System.currentTimeMillis()));
            if (file.exists()) file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }

        return storageDir;
    }


    public static void hideKeyBoard(Activity mActivity) {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void showLog(String tag, String message) {
        if (BuildConfig.DEBUG && message != null)
            Log.d(tag, message);
    }

    public static void showLogE(String tag, String message) {
        if (BuildConfig.DEBUG && message != null)
            Log.e(tag, message);

    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static void logout(Activity context) {
        //PrefUtil.putBoolean(Constant.PreferenceKey.IS_LOGIN, false);
        PrefUtil.clearAll();
        new Thread(() -> {
            try {
                MoliticsDatabase.getAppDatabase(context).getAllVoiceRecordDao().deleteAllRecord();
            } catch (Exception e) {
                e.printStackTrace();
                showLogE("Database", "Error in writeScanRecords");
            }
        }).start();

        Intent intent = new Intent(context, ActivityFragment.class);
        intent.putExtra(Constant.INTENT_FROM, Constant.From.ACTIVITY_SIGN_IN_FRAGMENT);
        context.startActivity(intent);
        context.finish();
    }

    public static String getAction(int action) {
        if (action == 1) {
            return "Upvote";

        } else if (action == 2) {
            return "Downvote";
        }
        return "";
    }

    static File getContactFromDevice(Context mContext) {

        File outputDir = getCacheDir(); // context being the Activity pointer
        File outputFile = null;
        HashMap<String, String> contact_hash_map = new HashMap<>();
        try {
            outputFile = File.createTempFile("contact", ".csv", outputDir);
            Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            CSVWriter writer = null;
            writer = new CSVWriter(new FileWriter(outputFile));
            if (phones != null) {
                while (phones.moveToNext()) {
                    String localPhoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    localPhoneNumber = localPhoneNumber.replaceAll("\\D+", "");
                    if (localPhoneNumber.length() >= 10) {
                        // String user_number = localPhoneNumber.substring(localPhoneNumber.length() - 11);
                        String user_name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        contact_hash_map.put(localPhoneNumber, user_name);
                    }
                }
            }
            for (String key : contact_hash_map.keySet()) {
                String value = contact_hash_map.get(key);

                String[] contact_details = (value + "#" + key).split("#");
                writer.writeNext(contact_details);
            }
            writer.close();
            assert phones != null;
            phones.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    public static int getRecyclerItemVisiblePosition(int position, RecyclerView recyclerView) {
        if (recyclerView != null) {
            int visible_more_than_30 = 0;
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());

            final int firstPosition = layoutManager.findFirstVisibleItemPosition() + position;
            final int lastPosition = layoutManager.findLastVisibleItemPosition();

            Rect rvRect = new Rect();
            recyclerView.getGlobalVisibleRect(rvRect);

            for (int i = firstPosition; i <= lastPosition; i++) {
                Rect rowRect = new Rect();
                layoutManager.findViewByPosition(i).getGlobalVisibleRect(rowRect);

                int percentFirst;
                if (rowRect.bottom >= rvRect.bottom) {
                    int visibleHeightFirst = rvRect.bottom - rowRect.top;
                    percentFirst = (visibleHeightFirst * 100) / layoutManager.findViewByPosition(i).getHeight();
                } else {
                    int visibleHeightFirst = rowRect.bottom - rvRect.top;
                    percentFirst = (visibleHeightFirst * 100) / layoutManager.findViewByPosition(i).getHeight();
                }

                if (percentFirst > 30) {
                    visible_more_than_30 = i;
                    break;
                }
            }
            return visible_more_than_30;
        }
        return 0;
    }

    public static void
    toastLong(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static void toastShort(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

    }

    public static boolean isProbablyHindi(String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0900 && c <= 0x0960)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static PackageInfo getAppVersion(Context mContext) {
        try {
            if (mContext != null)
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDeviceIdRegisteredOnServer(Context context) {
        return Pref.readString(context, DEVICE_ID_FOR_DEVICE, Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID));
    }


    public static String getUnicDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static void showProgressAnimation(ProgressBar progressBar, int resultPercent) {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, resultPercent);
        progressAnimator.setDuration(500);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }


    public static String formatNumberToShort(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatNumberToShort(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumberToShort(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static String getVideoKeyFromUrl(String youtubeUrl) {
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            return matcher.group();
        } else {
            String finalUrl = youtubeUrl;
            String youtubeKey = finalUrl;
            int index_of_key = finalUrl.indexOf("v=");
            int index_of_and = finalUrl.indexOf("&");
            if (index_of_key != -1) {
                if (index_of_and > index_of_key)
                    youtubeKey = finalUrl.substring(index_of_key + 2, index_of_and);
                else
                    youtubeKey = finalUrl.substring(index_of_key + 2);
            }
            return youtubeKey;
        }
    }

    public static int getImageHeight(String image_size, Context mContext) {
        String imageHeight = "";
        int imageFinalHeight = 0;
        imageHeight = image_size.split("x")[1];
        String tempImageData = imageHeight.split("\\|")[0];
        if (!TextUtils.isEmpty(tempImageData)) {
            if (!tempImageData.equalsIgnoreCase("none"))
                imageFinalHeight = Integer.parseInt(tempImageData);
        } else
            imageFinalHeight = Integer.parseInt(imageHeight);

        ////return ExtraUtils.convertPixelsToDp(imageFinalHeight, mContext);
        return imageFinalHeight;
    }

    public static void inviteToMolitics(Context context, String appPackageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void customToast(Context context, String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom, null);
        TextView toastMessage = view.findViewById(R.id.text_view);
        toastMessage.setText(message);
        toast.setView(view);
        toast.show();
    }

    public enum YoutubeType {

        national("national"),
        local("local"),
        leader("leader"),
        trending("trending");

        private final String name;

        YoutubeType(String s) {
            name = s;
        }
    }

    public static void attachLeaderToLinearView(Context mContext, LinearLayout parentView, List<AllLeaderModel> candidateLeaderModels) {
        parentView.removeAllViews();
        if (candidateLeaderModels != null) {
            for (int i = 0; i < Math.min(candidateLeaderModels.size(), 4); i++) {
                View child = LayoutInflater.from(mContext).inflate(R.layout.include_news_tag_leader, null);
                parentView.addView(child);
                CircleImageView leader_image = child.findViewById(R.id.leaderImageView);
                if (!TextUtils.isEmpty(candidateLeaderModels.get(i).getProfileImage()))
                    Picasso.with(mContext).load(candidateLeaderModels.get(i).getProfileImage()).into(leader_image);
            }
        }
    }
    public static void attachSuggestionToLinearView(Context mContext, LinearLayout parentView, UsersFeedModel candidateLeaderModels) {
        parentView.removeAllViews();
        if (candidateLeaderModels != null) {
            View child = LayoutInflater.from(mContext).inflate(R.layout.include_news_tag_leader, null);
            parentView.addView(child);
            CircleImageView leader_image = child.findViewById(R.id.leaderImageView);
            if (!TextUtils.isEmpty(candidateLeaderModels.getImage()))
                Picasso.with(mContext).load(candidateLeaderModels.getImage()).into(leader_image);
        }
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static void openBrowser(Context mContext, String url) {

        if (TextUtils.isEmpty(url))
            return;

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        // set toolbar color and/or setting custom actions before invoking build()
        // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
        builder.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER,
                    Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + mContext.getPackageName()));
        }
        // and launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(mContext, Uri.parse(url));
    }

    public static void openTermConditionActivity(Context context, String headerText, String url) {
        Intent mIntent = new Intent(context, TermConditionActivity.class);
        mIntent.putExtra(Constant.IntentKey.TOOL_BAR, headerText);
        mIntent.putExtra(Constant.IntentKey.URL, url);
        context.startActivity(mIntent);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
