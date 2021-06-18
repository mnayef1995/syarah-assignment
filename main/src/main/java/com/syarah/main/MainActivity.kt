package com.syarah.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.syarah.core.save
import com.syarah.main.databinding.ActivityMainBinding
import com.syarah.main.editor.ImageEditorActivity
import java.io.File

/**
 * Project: Syarah Assignment
 * Created: Jun 18, 2021
 *
 * @author Mohamed Hamdan
 */
class MainActivity : AppCompatActivity() {

    private var imageFile: File? = null

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.linearLayoutCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imageFile = createImageFile()
            val photoURI: Uri = FileProvider.getUriForFile(this, "com.syarah.fileprovider", imageFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraActivityResultLauncher.launch(intent)
        }

        binding.linearLayoutGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryActivityResultLauncher.launch(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("file", imageFile)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageFile = savedInstanceState.getSerializable("file") as? File?
    }

    private var cameraActivityResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            editImageActivityResultLauncher.launch(ImageEditorActivity.getIntent(this, imageFile!!))
        }
    }

    private var galleryActivityResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageFile = result.data?.data?.save(this)
            editImageActivityResultLauncher.launch(ImageEditorActivity.getIntent(this, imageFile!!))
        }
    }

    private var editImageActivityResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Glide.with(binding.imageViewResult)
                .load(ImageEditorActivity.getImage(result.data!!))
                .into(binding.imageViewResult)
        }
    }

    private fun createImageFile(): File {
        return File(externalCacheDir, "Image_${System.currentTimeMillis()}.jpg")
    }
}
