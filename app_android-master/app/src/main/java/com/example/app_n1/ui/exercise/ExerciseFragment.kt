package com.example.app_n1.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_n1.Adapter.ExerciseAdapter
import com.example.app_n1.R
import com.example.app_n1.databinding.FragmentExerciseBinding
import com.example.app_n1.models.Exercise


class ExerciseFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private val exerciseList = listOf(
        Exercise("Hít đất ", "Cách tập:  " +
                "Đầu tiên, bạn cần phải khởi động nhẹ nhàng và thật kỹ cơ thể trước khi thực hiện bài tập hít đất. Dành thời gian khoảng 5 phút để khởi động trước khi tập sẽ giúp bạn làm quen với cường độ tập, giúp bài tập mang lại hiệu quả tốt nhất và đồng thời giảm tránh chấn thương cho bạn. Bạn có thể khởi động với một số động tác cơ bản như xoay khớp cổ tay cổ chân, văng tay, đá chân hoặc hít thở thật sâu ở tư thế ưỡn căng người...\n" +
                "\n" +
                "- Để thực hiện tập hít đất đúng cách thì đầu tiên bạn trong tư thế nằm sấp xuống đất, đặt khoảng cách 2 tay rộng hơn vai một chút và chống tay cùng mũi chân tiếp xúc với sàn nhà. Gữ thẳng người sao cho thân người tạo nên một đường thẳng từ đốt sống cổ, đến đường cột sống (lưng), mông, chân và gót chân. Hai chân hơi khép lại, để song song với nhau và hơi thả lỏng đầu gối. Đây là tư thế chuẩn khi tập hít đất.\n" +
                "\n" +
                "- Bắt đầu động tác hít đất bằng cách từ từ hạ người thấp xuống trong khi hít vào đều đặn đến khi ngực cách mặt đất 10cm (chuẩn) thì giữ khuỷu tay và vai thẳng hàng với nhau. Cánh tay vuông góc với sàn, lòng bàn tay úp xuống đất, hướng lên trên, thả lỏng vai và hơi co vai vào trong một chút. Tùy theo sức chịu đựng của cơ thể mà bạn có thể giữ tư thế này trong khoảng 2-3 nhịp đếm hoặc hơn.\n" +
                "\n" +
                "- Sau đó, từ từ nâng người lên thẳng tay và đồng thời thở đều ra ở động tác này.\n" +
                "\n" +
                "- Lặp lại toàn bộ động tác để tiếp tục bài tập hít đất cho đến khi đạt đủ số lần tập.", R.drawable.push_up),
        Exercise("Gập bụng", "Cách tập: " +
                "Bước 1: Nằm ngửa trên thảm hoặc sàn. Đồng thời, hai tay đưa ra phía sau đầu và các ngón tay đặt sát gáy. \n" +
                "\n" +
                "Bước 2: Co hai chân lên để tạo thành góc 90 độ so với sàn và lưng ép sát xuống thảm. \n" +
                "\n" +
                "Bước 3: Siết chặt cơ bụng để lấy sức nâng vai và đầu lên khỏi sàn, thở ra. Lưu ý: chỉ sử dụng lực ở bụng để nâng người lên,không sử dụng lực của cánh tay để kéo vai lên. \n" +
                "\n" +
                "Bước 4: Từ từ hạ người về lại vị trí ban đầu và hít vào. \n" +
                "\n" +
                "Bước 5: Lặp lại 10 - 15 lần/hiệp và trong 3 hiệp. ", R.drawable.crunch),
        Exercise("Chạy bộ", "Cách tập: Chạy nhẹ nhàng, duy trì nhịp tim từ 100 đến 110 nhịp trên 1 phút", R.drawable.jogging),
        Exercise("Hít xà", "Cách tập: Đầu tiên, bạn thực hiện động tác treo người lên thanh xà, nắm xà đơn treo tường với lòng bàn tay hướng về phía thân người và chú ý khoảng cách hai tay nên để bằng hoặc rộng hơn độ rộng của vai một chút thì sẽ dễ lên xà hơn.\n" +
                "\n" +
                "- Ở vị trí bắt đầu bài tập, bạn giữ cơ thể luôn thẳng (nhất là cột sống phải thẳng) và hơi ưỡn ngực ra một chút. Chú ý, khi bạn giữ thân người càng thẳng thì sẽ càng có tác dụng tốt để giúp kích thích cơ nhị đầu và giảm tối thiểu ảnh hưởng xấu của bài tập tới lưng.\n" +
                "\n" +
                "- Tiếp theo, bạn dùng lực từ cánh tay và vai, kéo cơ thể lên cho đến khi đầu ngang tầm với thanh xà. Thực hiện động tác tập trung vào cơ nhị đầu (cơ tay trước) để bài tập có hiệu quả cao hơn và cố gắng giữ khuỷu tay gần với thân người. Lưu ý, khi kéo người lên thì nhịp thở là thở ra từ từ, phần thân trên cơ thể không nên di chuyển trong khi thực hiện động tác và chỉ có tay là co lại, cẳng tay chỉ làm việc giữ thanh xà.\n" +
                "\n" +
                "- Khi bạn đang ở vị trí co lại trên cùng thì lúc này bạn gồng cơ nhị đầu khoảng một vài giây. Sau đó, từ từ hạ thân người xuống vị trí bắt đầu bài tập cho đến khi tay duỗi ra toàn bộ nhưng chân không được chạm đất. Lưu ý, nhịp thở lúc này là hít vào từ từ.\n" +
                "\n" +
                "- Lặp lại toàn bộ động tác trên để tiếp tục bài tập cho tới khi đạt đủ số lần tập.", R.drawable.hit_xa),
        Exercise("Squat", "Cách tập: Bước 1: Bạn đứng thẳng, mở rộng 2 chân rộng bằng vai, hai tay đan lại với nhau để trước ngực, bụng hóp lại và vai mở rộng.\n" +
                "Bước 2: Bạn đẩy hông và mông ra phía sau rồi từ từ hạ thấp cơ thể xuống cho đến khi phần đùi song song với sàn nhà, đồng thời để đầu gối và bàn chân hơi hướng ra ngoài. Chú ý, cổ, vai và mông tạo thành một đường xiên.\n" +
                "Bước 3: Bạn giữ nguyên 2 giây và trở về vị trí ban đầu.", R.drawable.squat),
        Exercise("Plank", "Cách tập: " +
                "Bước 1: Bắt đầu với tư thế plank, hai bàn tay chống đất, ngón chân ép sát sàn, hai chân khép vào nhau và cơ bụng căng cứng\n" +
                "Bước 2: Bắt đầu nhảy để mở rộng hai chân rồi lại nhảy để khép chân lại. Bạn hãy cố gắng thực hiện càng nhanh càng tốt.\n" +
                "Có nhiều dạng biến thể của plank nhưng cơ bản làm động tác này sẽ giúp làm quen trước khi học những động tác nâng cao hơn.", R.drawable.plank),
        Exercise("Giãn cơ", "Cách tập: " +
                "Người tập ở tư thế hơi khuỵu gối và ngồi trên gót chân. Khi thực hiện, có thể sử dụng gối hoặc đệm đặt dưới đùi (trán) để tạo cảm giác thoải mái trong quá trình tập luyện.\n" +
                "\n" +
                "Từ từ gập người về phía sau sao cho trán có thể chạm tới sàn.\n" +
                "\n" +
                "Người tập thực hiện mở rộng cánh tay ra phía trước để đỡ cổ, hoặc đưa cánh tay trải dài theo chiều dọc của cơ thể.\n" +
                "\n" +
                "Giữ tư thế tối đa trong vòng 5 phút kết hợp với điều trình hơi thở, thư giãn. Nếu cảm thấy các tình trạng khó chịu, tức ngực hoặc quá đau tức ở lưng thì có thể dừng bài tập.", R.drawable.gian_co),
        Exercise("Bật nhảy tại chỗ", "Cách tập: " +
                "Đứng với hai chân rộng bằng vai và lùi lại cách bục nhảy một khoảng tùy thuộc vào độ cao của bục.Lấy đà bằng cách hạ thấp người và đưa hai cánh tay ra sau.Thực hiện cú nhảy bằng sức mạnh của bàn chân, đầu gối và hông, đồng thời vung mạnh hai tay về phía trước.Tiếp đất trên bục trong tư thế hạ thấp người để giảm lực tác động của cú nhảy.", R.drawable.bat_nhay),
        Exercise("", "Cách tập: " +
                "Thực hiện tư thế xếp bằng, lưng thẳng, cơ bụng thả lỏng. Miệng nhẩm câu “Om Shanti” trong 1 phút. Đây là hình thức cầu nguyện quan trọng của đạo Hindu. Quá trình này sẽ giúp tâm hồn bạn được thư giãn hơn và các suy nghĩ dần lắng đọng. Bạn cũng có thể chắp tay cầu nguyện trong thời gian này để tinh thần được thả lỏng hoàn toàn.", R.drawable.yoga),
        Exercise("Đạp xe", "Cách tập: " +
                "Đạp xe dần đều", R.drawable.dap_xe),
        Exercise("Tập gym", "Cách tập: " +
                "Đến phòng gym , gặp gỡ PT để có được chế độ tập tốt nhất, hoặc rèn luyện theo sở thích cá nhân một cách an toàn và hoàn hảo theo mong muốn của bản thân", R.drawable.gym)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = ExerciseAdapter(exerciseList) { exercise ->
            val action = ExerciseFragmentDirections
                .actionNavigationExerciseToExerciseDetailFragment(
                    exercise.title,
                    exercise.description,
                    exercise.imageResId
                )
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        return view
    }
}
