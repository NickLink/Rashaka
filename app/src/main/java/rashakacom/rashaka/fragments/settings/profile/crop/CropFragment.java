package rashakacom.rashaka.fragments.settings.profile.crop;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.MainRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.fragments.BaseFragment;
import rashakacom.rashaka.utils.Consts;
import rashakacom.rashaka.utils.database.SharedUserModel;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.rest.models.profile.UserProfile;


/**
 * Created by User on 24.08.2017.
 */

@Layout(id = R.layout.fr_set_crop_image)
public class CropFragment extends BaseFragment implements CropView {




    private MainRouter myRouter;
    private CropPresenter mPresenter;
    private SharedUserModel model;
    private Uri mCropImageUri;
    private int x, y;

    public static CropFragment newInstance(String type, String uri) {
        Bundle args = new Bundle();
        args.putString(Consts.KEY_TYPE, type);
        args.putString(Consts.KEY_URI, uri);
        CropFragment fragment = new CropFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (MainRouter) getActivity();
        mPresenter = new CropPresenter();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//            getActivity().setTitle("BARABAKA");
//
//            menu.clear();
//            //inflater.inflate(R.menu.shadow, menu);
//
//            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setHomeButtonEnabled(false);
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                actionBar.setHomeAsUpIndicator(R.drawable.ic_abar_back);
//            }
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        model = ViewModelProviders.of(getActivity()).get(SharedUserModel.class);

        //TODO Get arguments
        if (getArguments() != null) {
            final String type = getArguments().getString(Consts.KEY_TYPE);
            final String uri = getArguments().getString(Consts.KEY_URI);
            if(type.equals(Consts.backImage)){
                x = Consts.backDim[0];
                y = Consts.backDim[1];
            }
            if(type.equals(Consts.userImage)){
                x = Consts.userDim[0];
                y = Consts.userDim[1];
            }

            imageSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap cropped = mCropImageView.getCroppedImage(x == 0 ? 500 : x, y == 0 ? 500 : y);
                    if (cropped != null) {
                        doSave(cropped, type);
                    }
                }
            });

            if (!TextUtils.isEmpty(uri)) {
                mCropImageUri = Uri.parse(uri);
                mCropImageView.setImageUriAsync(mCropImageUri);
            }
        }
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setValues(String one, String two, String three) {
//        share_title.setText(one);
//        share_text.setText(two);
    }

    private void doSave(Bitmap bitmap, String type) {

        File f = null;
        try {
            f = new File(getContext().getFilesDir(), type);
            f.createNewFile();
            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            byte[] bitmapdata = bos.toByteArray();
            //write the bytes in file
            FileOutputStream fos = null;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        UserProfile data = model.getSelected().getValue();
        if(type.equals(Consts.backImage)){
            data.setBackground(Uri.fromFile(f).toString());
        }
        if(type.equals(Consts.userImage)){
            data.setImage(Uri.fromFile(f).toString());
        }
        model.select(data);
        mFragmentNavigation.popFragment();
    }

    @BindView(R.id.crop_image_view)
    CropImageView mCropImageView;

    @BindView(R.id.image_save)
    Button imageSave;

}
