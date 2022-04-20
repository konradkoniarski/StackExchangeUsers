package co.uk.stackexchangeusers.presentation.userlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.stackexchangeusers.R
import co.uk.stackexchangeusers.databinding.FragmentUserListBinding
import co.uk.stackexchangeusers.domain.model.User
import co.uk.stackexchangeusers.presentation.userdetails.UserDetailsFragment
import kotlinx.android.synthetic.main.fragment_user_list.*
import org.koin.android.ext.android.inject

class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    val viewModel: UserListViewModel by inject()
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        val layoutManager = LinearLayoutManager(context)

        recycler_view_list_users.layoutManager = layoutManager
        recycler_view_list_users.hasFixedSize()
        recycler_view_list_users.adapter = ItemsAdapter(object : ItemsAdapter.OnUserClickListener {
            override fun onUserClick(user: User) {
                Toast.makeText(context, user.displayName, Toast.LENGTH_SHORT).show()
                user.accountId?.let {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, UserDetailsFragment.newInstance(it))
                        .addToBackStack(null)
                        .commit()
                }
            }
        })
        recycler_view_list_users.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )

        button_refresh.setOnClickListener {
            viewModel.loadData()
        }
    }
}