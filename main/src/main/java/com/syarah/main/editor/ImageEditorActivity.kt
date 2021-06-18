package com.syarah.main.editor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.syarah.core.screenshot
import com.syarah.main.R
import com.syarah.main.databinding.ActivityImageEditorBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

/**
 * Project: Syarah Assignment
 * Created: Jun 18, 2021
 *
 * @author Mohamed Hamdan
 */
@AndroidEntryPoint
class ImageEditorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityImageEditorBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<ImageEditorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initImage()
    }

    private fun initImage() {
        Glide.with(this)
            .asBitmap()
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .load(getImage(intent))
            .into(binding.imageViewImage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_image_editor, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_done -> onDoneClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onDoneClicked() {
        binding.progressBar.isVisible = true
        viewModel.saveBitmap(binding.frameLayoutImage.screenshot()).observe(this) { file ->
            val result = Intent()
            result.putExtra(EXTRA_IMAGE, file.absolutePath)
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    companion object {

        private const val EXTRA_IMAGE = "image"

        fun getIntent(context: Context, file: File): Intent {
            val intent = Intent(context, ImageEditorActivity::class.java)
            intent.putExtra(EXTRA_IMAGE, file.absolutePath)
            return intent
        }

        fun getImage(intent: Intent): String {
            return intent.getStringExtra(EXTRA_IMAGE)!!
        }
    }
}
