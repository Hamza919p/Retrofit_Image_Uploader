package com.project.comparecarts.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.comparecarts.CustomProgressDialog;
import com.project.comparecarts.Permissions;
import com.project.comparecarts.R;
import com.project.comparecarts.UpdateReceiptResultOnItemClickListener;
import com.project.comparecarts.adapters.UploadReceiptResultAdapter;
import com.project.comparecarts.api.ApiServices;
import com.project.comparecarts.api.RetrofitHelper;
import com.project.comparecarts.databinding.FragmentUploadReceiptBinding;
import com.project.comparecarts.models.Products;
import com.project.comparecarts.models.YourReceiptsParent;
import com.project.comparecarts.ui.activities.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadReceiptFragment extends Fragment implements View.OnClickListener {
    private FragmentUploadReceiptBinding binding;
    private Uri imageUri;
    private CustomProgressDialog customProgressDialog;
    private ApiServices apiServices;
    private ArrayList<Products> tempProdArr;
    private YourReceiptsParent receipts;
    private MainActivity parentActivity;

    private UploadReceiptFragment() {
    }

    private static UploadReceiptFragment instance = null;

    public static UploadReceiptFragment getInstance() {
        if (instance == null) {
            instance = new UploadReceiptFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUploadReceiptBinding.inflate(inflater);
        init();
        return binding.getRoot();
    }

    private void init() {
        parentActivity = (MainActivity) getActivity();
        tempProdArr = new ArrayList<>();
        apiServices = RetrofitHelper.getInstance().create(ApiServices.class);
        customProgressDialog = new CustomProgressDialog(requireContext());
        initializeClickListeners();
    }

    private void initializeClickListeners() {
        binding.ivGallery.setOnClickListener(this);
        binding.btnUploadToServer.setOnClickListener(this);
        binding.btnScanImage.setOnClickListener(this);
        binding.btnUpdateResponse.setOnClickListener(this);
    }

    private void openCamera() throws IOException {
        if (!Permissions.checkCameraPermission(requireContext())) {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            imageUri = createImageUri();
            takePicture.launch(imageUri);
        }
    }

    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    try {
                        openCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private final ActivityResultLauncher<String> requestGalleryPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openGallery();
                }
            });

    private Uri createImageUri() throws IOException {
        File photoFile = File.createTempFile(
                "IMG_",
                ".jpg",
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        );
        return FileProvider.getUriForFile(
                requireContext(),
                "com.project.comparecarts.fileProvider",
                photoFile
        );
    }

    ActivityResultLauncher<Uri> takePicture = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        beginCrop();
//                        binding.ivCamera.setImageURI(imageUri);
                    }
                }
            }
    );

    private void beginCrop() {
        File file = new File(
                requireActivity().getExternalCacheDir().getAbsolutePath(), File.separator +
                "crop" + Calendar.getInstance().getTimeInMillis() + ".jpg");
        cropLauncher.launch(new CropImageContractOptions(imageUri, new CropImageOptions()));
    }


    ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imageUri = result.getData().getData();
                        binding.ivGallery.setImageURI(imageUri);
                    }
                }
            }
    );

    ActivityResultLauncher<CropImageContractOptions> cropLauncher = registerForActivityResult(
            new CropImageContract(), new ActivityResultCallback<CropImageView.CropResult>() {
                @Override
                public void onActivityResult(CropImageView.CropResult result) {
//                    String path = getRealPathFromUri(result.getUriContent());
                    imageUri = result.getUriContent();
                    binding.ivCamera.setImageURI(imageUri);
                }
            }
    );

//    private void handleCropResult(Intent result) {
//        if(result != null){
//            Uri resultUri = UCrop.getOutput(result);
//            if(resultUri != null) {
//                String path = getRealPathFromUri(resultUri);
//                imageUri = resultUri;
//                binding.ivCamera.setImageURI(imageUri);
//            }
//
//        }
//    }


    private void openGallery() {
        if (!Permissions.checkExternalStoragePermission(requireContext())) {
            requestGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            chooseImage.launch(intent);
        }
    }

    private String getFileName() {
        String name = "";
        Cursor returnCursor = requireActivity().getContentResolver().query(imageUri, null, null, null, null);
        if (returnCursor != null) {
            returnCursor.moveToFirst();
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            name = returnCursor.getString(nameIndex);
            returnCursor.close();
        }
        return name;
    }


    private String getRealPathFromUri(Uri uri) {
        String result = "";
        Cursor cursor = requireActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor == null)
            uri.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void postImageToServer() throws IOException {
        customProgressDialog.show();

        File file = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            ParcelFileDescriptor descriptor = requireActivity().getContentResolver().openFileDescriptor(imageUri, "r", null);
            inputStream = new FileInputStream(descriptor.getFileDescriptor());
            file = new File(requireActivity().getCacheDir(), getFileName());
            outputStream = new FileOutputStream(file);
            byte[] buf = new byte[8192];
            int length;
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        apiServices.uploadImage(body, true).enqueue(new Callback<YourReceiptsParent>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<YourReceiptsParent> call, Response<YourReceiptsParent> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        receipts = response.body();
                        binding.tvZipCode.setText("ZipCode: " + response.body().zipcode);
                        initializeAdapter();
                        binding.btnUpdateResponse.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<YourReceiptsParent> call, Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        });
    }

    private void initializeAdapter() {
        try {
            binding.rvProducts.setVisibility(View.VISIBLE);
            UploadReceiptResultAdapter adapter = new UploadReceiptResultAdapter(receipts.products);
            binding.rvProducts.setAdapter(adapter);
            tempProdArr.addAll(Arrays.asList(receipts.products));
            adapter.observeClickListener(new UpdateReceiptResultOnItemClickListener() {
                @Override
                public void onClick(int position, String storeName, String price, String itemName) {
                    generateTempArrForProd(position, storeName, price, itemName);
                }
            });
        } catch (Exception e) {

        }
    }

    private void generateTempArrForProd(int position, String storeName, String price, String itemName) {
        Products tempPro = new Products();
        tempPro.id = receipts.products[position].id;
        tempPro.name = itemName;
        tempPro.price = Double.parseDouble(price);
        tempPro.unit = receipts.products[position].unit;
        tempPro.imageUrl = receipts.products[position].imageUrl;
        tempPro.brand = receipts.products[position].brand;
        tempPro.storeName = storeName;
        tempProdArr.remove(position);
        tempProdArr.add(position, tempPro);
    }

    private void createJsonObject() {
        JsonObject jsonObject = null;
        try {
            jsonObject = new JsonObject();
            jsonObject.addProperty("id", receipts.id);
            jsonObject.addProperty("storename", receipts.storeName);
            jsonObject.addProperty("zipcode", receipts.zipcode);
            jsonObject.addProperty("username", receipts.username);
            jsonObject.addProperty("imagePath", receipts.imagePath);
            jsonObject.addProperty("transactiondate", receipts.transactionDate);
            jsonObject.addProperty("createddate", receipts.createdDate);
            jsonObject.addProperty("total", receipts.total);

            JsonArray jsonArray = new JsonArray();
            for (int i = 0; i < tempProdArr.size(); i++) {
                JsonObject prodJsonObj = new JsonObject();
                prodJsonObj.addProperty("id", tempProdArr.get(i).id);
                prodJsonObj.addProperty("name", tempProdArr.get(i).name);
                prodJsonObj.addProperty("price", tempProdArr.get(i).price);
                prodJsonObj.addProperty("unit", tempProdArr.get(i).unit);
                prodJsonObj.addProperty("imageurl", tempProdArr.get(i).imageUrl);
                prodJsonObj.addProperty("brand", tempProdArr.get(i).brand);
                prodJsonObj.addProperty("storename", tempProdArr.get(i).storeName);
                jsonArray.add(prodJsonObj);
            }
            jsonObject.add("products", jsonArray);
        } catch (Exception e) {
        }
        updateReceipt(jsonObject);
    }

    private void updateReceipt(JsonObject jsonObject) {
        customProgressDialog.show();
        apiServices.updateReceipt(String.valueOf(2), jsonObject).enqueue(new Callback<YourReceiptsParent>() {
            @Override
            public void onResponse(Call<YourReceiptsParent> call, Response<YourReceiptsParent> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        binding.rvProducts.setVisibility(View.GONE);
                        binding.btnUpdateResponse.setVisibility(View.GONE);
                        binding.tvZipCode.setText("");
                        binding.ivCamera.setImageURI(null);
                        binding.ivCamera.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_camera));
                        parentActivity.moveToUsersReceipt();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<YourReceiptsParent> call, Throwable t) {
                customProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_scan_image) {
            try {
                openCamera();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.iv_gallery) {
            openGallery();
        } else if (v.getId() == R.id.btn_upload_to_server) {
            try {
                if (imageUri != null)
                    postImageToServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.btn_update_response) {
            if (tempProdArr.size() > 0)
                createJsonObject();
        }
    }
}