package wiki.scene.demo

import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import com.fondesa.recyclerviewdivider.dividerBuilder
import wiki.scene.demo.databinding.ActivityMainBinding
import wiki.scene.lib_base.base_ac.BaseAc

class MainActivity : BaseAc<ActivityMainBinding>() {
    override fun initViews() {
        super.initViews()
        val adapter = MainAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
        dividerBuilder().size(10, TypedValue.COMPLEX_UNIT_DIP)
            .color(Color.RED)
            .build()
            .addTo(binding.recyclerView)

        for (i in 1..30) {
            adapter.addData("数据${i}")
        }

    }

}