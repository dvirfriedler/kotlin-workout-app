import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.*
import com.example.myapplication.ui.fragment.AllWorkoutsFragment
import com.example.myapplication.ui.fragment.BMIFragment
import com.example.myapplication.ui.fragment.MenuFragment
import com.example.myapplication.ui.fragment.YourWorkoutsFragment
import android.content.Context

private val TAB_TITLES = arrayOf(
    R.string.your_workouts,
    R.string.all_workouts,
    R.string.menus,
    R.string.bmi_calculator
)

class SectionsPagerAdapter(fragmentActivity: FragmentActivity, private val context: Context) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> YourWorkoutsFragment()
            1 -> AllWorkoutsFragment()
            2 -> MenuFragment()
            3 -> BMIFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }

    fun getPageTitle(context: Context, position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }
}
