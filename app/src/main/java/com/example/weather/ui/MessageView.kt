package com.example.weather.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.weather.R
import com.example.weather.databinding.MessageViewBinding
import com.example.weather.extensions.visible

class MessageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    private val binding: MessageViewBinding

    init {
        val layoutInflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = MessageViewBinding.inflate(layoutInflater, this, true)
        applyAttributes(context, attrs)
    }

    private fun applyAttributes(context: Context, attrs: AttributeSet? = null) {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.MessageView, 0, 0)
        try {
            val title = obtainStyledAttributes.getString(R.styleable.MessageView_title).orEmpty()
            val message = obtainStyledAttributes.getString(R.styleable.MessageView_message).orEmpty()
            binding.messageTitleTextView.text = title
            binding.messageTextView.text = message
            binding.messageImageView.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.MessageView_image))
        } finally {
            obtainStyledAttributes.recycle()
        }
    }

    private fun setImage(@DrawableRes icon: Int) {
        binding.messageImageView.setImageDrawable(ContextCompat.getDrawable(context, icon))
    }

    private fun setTitle(@StringRes title: Int) {
        binding.messageTitleTextView.text = context.getString(title)
    }

    private fun setMessage(@StringRes message: Int) {
        binding.messageTextView.text = context.getString(message)
    }

    fun showConnectionError() = show(R.drawable.ic_no_internet, R.string.error_device_not_connected, R.string.error_verify_signal)

    fun showGenericError() = show(R.drawable.ic_error, R.string.error_we_have_an_issue, R.string.error_checking_issue)

    fun showNotFoundError() = show(R.drawable.ic_normal_search, R.string.error_search_not_found, R.string.error_try_other_search)

    private fun show(@DrawableRes image: Int, @StringRes title: Int? = null, @StringRes message: Int? = null) {
        setImage(image)
        if (title != null) setTitle(title) else clearTitle()
        if (message != null) setMessage(message) else clearMessage()
        visible()
    }

    private fun clearTitle() {
        binding.messageTitleTextView.text = null
    }

    private fun clearMessage() {
        binding.messageTextView.text = null
    }
}
