package br.com.itau.github.common.extentions

import android.content.Context.ACCESSIBILITY_SERVICE
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_ANNOUNCEMENT
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

internal fun Fragment.setupActionBar(toolbar: Toolbar,
                                     title: String,
                                     showBackBt: Boolean = false
){
    (activity as AppCompatActivity?)?.apply {
        setSupportActionBar(toolbar)
        this.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackBt)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}

fun Fragment.announceForAccessibility(announcement: String) {
    val am = context!!.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
    if (am.isEnabled) {
        context
            ?.getSystemService(ACCESSIBILITY_SERVICE)
            .let { it as AccessibilityManager }
            .let { manager ->
                AccessibilityEvent
                    .obtain()
                    .apply {
                        eventType = TYPE_ANNOUNCEMENT
                        className = context?.javaClass?.name
                        packageName = context?.packageName
                        text.add(announcement)
                    }
                    .let {
                        manager.sendAccessibilityEvent(it)
                    }
            }
    }
}
