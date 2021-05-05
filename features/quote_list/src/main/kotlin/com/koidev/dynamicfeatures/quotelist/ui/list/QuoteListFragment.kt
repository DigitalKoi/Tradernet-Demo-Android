package com.koidev.dynamicfeatures.quotelist.ui.list

import android.os.Bundle
import android.view.View
import com.koidev.commons.ui.base.BaseFragment
import com.koidev.commons.ui.extensions.observe
import com.koidev.core.domain.model.Quote
import com.koidev.dynamicfeatures.quotelist.R
import com.koidev.dynamicfeatures.quotelist.databinding.FragmentQuoteListBinding
import com.koidev.dynamicfeatures.quotelist.ui.list.adapter.QuoteListAdapter
import com.koidev.dynamicfeatures.quotelist.ui.list.adapter.QuoteListAdapterState
import com.koidev.dynamicfeatures.quotelist.ui.list.di.DaggerQuoteListComponent
import com.koidev.dynamicfeatures.quotelist.ui.list.di.QuoteListModule
import com.koidev.tradernetdemo.TradernetApp.Companion.coreComponent
import javax.inject.Inject

/**
 * View listing the trader quotes with option to display the detail view in future.
 *
 * @see BaseFragment
 */
class QuoteListFragment : BaseFragment<FragmentQuoteListBinding, QuoteListViewModel>(
    layoutId = R.layout.fragment_quote_list
) {

    @Inject
    lateinit var viewAdapter: QuoteListAdapter

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.state, ::onViewStateChange)
        observe(viewModel.data, ::onViewDataChange)
        observe(viewModel.event, ::onViewEvent)
    }

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerQuoteListComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .quoteListModule(QuoteListModule(this))
            .build()
            .inject(this)
    }

    /**
     * Initialize view data binding variables.
     */
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.includeList.quoteList.apply {
            adapter = viewAdapter
            itemAnimator = null
        }
    }

    // ============================================================================================
    //  Private observers methods
    // ============================================================================================

    /**
     * Observer view state change on [QuoteListViewModel].
     *
     * @param viewState State of characters list.
     */
    private fun onViewStateChange(viewState: QuoteListViewState) {
        when (viewState) {
            is QuoteListViewState.Loaded ->
                viewAdapter.submitState(QuoteListAdapterState.Added)
            is QuoteListViewState.AddError ->
                viewAdapter.submitState(QuoteListAdapterState.AddError)
        }
    }
    /**
     * Observer view data change on [QuoteListViewModel].
     *
     * @param viewData Paged list of characters.
     */
    private fun onViewDataChange(viewData: List<Quote>) {
        viewAdapter.submitList(viewData)
    }

    /**
     * Observer view event change on [QuoteListViewModel].
     *
     * @param viewEvent Event on Quote list.
     */
    private fun onViewEvent(viewEvent: QuoteListViewEvent) {
        when (viewEvent) {
            is QuoteListViewEvent.OpenQuoteDetail -> { /* todo show details */ }
        }
    }
}
