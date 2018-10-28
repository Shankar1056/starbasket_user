package apextechies.starbasket.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import apextechies.starbasket.R
import apextechies.starbasket.easyphotopicker.DefaultCallback
import apextechies.starbasket.easyphotopicker.EasyImage
import apextechies.starbasket.retrofit.RetrofitDataProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.actvity_uploadimage.*
import kotlinx.android.synthetic.main.toolbar.*
import pl.tajchert.nammu.Nammu
import pl.tajchert.nammu.PermissionCallback
import java.io.File
import java.util.ArrayList

class UploadImage: AppCompatActivity() {

    private val PHOTOS_KEY = "easy_image_photos_list"
    private var photos = ArrayList<File>()
    private var retrofitDataProvider: RetrofitDataProvider? = null
    private val permision = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
            .permission.CAMERA)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_uploadimage)

        setSupportActionBar(toolbarr)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        retrofitDataProvider = RetrofitDataProvider(this)

       /* Nammu.init(this)
        checkPermission()
        checkGalleryAppAvailability()

        if (savedInstanceState != null) {
            photos = savedInstanceState.getSerializable(PHOTOS_KEY) as ArrayList<File>
        }

        EasyImage.configuration(this)
                .setImagesFolderName()
                .setCopyTakenPhotosToPublicGalleryAppFolder()
                .setCopyPickedImagesToPublicGalleryAppFolder()
                .setAllowMultiplePickInGallery()

        imageView.setOnClickListener {
            EasyImage.openChooserWithGallery(this, "Pick source", 0)
        }

    }

    private fun checkPermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, permision, object : PermissionCallback {
                override fun permissionGranted() {
                    //Nothing, this sample saves to Public gallery so it needs permission
                }

                override fun permissionRefused() {

                }
            })
        }*/
    }

    /*public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(PHOTOS_KEY, photos)
    }

    private fun checkGalleryAppAvailability() {
        if (!EasyImage.canDeviceHandleGallery(this)) {
            //Device has no app that handles gallery intent
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception, source: EasyImage.ImageSource, type: Int) {
                //Some error handling
                e.printStackTrace()
            }

            override fun onImagesPicked(imageFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                onPhotosReturned(imageFiles)
            }

            override fun onCanceled(source: EasyImage.ImageSource, type: Int) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source === EasyImage.ImageSource.CAMERA) {
                    val photoFile = EasyImage.lastlyTakenButCanceledPhoto(this@UploadImage)
                    photoFile?.delete()
                }
            }

        })
    }

    private fun onPhotosReturned(returnedPhotos: List<File>) {
        photos.addAll(returnedPhotos)
        *//* textname.add(selectedphotoname)
         imagesAdapter.notifyDataSetChanged()
         changAddPhotoText(selectedphotoname)
         val uri = Uri.fromFile(returnedPhotos[0])
         val temppath = getPathFromContentUri(uri)
         path.add(temppath)*//*
        Picasso.with(this)
                .load(returnedPhotos.get(0))
                .fit()
                .centerCrop()
                .into(imageView)
    }

    override fun onDestroy() {
        // Clear any configuration that was done!
        EasyImage.clearConfiguration(this)
        super.onDestroy()
    }*/
}