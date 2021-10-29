package com.example.appquanlicuahang.Sceen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appquanlicuahang.Arapter.ArapterCatToc;
import com.example.appquanlicuahang.Model.KhachHang;
import com.example.appquanlicuahang.Model.ThoCatToc;
import com.example.appquanlicuahang.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CatToc extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PERMISSION = 123;
    TextView txtTenKhachHang, txtThem;
    TextView txtCamera, txtLibrary;
    ArrayList<ThoCatToc> arraylistTho = new ArrayList<>();
    Spinner spinnerListTho;
    static int request = 100;
    ArrayList<Bitmap> anhKhach = new ArrayList<>();
    RecyclerView recyCatToc;
    ArapterCatToc arapterCatToc;
    static int galleryRequest = 101;
    int idTho;
    Bitmap bm;
    String imageName ;
    ;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] Byte;
    String ConvertImage;
    KhachHang kh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_toc);
        innit();
        anhXa();
        setUp();
        setClick();
    }

    private void innit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);

            return;
        }
    }

    private void anhXa() {
        txtTenKhachHang = findViewById(R.id.txtTenKhachHang);
        spinnerListTho = findViewById(R.id.spinerTho);
        txtCamera = findViewById(R.id.txtCamera);
        recyCatToc = findViewById(R.id.recyCatToc);
        txtLibrary = findViewById(R.id.txtLibrary);
        txtThem = findViewById(R.id.txtThem);


    }


    private void setUp() {


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        kh = (KhachHang) bundle.getSerializable("objKhachHang");
        arraylistTho = (ArrayList<ThoCatToc>) bundle.getSerializable("arrTho");
        txtTenKhachHang.setText(kh.getTenKhachHang() + "");

        ArrayAdapter<ThoCatToc> adapter = new ArrayAdapter<ThoCatToc>(getApplicationContext(), android.R.layout.simple_spinner_item, arraylistTho);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerListTho.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        spinnerListTho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idTho = arraylistTho.get(position).getIdTho();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set arapter cho recyview
        arapterCatToc = new ArapterCatToc(anhKhach, this);
        recyCatToc.setAdapter(arapterCatToc);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyCatToc.setLayoutManager(gridLayoutManager);
    }

    private void setClick() {
        txtCamera.setOnClickListener(this);
        txtLibrary.setOnClickListener(this);
        txtThem.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtCamera:
                changeCamera();
                break;

            case R.id.txtLibrary:
                chageLibrary();
                break;
            case R.id.txtThem:
                addImageCustomer();
                break;
        }

    }

    // thêm toàn bộ ảnh của khách hàng lên data base
    private void addImageCustomer() {
        if (checkInfo() == false) {
            Toast.makeText(this, "bạn chưa thêm ảnh khách ", Toast.LENGTH_LONG).show();
        } else {
            addImageCustomers();
        }

    }

    // upload ảnh lên sever
    private void addImageCustomers() {
        for (int i = 0; i < anhKhach.size(); i++) {
            //conver bitmap sang kiểu byte
            anhKhach.get(i).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            Byte = byteArrayOutputStream.toByteArray();
            ConvertImage = Base64.encodeToString(Byte, Base64.DEFAULT);
            //lấy ra tên của ảnh
            Uri tempUri = getImageUri(getApplicationContext(), anhKhach.get(i));
            File finalFile = new File(getRealPathFromURI(tempUri));
            imageName = finalFile.getName();

            // gửi lên sever
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, "https://levannghia2105.000webhostapp.com/upLoadInfoCustomer.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "ERR", Toast.LENGTH_LONG).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("numberHairCuts",String.valueOf(kh.getSoLancat()));
                    map.put("image_name", imageName);
                    map.put("encoded_string", ConvertImage);
                    map.put("id_customer", String.valueOf(kh.getId()));
                    map.put("id_staff", String.valueOf(idTho));
                    return map;
                }
            };
            requestQueue.add(request);


        }
       sendToMain(110);
    }


    // chuyển sang thư viện để lấy ảnh về
    private void chageLibrary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, galleryRequest);

    }

    // chuyển sang camera và trả về bitmap
    private void changeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, request);
    }


    //tra về bức ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request && data != null) {

            switch (resultCode) {
                case RESULT_OK:

                    bm = (Bitmap) data.getExtras().get("data");
                    anhKhach.add(bm);
                    arapterCatToc.notifyDataSetChanged();
                    // upLoadImageToSever();
                    break;


            }
        }

        // nếu từ bên thư viện trả về
        if (requestCode == galleryRequest && data != null) {
            Uri selectImage = data.getData();
            try {

                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectImage);
                anhKhach.add(bm);
                arapterCatToc.notifyDataSetChanged();
                //  upLoadImageToSever();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    // check thông tin của khách hàng
    public boolean checkInfo() {
        if (anhKhach == null) {
            return false;
        } else
            return true;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    // quyền đọc bộ nhớ máy
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
            } else {
                // User refused to grant permission.
            }
        }
    }
    public void sendToMain(int request){
        Intent intent = getIntent();
        intent.putExtra("objCustomer1",kh);
        setResult(request,intent);
        finish();


    }

}
