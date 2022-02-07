package zar1official.simplenote.ui.customViews

import android.content.Context
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import zar1official.simplenote.R

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var htmlText: String? = null
        @RequiresApi(Build.VERSION_CODES.N)
        set(value) {
            field = value
            text = value?.let { Html.fromHtml(value, HtmlCompat.FROM_HTML_MODE_LEGACY) }
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr, 0)
            .also { typedArray ->
                htmlText = typedArray.getString(R.styleable.CustomTextView_htmlText)
            }.recycle()
    }
}