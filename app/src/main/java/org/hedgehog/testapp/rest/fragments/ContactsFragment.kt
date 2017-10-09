package org.hedgehog.testapp.rest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.hedgehog.testapp.R

/**
 * Created by hedgehog on 08.10.17.
 */
class ContactsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_contacts, container, false)

}