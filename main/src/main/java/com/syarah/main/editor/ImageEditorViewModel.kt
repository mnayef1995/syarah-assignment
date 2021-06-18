package com.syarah.main.editor

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.syarah.core.save
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Project: Syarah Assignment
 * Created: Jun 18, 2021
 *
 * @author Mohamed Hamdan
 */
@HiltViewModel
class ImageEditorViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    fun saveBitmap(bitmap: Bitmap) = liveData(Dispatchers.IO) {
        emit(bitmap.save(application))
    }
}
