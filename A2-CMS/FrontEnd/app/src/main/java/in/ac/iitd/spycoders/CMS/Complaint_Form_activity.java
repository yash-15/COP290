package in.ac.iitd.spycoders.CMS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class Complaint_Form_activity extends AppCompatActivity {

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect,btnLodge;
    ImageView ivImage;
    EditText txt_lodge_title,txt_lodge_descr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.complaint_form_layout);

        btnSelect = (Button) findViewById(R.id.attachImage);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        txt_lodge_title=(EditText) findViewById(R.id.txt_lodge_title);
        txt_lodge_descr=(EditText) findViewById(R.id.txt_lodge_descr);
        txt_lodge_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (txt_lodge_title.getText().toString().length() == 0 ||
                        txt_lodge_descr.getText().toString().length() == 0) {
                    btnLodge.setEnabled(false);
                } else {
                    btnLodge.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });
        txt_lodge_descr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (txt_lodge_title.getText().toString().length() == 0 ||
                        txt_lodge_descr.getText().toString().length() == 0) {
                    btnLodge.setEnabled(false);
                } else {
                    btnLodge.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });
        btnLodge=(Button) findViewById(R.id.btn_lodge);
        btnLodge.setEnabled(false);
        btnLodge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    //Source for below
                    // http://stackoverflow.com/questions/573184/java-convert-string-to-valid-uri-object
                    String title_text = URLEncoder.encode(txt_lodge_title.getText().toString(), "utf-8");
                    String descr_text = URLEncoder.encode(txt_lodge_descr.getText().toString(), "utf-8");
                    String api_header = "http://" + Login_activity.ip + ":"+Login_activity.port+Login_activity.extras+
                            "/complaints/";
                    String api_params="";
                    switch(Login_activity.lodge_var1)
                    {
                        case 0:api_params="lodge_ind.json?title="+title_text+"&description="+descr_text
                                +"&solverid="+String.valueOf(Login_activity.lodge_var2);
                                break;
                        case 1:api_params="lodge_hstl.json?title="+title_text+"&description="+descr_text
                                +"&adminid="+String.valueOf(Login_activity.lodge_var2);
                            break;
                        case 2:api_params="lodge_insti.json?title="+title_text+"&description="+descr_text
                                +"&adminid="+String.valueOf(Login_activity.lodge_var2);
                            break;
                    }
                    JsonObjectRequest jsObjRequest_lodge = new JsonObjectRequest
                            (Request.Method.GET, api_header+api_params, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast toast = Toast.makeText(Complaint_Form_activity.this,
                                            "Complaint Lodged!", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(Complaint_Form_activity.this, Normal_activity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub

                                    Toast toast = Toast.makeText(Complaint_Form_activity.this,
                                            "Network Error!", Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            });
                    Login_activity.queue.add(jsObjRequest_lodge);
                } catch (Exception e) {
                    System.out.println("Failed");
                }



            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Complaint_Form_activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivImage.setImageBitmap(bm);
    }

}
