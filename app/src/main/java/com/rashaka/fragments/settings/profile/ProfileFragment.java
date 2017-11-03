package com.rashaka.fragments.settings.profile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rashaka.MainRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.fragments.BackFragment;
import com.rashaka.fragments.settings.profile.crop.CropFragment;
import com.rashaka.fragments.settings.profile.dialogs.DobDialog;
import com.rashaka.fragments.settings.profile.dialogs.GenderDialog;
import com.rashaka.fragments.settings.profile.dialogs.HeightDialog;
import com.rashaka.fragments.settings.profile.dialogs.StepsGoalDialog;
import com.rashaka.fragments.settings.profile.dialogs.WeightDialog;
import com.rashaka.fragments.settings.profile.dialogs.WeightGoalDialog;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Utility;
import com.rashaka.utils.database.SharedUserModel;
import com.rashaka.utils.dialogs.DialogActionInterface;
import com.rashaka.utils.dialogs.DialogTwoButton;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_set_profile)
public class ProfileFragment extends BackFragment implements ProfileView {

    private MainRouter myRouter;
    private ProfilePresenter mPresenter;
    private SharedUserModel model;
    private static final String TAG = ProfileFragment.class.getSimpleName();

    //-----------------------------------------------------

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask = null, userChoosenSource = null;

    private boolean changed;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new ProfilePresenter(myRouter);
    }

    @Override
    public void onBackButtonPressed() {
        Log.e(TAG, "Profile Fragment ON BACK Pressed -> ");
        if(changed){
            DialogTwoButton mDialog = new DialogTwoButton(
                    getActivity(),
                    "Warning!",
                    "You have unsaved changes, save them prior to exit?",
                    "Yes",
                    "No",
                    new DialogActionInterface() {
                        @Override
                        public void actionYes() {
                            mPresenter.saveProfileUpdate(model.getSelected().getValue());
                        }

                        @Override
                        public void actionNo() {
                            myRouter.onBackPressed();
                        }
                    });
            mDialog.show();
        } else {
            myRouter.onBackPressed();
        }
        changed = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);
        model.select(new UserProfile(RaApp.getBase().getProfileUser()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
        }
        super.onCreateOptionsMenu(menu, inflater);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackButtonPressed());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //TODO Set background & user images
        mProfileGrayPlus.setOnClickListener(view18 -> {
            userChoosenSource = Consts.backImage;
            mPresenter.onBackgroundPlusClick();
        });
        mProfileImage.setOnClickListener(view17 -> {
            userChoosenSource = Consts.userImage;
            mPresenter.onProfileImageClick();
        });

        mProfileTopBackground.setOnClickListener(view19 -> {
            userChoosenSource = Consts.backImage;
            mPresenter.onBackgroundImageClick();
        });

        //TODO User info dialogs click
        mProfileGenderButton.setOnClickListener(view1 -> {
            mPresenter.onDialogClick(new GenderDialog());
            //mPresenter.onGenderClick();
        });
        mProfileDobButton.setOnClickListener(view16 -> {
            mPresenter.onDialogClick(new DobDialog());
            //mPresenter.onDobClick();
        });
        mProfileHeightButton.setOnClickListener(view12 -> {
            mPresenter.onDialogClick(new HeightDialog());
            //mPresenter.onHeightClick();
        });
        mProfileWeightButton.setOnClickListener(view13 -> {
            mPresenter.onDialogClick(new WeightDialog());
            //mPresenter.onWeightClick();
        });
        mProfileWeightGButton.setOnClickListener(view14 -> {
            mPresenter.onDialogClick(new WeightGoalDialog());
            //mPresenter.onWeightGClick();
        });
        mProfileStepsGButton.setOnClickListener(view15 -> {
            mPresenter.onDialogClick(new StepsGoalDialog());
            //mPresenter.onStepsGClick();
        });

        //TODO Save all this sh..t
        mProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveProfileUpdate(model.getSelected().getValue());
                //mPresenter.onSaveClick();
            }
        });


        model.getSelected().observe(this, o -> {
            mPresenter.onProfileDataChanged(o);
            Log.e(TAG, "model.getSelected().observe");
        });



    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setLangValue() {
        mProfileUserInformation.setText(RaApp.getLabel("key_user_information"));
        mProfileGenderText.setText(RaApp.getLabel("key_gender"));
        mProfileDobText.setText(RaApp.getLabel("key_date_of_birth"));
        mProfileHeightText.setText(RaApp.getLabel("key_height"));
        mProfileWeightText.setText(RaApp.getLabel("key_weight"));
        mProfileWeightGText.setText(RaApp.getLabel("key_set_weight_goal"));
        mProfileStepsGText.setText(RaApp.getLabel("key_set_steps_goal"));

        mProfileWeeklySummary.setText(RaApp.getLabel("key_weekly_summary"));
        mProgressActiveMinsText.setText(RaApp.getLabel("key_average_daily_active_mins"));
        mProgressActiveMinsDef.setText(RaApp.getLabel("key_min"));
        mProgressStepsText.setText(RaApp.getLabel("key_average_daily_steps"));
        mProgressStepsDef.setText(RaApp.getLabel("key_caps_step"));
    }

    @Override
    public void setProfileBackground(String background) {
        mProfileTopBackground.setVisibility(View.VISIBLE);
        Picasso.with(getActivity()).invalidate(background);
        Picasso.with(getActivity())
                .load(background)
                .into(mProfileTopBackground);
    }

    @Override
    public void setProfileImage(String image) {
        mProfileImage.setVisibility(View.VISIBLE);
        Picasso.with(getActivity()).invalidate(image);
        Picasso.with(getActivity())
                .load(image)
                .into(mProfileImage);
    }

    @Override
    public void setProfileInfo(String name, String email) {
        mProfileName.setText(name);
        mProfileEmail.setText(email);
    }

    @Override
    public void setProfileGenderText(String text) {
        mProfileGenderText.setText(text);
    }

    @Override
    public void setProfileDobText(String text) {
        mProfileDobText.setText(text);
    }

    @Override
    public void setProfileHeightText(String text) {
        mProfileHeightText.setText(text);
    }

    @Override
    public void setProfileWeightText(String text) {
        mProfileWeightText.setText(text);
    }

    @Override
    public void setProfileWeightGText(String text) {
        mProfileWeightGText.setText(text);
    }

    @Override
    public void setProfileStepsGText(String text) {
        mProfileStepsGText.setText(text);
    }

    @Override
    public void setActiveBar(int max, int progress) {
        mProgressActiveMinsValue.setText(String.valueOf(max));
        mProgressActiveMins.setProgress(progress);
    }

    @Override
    public void setStepsBar(int max, int progress) {
        mProgressStepsValue.setText(String.valueOf(max));
        mProgressSteps.setProgress(progress);
    }

    @Override
    public void setBottomDialog(BottomSheetDialogFragment bottomDialog) {
        bottomDialog.show(getChildFragmentManager(), bottomDialog.getTag());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void selectImage() {
        Log.e(TAG, "selectImage - >");
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        goCrop(Uri.fromFile(destination));
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        goCrop(data.getData());
    }

    private void goCrop(Uri uri) {
        mFragmentNavigation.pushFragment(CropFragment.newInstance(userChoosenSource, uri.toString()));
    }

    @Override
    public void setSaveEnabled(boolean enabled) {
        this.changed = enabled;
//        Log.e(TAG, "setSaveEnabled " + enabled);
//        mProfileSave.setEnabled(enabled);
//        if (enabled)
//            mProfileImageBack.setBackgroundColor(Color.RED);
//        else
//            mProfileImageBack.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void changesSaved() {
        myRouter.onBackPressed();
    }


    //--------------------------------------------
    @BindView(R.id.profile_save_back)
    View mProfileImageBack;


    @BindView(R.id.profile_gray_plus)
    ImageView mProfileGrayPlus;

    @BindView(R.id.profile_top_background)
    ImageView mProfileTopBackground;

    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

    @BindView(R.id.profile_save)
    CircleImageView mProfileSave;

    @BindView(R.id.profile_name)
    TextView mProfileName;

    @BindView(R.id.profile_email)
    TextView mProfileEmail;

    @BindView(R.id.profile_user_information)
    TextView mProfileUserInformation;

    //TODO Profile dialogs buttons
    @BindView(R.id.profile_gender_button)
    FrameLayout mProfileGenderButton;

    @BindView(R.id.profile_dob_button)
    FrameLayout mProfileDobButton;

    @BindView(R.id.profile_height_button)
    FrameLayout mProfileHeightButton;

    @BindView(R.id.profile_weight_button)
    FrameLayout mProfileWeightButton;

    @BindView(R.id.profile_weight_g_button)
    FrameLayout mProfileWeightGButton;

    @BindView(R.id.profile_steps_g_button)
    FrameLayout mProfileStepsGButton;

    //TODO Profile dialogs buttons text
    @BindView(R.id.profile_gender_text)
    TextView mProfileGenderText;

    @BindView(R.id.profile_dob_text)
    TextView mProfileDobText;

    @BindView(R.id.profile_height_text)
    TextView mProfileHeightText;

    @BindView(R.id.profile_weight_text)
    TextView mProfileWeightText;

    @BindView(R.id.profile_weight_g_text)
    TextView mProfileWeightGText;

    @BindView(R.id.profile_steps_g_text)
    TextView mProfileStepsGText;

    //TODO Weekly summary with progress
    @BindView(R.id.profile_weekly_summary)
    TextView mProfileWeeklySummary;

    @BindView(R.id.profile_active_mins_progress)
    ProgressBar mProgressActiveMins;

    @BindView(R.id.profile_active_mins_text)
    TextView mProgressActiveMinsText;

    @BindView(R.id.profile_active_mins_def)
    TextView mProgressActiveMinsDef;

    @BindView(R.id.profile_active_mins_value)
    TextView mProgressActiveMinsValue;

    @BindView(R.id.profile_steps_progress)
    ProgressBar mProgressSteps;

    @BindView(R.id.profile_steps_text)
    TextView mProgressStepsText;

    @BindView(R.id.profile_steps_def)
    TextView mProgressStepsDef;

    @BindView(R.id.profile_steps_value)
    TextView mProgressStepsValue;

}
