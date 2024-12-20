package com.example.app_n1.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.app_n1.Adapter.IteambreakfastAdapter
import com.example.app_n1.Adapter.Item
import com.example.app_n1.Adapter.NewsAdapter
import com.example.app_n1.NewsDetailActivity
import com.example.app_n1.R
import com.example.app_n1.databinding.FragmentHomeBinding
import com.example.app_n1.mealActivity
import com.example.app_n1.models.DailyLog
import com.example.app_n1.models.NewsItem
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Danh sách item mà bạn muốn hiển thị
    private val breakfastItems = mutableListOf<Item>()
    private val lunchItems = mutableListOf<Item>()
    private val dinnerItems = mutableListOf<Item>()
    private val newsList = mutableListOf<NewsItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupViews(root)

        setupNewsCarousel()

        changlink()

        // Gọi hàm để vẽ biểu đồ
        setupLineChart()

        val fabAddMeal: FloatingActionButton = root.findViewById(R.id.fab_add_meal)
        fabAddMeal.setOnClickListener {
            showCustomDialog()
        }

        loadMealData()
        return root
    }

    private fun setupViews(root: View) {
        val textViewDinner: TextView = root.findViewById(R.id.textViewDinner)
        val textViewBreakfast: TextView = root.findViewById(R.id.textViewBreakfast)
        val textViewLunch: TextView = root.findViewById(R.id.textViewLunch)

        // Kiểm tra nếu breakfastItems không rỗng thì hiển thị textViewBreakfast
        textViewBreakfast.visibility = if (breakfastItems.isEmpty()) View.GONE else View.VISIBLE
        textViewLunch.visibility = if (lunchItems.isEmpty()) View.GONE else View.VISIBLE
        textViewDinner.visibility = if (dinnerItems.isEmpty()) View.GONE else View.VISIBLE

    }

    private fun loadMealData() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())  // Lấy ngày hôm nay
        val sharedPreferences = requireContext().getSharedPreferences("user_session", AppCompatActivity.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null).orEmpty()  // Lấy userId từ SharedPreferences

        // Lấy dữ liệu từ Firebase DailyLogs của người dùng trong ngày hôm nay
        val logRef = FirebaseDatabase.getInstance().getReference("dailyLogs").child(userId).child(currentDate)

        logRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Xóa danh sách cũ trước khi thêm mới
                breakfastItems.clear()
                lunchItems.clear()
                dinnerItems.clear()

                // Lọc món ăn từ dailyLogs
                val dailyLog = snapshot.getValue(DailyLog::class.java)
                dailyLog?.meals?.forEach { meal ->
                    when (meal.mealName) {
                        "Breakfast" -> {  // Món ăn của bữa sáng
                            meal.foods.forEach { food ->
                                breakfastItems.add(Item(food.name, food.calories.toString() + " Kcal")) // Lưu foodId
                            }
                        }
                        "Lunch" -> {  // Món ăn của bữa trưa
                            meal.foods.forEach { food ->
                                lunchItems.add(Item(food.name, food.calories.toString() + " Kcal")) // Lưu foodId
                            }
                        }
                        "Dinner" -> {  // Món ăn của bữa tối
                            meal.foods.forEach { food ->
                                dinnerItems.add(Item(food.name, food.calories.toString() + " Kcal")) // Lưu foodId
                            }
                        }
                    }
                }

                // Cập nhật lại UI sau khi tải dữ liệu
                setupViews(requireView())

                setupMealViews()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun showCustomDialog() {
        // Tạo AlertDialog Builder
        val dialogBuilder = AlertDialog.Builder(requireContext())

        // Inflate layout tùy chỉnh
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null)

        // Thiết lập nội dung cho AlertDialog
        dialogBuilder.setView(dialogView)

        // Tạo AlertDialog
        val alertDialog = dialogBuilder.create()

        // Xử lý nút Thêm
        dialogView.findViewById<Button>(R.id.add_meal_button).setOnClickListener {
            val intent = Intent(requireContext(), mealActivity()::class.java)
            startActivity(intent)
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.cancel_buttones).setOnClickListener {
            alertDialog.dismiss()
        }

        // Hiển thị AlertDialog
        alertDialog.show()
    }

    private fun setupMealViews() {
        // Bữa sáng
        if (breakfastItems.isNotEmpty()) {
            binding.recyclerViewBreakfast.visibility = View.VISIBLE
            setupRecyclerView(binding.recyclerViewBreakfast, breakfastItems)
        } else {
            binding.recyclerViewBreakfast.visibility = View.GONE
        }

        // Bữa trưa
        if (lunchItems.isNotEmpty()) {
            binding.recyclerViewLunch.visibility = View.VISIBLE
            setupRecyclerView(binding.recyclerViewLunch, lunchItems)
        } else {
            binding.recyclerViewLunch.visibility = View.GONE
        }

        // Bữa tối
        if (dinnerItems.isNotEmpty()) {
            binding.recyclerViewDinner.visibility = View.VISIBLE
            setupRecyclerView(binding.recyclerViewDinner, dinnerItems)
        } else {
            binding.recyclerViewDinner.visibility = View.GONE
        }

    }

    private fun setupRecyclerView(recyclerView: RecyclerView, items: MutableList<Item>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = IteambreakfastAdapter(items) { position ->
            val itemToRemove = items[position]
            val foodName = itemToRemove.name // Lấy tên món ăn cần xóa
            val mealName = when (recyclerView.id) {
                R.id.recyclerViewBreakfast -> "Breakfast"
                R.id.recyclerViewLunch -> "Lunch"
                R.id.recyclerViewDinner -> "Dinner"
                else -> ""
            }

            // Tạo hộp thoại xác nhận xóa món ăn
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setTitle("Xóa món ăn")
            dialogBuilder.setMessage("Bạn có chắc chắn muốn xóa món ăn \"$foodName\" không?")

            // Nếu người dùng chọn "Yes", xóa món ăn
            dialogBuilder.setPositiveButton("Yes") { _, _ ->
                // Xóa món ăn khỏi Firebase
                removeMealFromFirebaseById(foodName, mealName)

                // Xóa món ăn khỏi danh sách và cập nhật RecyclerView
                items.removeAt(position)
                recyclerView.adapter?.notifyItemRemoved(position)  // Cập nhật RecyclerView
            }

            // Nếu người dùng chọn "No", đóng hộp thoại
            dialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Đóng hộp thoại nếu không muốn xóa
            }

            // Hiển thị hộp thoại xác nhận
            dialogBuilder.create().show()
        }

        recyclerView.adapter = adapter
    }


    private fun removeMealFromFirebaseById(foodname: String, mealName: String) {
        // Lấy ngày hiện tại
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Lấy userId từ SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_session", AppCompatActivity.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null).orEmpty()

        // Lấy dữ liệu từ Firebase DailyLogs của người dùng trong ngày hôm nay
        val logRef = FirebaseDatabase.getInstance().getReference("dailyLogs").child(userId).child(currentDate)

        logRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Lấy dữ liệu DailyLog từ Firebase
                val dailyLog = snapshot.getValue(DailyLog::class.java)
                dailyLog?.meals?.forEach { meal ->
                    if (meal.mealName == mealName) {
                        // Loại bỏ món ăn theo foodId
                        val updatedFoods = meal.foods.filterNot { it.name == foodname }.toMutableList()
                        meal.foods = updatedFoods // Cập nhật lại foods
                    }
                }

                // Cập nhật lại dữ liệu vào Firebase
                logRef.setValue(dailyLog)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
    }

    private fun setupLineChart() {
        // Lấy tham chiếu tới LineChart từ View Binding
        val lineChart = binding.lineChart

        // Dữ liệu mẫu cho biểu đồ - thay thế bằng dữ liệu thực từ Firebase nếu có
        val sampleData = listOf(
            Entry(0f, 65f),  // Ngày 1: 65kg
            Entry(1f, 64.5f), // Ngày 2: 64.5kg
            Entry(2f, 64f),   // Ngày 3: 64kg
            Entry(3f, 63.8f), // Ngày 4: 63.8kg
            Entry(4f, 65f),  // Ngày 5: 63.5kg
            Entry(5f,63f), // Ngày 6: 63kg
            Entry(6f,63.5f) // Ngày 7: 63.5kg
        )

        val xLabels = listOf("Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7")

        // Tạo dataset cho biểu đồ
        val lineDataSet = LineDataSet(sampleData, "Cân nặng hàng ngày").apply {
            color = resources.getColor(R.color.purple_500, null)
            valueTextColor = resources.getColor(R.color.black, null)
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(resources.getColor(R.color.teal_200, null))
            setDrawCircleHole(false)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER // Đường cong mượt
        }

        // Tạo dữ liệu biểu đồ từ dataset
        val lineData = LineData(lineDataSet)

        // Gán dữ liệu vào biểu đồ
        lineChart.data = lineData

        // Tùy chỉnh trục X
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xLabels)
        xAxis.granularity = 1f
        xAxis.textColor = resources.getColor(R.color.black, null)

        // Tùy chỉnh trục Y trái
        val leftAxis = lineChart.axisLeft
        leftAxis.textColor = resources.getColor(R.color.black, null)

        // Ẩn trục Y phải
        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        // Tùy chỉnh giao diện chung của biểu đồ
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.animateX(1000)

        // Làm mới biểu đồ để hiển thị
        lineChart.invalidate()
    }

    private fun setupNewsCarousel() {
        // Dữ liệu tin tức mẫu
        newsList.add(NewsItem(R.drawable.khangbibat, "Ngất xĩu do uống nhiều đường mía", "Hôm qua anh Trần Nhật Khang đã bị bắt do uống nhiều đường mía"))
        newsList.add(NewsItem(R.drawable.lenconthankinh, "Máu dồn lên não và những hành động kì lạ", "Hào không gay đã lên cơn động dục không kiểm soát do máu lên não"))
        newsList.add(NewsItem(R.drawable.ung_thu, "70% bệnh nhân ung thư phổi phát hiện muộn, ai có nguy cơ?", "Ung thư phổi diễn biến âm thầm dễ nhầm lẫn với bệnh hô hấp thông thường"))
        newsList.add(NewsItem(R.drawable.trai_cay, "Ăn trái cây có giúp giảm cân không?", "Ăn trái cây có thể giúp bạn giảm cân, khi bạn lựa chọn trái cây thay vì những món ăn chế biến sẵn giàu lượng đường bổ sung hoặc chất béo."))
        newsList.add(NewsItem(R.drawable.ung_thu, "70% bệnh nhân ung thư phổi phát hiện muộn, ai có nguy cơ?", "Ung thư phổi diễn biến âm thầm dễ nhầm lẫn với bệnh hô hấp thông thường"))
        newsList.add(NewsItem(R.drawable.ung_thu, "70% bệnh nhân ung thư phổi phát hiện muộn, ai có nguy cơ?", "Ung thư phổi diễn biến âm thầm dễ nhầm lẫn với bệnh hô hấp thông thường"))
        newsList.add(NewsItem(R.drawable.ung_thu, "70% bệnh nhân ung thư phổi phát hiện muộn, ai có nguy cơ?", "Ung thư phổi diễn biến âm thầm dễ nhầm lẫn với bệnh hô hấp thông thường"))

        val adapter = NewsAdapter(newsList) { newsItem ->
            // Xử lý sự kiện khi nhấn vào tin tức
            val intent = Intent(requireContext(), NewsDetailActivity::class.java).apply {
                putExtra("title", newsItem.title)
                putExtra("description", newsItem.description)
                putExtra("imageRes", newsItem.imageRes)
            }
            startActivity(intent)
        }
        binding.viewPager2.adapter = adapter // Gán adapter cho ViewPager2
    }

    private fun changlink()
    {
        val cancelButton = view?.findViewById<Button>(R.id.cancel_buttones)

        cancelButton?.setOnClickListener()
        {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_exercise)
        }
    }

}
