package in.ernadeembhat.pdfviewer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class PdfRenderActivity extends AppCompatActivity {


    private String DUMMY_PDF_FILE_LOCATION = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
    private int pageIndex,pagecount;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;
    private ImageView imageView;
    private Handler handler;
    private Runnable runnable;
    private int width=0, height=0;
    private FrameLayout layoutparent;
    private FloatingActionButton prePageButton,nextPageButton;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.pdf_image);
        layoutparent= findViewById(R.id.parent);
        Log.e("Main\t","Width:\t"+width+"\tHeight:\t"+height);
        pageIndex=0;


    }






    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPDF(int pageindexNo,int width, int height) throws IOException {
        Log.e("PDF_READER\t","PageNo:\t"+pageindexNo);
        File file = new File(getApplication().getFilesDir(), "Kt.pdf");

        ParcelFileDescriptor fileDescriptor = null;
        fileDescriptor = ParcelFileDescriptor.open(
                file, ParcelFileDescriptor.MODE_READ_ONLY);

        //min. API Level 21
        PdfRenderer pdfRenderer = null;
        pdfRenderer = new PdfRenderer(fileDescriptor);

        final int pageCount = pdfRenderer.getPageCount();
//        Toast.makeText(this,
//                "pageCount = " + pageCount,
//                Toast.LENGTH_LONG).show();

        //Display page 0
        int rendererPageWidth = width;
        int rendererPageHeight = height;
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bao);

        PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY));
        if(pageindexNo>0){
            pageindexNo=0;
        }else if(pageindexNo > renderer.getPageCount()) {
            pageindexNo =  renderer.getPageCount()-1;
        }

        Matrix matrix = imageView.getImageMatrix();
        Rect rect = new Rect(0,0,rendererPageWidth,rendererPageHeight);
        renderer.openPage(pageindexNo).render(bitmap,rect,matrix,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        imageView.setImageMatrix(matrix);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
        pageIndex++;
    }*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPDF(int index) throws IOException {

        //open file in assets
        File file = new File(getApplication().getFilesDir(), "Kt.pdf");
        ParcelFileDescriptor fileDescriptor = ParcelFileDescriptor.open(
                file, ParcelFileDescriptor.MODE_READ_ONLY);
//        AssetManager assetManager = getAssets();
//        AssetFileDescriptor assetFileDescriptor = assetManager.openFd("Kt.pdf");
//        ParcelFileDescriptor fileDescriptor =
//                assetFileDescriptor.getParcelFileDescriptor();

        //open file from sdcard
        /*
        String targetPdf = "/sdcard/test.pdf";
        File file = new File(targetPdf);

        ParcelFileDescriptor fileDescriptor = null;
        fileDescriptor = ParcelFileDescriptor.open(
                file, ParcelFileDescriptor.MODE_READ_ONLY);
        */

        //min. API Level 21
        PdfRenderer pdfRenderer = null;
        pdfRenderer = new PdfRenderer(fileDescriptor);

        final int pageCount = pdfRenderer.getPageCount();
        Toast.makeText(this,
                "pageCount = " + pageCount,
                Toast.LENGTH_LONG).show();

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(index);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
//        Bitmap bitmap = Bitmap.createBitmap(
//                rendererPageWidth,
//                rendererPageHeight,
//                Bitmap.Config.ARGB_8888);
//        PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY));
//        if(index>0){
//            index=0;
//        }else if(index > renderer.getPageCount()) {
//            index =  renderer.getPageCount()-1;
//        }
//        Matrix matrix = imageView.getImageMatrix();
//        Rect rect = new Rect(0,0,rendererPageWidth,rendererPageHeight);
//        pdfRenderer.openPage(index).render(bitmap,rect,matrix,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,bao);
//        rendererPage.render(bitmap, null, null,
//                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//        imageView.setImageMatrix(matrix);
//        imageView.setImageBitmap(bitmap);
//        rendererPage.close();
//        pdfRenderer.close();
//        pageIndex++;
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bao);

        PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY));
        if(index>0){
            index=0;
        }else if(index > renderer.getPageCount()) {
            index =  renderer.getPageCount()-1;
        }

        Matrix matrix = imageView.getImageMatrix();
        Rect rect = new Rect(0,0,rendererPageWidth,rendererPageHeight);
        renderer.openPage(index).render(bitmap,rect,matrix,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        imageView.setImageMatrix(matrix);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
        pageIndex++;
        //assetFileDescriptor.close();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                        //openPDF(pageIndex);
                    try {
                        openRenderer(getApplicationContext());
                        showPage(pageIndex);
                        handler.postDelayed(this,1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Something Wrong: " + e.toString(), Toast.LENGTH_LONG).show();

                    }
                }
            };
            handler.post(runnable);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openRenderer(Context context) throws IOException {
        // In this sample, we read a PDF from the assets directory.
        File file = new File(context.getCacheDir(), "Kt.pdf");
        if (!file.exists()) {
            // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
            // the cache directory.
            InputStream asset = context.getAssets().open("Kt.pdf");
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfRenderer we use to render the PDF.
        if (parcelFileDescriptor != null) {
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
            pagecount = pdfRenderer.getPageCount();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void closeRenderer() throws IOException {
        if (null != currentPage) {
            currentPage.close();
        }
        pdfRenderer.close();
        parcelFileDescriptor.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != currentPage) {
            currentPage.close();
        }
        if(index>pagecount){
            index=0;
            pageIndex=0;
        }

        // Use `openPage` to open a specific page in PDF.
        currentPage = pdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
//        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
//                Bitmap.Config.ARGB_8888);

        int width = getResources().getDisplayMetrics().widthPixels+60;
        int height = getResources().getDisplayMetrics().heightPixels +60;
        Log.e("DISPLAY\t","Width\t"+width);
        Log.e("DISPLAY\t","Height\t"+height);
        Bitmap bitmap = Bitmap.createBitmap(width,
                height,
                Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bao);


        Bitmap newBitmap = getResizedBitmap(bitmap,width,height,false);

        Rect rect = new Rect(0,0,width,height);
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        currentPage.render(newBitmap, rect, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        int [] allpixels = new int [newBitmap.getHeight() * newBitmap.getWidth()];

        newBitmap.getPixels(allpixels, 0, newBitmap.getWidth(), 0, 0, newBitmap.getWidth(), newBitmap.getHeight());
       // Log.e("Log.e(\"COLOR\\t\",\"Code\\t\"+Color.YELLOW);\t","Code\t"+Color.YELLOW);
        for(int i = 0; i < allpixels.length; i++)
        {
            if(allpixels[i] == Color.YELLOW)
            {
                //allpixels[i] = Color.rgb(255,165,0);
                allpixels[i] = Color.alpha(Color.parseColor("#FF4500"));
            }
        }

        newBitmap.setPixels(allpixels,0,newBitmap.getWidth(),0, 0, newBitmap.getWidth(),newBitmap.getHeight());
        imageView.setImageBitmap(newBitmap);
        imageView.invalidate();
        //currentPage.close();
        pageIndex++;

    }


    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight, boolean isNecessaryToKeepOrig) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        if(!isNecessaryToKeepOrig){
            bm.recycle();
        }
        return resizedBitmap;
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateUi() {
        int index = currentPage.getIndex();
        int pageCount = pdfRenderer.getPageCount();
//        prePageButton.setEnabled(0 != index);
//        nextPageButton.setEnabled(index + 1 < pageCount);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getPageCount() {
        return pdfRenderer.getPageCount();
    }


    @Override
    protected void onPause() {
        super.onPause();
        pageIndex=0;
        if(handler!=null){
            handler.removeCallbacks(runnable);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStop() {
        super.onStop();
        pageIndex=0;
        if(handler!=null){
            handler.removeCallbacks(runnable);
        }
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
