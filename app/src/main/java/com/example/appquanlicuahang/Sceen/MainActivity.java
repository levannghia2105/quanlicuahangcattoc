package com.example.appquanlicuahang.Sceen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appquanlicuahang.Arapter.ArapterKhachHang;
import com.example.appquanlicuahang.CallBack.SetClickListCustomer;
import com.example.appquanlicuahang.CallBack.SetClickUpdateCustomer;
import com.example.appquanlicuahang.Model.KhachHang;
import com.example.appquanlicuahang.Model.ThoCatToc;
import com.example.appquanlicuahang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUESCODEINPUT = 111;
    public static final int REQUESCODE = 110 ;
    public static final int RESULT = 112;
    RecyclerView recyKhachHang;
    KhachHang kh ;
    ArrayList<KhachHang> khachHangArrayList = new ArrayList<>();
    ArapterKhachHang arapterKhachHang;
    EditText edtKhachHang, edtSoDienThoai;
    String ten = "";
    String sdt = "";
    ArrayList<KhachHang> arr;
    int chon = 0;
    ArrayList<KhachHang> arr1;
    TextView txtNam, txtNu;
    ImageView imgThem;
    AlertDialog dialog;
    String urlDelete = "https://levannghia2105.000webhostapp.com/deleteCustomer.php";
    SetClickUpdateCustomer setClickUpdateCustomer;
    TextView txtCatToc;
    ArrayList<ThoCatToc> arraylistTho = new ArrayList<>();
    String url = "https://levannghia2105.000webhostapp.com/getDataStaff.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleSSLHandshake();

        innit();
        anhxa();
        setup();
        setClick();
    }

    private void setClick() {
        edtKhachHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ten = edtKhachHang.getText().toString().trim();
                ten = ten.toLowerCase();
                locTen();

            }
        });


        edtSoDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sdt = edtSoDienThoai.getText().toString().trim();
                locSDT();
            }
        });
        // set s??? ki???n cho ClickItem
        arapterKhachHang.setSetClickListCustomer(new SetClickListCustomer() {
            @Override
            public void ClickListCustomer(KhachHang kh) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater layoutInflater = getLayoutInflater();

                View view = layoutInflater.inflate(R.layout.custom_dialog_click_customer, null);
                TextView txtName = view.findViewById(R.id.txtDialogCustomer);
                TextView txtUpdate = view.findViewById(R.id.txtDialogUpdate);
                TextView txtDelete = view.findViewById(R.id.txtDialogDelete);
                txtCatToc = view.findViewById(R.id.txtDialogHairCuts);
                txtName.setText(kh.getTenKhachHang());
                // b???m v??o dialog c???t t??c
                txtCatToc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(MainActivity.this, CatToc.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arrTho", arraylistTho);
                        bundle.putSerializable("objKhachHang", kh);
                        intent.putExtra("bundle", bundle);

                        startActivityForResult(intent, REQUESCODEINPUT);
                    }
                });

                // s??? ki???n X??a Kh??ch H??ng Ra kh???i List
                txtDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //hi???n th??? dialog xem c?? mu???n x??a kh??ng
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setTitle("X??c nh???n x??a ");
                        alert.setMessage("B???n x??c nh???n c?? mu???n x??a kh??ch h??ng kh??ng ??");
                        //b???m ?????ng ??
                        alert.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getDataVolley("https://levannghia2105.000webhostapp.com/deleteCustomer.php?id=" + kh.getId());
                                khachHangArrayList.clear();
                                innit();
                                dialog.dismiss();

                            }
                        });
                        // kh??gn ?????ng ??
                        alert.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog AlerDialog = alert.create();
                        AlerDialog.show();


                    }
                });
                // S??? ki???n Update Kh??ch h??ng
                txtUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ThemKhachHang.class);
                        //truy???n ?????i t?????ng sang m??n h??nh kh??ch h??ng
                        intent.putExtra("objCustomer", kh);
                        startActivityForResult(intent, REQUESCODEINPUT);
                    }
                });


                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void locSDT() {
        if (arr == null) {
            arr1 = new ArrayList<>();
            for (KhachHang kh : khachHangArrayList) {
                if (kh.getSdt().substring(7).indexOf(sdt) >= 0) {
                    arr1.add(kh);
                }
            }
            arapterKhachHang.thayDoiList(arr1);

        } else {
            ArrayList<KhachHang> arr2 = new ArrayList<>();
            for (KhachHang kh : arr) {
                if (kh.getSdt().substring(7).indexOf(sdt) >= 0) {
                    arr2.add(kh);
                }
            }
            arapterKhachHang.thayDoiList(arr2);
        }

    }


    // l???c ra t??n kh??ch h??ng
    private void locTen() {

        if (ten.length() > 2) {
            arr = new ArrayList<>();
            for (KhachHang kh : khachHangArrayList) {
                if (kh.getTenKhachHang().toLowerCase().indexOf(ten) >= 0) {
                    arr.add(kh);
                }
            }
        } else {
            arr = khachHangArrayList;
        }
        arapterKhachHang.thayDoiList(arr);

    }

    private void setup() {
        arapterKhachHang = new ArapterKhachHang(MainActivity.this, khachHangArrayList);
        recyKhachHang.setAdapter(arapterKhachHang);
        recyKhachHang.setLayoutManager(new LinearLayoutManager(this));
        txtNam.setOnClickListener(this);
        txtNu.setOnClickListener(this);
        imgThem.setOnClickListener(this);

        // l???y data c???a th??? c???t t??c v???
        RequestQueue string = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        arraylistTho.add(new ThoCatToc(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        string.add(jsonArrayRequest);


    }

    private void anhxa() {
        txtNam = findViewById(R.id.txtNam);
        txtNu = findViewById(R.id.txtNu);
        recyKhachHang = findViewById(R.id.recyKhachHang);
        edtKhachHang = findViewById(R.id.edtTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        imgThem = findViewById(R.id.imgThem);

    }

    private void innit() {

        // khoi tao volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://levannghia2105.000webhostapp.com/getDataCustomer.php";
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //   tra ve mang doi tuong khach hang
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        khachHangArrayList.add(new KhachHang(jsonObject));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                arapterKhachHang.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonArray);


    }

    // fix loi khong lay data ve duoc

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtNam:
                locGioiTinh(txtNam, txtNu, 1);
                break;
            case R.id.txtNu:
                locGioiTinh(txtNu, txtNam, 0);
                break;
            case R.id.imgThem:
                imgThemClick();
                break;
        }

    }

    //
    private void imgThemClick() {
        PopupMenu popupMenu = new PopupMenu(this, this.imgThem);
        popupMenu.inflate(R.menu.menu_them);
//        Menu menu = popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            //set s??? ki???n cho popup
            public boolean onMenuItemClick(MenuItem item) {
                return menuItemClick(item);

            }
        });
        popupMenu.show();

    }


    @SuppressLint("ResourceAsColor")
    private void locGioiTinh(TextView txt1, TextView txt2, int gt) {
        txt2.setBackgroundColor(Color.WHITE);
        txt2.setTextColor(Color.BLACK);
        txt1.setBackgroundColor(Color.BLACK);
        txt1.setTextColor(Color.WHITE);
        ArrayList<KhachHang> arrayListGt = new ArrayList<>();
        for (KhachHang kh : khachHangArrayList) {

            if (kh.getGioiTinh() == gt) {
                arrayListGt.add(kh);
            }

        }
        arapterKhachHang.thayDoiList(arrayListGt);

    }

    public boolean menuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.themKh:
                Intent intent = new Intent(MainActivity.this, ThemKhachHang.class);
                startActivityForResult(intent, REQUESCODEINPUT);
                break;
            case R.id.themTho:
                showDialog();
                break;
        }
        return false;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String urlThemTho = "https://levannghia2105.000webhostapp.com/addStaff.php";
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themtho, null);
        builder.setView(view);
        EditText edtTho;
        TextView txtThemTho;
        edtTho = view.findViewById(R.id.edtThemTho);
        txtThemTho = view.findViewById(R.id.txtThemTho);


        // th??m th???
        txtThemTho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest string = new StringRequest(Request.Method.POST, urlThemTho, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().trim().equals("Success")) {
                            Toast.makeText(MainActivity.this, "Th??m Th??? Th??nh C??ng", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("tenTho", edtTho.getText().toString().trim());
                        return params;

                    }
                };
                requestQueue.add(string);

            }
        });
        dialog = builder.create();
        dialog.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESCODEINPUT)
            switch (resultCode) {
                // n???u m??n h??nh t??? b??n kh??ch h??ng tr??? v??? , c???p nh???t b???ng kh??ch h??ng
                case RESULT:
                    khachHangArrayList.clear();
                    innit();
                    break;
                    //m??n h??nh khi m??nh th??m ???nh kh??ch h??ng tr??? v???
                case REQUESCODE:


                kh = (KhachHang) data.getSerializableExtra("objCustomer1");

                    // th???c hi???n update th??m s??? l???n c???t
                    updateCustomer();
                    //c???p nh???t l???i b???ng kh??ch h??ng
                    innit();



            }
    }



    //th???c hi???n s???a l???i s??? l???n c???t cho ph?? h???p
    private void updateCustomer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest string = new StringRequest(Request.Method.POST, "https://levannghia2105.000webhostapp.com/updateNumberHair.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 Toast.makeText(getApplicationContext(),"Th??nh C??ng",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getApplicationContext(),"c???p nh??t th???t b???i",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String ,String> map = new HashMap<>();
                map.put("id",String.valueOf(kh.getId()));
                map.put("numberHair",String.valueOf(kh.getSoLancat()+1));
                return map;
            }
        };
        requestQueue.add(string);
    }

    //d??ng voley ????? g???i d??? id l??n
    public void getDataVolley(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest string = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Th??nh c??ng", Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "B???n x??a th???t b???i h??y th??? l???i sau ", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(string);


    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }


}