package com.example.dicodingevent.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.dicodingevent.databinding.ActivityDetailBinding
import com.example.dicodingevent.ui.viewmodel.MainViewModel

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val mainViewModel by viewModels<MainViewModel>()
    private val binding get() = _binding!!

    companion object {
        const val EVENT_DETAIL = "event_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.isLoading.observe(this) {
            toggleLoading(it)
        }

        val eventId = intent.getIntExtra(EVENT_DETAIL, 0)
        mainViewModel.getDetailEvent(eventId)

        mainViewModel.eventDetail.observe(this) { eventDetail ->
            val availableQuota = eventDetail.quota - eventDetail.registrants
            with(binding) {
                tvDetailTitle.text = eventDetail.name
                tvDetailOwn.text = eventDetail.ownerName
                tvDetailQuota.text = availableQuota.toString()
                tvDetailBeginTime.text = eventDetail.beginTime
                tvDetailEndTime.text = eventDetail.endTime
                tvDetailDescription.text = HtmlCompat.fromHtml(
                    eventDetail.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }
            Glide.with(this)
                .load(eventDetail.mediaCover)
                .into(binding.imgDetailPhoto)
        }

        binding.btnRegister.setOnClickListener {
            val registerIntent = Intent(Intent.ACTION_VIEW).apply {
                data = mainViewModel.eventDetail.value?.link?.toUri()
            }
            startActivity(registerIntent)
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
