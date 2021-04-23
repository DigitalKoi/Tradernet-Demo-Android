package com.koidev.dynamicfeatures.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.koidev.dynamicfeatures.home.R

//val NAV_FRAGMENTS_ID = setOf(
    // TODO: add here fragment ids like
    //  R.id.list_fragment, R.id.favorites_fragment
//)

/**
 * View model responsible for preparing and managing the data for [HomeFragment].
 *
 * @see ViewModel
 */
class HomeViewModel : ViewModel() {

    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState>
        get() = _state

    /**
     * Navigation controller add destination changed listener.
     *
     * @param navController Navigation manager.
     */
    fun navigationControllerChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if (NAV_FRAGMENTS_ID.contains(destination.id)) {
//                _state.postValue(HomeViewState.NavigationScreen)
//            } else {
//                _state.postValue(HomeViewState.FullScreen)
//            }
        }
    }
}
