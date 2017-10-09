package org.hedgehog.testapp.rest.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import org.hedgehog.testapp.R
import org.hedgehog.testapp.rest.adapters.UsersRecyclerViewAdapter
import org.hedgehog.testapp.rest.utils.createRestUtilsObject
import org.hedgehog.testapp.rest.models.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


class CardsFragment : Fragment() {

    // Post card
    private lateinit var postTitle: TextView
    private lateinit var postBody: TextView
    private lateinit var postEditText: EditText
    private lateinit var postButton: Button

    // Comment card
    private lateinit var commentName: TextView
    private lateinit var commentEmail: TextView
    private lateinit var commentBody: TextView
    private lateinit var commentEditText: EditText
    private lateinit var commentButton: Button

    // Users card
    private lateinit var usersRecyclerView: RecyclerView

    // Photo card
    private lateinit var image: ImageView
    private lateinit var imageTitle: TextView

    // ToDos card
    private lateinit var todosTitle: TextView
    private lateinit var todosUser: TextView
    private lateinit var todosStatus: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_cards, container, false)

        // Post card
        postTitle = view?.findViewById(R.id.post_title)!!
        postBody = view.findViewById(R.id.post_body)!!
        postEditText = view.findViewById(R.id.post_edit_text)!!
        postButton = view.findViewById(R.id.post_button)!!

        postBody.visibility = View.GONE

        // Comment card
        commentName = view.findViewById(R.id.comment_name)!!
        commentEmail = view.findViewById(R.id.comment_email)!!
        commentBody = view.findViewById(R.id.comment_body)!!
        commentEditText = view.findViewById(R.id.comment_edit_text)!!
        commentButton = view.findViewById(R.id.comment_button)!!

        commentEmail.visibility = View.GONE
        commentBody.visibility = View.GONE

        // Users card
        usersRecyclerView = view.findViewById(R.id.users_recycler_view)

        // Photo card
        image = view.findViewById(R.id.image_view)
        imageTitle = view.findViewById(R.id.image_title)

        // ToDos card
        todosTitle = view.findViewById(R.id.todos_title)
        todosUser = view.findViewById(R.id.todos_user)
        todosStatus = view.findViewById(R.id.todos_status)

        postButton.setOnClickListener {
            if (postEditText.length() > 0) {
                getPost(postEditText.text.toString().toInt())
            } else {
                Toast.makeText(context, getString(R.string.enter_id), Toast.LENGTH_SHORT).show()
            }
        }

        commentButton.setOnClickListener {
            if (commentEditText.length() > 0) {
                getComment(commentEditText.text.toString().toInt())
            } else {
                Toast.makeText(context, getString(R.string.enter_id), Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fragment state
        if (post != null) {
            showPost(post!!)
        }

        if (comment != null) {
            showComment(comment!!)
        }

        if (users != null && users?.size == 5) {
            showUsers(users!!)
        } else {
            getUsers()
        }

        if (photo != null) {
            showPhoto(photo!!, image)
        } else {
            getPhoto(image)
        }

        if (todos != null) {
            showToDos(todos!!)
        } else {
            getToDos()
        }
    }

    private fun getPost(id: Int) {
        createRestUtilsObject().getPost(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Toast.makeText(context, getString(R.string.post_error), Toast.LENGTH_SHORT).show() }
                .subscribe({ success ->
                    post = success.body()
                    showPost(post!!)
                })
    }

    private fun showPost(postResponse: PostResponse) {
        postTitle.text = postResponse.title
        postBody.text = postResponse.body
        postEditText.setText(postResponse.id.toString(), TextView.BufferType.EDITABLE)
        postBody.visibility = View.VISIBLE
    }

    private fun getComment(id: Int) {
        createRestUtilsObject().getComment(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Toast.makeText(context, getString(R.string.comment_error), Toast.LENGTH_SHORT).show() }
                .subscribe({ success ->
                    comment = success.body()
                    showComment(comment!!)
                })
    }

    private fun showComment(commentResponse: CommentResponse) {
        commentName.text = commentResponse.name
        commentEmail.text = "От: " + commentResponse.email
        commentBody.text = commentResponse.body
        commentEditText.setText(commentResponse.id.toString(), TextView.BufferType.EDITABLE)
        commentEmail.visibility = View.VISIBLE
        commentBody.visibility = View.VISIBLE
    }

    private fun getUsers(): List<UsersResponse> {
        val u = mutableListOf<UsersResponse>()
        for (i in 1..5) {
            createRestUtilsObject().getUser(i)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError { Toast.makeText(context, getString(R.string.users_error), Toast.LENGTH_SHORT).show() }
                    .doOnCompleted {
                        if (fragmentManager != null) {
                            users = u
                            showUsers(users!!)
                        }
                    }
                    .subscribe({ success ->
                        u.add((success.body()!!))
                    })
        }
        return u
    }

    private fun showUsers(users: List<UsersResponse>) {
        val lm = LinearLayoutManager(context)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        usersRecyclerView.layoutManager = lm
        usersRecyclerView.adapter = UsersRecyclerViewAdapter(fragmentManager, users)
        usersRecyclerView.adapter.notifyDataSetChanged()
    }

    private fun getPhoto(imageView: ImageView) {
        createRestUtilsObject().getPhoto((1..500).random())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Toast.makeText(context, getString(R.string.photo_error), Toast.LENGTH_SHORT).show() }
                .subscribe({ success ->
                    photo = success.body()
                    showPhoto(photo!!, imageView)
                })
    }

    private fun showPhoto(photoResponse: PhotoResponse, imageView: ImageView) {
        Picasso.with(context).load(photoResponse.url).into(imageView)
        imageTitle.text = photoResponse.title
    }

    private fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start

    private fun getToDos() {
        createRestUtilsObject().getToDos((1..200).random())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Toast.makeText(context, getString(R.string.todos_warning), Toast.LENGTH_SHORT).show() }
                .subscribe({ success ->
                    todos = success.body()
                    getTaskAuthorName(todos?.userId!!)
                    showToDos(todos!!)
                })

    }

    private fun getTaskAuthorName(id: Int) {
        createRestUtilsObject().getUser(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted { showToDos(todos!!) }
                .doOnError { Toast.makeText(context, getString(R.string.name_warning), Toast.LENGTH_SHORT).show() }
                .subscribe({ name ->
                    taskAuthor = name.body()?.name
                })
    }

    private fun showToDos(toDosResponse: ToDosResponse) {
        todosTitle.text = toDosResponse.title
        if (toDosResponse.completed) {
            if (activity != null) {
                todosStatus.text = getString(R.string.task_is_completed)
            }
            todosStatus.setBackgroundColor(Color.GREEN)
        } else {
            if (activity != null) {
                todosStatus.text = getString(R.string.task_is_not_completed)
            }
            todosStatus.setBackgroundColor(Color.RED)
        }
        todosUser.text = taskAuthor
    }

    companion object {
        // Fragment state
        private var post: PostResponse? = null
        private var comment: CommentResponse? = null
        private var users: List<UsersResponse>? = null
        private var photo: PhotoResponse? = null
        private var todos: ToDosResponse? = null
        private var taskAuthor: String? = null
    }

}