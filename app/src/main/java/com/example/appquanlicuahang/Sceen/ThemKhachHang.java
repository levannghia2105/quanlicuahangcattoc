package com.example.appquanlicuahang.Sceen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appquanlicuahang.CallBack.SetClickUpdateCustomer;
import com.example.appquanlicuahang.Model.KhachHang;
import com.example.appquanlicuahang.R;

import java.util.HashMap;
import java.util.Map;

public class ThemKhachHang extends AppCompatActivity implements View.OnClickListener {
    ImageView imgNgaySinh;
    EditText edtThemKh, edtThemNs, edtThemDc, edtThemMoTa, edtSdt;
    TextView txtThemNam, txtThemNu, txtThem;
    int chon = 2;
    String url = "https://levannghia2105.000webhostapp.com/addCustomer.php";
    String urlUpdate = "https://levannghia2105.000webhostapp.com/updateCustomer.php";
    KhachHang khachHang;
    SetClickUpdateCustomer ClickCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);
        innit();
        anhXa();
        setUp();
        setClick();
    }

    private void setClick() {
        imgNgaySinh.setOnClickListener(this);
    }

    private void setUp() {
        txtThem.setOnClickListener(this);
        txtThemNu.setOnClickListener(this);
        txtThemNam.setOnClickListener(this);

        Intent intent = getIntent();
        khachHang = (KhachHang) intent.getSerializableExtra("objCustomer");
        if (khachHang == null) {
            return;
        } else {
            edtThemKh.setText(khachHang.getTenKhachHang() + "");
            edtSdt.setText(khachHang.getSdt() + "");
            edtThemDc.setText(khachHang.getDiaChi() + "");
            edtThemMoTa.setText(khachHang.getMoTa() + "");
            edtThemNs.setText(khachHang.getNgaySinh() + "");
            if (khachHang.getGioiTinh() == 1) {
                chonGioiTinh(txtThemNam, txtThemNu);
                chon = 1;
            } else {
                chonGioiTinh(txtThemNu, txtThemNam);
                chon = 0;
            }


        }

    }

    private void anhXa() {
        imgNgaySinh = findViewById(R.id.imgNgaySinh);
        edtThemKh = findViewById(R.id.edtKhachHang);
        edtSdt = findViewById(R.id.edtSdt);
        edtThemDc = findViewById(R.id.edtDiaChi);
        edtThemNs = findViewById(R.id.edtNgaySinh);
        edtThemMoTa = findViewById(R.id.edtThemMoTa);
        txtThemNam = findViewById(R.id.txtThemNam);
        txtThemNu = findViewById(R.id.txtThemNu);
        txtThem = findViewById(R.id.txtThemKh);
    }

    private void innit() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgNgaySinh:
                showDialog();
                break;
            case R.id.txtThemKh:
                if (khachHang == null) {
                    themKh();
                    break;
                } else {
                    updateKhachHang();
                    break;
                }


            case R.id.txtThemNam:
                chon = 1;
                chonGioiTinh(txtThemNam, txtThemNu);
                break;
            case R.id.txtThemNu:
                chon = 0;
                chonGioiTinh(txtThemNu, txtThemNam);
                break;

        }

    }

    private void updateKhachHang() {

        if (Check(edtThemKh) == false || Check(edtThemDc) == false || Check(edtThemNs) == false || Check(edtThemMoTa) == false || Check(edtSdt) == false || chon == 2) {
            Toast.makeText(ThemKhachHang.this, "bạn vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            //update khach hang
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("SUCCESS")) {
                        Toast.makeText(ThemKhachHang.this, "bạn đã sửa lại tên khách hàng này", Toast.LENGTH_LONG).show();
                        setResult(112);

                        finish();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ThemKhachHang.this, "Không thể chỉnh sửa", Toast.LENGTH_LONG).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> pagarams = new HashMap<>();
                    pagarams.put("id", String.valueOf(khachHang.getId()));
                    pagarams.put("ten", edtThemKh.getText().toString().trim());
                    pagarams.put("ngaysinh", edtThemNs.getText().toString().trim());
                    pagarams.put("sdt", edtSdt.getText().toString().trim());
                    pagarams.put("diachi", edtThemDc.getText().toString().trim());
                    pagarams.put("gioitinh", String.valueOf(chon));
                    pagarams.put("mota", edtThemMoTa.getText().toString().trim());
                    return pagarams;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

    // chọn giới tính khách hàng
    private void chonGioiTinh(TextView txt1, TextView txt2) {
        txt2.setBackgroundColor(Color.WHITE);
        txt2.setTextColor(Color.BLACK);
        txt1.setBackgroundColor(Color.BLACK);
        txt1.setTextColor(Color.WHITE);
    }

    private void themKh() {
        //kiểm tra các ô
        if (Check(edtThemKh) == false || Check(edtThemDc) == false || Check(edtThemNs) == false || Check(edtThemMoTa) == false || Check(edtSdt) == false || chon == 2) {
            Toast.makeText(ThemKhachHang.this, "bạn vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            // nếu kiểm tra thì thêm khách hàng vao database
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toString().trim().equals("Success")) {
                        Toast.makeText(ThemKhachHang.this, "Thêm thành công", Toast.LENGTH_LONG).show();


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ThemKhachHang.this, error.toString(), Toast.LENGTH_LONG).show();


                }
            }) {
                @Nullable
                // thêm key value vào trong
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> pagarams = new HashMap<>();
                    pagarams.put("ten", edtThemKh.getText().toString().trim());
                    pagarams.put("ngaysinh", edtThemNs.getText().toString().trim());
                    pagarams.put("sdt", edtSdt.getText().toString().trim());
                    pagarams.put("diachi", edtThemDc.getText().toString().trim());
                    pagarams.put("gioitinh", String.valueOf(chon));
                    pagarams.put("mota", edtThemMoTa.getText().toString().trim());
                    pagarams.put("solancat",String.valueOf(0));
                    return pagarams;
                }
            };
            requestQueue.add(stringRequest);

        }
    }

    // hiển thị dialog chọn ngày cho khách hàng
    private void showDialog() {
        int chonNgay = 20;
        int chonThang = 5;
        int chonNam = 2000;
        DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (month < 9) {
                    edtThemNs.setText(dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else {
                    edtThemNs.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                }
            }
        };
        //tạo
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar, setDate, chonNam, chonThang, chonNgay);
        //show
        datePickerDialog.show();

    }


    private boolean Check(EditText v) {
        String s = v.getText().toString().trim();
        if (s.length() == 0) {
            return false;
        } else
            return true;
    }
}