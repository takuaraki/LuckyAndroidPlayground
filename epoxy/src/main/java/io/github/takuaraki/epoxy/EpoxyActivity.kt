package io.github.takuaraki.epoxy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.takuaraki.epoxy.databinding.ActivityEpoxyBinding

class EpoxyActivity : AppCompatActivity() {

    lateinit var binding: ActivityEpoxyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_epoxy)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setController(TextController().apply {
            setData(listOf("Alice", "Bob"))
        })
    }
}
